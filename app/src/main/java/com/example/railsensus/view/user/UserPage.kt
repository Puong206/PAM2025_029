package com.example.railsensus.view.user

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.railsensus.modeldata.UserManagement
import com.example.railsensus.ui.component.RailSensusTheme
import com.example.railsensus.view.RailSensusBottomNavigation
import com.example.railsensus.viewmodel.LoginViewModel
import com.example.railsensus.viewmodel.UserViewModel
import com.example.railsensus.viewmodel.provider.RailSensusViewModel

@Composable
fun UserPage(
    modifier: Modifier = Modifier,
    onBottomNavClick: (Int) -> Unit = {},
    onLogout: () -> Unit = {},
    loginViewModel: LoginViewModel = viewModel(factory = RailSensusViewModel.Factory),
    userViewModel: UserViewModel = viewModel(factory = RailSensusViewModel.Factory)
) {
    val currentUser by loginViewModel.currentUser.collectAsState()
    
    when (currentUser?.role) {
        "Admin" -> AdminUserManagementView(
            modifier = modifier,
            onBottomNavClick = onBottomNavClick,
            onLogout = onLogout,
            loginViewModel = loginViewModel,
            userViewModel = userViewModel
        )
        else -> RegularUserProfileView(
            modifier = modifier,
            onBottomNavClick = onBottomNavClick,
            onLogout = onLogout,
            loginViewModel = loginViewModel
        )
    }
}

// Regular User Profile View
@Composable
fun RegularUserProfileView(
    modifier: Modifier = Modifier,
    onBottomNavClick: (Int) -> Unit,
    onLogout: () -> Unit,
    loginViewModel: LoginViewModel
) {
    var showLogoutDialog by remember { mutableStateOf(false) }
    val currentUser by loginViewModel.currentUser.collectAsState()
    
    Scaffold(
        bottomBar = {
            RailSensusBottomNavigation(
                selectedIndex = 3,
                onItemClick = onBottomNavClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FB))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(RailSensusTheme.blueColor)
                    .padding(20.dp)
                    .padding(top = 20.dp)
            ) {
                Text(
                    text = "Profil Saya",
                    style = TextStyle(
                        fontSize = 22.sp,
                        color = Color.White,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Profile Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Avatar
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                            .background(RailSensusTheme.blueColor.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = RailSensusTheme.blueColor,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                    
                    // Username
                    Text(
                        text = currentUser?.username ?: "User",
                        style = TextStyle(
                            fontSize = 24.sp,
                            color = RailSensusTheme.blueColor,
                            fontFamily = RailSensusTheme.orangeFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    
                    // Email
                    Text(
                        text = currentUser?.email ?: "email@example.com",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = RailSensusTheme.lightGrayColor,
                            fontFamily = RailSensusTheme.blueFontFamily
                        )
                    )
                    
                    // Role Badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = RailSensusTheme.blueColor.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = currentUser?.role ?: "User",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                    
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = RailSensusTheme.lightGrayColor.copy(alpha = 0.3f)
                    )
                    
                    // Joined Date
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = RailSensusTheme.lightGrayColor,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Bergabung: ${currentUser?.created_at?.take(10) ?: "N/A"}",
                            style = TextStyle(
                                fontSize = 13.sp,
                                color = RailSensusTheme.lightGrayColor,
                                fontFamily = RailSensusTheme.blueFontFamily
                            )
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(20.dp))
            
            // Logout Button
            Button(
                onClick = { showLogoutDialog = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RailSensusTheme.orangeColor
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Keluar",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = RailSensusTheme.blueFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
    
    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null,
                    tint = RailSensusTheme.orangeColor,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text(
                    text = "Keluar dari Akun",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text(
                    text = "Apakah Anda yakin ingin keluar dari akun Anda?",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.blueFontFamily
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        loginViewModel.logout()
                        showLogoutDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RailSensusTheme.orangeColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Keluar",
                        style = TextStyle(
                            fontFamily = RailSensusTheme.blueFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showLogoutDialog = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = RailSensusTheme.blueColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Batal",
                        style = TextStyle(
                            fontFamily = RailSensusTheme.blueFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        )
    }
}

// Admin User Management View
@Composable
fun AdminUserManagementView(
    modifier: Modifier = Modifier,
    onBottomNavClick: (Int) -> Unit,
    onLogout: () -> Unit,
    loginViewModel: LoginViewModel,
    userViewModel: UserViewModel
) {
    var searchQuery by remember { mutableStateOf("") }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showLogoutDialog by remember { mutableStateOf(false) }
    var userToDelete by remember { mutableStateOf<UserManagement?>(null) }
    
    val userList by userViewModel.userList.collectAsState()
    val isLoading by userViewModel.isLoading.collectAsState()
    val currentToken by loginViewModel.currentToken.collectAsState()
    val currentUser by loginViewModel.currentUser.collectAsState()
    
    // Load users on first render
    LaunchedEffect(currentToken) {
        currentToken?.let { token ->
            userViewModel.loadAllUsers(token)
        }
    }
    
    // Filter list by search query
    val filteredList = remember(userList, searchQuery) {
        if (searchQuery.isBlank()) {
            userList
        } else {
            userList.filter { user ->
                user.username.contains(searchQuery, ignoreCase = true) ||
                user.email.contains(searchQuery, ignoreCase = true) ||
                user.role.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    
    Scaffold(
        topBar = {
            AdminUserHeader(
                searchQuery = searchQuery,
                onSearchChange = { searchQuery = it },
                onLogoutClick = { showLogoutDialog = true }
            )
        },
        bottomBar = {
            RailSensusBottomNavigation(
                selectedIndex = 3,
                onItemClick = onBottomNavClick
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FB))
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            // Loading state
            if (isLoading) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = RailSensusTheme.blueColor)
                    }
                }
            }
            
            // Empty state
            if (!isLoading && filteredList.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(32.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = null,
                            tint = RailSensusTheme.lightGrayColor,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = if (searchQuery.isBlank()) {
                                "Belum ada user terdaftar"
                            } else {
                                "Tidak ada hasil untuk \"$searchQuery\""
                            },
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = RailSensusTheme.lightGrayColor,
                                fontFamily = RailSensusTheme.blueFontFamily
                            )
                        )
                    }
                }
            }
            
            // User list
            items(filteredList) { user ->
                UserCard(
                    user = user,
                    currentUserId = currentUser?.id,
                    isAdmin = true,
                    onClick = { },
                    onDelete = {
                        userToDelete = user
                        showDeleteDialog = true
                    }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
    
    // Delete confirmation dialog
    if (showDeleteDialog && userToDelete != null) {
        AlertDialog(
            onDismissRequest = { 
                showDeleteDialog = false
                userToDelete = null
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = null,
                    tint = RailSensusTheme.orangeColor,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text(
                    text = "Hapus User",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text(
                    text = "Apakah Anda yakin ingin menghapus user \"${userToDelete?.username}\"? " +
                            "Data yang sudah dihapus tidak dapat dikembalikan.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.blueFontFamily
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        currentToken?.let { token ->
                            userToDelete?.let { user ->
                                userViewModel.deleteUser(token, user.user_id)
                            }
                        }
                        showDeleteDialog = false
                        userToDelete = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RailSensusTheme.orangeColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Hapus",
                        style = TextStyle(
                            fontFamily = RailSensusTheme.blueFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { 
                        showDeleteDialog = false
                        userToDelete = null
                    },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = RailSensusTheme.blueColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Batal",
                        style = TextStyle(
                            fontFamily = RailSensusTheme.blueFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        )
    }
    
    // Logout Confirmation Dialog
    if (showLogoutDialog) {
        AlertDialog(
            onDismissRequest = { showLogoutDialog = false },
            icon = {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = null,
                    tint = RailSensusTheme.orangeColor,
                    modifier = Modifier.size(48.dp)
                )
            },
            title = {
                Text(
                    text = "Keluar dari Akun",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
            },
            text = {
                Text(
                    text = "Apakah Anda yakin ingin keluar dari akun Anda?",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.blueFontFamily
                    ))
                }
            ,
            confirmButton = {
                Button(
                    onClick = {
                        loginViewModel.logout()
                        showLogoutDialog = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = RailSensusTheme.orangeColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Keluar",
                        style = TextStyle(
                            fontFamily = RailSensusTheme.blueFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showLogoutDialog = false },
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = RailSensusTheme.blueColor
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = "Batal",
                        style = TextStyle(
                            fontFamily = RailSensusTheme.blueFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        )
    }
}

@Composable
fun AdminUserHeader(
    searchQuery: String,
    onSearchChange: (String) -> Unit,
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(RailSensusTheme.blueColor)
            .padding(20.dp)
            .padding(top = 20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Manajemen User",
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.White,
                    fontFamily = RailSensusTheme.orangeFontFamily,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = onLogoutClick) {
                Icon(
                    imageVector = Icons.Default.Logout,
                    contentDescription = "Logout",
                    tint = Color.White
                )
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Cari username, email, atau role...",
                    style = TextStyle(
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Color.White.copy(alpha = 0.7f)
                )
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { onSearchChange("") }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = "Clear",
                            tint = Color.White.copy(alpha = 0.7f)
                        )
                    }
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            singleLine = true
        )
    }
}

@Composable
fun UserCard(
    user: UserManagement,
    currentUserId: Int?,
    isAdmin: Boolean,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = if (user.role == "Admin") 
                            RailSensusTheme.orangeColor.copy(alpha = 0.2f)
                        else 
                            RailSensusTheme.blueColor.copy(alpha = 0.2f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (user.role == "Admin") Icons.Default.AdminPanelSettings else Icons.Default.Person,
                    contentDescription = null,
                    tint = if (user.role == "Admin") RailSensusTheme.orangeColor else RailSensusTheme.blueColor,
                    modifier = Modifier.size(28.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // User info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = user.username,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = RailSensusTheme.blueColor,
                            fontFamily = RailSensusTheme.orangeFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    if (user.user_id == currentUserId) {
                        Surface(
                            shape = RoundedCornerShape(4.dp),
                            color = RailSensusTheme.blueColor.copy(alpha = 0.1f)
                        ) {
                            Text(
                                text = "You",
                                style = TextStyle(
                                    fontSize = 10.sp,
                                    color = RailSensusTheme.blueColor,
                                    fontFamily = RailSensusTheme.blueFontFamily,
                                    fontWeight = FontWeight.SemiBold
                                ),
                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                            )
                        }
                    }
                }
                
                Text(
                    text = user.email,
                    style = TextStyle(
                        fontSize = 13.sp,
                        color = RailSensusTheme.lightGrayColor,
                        fontFamily = RailSensusTheme.blueFontFamily
                    )
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = if (user.role == "Admin") 
                            RailSensusTheme.orangeColor.copy(alpha = 0.2f)
                        else 
                            RailSensusTheme.blueColor.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = user.role,
                            style = TextStyle(
                                fontSize = 11.sp,
                                color = if (user.role == "Admin") RailSensusTheme.orangeColor else RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    
                    Text(
                        text = "• ${user.created_at.take(10)}",
                        style = TextStyle(
                            fontSize = 11.sp,
                            color = RailSensusTheme.lightGrayColor,
                            fontFamily = RailSensusTheme.blueFontFamily
                        )
                    )
                }
            }
            
            // Delete button (only for admins, can't delete self)
            if (isAdmin && user.user_id != currentUserId) {
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = RailSensusTheme.orangeColor
                    )
                }
            }
        }
    }
}
