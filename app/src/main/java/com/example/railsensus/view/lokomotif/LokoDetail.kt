package com.example.railsensus.view.lokomotif

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.railsensus.ui.component.RailSensusTheme
import com.example.railsensus.viewmodel.LokoViewModel
import com.example.railsensus.viewmodel.provider.RailSensusViewModel

@Composable
fun LokoDetailPage(
    lokoId: Int,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onTambahFotoClick: () -> Unit = {},
    onDeleteClick: () -> Unit = {},
    lokoViewModel: LokoViewModel = viewModel(factory = RailSensusViewModel.Factory)
) {
    val selectedLoko by lokoViewModel.selectedLoko.collectAsState()
    val isLoading by lokoViewModel.isLoading.collectAsState()
    val errorMessage by lokoViewModel.errorMessage.collectAsState()
    
    LaunchedEffect(lokoId) {
        lokoViewModel.loadLokoById(lokoId)
    }
    var showEditDialog by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    
    Scaffold(
        bottomBar = {
            LokoDetailBottomBar(
                onEditClick = { showEditDialog = true },
                onTambahFotoClick = onTambahFotoClick
            )
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
            selectedLoko == null -> {
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
                            text = errorMessage ?: "Data lokomotif tidak ditemukan",
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
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(RailSensusTheme.blueColor)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Train,
                            contentDescription = null,
                            tint = Color.White.copy(alpha = 0.1f),
                            modifier = Modifier
                                .size(120.dp)
                                .align(Alignment.Center)
                        )
                    }
                    
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.5f)
                                    )
                                )
                            )
                    )
                }
                
                // Back Button
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(16.dp)
                        .size(40.dp)
                        .background(
                            color = Color.White.copy(alpha = 0.4f),
                            shape = RoundedCornerShape(50.dp)
                        )
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = RailSensusTheme.whiteColor
                    )
                }
                
                // Menu Button
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    IconButton(
                        onClick = { showMenu = true },
                        modifier = Modifier
                            .size(40.dp)
                            .background(
                                color = Color.White.copy(alpha = 0.4f),
                                shape = RoundedCornerShape(50.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menu",
                            tint = RailSensusTheme.whiteColor
                        )
                    }
                    
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null,
                                        tint = RailSensusTheme.orangeColor
                                    )
                                    Text(
                                        text = "Hapus",
                                        style = TextStyle(
                                            color = RailSensusTheme.orangeColor,
                                            fontFamily = RailSensusTheme.blueFontFamily
                                        )
                                    )
                                }
                            },
                            onClick = {
                                showMenu = false
                                showDeleteDialog = true
                            }
                        )
                    }
                }
                
                // Lokomotif Type Badge and Number
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Badge
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = RailSensusTheme.orangeColor.copy(alpha = 0.9f)
                    ) {
                        Text(
                            text = selectedLoko?.nomor_seri?.split(" ")?.take(2)?.joinToString(" ") ?: "N/A",
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = Color.White,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = selectedLoko?.nomor_seri ?: "N/A",
                        style = TextStyle(
                            fontSize = 26.sp,
                            color = Color.White,
                            fontFamily = RailSensusTheme.orangeFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    
                    Text(
                        text = "Lokomotif Diesel Elektrik",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White,
                            fontFamily = RailSensusTheme.blueFontFamily
                        )
                    )
                }
            }
            
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoCard(
                        icon = Icons.Default.DirectionsRailway,
                        label = "Seri Lokomotif",
                        value = selectedLoko?.nomor_seri?.split(" ")?.take(2)?.joinToString(" ") ?: "N/A",
                        subtitle = "General Electric",
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    InfoCard(
                        icon = Icons.Default.LocationOn,
                        label = "Depo Induk",
                        value = selectedLoko?.dipo_induk ?: "N/A",
                        subtitle = "Sidotopo",
                        modifier = Modifier.weight(1f)
                    )
                    
                    InfoCard(
                        icon = Icons.Default.Palette,
                        label = "Status",
                        value = selectedLoko?.status ?: "N/A",
                        subtitle = null,
                        modifier = Modifier.weight(1f)
                    )
                }
                
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Galeri Foto",
                    style = TextStyle(
                        fontSize = 20.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                
                val photos = listOf(1, 2, 3)
                Column(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    photos.chunked(2).forEach { rowPhotos ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowPhotos.forEach { _ ->
                                PhotoCard(
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(160.dp)
                                )
                            }
                            if (rowPhotos.size == 1) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
            }
        }
    }
    
    // TODO: Implement EditLokoDialog
    // if (showEditDialog) {
    //     EditLokoDialog(
    //         onDismiss = { showEditDialog = false },
    //         onSave = { detail ->
    //             // TODO: Update via ViewModel
    //             showEditDialog = false
    //         },
    //         initialLoko = selectedLoko
    //     )
    // }
    
    // Show Delete Confirmation Dialog
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
                    text = "Hapus Sarana",
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
                    text = "Apakah Anda yakin ingin menghapus lokomotif ${selectedLoko?.nomor_seri ?: "ini"}? " +
                            "Data yang sudah dihapus tidak dapat dikembalikan.",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.blueFontFamily,
                        textAlign = TextAlign.Center
                    )
                )
            },
            confirmButton = {
                Button(
                    onClick = {
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
fun LokoDetailBottomBar(
    onEditClick: () -> Unit,
    onTambahFotoClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
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
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Edit Data",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = RailSensusTheme.blueFontFamily,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            
            Button(
                onClick = onTambahFotoClick,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = RailSensusTheme.orangeColor,
                    contentColor = Color.White
                )
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Tambah Foto",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}

@Composable
fun InfoCard(
    icon: ImageVector,
    label: String,
    value: String,
    subtitle: String? = null,
    modifier: Modifier = Modifier,
    iconColor: Color = RailSensusTheme.blueColor,
    iconBackground: Color = RailSensusTheme.blueColor.copy(alpha = 0.1f)
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = iconBackground,
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconColor,
                    modifier = Modifier.size(24.dp)
                )
            }
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = label,
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = RailSensusTheme.lightGrayColor,
                            fontFamily = RailSensusTheme.blueFontFamily
                        )
                    )
                    Text(
                        text = value,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = RailSensusTheme.blueColor,
                            fontFamily = RailSensusTheme.orangeFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
                
                subtitle?.let {
                    Text(
                        text = it,
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = RailSensusTheme.lightGrayColor,
                            fontFamily = RailSensusTheme.blueFontFamily
                        ),
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun PhotoCard(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = modifier.clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Train,
                contentDescription = null,
                tint = RailSensusTheme.blueColor.copy(alpha = 0.3f),
                modifier = Modifier.size(48.dp)
            )
        }
    }
}
