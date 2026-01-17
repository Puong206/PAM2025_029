package com.example.railsensus.view.home

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.railsensus.modeldata.Sensus
import com.example.railsensus.ui.component.RailSensusLogo
import com.example.railsensus.ui.component.RailSensusTheme
import com.example.railsensus.view.RailSensusBottomNavigation
import com.example.railsensus.view.sensus.SensusCard
import com.example.railsensus.viewmodel.LoginViewModel
import com.example.railsensus.viewmodel.SensusViewModel
import com.example.railsensus.viewmodel.provider.RailSensusViewModel

@Composable
fun DashboardPage(
    modifier: Modifier = Modifier,
    onSaranaClick: () -> Unit = {},
    onSensusClick: () -> Unit = {},
    onSeeAllClick: () -> Unit = {},
    onBottomNavClick: (Int) -> Unit = {},
    onUserManagementClick: () -> Unit = {},
    onKeretaManagementClick: () -> Unit = {},
    sensusViewModel: SensusViewModel = viewModel(factory = RailSensusViewModel.Factory),
    loginViewModel: LoginViewModel = viewModel(factory = RailSensusViewModel.Factory)
) {
    val currentUser by loginViewModel.currentUser.collectAsState()
    val isAdmin = loginViewModel.isAdmin()
    val recentSensus by sensusViewModel.sensusList.collectAsState()

    LaunchedEffect(Unit) {
        sensusViewModel.loadAllSensus(limit = 5)
    }

    Scaffold(
        bottomBar = {
            RailSensusBottomNavigation(
                selectedIndex = 0,
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
            DashboardHeader(
                username = currentUser?.username ?: "User",
            )
            FiturUtamaSection(
                onSaranaClick = onSaranaClick,
                onSensusClick = onSensusClick,
                modifier = Modifier.padding(horizontal = 24.dp)
            )

            if (isAdmin) {
                Spacer(modifier = Modifier.height(12.dp))
                AdminSection(
                    onUserManagementClick = onUserManagementClick,
                    onKeretaManagementClick = onKeretaManagementClick,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            SensusTerbaruSection(
                sensusList = recentSensus.take(5),
                onSeeAllClick = onSeeAllClick,
                modifier = Modifier.padding(horizontal = 24.dp)
            )
            Spacer(modifier = Modifier.height(100.dp))
        }
    }
}

@Composable
fun DashboardHeader(
    username: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.padding(top = 12.dp)) {
            RailSensusLogo(logoHeight = 40.dp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Halo, $username! 👋",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = RailSensusTheme.blueColor,
                    fontFamily = RailSensusTheme.orangeFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
            
            Text(
                text = "Siap hunting kereta hari ini?",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = RailSensusTheme.lightGrayColor,
                    fontFamily = RailSensusTheme.blueFontFamily
                )
            )
        }
    }
}

@Composable
fun FiturUtamaSection(
    onSaranaClick: () -> Unit,
    onSensusClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Quick Action",
            style = TextStyle(
                fontSize = 18.sp,
                color = RailSensusTheme.blueColor,
                fontFamily = RailSensusTheme.orangeFontFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            FeatureCard(
                icon = Icons.Default.Train,
                iconColor = RailSensusTheme.blueColor,
                iconBackground = RailSensusTheme.blueColor.copy(alpha = 0.1f),
                title = "Sarana Lokomotif",
                description = "Data armada dan spesifikasi",
                onClick = onSaranaClick
            )
            
            FeatureCard(
                icon = Icons.Default.Assignment,
                iconColor = RailSensusTheme.orangeColor,
                iconBackground = RailSensusTheme.orangeColor.copy(alpha = 0.1f),
                title = "Sensus KA",
                description = "Input dan monitor pergerakan",
                onClick = onSensusClick
            )
        }
    }
}

@Composable
fun FeatureCard(
    icon: ImageVector,
    iconColor: Color,
    iconBackground: Color,
    title: String,
    description: String,
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
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .background(
                            color = iconBackground,
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = title,
                        tint = iconColor,
                        modifier = Modifier.size(28.dp)
                    )
                }
                
                Column {
                    Text(
                        text = title,
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = RailSensusTheme.blueColor,
                            fontFamily = RailSensusTheme.orangeFontFamily,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    
                    Text(
                        text = description,
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = RailSensusTheme.lightGrayColor,
                            fontFamily = RailSensusTheme.blueFontFamily
                        )
                    )
                }
            }
            
            // Arrow
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Buka",
                tint = RailSensusTheme.lightGrayColor,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun SensusTerbaruSection(
    sensusList: List<Sensus>,
    onSeeAllClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sensus Terbaru",
                style = TextStyle(
                    fontSize = 18.sp,
                    color = RailSensusTheme.blueColor,
                    fontFamily = RailSensusTheme.orangeFontFamily,
                    fontWeight = FontWeight.Bold
                )
            )
            TextButton(onClick = onSeeAllClick) {
                Text(
                    text = "Lihat Semua",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RailSensusTheme.orangeColor,
                        fontFamily = RailSensusTheme.blueFontFamily,
                        fontWeight = FontWeight.Medium
                    )
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        if (sensusList.isEmpty()) {
            Text(
                text = "Belum ada data sensus",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = RailSensusTheme.lightGrayColor,
                    fontFamily = RailSensusTheme.blueFontFamily,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
            )
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                sensusList.forEach { sensus ->
                    SensusCard(
                        sensus = sensus,
                        onClick = { /* navigate to detail */ }
                    )
                }
            }
        }
    }
}

@Composable
fun AdminSection(
    onUserManagementClick: () -> Unit,
    onKeretaManagementClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(
            text = "Admin Panel",
            style = TextStyle(
                fontSize = 18.sp,
                color = RailSensusTheme.blueColor,
                fontFamily = RailSensusTheme.orangeFontFamily,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            FeatureCard(
                icon = Icons.Default.AdminPanelSettings,
                iconColor = Color(0xFF9C27B0),
                iconBackground = Color(0xFF9C27B0).copy(alpha = 0.1f),
                title = "User Management",
                description = "Kelola User (Admin Only)",
                onClick = onUserManagementClick
            )
            FeatureCard(
                icon = Icons.Default.Train,
                iconColor = Color(0xFF1976D2),
                iconBackground = Color(0xFF1976D2).copy(alpha = 0.1f),
                title = "Kereta Management",
                description = "Kelola Data Kereta (Admin Only)",
                onClick = onKeretaManagementClick
            )
        }
    }
}