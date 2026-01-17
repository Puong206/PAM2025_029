package com.example.railsensus.view.sensus

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.railsensus.modeldata.Sensus
import com.example.railsensus.modeldata.DetailSensus
import com.example.railsensus.ui.component.RailSensusTheme
import com.example.railsensus.viewmodel.KeretaViewModel
import com.example.railsensus.viewmodel.LokoViewModel
import com.example.railsensus.viewmodel.provider.RailSensusViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditSensusDialog(
    onDismiss: () -> Unit,
    onSave: (DetailSensus) -> Unit,
    initialSensus: Sensus?,
    modifier: Modifier = Modifier,
    lokoViewModel: LokoViewModel = viewModel(factory = RailSensusViewModel.Factory),
    keretaViewModel: KeretaViewModel = viewModel(factory = RailSensusViewModel.Factory)
) {
    val lokoList by lokoViewModel.lokoList.collectAsState()
    val keretaList by keretaViewModel.keretaList.collectAsState()
    var selectedLokoId by remember { mutableStateOf(initialSensus?.loko_id) }
    var selectedKaId by remember { mutableStateOf(initialSensus?.ka_id) }
    var lokoDropdownExpanded by remember { mutableStateOf(false) }
    var keretaDropdownExpanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        lokoViewModel.loadAllLoko()
        keretaViewModel.loadAllKereta()
    }
    
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
                            text = "Edit Sensus",
                            style = TextStyle(
                                fontSize = 20.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.orangeFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "Perbarui informasi sensus",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.lightGrayColor,
                                fontFamily = RailSensusTheme.blueFontFamily
                            )
                        )
                    }
                    
                    // Nomor Lokomotif
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Nomor Lokomotif",
                            style = TextStyle(
                                fontSize = 14.sp,
                                color = RailSensusTheme.blueColor,
                                fontFamily = RailSensusTheme.blueFontFamily,
                                fontWeight = FontWeight.SemiBold
                            )
                        )

                        ExposedDropdownMenuBox(
                            expanded = lokoDropdownExpanded,
                            onExpandedChange = { lokoDropdownExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = lokoList.find { it.loko_id == selectedLokoId }?.nomor_seri ?: "",
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                placeholder = {
                                    Text("Pilih Lokomotif")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.Train,
                                        contentDescription = null,
                                        tint = RailSensusTheme.lightGrayColor
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = lokoDropdownExpanded)
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

                            ExposedDropdownMenu(
                                expanded = lokoDropdownExpanded,
                                onDismissRequest = { lokoDropdownExpanded = false }
                            ) {
                                lokoList.forEach { loko ->
                                    DropdownMenuItem(
                                        text = { Text("${loko.nomor_seri} - ${loko.status ?: "N/A"}") },
                                        onClick = {
                                            selectedLokoId = loko.loko_id
                                            lokoDropdownExpanded = false
                                        }
                                    )
                                }
                            }
                        }
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

                        ExposedDropdownMenuBox(
                            expanded = keretaDropdownExpanded,
                            onExpandedChange = { keretaDropdownExpanded = it }
                        ) {
                            OutlinedTextField(
                                value = keretaList.find { it.ka_id == selectedKaId }?.nama_ka ?: "",
                                onValueChange = {},
                                readOnly = true,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                placeholder = {
                                    Text("Pilih Kereta")
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = Icons.Default.DirectionsRailway,
                                        contentDescription = null,
                                        tint = RailSensusTheme.lightGrayColor
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = keretaDropdownExpanded)
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

                            ExposedDropdownMenu(
                                expanded = keretaDropdownExpanded,
                                onDismissRequest = { keretaDropdownExpanded = false }
                            ) {
                                keretaList.forEach { kereta ->
                                    DropdownMenuItem(
                                        text = { Text("${kereta.nomor_ka} - ${kereta.nama_ka}") },
                                        onClick = {
                                            selectedKaId = kereta.ka_id
                                            keretaDropdownExpanded = false
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
                                val detail = DetailSensus(
                                    loko_id = selectedLokoId ?: 0,
                                    ka_id = selectedKaId ?: 0,
                                    nomor_seri = lokoList.find { it.loko_id == selectedLokoId }?.nomor_seri ?: "",
                                    nama_ka = keretaList.find { it.ka_id == selectedKaId }?.nama_ka ?: ""
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
                            enabled = selectedLokoId != null && selectedKaId != null
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
