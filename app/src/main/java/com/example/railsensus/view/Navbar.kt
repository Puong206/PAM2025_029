package com.example.railsensus.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.DirectionsRailway
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.railsensus.ui.component.RailSensusTheme

@Composable
fun RailSensusBottomNavigation(
    selectedIndex: Int,
    onItemClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .navigationBarsPadding(), // Add padding for system navigation bar
        color = Color.White,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Home
            NavItem(
                icon = Icons.Default.Home,
                label = "Home",
                isSelected = selectedIndex == 0,
                onClick = { onItemClick(0) }
            )
            
            // Sarana
            NavItem(
                icon = Icons.Default.DirectionsRailway,
                label = "Sarana",
                isSelected = selectedIndex == 1,
                onClick = { onItemClick(1) }
            )
            
            // Sensus
            NavItem(
                icon = Icons.Default.Assignment,
                label = "Sensus",
                isSelected = selectedIndex == 2,
                onClick = { onItemClick(2) }
            )
            
            // Profile
            NavItem(
                icon = Icons.Default.Person,
                label = "Profile",
                isSelected = selectedIndex == 3,
                onClick = { onItemClick(3) }
            )
        }
    }
}

@Composable
private fun NavItem(
    icon: ImageVector,
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .widthIn(min = 64.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon with background for selected state
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(
                    color =
                        if (isSelected)
                            RailSensusTheme.blueColor
                        else
                            Color.Transparent,
                    shape = RoundedCornerShape(16.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint =
                    if (isSelected)
                        Color.White
                    else
                        RailSensusTheme.lightGrayColor,
                modifier = Modifier.size(28.dp)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Label
        Text(
            text = label,
            style = TextStyle(
                fontSize = 12.sp,
                fontFamily = RailSensusTheme.blueFontFamily,
                fontWeight =
                    if (isSelected)
                        FontWeight.SemiBold
                    else
                        FontWeight.Normal,
                color =
                    if (isSelected)
                        RailSensusTheme.blueColor
                    else
                        RailSensusTheme.lightGrayColor
            )
        )
    }
}
