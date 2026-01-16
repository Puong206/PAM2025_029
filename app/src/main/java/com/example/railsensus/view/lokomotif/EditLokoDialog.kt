package com.example.railsensus.view.lokomotif

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.railsensus.modeldata.DetailLokomotif
import com.example.railsensus.modeldata.Lokomotif
import com.example.railsensus.ui.component.RailSensusTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLokoDialog(
    onDismiss: () -> Unit,
    onSave: (DetailLokomotif) -> Unit,
    initialLoko: Lokomotif? = null,
    modifier: Modifier = Modifier
) {
    var nomorSeri by remember { mutableStateOf(initialLoko?.nomor_seri ?: "") }
    var dipoInduk by remember { mutableStateOf(initialLoko?.dipo_induk ?: "") }
    var status by remember { mutableStateOf(initialLoko?.status ?: "") }
    var showDipoDropdown by remember { mutableStateOf(false) }
    var showStatusDropdown by remember { mutableStateOf(false) }
    
    val dipoOptions = listOf("SDT", "YK", "BD", "PWK", "SBI")
    val statusOptions = listOf("Aktif", "Nonaktif", "Rusak", "Dalam Perbaikan")
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                modifier = modifier
                    .fillMaxHeight(0.85f)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Header
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Edit Lokomotif",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.orangeFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Perbarui informasi lokomotif",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.lightGrayColor,
                                fontFamily = RailSensusTheme.blueFontFamily
                            )
                        )
                    }
                    
                    // Nomor Seri
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Nomor Seri Lokomotif",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        
                        OutlinedTextField(
                            value = nomorSeri,
                            onValueChange = { nomorSeri = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(
                                    text = "Contoh: CC 206 13 01",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        color = RailSensusTheme.lightGrayColor
                                    )
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Tag,
                                    contentDescription = null,
                                    tint = RailSensusTheme.lightGrayColor
                                )
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
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
                    }
                    
                    // Depo Induk
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Depo Induk",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        
                        ExposedDropdownMenuBox(
                            expanded = showDipoDropdown,
                            onExpandedChange = { showDipoDropdown = it }
                        ) {
                            OutlinedTextField(
                                value = dipoInduk,
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                                placeholder = {
                                    Text(
                                        text = "Pilih Depo Induk",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = RailSensusTheme.lightGrayColor
                                        )
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = null,
                                        tint = RailSensusTheme.lightGrayColor
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = showDipoDropdown)
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    focusedBorderColor = RailSensusTheme.blueColor
                                ),
                                textStyle = TextStyle(
                                    fontSize = 14.sp,
                                    color = RailSensusTheme.blueColor,
                                    fontFamily = RailSensusTheme.blueFontFamily
                                )
                            )
                            
                            ExposedDropdownMenu(
                                expanded = showDipoDropdown,
                                onDismissRequest = { showDipoDropdown = false }
                            ) {
                                dipoOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            dipoInduk = option
                                            showDipoDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    
                    // Status
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Status Lokomotif (Opsional)",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        
                        ExposedDropdownMenuBox(
                            expanded = showStatusDropdown,
                            onExpandedChange = { showStatusDropdown = it }
                        ) {
                            OutlinedTextField(
                                value = status,
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                                placeholder = {
                                    Text(
                                        text = "Pilih Status",
                                        style = TextStyle(
                                            fontSize = 14.sp,
                                            color = RailSensusTheme.lightGrayColor
                                        )
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Info,
                                        contentDescription = null,
                                        tint = RailSensusTheme.lightGrayColor
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = showStatusDropdown)
                                },
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    unfocusedBorderColor = Color(0xFFE0E0E0),
                                    focusedBorderColor = RailSensusTheme.blueColor
                                ),
                                textStyle = TextStyle(
                                    fontSize = 14.sp,
                                    color = RailSensusTheme.blueColor,
                                    fontFamily = RailSensusTheme.blueFontFamily
                                )
                            )
                            
                            ExposedDropdownMenu(
                                expanded = showStatusDropdown,
                                onDismissRequest = { showStatusDropdown = false }
                            ) {
                                statusOptions.forEach { option ->
                                    DropdownMenuItem(
                                        text = { Text(option) },
                                        onClick = {
                                            status = option
                                            showStatusDropdown = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                    
                    // Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                val detail = DetailLokomotif(
                                    loko_id = initialLoko?.loko_id ?: 0,
                                    nomor_seri = nomorSeri,
                                    dipo_induk = dipoInduk,
                                    status = status.ifBlank { "Aktif" }
                                )
                                onSave(detail)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = RailSensusTheme.blueColor,
                                contentColor = Color.White
                            ),
                            enabled = nomorSeri.isNotBlank() && dipoInduk.isNotBlank()
                        ) {
                            Text(
                                text = "Simpan Perubahan",
                                style = TextStyle(
                                    fontSize = 15.sp,
                                    fontFamily = RailSensusTheme.blueFontFamily,
                                    fontWeight = FontWeight.SemiBold
                                )
                            )
                        }
                        
                        OutlinedButton(
                            onClick = onDismiss,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = RailSensusTheme.blueColor
                            ),
                            border = BorderStroke(1.5.dp, RailSensusTheme.blueColor)
                        ) {
                            Text(
                                text = "Batal",
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
    }
}
