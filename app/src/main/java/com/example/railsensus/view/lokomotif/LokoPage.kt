package com.example.railsensus.view.lokomotif

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.railsensus.modeldata.Lokomotif
import com.example.railsensus.ui.component.RailSensusLogo
import com.example.railsensus.ui.component.RailSensusTheme
import com.example.railsensus.view.RailSensusBottomNavigation
import com.example.railsensus.viewmodel.LokoViewModel
import com.example.railsensus.viewmodel.provider.RailSensusViewModel

@Composable
fun LokoPage(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onAddClick: () -> Unit = {},
    onItemClick: (Int) -> Unit = {},
    onBottomNavClick: (Int) -> Unit = {},
    lokoViewModel: LokoViewModel = viewModel(factory = RailSensusViewModel.Factory),
    loginViewModel: com.example.railsensus.viewmodel.LoginViewModel = viewModel(factory = RailSensusViewModel.Factory)
) {
    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }
    
    val lokoList by lokoViewModel.lokoList.collectAsState()
    val isLoading by lokoViewModel.isLoading.collectAsState()
    val currentToken by loginViewModel.currentToken.collectAsState()
    
    LaunchedEffect(Unit) {
        lokoViewModel.loadAllLoko()
    }
    
    val filteredList = remember(lokoList, searchQuery) {
        if (searchQuery.isBlank()) {
            lokoList
        } else {
            lokoList.filter { loko ->
                loko.nomor_seri.contains(searchQuery, ignoreCase = true) ||
                loko.dipo_induk.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = RailSensusTheme.orangeColor,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Lokomotif",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        bottomBar = {
            RailSensusBottomNavigation(
                selectedIndex = 1, // Loko selected
                onItemClick = onBottomNavClick
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFF8F9FB))
                .padding(paddingValues)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {
                Spacer(modifier = Modifier.height(28.dp))
                RailSensusLogo(logoHeight = 40.dp)
                Spacer(modifier = Modifier.height(16.dp))

                // Title
                Text(
                    text = "Data Lokomotif",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // Search and Filter
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        placeholder = {
                            Text(
                                text = "Cari nomor lokomotif",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    color = RailSensusTheme.lightGrayColor,
                                    fontFamily = RailSensusTheme.blueFontFamily
                                )
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = RailSensusTheme.lightGrayColor
                            )
                        },
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color.White,
                            focusedContainerColor = Color.White,
                            unfocusedBorderColor = Color(0xFFE0E0E0),
                            focusedBorderColor = RailSensusTheme.blueColor,
                            cursorColor = RailSensusTheme.blueColor
                        ),
                        textStyle = TextStyle(
                            fontSize = 14.sp,
                            color = RailSensusTheme.blueColor,
                            fontFamily = RailSensusTheme.blueFontFamily
                        )
                    )
                    
                    // Filter Button
                    IconButton(
                        onClick = { /* TODO: Show filter */ },
                        modifier = Modifier
                            .size(56.dp)
                            .background(
                                color = RailSensusTheme.blueColor,
                                shape = RoundedCornerShape(12.dp)
                            )
                    ) {
                        Icon(
                            imageVector = Icons.Default.FilterList,
                            contentDescription = "Filter",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(20.dp))
                
                // List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
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
                                    imageVector = Icons.Default.SearchOff,
                                    contentDescription = null,
                                    tint = RailSensusTheme.lightGrayColor,
                                    modifier = Modifier.size(64.dp)
                                )
                                Text(
                                    text = if (searchQuery.isBlank()) {
                                        "Belum ada data lokomotif"
                                    } else {
                                        "Tidak ada hasil untuk \"$searchQuery\""
                                    },
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = RailSensusTheme.lightGrayColor,
                                        fontFamily = RailSensusTheme.blueFontFamily
                                    )
                                )
                            }
                        }
                    }
                    
                    // List items
                    items(filteredList) { lokomotif ->
                        LokomotifCard(
                            lokomotif = lokomotif,
                            onClick = { onItemClick(lokomotif.loko_id) }
                        )
                    }
                    
                    // Add spacing at bottom for FAB
                    item {
                        Spacer(modifier = Modifier.height(80.dp))
                    }
                }
            }
        }
    }
    
    // Observer for form state to handle success/error
    val formState by lokoViewModel.lokoFormState.collectAsState()
    val context = androidx.compose.ui.platform.LocalContext.current
    var previousLoading by remember { mutableStateOf(false) }

    LaunchedEffect(formState.isLoading) {
        if (previousLoading && !formState.isLoading) {
            if (formState.errorMessage == null) {
                showDialog = false
                android.widget.Toast.makeText(context, "Berhasil menambah lokomotif", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                android.widget.Toast.makeText(context, formState.errorMessage, android.widget.Toast.LENGTH_SHORT).show()
            }
        }
        previousLoading = formState.isLoading
    }

    // Show Dialog
    if (showDialog) {
        TambahLokoDialog(
            onDismiss = { showDialog = false },
            onSave = { lokoDetail ->
                // Update ViewModel state
                lokoViewModel.updateNomorSeri(lokoDetail.nomor_seri)
                lokoViewModel.updateDipoInduk(lokoDetail.dipo_induk)
                lokoViewModel.updateStatus(lokoDetail.status)

                if (currentToken == null) {
                    android.widget.Toast.makeText(context, "Anda belum login! Silakan login kembali.", android.widget.Toast.LENGTH_LONG).show()
                } else {
                    // Create via ViewModel with token
                    currentToken?.let { token ->
                        lokoViewModel.createLoko(token)
                    }
                }
                // Do NOT close dialog here, wait for success
            }
        )
    }
}

@Composable
fun LokoHeader(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 24.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Logo
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Train,
                contentDescription = "Rail Sensus Logo",
                tint = RailSensusTheme.blueColor,
                modifier = Modifier.size(32.dp)
            )
            Column {
                Text(
                    text = "Rail",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RailSensusTheme.orangeColor,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = "Sensus",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.blueFontFamily,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
        
        // Logout/Exit Button
        IconButton(
            onClick = onBackClick,
            modifier = Modifier
                .size(40.dp)
                .background(
                    color = Color(0xFFF8F9FB),
                    shape = RoundedCornerShape(10.dp)
                )
        ) {
            Icon(
                imageVector = Icons.Default.Logout,
                contentDescription = "Logout",
                tint = RailSensusTheme.blueColor,
                modifier = Modifier.size(20.dp)
            )
        }
    }
}

@Composable
fun LokomotifCard(
    lokomotif: Lokomotif,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Image
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE0E0E0))
            ) {
                // Placeholder for train image
                Icon(
                    imageVector = Icons.Default.Train,
                    contentDescription = null,
                    tint = RailSensusTheme.blueColor.copy(alpha = 0.3f),
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            }
            
            // Info
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = lokomotif.nomor_seri,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = null,
                        tint = RailSensusTheme.lightGrayColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = lokomotif.dipo_induk,
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = RailSensusTheme.lightGrayColor,
                            fontFamily = RailSensusTheme.blueFontFamily
                        )
                    )
                }
            }
            
            // Menu Button
            IconButton(
                onClick = { /* TODO: Show menu */ },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu",
                    tint = RailSensusTheme.lightGrayColor
                )
            }
        }
    }
}
