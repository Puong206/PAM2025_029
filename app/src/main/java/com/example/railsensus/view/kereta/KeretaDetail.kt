package com.example.railsensus.view.kereta

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.railsensus.ui.component.RailSensusTheme
import com.example.railsensus.viewmodel.KeretaViewModel
import com.example.railsensus.viewmodel.LoginViewModel
import com.example.railsensus.viewmodel.provider.RailSensusViewModel

@Composable
fun KeretaDetailPage(
    kaId: Int,
    onBackClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
    keretaViewModel: KeretaViewModel = viewModel(factory = RailSensusViewModel.Factory),
    loginViewModel: LoginViewModel = viewModel(factory = RailSensusViewModel.Factory)
) {
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    val selectedKereta by keretaViewModel.selectedKereta.collectAsState()
    val isLoading by keretaViewModel.isLoading.collectAsState()
    val errorMessage by keretaViewModel.errorMessage.collectAsState()
    val currentToken by loginViewModel.currentToken.collectAsState()
    val currentUser by loginViewModel.currentUser.collectAsState()
    
    // Only admin can edit/delete
    val isAdmin = currentUser?.role == "Admin"
    
    LaunchedEffect(kaId) {
        keretaViewModel.loadKeretaById(kaId)
    }
    
    Scaffold(
        bottomBar = {
            if (isAdmin) {
                KeretaDetailBottomBar(
                    onEditClick = { showEditDialog = true },
                    onDeleteClick = { showDeleteDialog = true }
                )
            }
        }
    ) { paddingValues ->
        when {
            isLoading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = RailSensusTheme.blueColor)
                }
            }
            selectedKereta == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ErrorOutline,
                            contentDescription = null,
                            tint = RailSensusTheme.lightGrayColor,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = errorMessage ?: "Data kereta tidak ditemukan",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = RailSensusTheme.lightGrayColor,
                                fontFamily = RailSensusTheme.blueFontFamily
                            )
                        )
                        TextButton(onClick = onBackClick) {
                            Text("Kembali")
                        }
                    }
                }
            }
            else -> {
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
                        IconButton(
                            onClick = onBackClick,
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(40.dp)
                                .background(
                                    color = Color.White.copy(alpha = 0.2f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                tint = Color.White
                            )
                        }
                        
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 60.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = selectedKereta?.nama_ka ?: "",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    color = Color.White,
                                    fontFamily = RailSensusTheme.orangeFontFamily,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = RailSensusTheme.orangeColor
                            ) {
                                Text(
                                    text = selectedKereta?.nomor_ka ?: "",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = Color.White,
                                        fontFamily = RailSensusTheme.blueFontFamily,
                                        fontWeight = FontWeight.SemiBold
                                    ),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }
                    
                    // Content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Info Cards
                        InfoCard(
                            title = "Nomor Kereta",
                            value = selectedKereta?.nomor_ka ?: "-",
                            icon = Icons.Default.Tag
                        )
                        
                        InfoCard(
                            title = "Nama Kereta",
                            value = selectedKereta?.nama_ka ?: "-",
                            icon = Icons.Default.DirectionsRailway
                        )
                        
                        InfoCard(
                            title = "ID Kereta",
                            value = selectedKereta?.ka_id?.toString() ?: "-",
                            icon = Icons.Default.Info
                        )
                    }
                }
            }
        }
    }
    
    // Edit Dialog
    if (showEditDialog) {
        EditKeretaDialog(
            onDismiss = { showEditDialog = false },
            onSave = { detail ->
                // TODO: Call updateKereta in KeretaViewModel
                // currentToken?.let { token ->
                //     keretaViewModel.updateKereta(token, kaId)
                // }
                showEditDialog = false
            },
            initialKereta = selectedKereta
        )
    }
    
    // Delete Confirmation Dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
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
                    text = "Hapus Kereta",
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
                    text = "Apakah Anda yakin ingin menghapus kereta \"${selectedKereta?.nama_ka}\"? " +
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
                            keretaViewModel.deleteKereta(token, kaId)
                        }
                        showDeleteDialog = false
                        onDeleteClick()
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
                    onClick = { showDeleteDialog = false },
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
fun InfoCard(
    title: String,
    value: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = RailSensusTheme.blueColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = RailSensusTheme.blueColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = RailSensusTheme.lightGrayColor,
                        fontFamily = RailSensusTheme.blueFontFamily
                    )
                )
                Text(
                    text = value,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.blueFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
        }
    }
}

@Composable
fun KeretaDetailBottomBar(
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onEditClick,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = RailSensusTheme.blueColor
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    width = 1.5.dp
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Edit",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = RailSensusTheme.blueFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
            
            Button(
                onClick = onDeleteClick,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RailSensusTheme.orangeColor,
                    contentColor = Color.White
                )
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "Hapus",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = RailSensusTheme.blueFontFamily,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        }
    }
}
