package com.example.railsensus.view.sensus

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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.railsensus.modeldata.Sensus
import com.example.railsensus.ui.component.RailSensusLogo
import com.example.railsensus.ui.component.RailSensusTheme
import com.example.railsensus.view.user.RailSensusBottomNavigation
import com.example.railsensus.view.user.dialog.TambahSensusDialog
import com.example.railsensus.viewmodel.LoginViewModel
import com.example.railsensus.viewmodel.SensusViewModel
import com.example.railsensus.viewmodel.provider.RailSensusViewModel


@Composable
fun SensusPage(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit = {},
    onItemClick: (Int) -> Unit = {},
    onBottomNavClick: (Int) -> Unit = {},
    sensusViewModel: SensusViewModel = viewModel(factory = RailSensusViewModel.Factory),
    loginViewModel: LoginViewModel = viewModel(factory = RailSensusViewModel.Factory)
) {
    val sensusList by sensusViewModel.sensusList.collectAsState()
    val isLoading by sensusViewModel.isLoading.collectAsState()
    val currentToken by loginViewModel.currentToken.collectAsState()

    var searchQuery by remember { mutableStateOf("") }
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        sensusViewModel.loadAllSensus()
    }

    val filteredList = remember(sensusList, searchQuery) {
        if (searchQuery.isEmpty()) {
            sensusList
        } else {
            sensusList.filter {
                sensus ->
                sensus.nomor_seri?.contains(searchQuery, ignoreCase = true) == true ||
                sensus.nama_ka?.contains(searchQuery, ignoreCase = true) == true ||
                sensus.username?.contains(searchQuery, ignoreCase = true) == true
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
                    contentDescription = "Tambah Sensus",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        bottomBar = {
            RailSensusBottomNavigation(
                selectedIndex = 2,
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
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RailSensusLogo(logoHeight = 40.dp)
                    IconButton(
                        onClick = onLogoutClick,
                        modifier = Modifier
                            .size(44.dp)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(12.dp)
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
                
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Sensus KA",
                    style = TextStyle(
                        fontSize = 24.sp,
                        color = RailSensusTheme.blueColor,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
                
                Spacer(modifier = Modifier.height(20.dp))
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        modifier = Modifier
                            .weight(1f)
                            .height(56.dp),
                        placeholder = {
                            Text(
                                text = "Cari nomor lokomotif...",
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

                if (isLoading && sensusList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = RailSensusTheme.blueColor
                        )
                    }
                } else if (filteredList.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (searchQuery.isBlank()) {
                                "Belum ada sensus"
                            } else {
                                "Tidak ada hasil pencarian untuk \"$searchQuery\""
                            },
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.lightGrayColor,
                                fontFamily = RailSensusTheme.blueFontFamily
                            )
                        )
                    }
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(filteredList) { sensus ->
                            SensusCard(
                                sensus = sensus,
                                onClick = { onItemClick(sensus.sensus_id) }
                            )
                        }
                        item {
                            Spacer(modifier = Modifier.height(80.dp))
                        }
                    }
                }
            }
        }
    }
    
    if (showDialog) {
        TambahSensusDialog(
            onDismiss = { showDialog = false },
            onSave = { sensusDetail ->
                currentToken?.let { token ->
                    sensusViewModel.updateLokoId(sensusDetail.loko_id)
                    sensusViewModel.updateKaId(sensusDetail.ka_id)
                    sensusViewModel.createSensus(token)
                }
                showDialog = false
            }
        )
    }
}

@Composable
fun SensusCard(
    sensus: Sensus,
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
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            //Image Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(Color(0xFFE0E0E0))
            ) {
                Icon(
                    imageVector = Icons.Default.Train,
                    contentDescription = null,
                    tint = RailSensusTheme.blueColor.copy(alpha = 0.3f),
                    modifier = Modifier
                        .size(80.dp)
                        .align(Alignment.Center)
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    Text(
                        text = sensus.nama_ka ?: "Unknown Train",
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = Color.White,
                            fontFamily = RailSensusTheme.orangeFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Text(
                        text = sensus.nomor_seri ?: "N/A",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = Color.White,
                            fontFamily = RailSensusTheme.blueFontFamily
                        )
                    )
                }
            }
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    //User Ava
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .background(
                                color = RailSensusTheme.blueColor,
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = sensus.username?.take(2)?.uppercase() ?: "",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = Color.White,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                    
                    Text(
                        text = sensus.username ?: "Unknown User",
                        style = TextStyle(
                            fontSize = 14.sp,
                            color = RailSensusTheme.blueColor,
                            fontFamily = RailSensusTheme.blueFontFamily,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
                
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    Text(
                        text = "Trust Score",
                        style = TextStyle(
                            fontSize = 11.sp,
                            color = RailSensusTheme.lightGrayColor,
                            fontFamily = RailSensusTheme.blueFontFamily
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(
                                    color = Color(0xFF4CAF50),
                                    shape = CircleShape
                                )
                        )
                        Text(
                            text = "${sensus.trust_score}/10",
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = Color(0xFF4CAF50),
                                fontFamily = RailSensusTheme.orangeFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
}