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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
fun RegisterPage(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onRegisterSuccess: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    viewModel: LoginViewModel = viewModel(factory = RailSensusViewModel.Factory)
) {
    val registerState by viewModel.registerState.collectAsState()

    LaunchedEffect(registerState.errorMessage == null &&
    !registerState.isLoading) {
        if (viewModel.isLoggedIn()) {
            onRegisterSuccess()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState())
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
                text = "Daftar untuk bergabung",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = RailSensusTheme.lightGrayColor,
                    fontFamily = RailSensusTheme.blueFontFamily
                )
            )
        }
        
        RailSensusPageHeader(
            title = "Daftar Akun",
            subtitle = "Buat akun baru untuk memulai"
        )
        
        RailSensusTextField(
            value = registerState.registerData.username,
            onValueChange = { viewModel.updateRegisterUsername(it) },
            label = "Username",
            placeholder = "Masukkan username",
            leadingIcon = Icons.Default.Person
        )
        
        RailSensusTextField(
            value = registerState.registerData.email,
            onValueChange = { viewModel.updateRegisterEmail(it) },
            label = "Email",
            placeholder = "Masukkan email",
            leadingIcon = Icons.Default.Email
        )
        
        RailSensusPasswordField(
            value = registerState.registerData.password,
            onValueChange = { viewModel.updateRegisterPassword(it) },
            label = "Password",
            placeholder = "Masukkan password",
            leadingIcon = Icons.Default.Person
        )
        
        RailSensusPasswordField(
            value = registerState.registerData.confirmPassword,
            onValueChange = { viewModel.updateRegisterConfirmPassword(it) },
            label = "Konfirmasi Password",
            placeholder = "Masukkan ulang password",
            leadingIcon = Icons.Default.Person,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        if (registerState.errorMessage != null) {
            Text(
                text = registerState.errorMessage!!,
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
                if (registerState.isLoading)
                "Membuat akun..."
                else
                    "Daftar Sekarang",
            onClick = { viewModel.register() },
            enabled = registerState.isEntryValid && !registerState.isLoading
        )
        
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
        
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Sudah punya akun? ",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = RailSensusTheme.blueColor,
                    fontFamily = RailSensusTheme.blueFontFamily
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            TextButton(
                onClick = onLoginClick,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "Masuk sekarang",
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
