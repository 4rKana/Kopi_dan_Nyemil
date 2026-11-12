package com.android.kopidannyemil

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.kopidannyemil.ui.theme.KopiDanNyemilTheme
import com.android.kopidannyemil.ui.MenuViewModel
import com.android.kopidannyemil.data.MenuItemEntity

// Colors
val PrimaryBlack = Color(0xFF1C1C1E)
val PeachAccent = Color(0xFFF5CBA7)
val CreamAccent = Color(0xFFFDEBD0)
val GreenConfirm = Color(0xFFA9DFBF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KopiDanNyemilTheme {
                KopiApp()
            }
        }
    }
}

@Composable
fun KopiApp(menuViewModel: MenuViewModel = viewModel()) {
    val navController = rememberNavController()
    var userName by rememberSaveable { mutableStateOf("") }
    
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    
    val showBottomNav = currentRoute in listOf("home", "profile", "cart")
    
    Scaffold(
        bottomBar = {
            if (showBottomNav) {
                BottomNavBar(navController = navController, currentRoute = currentRoute)
            }
        },
        containerColor = Color.White
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color.White)
        ) {
            composable("splash") {
                SplashScreen(onNavigateToSignUp = { navController.navigate("signup_landing") })
            }
            composable("signup_landing") {
                SignUpLandingScreen(
                    onNavigateToLogin = { navController.navigate("login") },
                    onNavigateToRegister = { navController.navigate("register") }
                )
            }
            composable("login") {
                LoginScreen(
                    onLoginSuccess = { name -> 
                        userName = name
                        navController.navigate("home") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }
            composable("register") {
                RegisterScreen(
                    onRegisterSuccess = { name ->
                        userName = name
                        navController.navigate("home") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }
            composable("home") {
                HomeScreen(
                    userName = userName,
                    menuViewModel = menuViewModel,
                    onAddToCart = { menuItem ->
                        menuViewModel.addToCart(menuItem)
                    }
                )
            }
            composable("profile") {
                ProfileScreen(
                    userName = userName,
                    onNameChanged = { userName = it }
                )
            }
            composable("cart") {
                CartScreen(
                    userName = userName,
                    menuViewModel = menuViewModel,
                    onNavigateToConfirm = { navController.navigate("order_confirmation") }
                )
            }
            composable("order_confirmation") {
                OrderConfirmationScreen(
                    userName = userName,
                    onNavigateToHome = {
                        menuViewModel.resetCheckoutStatus()
                        navController.navigate("home") {
                            popUpTo("home") { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController, currentRoute: String?) {
    NavigationBar(
        containerColor = Color.White,
        contentColor = PrimaryBlack
    ) {
        NavigationBarItem(
            selected = currentRoute == "home",
            onClick = {
                if (currentRoute != "home") {
                    navController.navigate("home") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = PrimaryBlack,
                indicatorColor = PrimaryBlack,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = currentRoute == "cart",
            onClick = {
                if (currentRoute != "cart") {
                    navController.navigate("cart") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Keranjang") },
            label = { Text("Keranjang") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = PrimaryBlack,
                indicatorColor = PrimaryBlack,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
        NavigationBarItem(
            selected = currentRoute == "profile",
            onClick = {
                if (currentRoute != "profile") {
                    navController.navigate("profile") {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profile") },
            label = { Text("Profile") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = Color.White,
                selectedTextColor = PrimaryBlack,
                indicatorColor = PrimaryBlack,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray
            )
        )
    }
}

// =================== SCREENS ===================

@Composable
fun SplashScreen(onNavigateToSignUp: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))
        
        Box(
            modifier = Modifier
                .size(120.dp)
                .background(PeachAccent, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("Logo", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = PrimaryBlack)
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text("Kopi dan Nyemil", fontWeight = FontWeight.ExtraBold, fontSize = 32.sp, color = PrimaryBlack)
        Text("Get a taste of your wish", fontSize = 16.sp, color = Color.Gray)
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onNavigateToSignUp,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlack),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Start now", fontSize = 18.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun SignUpLandingScreen(onNavigateToLogin: () -> Unit, onNavigateToRegister: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.3f))
        
        Text("Sign up", fontWeight = FontWeight.Bold, fontSize = 32.sp, color = PrimaryBlack)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Join as a member & experience more fun from various meal at mesen_gube marketing app",
            textAlign = TextAlign.Center,
            color = Color.Gray,
            fontSize = 16.sp
        )
        
        Spacer(modifier = Modifier.weight(0.7f))
        
        Button(
            onClick = onNavigateToRegister,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlack),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Register", fontSize = 18.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedButton(
            onClick = onNavigateToLogin,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Login", fontSize = 18.sp, color = PrimaryBlack)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun LoginScreen(onLoginSuccess: (String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text("Login User", fontWeight = FontWeight.Bold, fontSize = 32.sp, color = PrimaryBlack)
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = { if (username.isNotBlank()) onLoginSuccess(username) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlack),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Login", fontSize = 18.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun RegisterScreen(onRegisterSuccess: (String) -> Unit) {
    var fullName by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text("Register", fontWeight = FontWeight.Bold, fontSize = 32.sp, color = PrimaryBlack)
        Spacer(modifier = Modifier.height(32.dp))
        
        OutlinedTextField(
            value = fullName,
            onValueChange = { fullName = it },
            label = { Text("Nama Lengkap") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = { if (fullName.isNotBlank()) onRegisterSuccess(fullName) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlack),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Register", fontSize = 18.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun HomeScreen(userName: String, menuViewModel: MenuViewModel, onAddToCart: (MenuItemEntity) -> Unit) {
    val menuItems by menuViewModel.menuItems.collectAsState()
    
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text("Halo $userName,", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = PrimaryBlack)
        Text("Makanan", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp, bottom = 24.dp))
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            items(menuItems) { item ->
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = CreamAccent),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            painter = painterResource(id = item.imageResId),
                            contentDescription = item.name,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(80.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                        
                        Spacer(modifier = Modifier.width(16.dp))
                        
                        Column(modifier = Modifier.weight(1f)) {
                            Text(item.name, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = PrimaryBlack)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• ${item.description}", fontSize = 14.sp, color = Color.DarkGray)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• ${item.price}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = PrimaryBlack)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text("• Stok: ${item.stock}", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = if (item.stock > 0) Color(0xFF27AE60) else Color.Red)
                        }
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Button(
                            onClick = { onAddToCart(item) },
                            enabled = item.stock > 0,
                            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlack),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                            modifier = Modifier.height(36.dp)
                        ) {
                            Text("+ Keranjang", fontSize = 12.sp, color = Color.White)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ProfileScreen(userName: String, onNameChanged: (String) -> Unit) {
    var editName by remember { mutableStateOf(userName) }
    
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text("Halo $userName,", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = PrimaryBlack)
        Text("Profile Saya", fontSize = 18.sp, color = Color.Gray, modifier = Modifier.padding(top = 4.dp))
        
        Spacer(modifier = Modifier.height(32.dp))
        Text("Profile Setting", fontWeight = FontWeight.SemiBold, fontSize = 18.sp, color = PrimaryBlack)
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = editName,
            onValueChange = { editName = it },
            label = { Text("Nama") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = { onNameChanged(editName) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlack),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Save", fontSize = 18.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun CartScreen(userName: String, menuViewModel: MenuViewModel, onNavigateToConfirm: () -> Unit) {
    val cartItems by menuViewModel.cartItems.collectAsState()
    val cartTotal by menuViewModel.cartTotal.collectAsState()
    val checkoutSuccess by menuViewModel.checkoutSuccess
    val checkoutError by menuViewModel.checkoutError
    
    LaunchedEffect(checkoutSuccess) {
        if (checkoutSuccess) {
            onNavigateToConfirm()
        }
    }
    
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text("Keranjang", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = PrimaryBlack)
        Spacer(modifier = Modifier.height(24.dp))
        
        if (cartItems.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Keranjang masih kosong", color = Color.Gray, fontSize = 16.sp)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(cartItems) { item ->
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = CreamAccent),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(item.name, fontWeight = FontWeight.Bold, fontSize = 16.sp, color = PrimaryBlack)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("Rp ${item.price}", fontSize = 14.sp, color = PrimaryBlack)
                            }
                            
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                IconButton(
                                    onClick = { menuViewModel.updateCartItemQuantity(item, false) },
                                    modifier = Modifier.size(32.dp).background(Color.White, CircleShape)
                                ) {
                                    Icon(
                                        if (item.quantity > 1) Icons.Default.Clear else Icons.Default.Delete,
                                        contentDescription = "Kurangi",
                                        modifier = Modifier.size(16.dp)
                                    )
                                }
                                
                                Text(
                                    "${item.quantity}",
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp),
                                    fontSize = 16.sp
                                )
                                
                                IconButton(
                                    onClick = { menuViewModel.updateCartItemQuantity(item, true) },
                                    modifier = Modifier.size(32.dp).background(PrimaryBlack, CircleShape)
                                ) {
                                    Icon(Icons.Default.Add, contentDescription = "Tambah", tint = Color.White, modifier = Modifier.size(16.dp))
                                }
                            }
                        }
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            checkoutError?.let {
                Text(it, color = Color.Red, fontSize = 14.sp, modifier = Modifier.padding(bottom = 8.dp))
            }
            
            Row(
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Total:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text("Rp $cartTotal", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp, color = PrimaryBlack)
            }
            
            Button(
                onClick = { menuViewModel.checkout() },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlack),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Checkout", fontSize = 18.sp, color = Color.White)
            }
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun OrderConfirmationScreen(userName: String, onNavigateToHome: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Row(modifier = Modifier.fillMaxWidth()) {
            Text("Halo $userName,", fontWeight = FontWeight.Bold, fontSize = 24.sp, color = PrimaryBlack)
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        Box(
            modifier = Modifier.size(120.dp).background(GreenConfirm, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("OK", fontWeight = FontWeight.Bold, fontSize = 40.sp, color = PrimaryBlack)
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            "Terima kasih sudah memesan\ntunggu aja sebentar kurir kami\nsegera mengantarkan pesananmu!",
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            color = PrimaryBlack,
            lineHeight = 28.sp
        )
        
        Spacer(modifier = Modifier.weight(1f))
        
        Button(
            onClick = onNavigateToHome,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryBlack),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text("Oke", fontSize = 18.sp, color = Color.White)
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}
