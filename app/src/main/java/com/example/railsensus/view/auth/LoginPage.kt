package com.example.railsensus.view.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.railsensus.ui.component.RailSensusBackButton
import com.example.railsensus.ui.component.RailSensusButton
import com.example.railsensus.ui.component.RailSensusLogo
import com.example.railsensus.ui.component.RailSensusPageHeader
import com.example.railsensus.ui.component.RailSensusPasswordField
import com.example.railsensus.ui.component.RailSensusTextField
import com.example.railsensus.ui.component.RailSensusTheme
import com.example.railsensus.viewmodel.LoginViewModel
import com.example.railsensus.viewmodel.provider.RailSensusViewModel

@Composable
fun LoginPage(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onLoginSuccess: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    viewModel: LoginViewModel = viewModel(factory = RailSensusViewModel.Factory)
) {
    val loginState by viewModel.loginState.collectAsState()

    LaunchedEffect(loginState.errorMessage == null && !loginState.isLoading) {
        if (viewModel.isLoggedIn()) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        RailSensusBackButton(onBackClick = onBackClick)
        
        Column(
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            RailSensusLogo()
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Masuk untuk melanjutkan hunting lokomotif",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = RailSensusTheme.lightGrayColor,
                    fontFamily = RailSensusTheme.blueFontFamily
                )
            )
        }
        RailSensusPageHeader(
            title = "Selamat Datang!",
            subtitle = "Login dengan data Anda"
        )
        
        RailSensusTextField(
            value = loginState.loginData.email,
            onValueChange = { viewModel.updateLoginEmail(it) },
            label = "Email/Username",
            placeholder = "Masukkan email atau username",
            leadingIcon = Icons.Default.Person,
            enabled = !loginState.isLoading
        )
        
        RailSensusPasswordField(
            value = loginState.loginData.password,
            onValueChange = { viewModel.updateLoginPassword(it) },
            label = "Password",
            placeholder = "Masukkan password",
            leadingIcon = Icons.Default.Person,
            enabled = !loginState.isLoading,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Error Message
        if (loginState.errorMessage != null) {
            Text(
                text = loginState.errorMessage!!,
                style = TextStyle(
                    fontSize = 12.sp,
                    color = RailSensusTheme.orangeColor,
                    fontFamily = RailSensusTheme.blueFontFamily
                ),
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        RailSensusButton(
            text =
                if (loginState.isLoading)
                    "Logging in..."
                else "Masuk Sekarang",
            onClick = { viewModel.login() },
            enabled = loginState.isEntryValid && !loginState.isLoading
        )
        
        // Divider Text
        Text(
            text = "atau",
            style = TextStyle(
                fontSize = 14.sp,
                color = RailSensusTheme.lightGrayColor,
                fontFamily = RailSensusTheme.blueFontFamily
            ),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = 16.dp)
        )
        
        // Register Link
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Belum punya akun? ",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = RailSensusTheme.blueColor,
                    fontFamily = RailSensusTheme.blueFontFamily
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(
                onClick = onRegisterClick,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Daftar sekarang",
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = RailSensusTheme.orangeColor,
                        fontFamily = RailSensusTheme.orangeFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                )
            }
        }
    }
}
