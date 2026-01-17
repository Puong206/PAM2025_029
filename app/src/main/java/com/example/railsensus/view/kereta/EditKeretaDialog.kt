package com.example.railsensus.view.kereta

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.railsensus.modeldata.DetailKereta
import com.example.railsensus.modeldata.Kereta
import com.example.railsensus.ui.component.RailSensusTheme

@Composable
fun EditKeretaDialog(
    onDismiss: () -> Unit,
    onSave: (DetailKereta) -> Unit,
    initialKereta: Kereta?,
    modifier: Modifier = Modifier
) {
    var nomorKa by remember { mutableStateOf(initialKereta?.nomor_ka ?: "") }
    var namaKa by remember { mutableStateOf(initialKereta?.nama_ka ?: "") }
    
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomCenter
        ) {
            Card(
                modifier = modifier
                    .wrapContentHeight()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    // Header
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Edit Kereta",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.orangeFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Perbarui informasi kereta",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.lightGrayColor,
                                fontFamily = RailSensusTheme.blueFontFamily
                            )
                        )
                    }
                    
                    // Nomor Kereta
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Nomor Kereta",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        OutlinedTextField(
                            value = nomorKa,
                            onValueChange = { nomorKa = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Contoh: KA123") },
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
                                focusedBorderColor = RailSensusTheme.blueColor
                            ),
                            textStyle = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.blueFontFamily
                            )
                        )
                    }
                    
                    // Nama Kereta
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Nama Kereta",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        OutlinedTextField(
                            value = namaKa,
                            onValueChange = { namaKa = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Contoh: Argo Bromo Anggrek") },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.DirectionsRailway,
                                    contentDescription = null,
                                    tint = RailSensusTheme.lightGrayColor
                                )
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
                    }
                    
                    // Buttons
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Button(
                            onClick = {
                                val detail = DetailKereta(
                                    ka_id = initialKereta?.ka_id ?: 0,
                                    nomor_ka = nomorKa,
                                    nama_ka = namaKa
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
                            enabled = nomorKa.isNotBlank() && namaKa.isNotBlank()
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
