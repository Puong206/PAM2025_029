package com.example.railsensus.uicontroller.route

object DestinasiSensusEdit : DestinasiNavigasi {
    override val route = "edit_sensus"
    override val titleRes = "Edit Sensus"
    
    const val sensusIdArg = "sensusId"
    val routeWithArgs = "$route/{$sensusIdArg}"
    
    fun createRoute(sensusId: Int) = "$route/$sensusId"
}
