package com.android.kopidannyemil.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuDao {
    @Query("SELECT * FROM menu_items")
    fun getAllMenuItems(): Flow<List<MenuItemEntity>>

    @Query("SELECT * FROM menu_items WHERE id = :id")
    suspend fun getMenuItemById(id: Int): MenuItemEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<MenuItemEntity>)

    @Query("UPDATE menu_items SET stock = stock - 1 WHERE id = :id AND stock > 0")
    suspend fun decreaseStock(id: Int)
    
    @Query("SELECT COUNT(*) FROM menu_items")
    suspend fun getCount(): Int

    // Cart operations
    @Query("SELECT * FROM cart_items")
    fun getCartItems(): Flow<List<CartItemEntity>>
    
    @Query("SELECT * FROM cart_items WHERE menuItemId = :menuItemId")
    suspend fun getCartItemByMenuId(menuItemId: Int): CartItemEntity?
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(item: CartItemEntity)
    
    @Update
    suspend fun updateCartItem(item: CartItemEntity)
    
    @Delete
    suspend fun deleteCartItem(item: CartItemEntity)
    
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
    
    // Transactions
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)
    
    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>
    
    @Transaction
    suspend fun checkout(cartItems: List<CartItemEntity>, totalPrice: Long, itemsSummary: String) {
        // Decrease stock for each item
        for (item in cartItems) {
            val menuItem = getMenuItemById(item.menuItemId)
            if (menuItem != null && menuItem.stock >= item.quantity) {
                // Update stock directly using raw query or update method
                decreaseStockByQuantity(item.menuItemId, item.quantity)
            } else {
                throw Exception("Stok tidak mencukupi untuk ${item.name}")
            }
        }
        
        // Save transaction record
        val transaction = TransactionEntity(
            timestamp = System.currentTimeMillis(),
            totalPrice = totalPrice,
            itemsSummary = itemsSummary
        )
        insertTransaction(transaction)
        
        // Clear cart
        clearCart()
    }
    
    @Query("UPDATE menu_items SET stock = stock - :quantity WHERE id = :id AND stock >= :quantity")
    suspend fun decreaseStockByQuantity(id: Int, quantity: Int)
}
