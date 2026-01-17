package com.example.railsensus.view.kereta

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.railsensus.modeldata.DetailKereta
import com.example.railsensus.modeldata.Kereta
import com.example.railsensus.ui.component.RailSensusTheme
import com.example.railsensus.view.RailSensusBottomNavigation
import com.example.railsensus.viewmodel.KeretaViewModel
import com.example.railsensus.viewmodel.LoginViewModel
import com.example.railsensus.viewmodel.provider.RailSensusViewModel

@Composable
fun KeretaPage(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onItemClick: (Int) -> Unit = {},
    onBottomNavClick: (Int) -> Unit = {},
    keretaViewModel: KeretaViewModel = viewModel(factory = RailSensusViewModel.Factory),
    loginViewModel: LoginViewModel = viewModel(factory = RailSensusViewModel.Factory)
) {
    var searchQuery by remember { mutableStateOf("") }
    var showTambahDialog by remember { mutableStateOf(false) }
    
    val keretaList by keretaViewModel.keretaList.collectAsState()
    val isLoading by keretaViewModel.isLoading.collectAsState()
    val currentToken by loginViewModel.currentToken.collectAsState()
    
    LaunchedEffect(Unit) {
        keretaViewModel.loadAllKereta()
    }
    
    val filteredList = remember(keretaList, searchQuery) {
        if (searchQuery.isBlank()) {
            keretaList
        } else {
            keretaList.filter { kereta ->
                kereta.nama_ka.contains(searchQuery, ignoreCase = true) ||
                kereta.nomor_ka.contains(searchQuery, ignoreCase = true)
            }
        }
    }
    
    Scaffold(
        topBar = {
            KeretaHeader(
                onBackClick = onBackClick,
                searchQuery = searchQuery,
                onSearchChange = { searchQuery = it }
            )
        },
        bottomBar = {
            RailSensusBottomNavigation(
                selectedIndex = 2, // Kereta tab
                onItemClick = onBottomNavClick
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showTambahDialog = true },
                containerColor = RailSensusTheme.orangeColor,
                contentColor = Color.White,
                shape = CircleShape
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Tambah Kereta",
                    modifier = Modifier.size(28.dp)
                )
            }
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
                            imageVector = Icons.Default.DirectionsRailway,
                            contentDescription = null,
                            tint = RailSensusTheme.lightGrayColor,
                            modifier = Modifier.size(64.dp)
                        )
                        Text(
                            text = if (searchQuery.isBlank()) {
                                "Belum ada data kereta"
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
            
            items(filteredList) { kereta ->
                KeretaCard(
                    kereta = kereta,
                    onClick = { onItemClick(kereta.ka_id) }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
    
    // Observer for form state to handle success/error
    val formState = keretaViewModel.keretaFormState.collectAsState().value
    val context = androidx.compose.ui.platform.LocalContext.current
    var previousLoading by remember { mutableStateOf(false) }

    LaunchedEffect(formState.isLoading) {
        if (previousLoading && !formState.isLoading) {
            if (formState.errorMessage == null) {
                showTambahDialog = false
                android.widget.Toast.makeText(context, "Berhasil menambah kereta", android.widget.Toast.LENGTH_SHORT).show()
            } else {
                android.widget.Toast.makeText(context, formState.errorMessage, android.widget.Toast.LENGTH_SHORT).show()
            }
        }
        previousLoading = formState.isLoading
    }

    if (showTambahDialog) {
        TambahKeretaDialog(
            onDismiss = { showTambahDialog = false },
            onSave = { detail ->
                // Update ViewModel from dialog detail
                keretaViewModel.updateNamaKa(detail.nama_ka)
                keretaViewModel.updateNomorKa(detail.nomor_ka)
                
                if (currentToken == null) {
                    android.widget.Toast.makeText(context, "Anda belum login! Silakan login kembali.", android.widget.Toast.LENGTH_LONG).show()
                } else {
                    // Create with token
                    currentToken?.let { token ->
                        keretaViewModel.createKereta(token)
                    }
                }
                // Do NOT close dialog here, wait for success
            }
        )
    }
}

@Composable
fun KeretaHeader(
    onBackClick: () -> Unit,
    searchQuery: String,
    onSearchChange: (String) -> Unit
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
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White
                )
            }
            Text(
                text = "Data Kereta",
                style = TextStyle(
                    fontSize = 22.sp,
                    color = Color.White,
                    fontFamily = RailSensusTheme.orangeFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
            Spacer(modifier = Modifier.width(48.dp))
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchChange,
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Cari nama atau nomor kereta...",
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
fun KeretaCard(
    kereta: Kereta,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
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
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(
                        color = RailSensusTheme.blueColor.copy(alpha = 0.1f),
                        shape = RoundedCornerShape(12.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.DirectionsRailway,
                    contentDescription = null,
                    tint = RailSensusTheme.blueColor,
                    modifier = Modifier.size(32.dp)
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = kereta.nama_ka,
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = RailSensusTheme.orangeColor.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = kereta.nomor_ka,
                            style = TextStyle(
                                fontSize = 12.sp,
                                color = RailSensusTheme.orangeColor,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.SemiBold
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }
            
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = RailSensusTheme.lightGrayColor
            )
        }
    }
}
