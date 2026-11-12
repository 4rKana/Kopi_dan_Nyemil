package com.android.kopidannyemil.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.mutableStateOf
import com.android.kopidannyemil.R
import com.android.kopidannyemil.data.AppDatabase
import com.android.kopidannyemil.data.MenuItemEntity
import com.android.kopidannyemil.data.CartItemEntity
import com.android.kopidannyemil.data.TransactionEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MenuViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).menuDao()

    val menuItems: StateFlow<List<MenuItemEntity>> = dao.getAllMenuItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val cartItems: StateFlow<List<CartItemEntity>> = dao.getCartItems()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
        
    val cartTotal: StateFlow<Long> = dao.getCartItems()
        .map { items -> items.sumOf { it.price * it.quantity } }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0L
        )

    val checkoutError = mutableStateOf<String?>(null)
    val checkoutSuccess = mutableStateOf(false)

    init {
        initializeDatabase()
    }

    private fun initializeDatabase() {
        viewModelScope.launch {
            if (dao.getCount() == 0) {
                val initialItems = listOf(
                    MenuItemEntity(name = "Kopi Susu Gula Aren", description = "Espresso, susu, gula aren", price = "Rp 22.000", imageResId = R.drawable.kopi_susu_gula_aren, stock = 50),
                    MenuItemEntity(name = "Donat Cokelat", description = "Donat empuk, glaze cokelat", price = "Rp 12.000", imageResId = R.drawable.donat_cokelat, stock = 30),
                    MenuItemEntity(name = "Sourdough Loaf", description = "Roti fermentasi alami", price = "Rp 35.000", imageResId = R.drawable.sourdough_loaf, stock = 20),
                    MenuItemEntity(name = "Cappuccino", description = "Espresso, foam susu lembut", price = "Rp 25.000", imageResId = R.drawable.cappuccino, stock = 40),
                    MenuItemEntity(name = "Matcha Latte", description = "Matcha Jepang, susu segar", price = "Rp 27.000", imageResId = R.drawable.matcha_latte, stock = 35)
                )
                dao.insertAll(initialItems)
            }
        }
    }

    fun buyItem(id: Int) {
        // Obsolete function, replaced by addToCart
        viewModelScope.launch {
            dao.decreaseStock(id)
        }
    }
    
    fun addToCart(menuItem: MenuItemEntity) {
        viewModelScope.launch {
            val existingItem = dao.getCartItemByMenuId(menuItem.id)
            if (existingItem != null) {
                // Check if adding one more exceeds stock
                if (existingItem.quantity < menuItem.stock) {
                    dao.updateCartItem(existingItem.copy(quantity = existingItem.quantity + 1))
                }
            } else {
                if (menuItem.stock > 0) {
                    // Extract price as Long (removing "Rp " and ".")
                    val priceLong = menuItem.price.replace("[^\\d]".toRegex(), "").toLongOrNull() ?: 0L
                    dao.insertCartItem(
                        CartItemEntity(
                            menuItemId = menuItem.id,
                            name = menuItem.name,
                            price = priceLong,
                            quantity = 1
                        )
                    )
                }
            }
        }
    }
    
    fun updateCartItemQuantity(cartItem: CartItemEntity, isIncrease: Boolean) {
        viewModelScope.launch {
            if (isIncrease) {
                val menuItem = dao.getMenuItemById(cartItem.menuItemId)
                if (menuItem != null && cartItem.quantity < menuItem.stock) {
                    dao.updateCartItem(cartItem.copy(quantity = cartItem.quantity + 1))
                }
            } else {
                if (cartItem.quantity > 1) {
                    dao.updateCartItem(cartItem.copy(quantity = cartItem.quantity - 1))
                } else {
                    dao.deleteCartItem(cartItem)
                }
            }
        }
    }
    
    fun checkout() {
        viewModelScope.launch {
            try {
                checkoutError.value = null
                checkoutSuccess.value = false
                
                val currentCart = cartItems.value
                if (currentCart.isEmpty()) return@launch
                
                val total = cartTotal.value
                val summary = currentCart.joinToString(", ") { "${it.name} (${it.quantity}x)" }
                
                dao.checkout(currentCart, total, summary)
                checkoutSuccess.value = true
            } catch (e: Exception) {
                checkoutError.value = e.message ?: "Terjadi kesalahan saat checkout"
            }
        }
    }
    
    fun resetCheckoutStatus() {
        checkoutError.value = null
        checkoutSuccess.value = false
    }
}
