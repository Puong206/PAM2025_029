package com.example.railsensus.uicontroller.route

object DestinasiSensusDetail : DestinasiNavigasi {
    override val route = "detail_sensus"
    override val titleRes = "Detail Sensus"
    
    const val sensusIdArg = "sensusId"
    val routeWithArgs = "$route/{$sensusIdArg}"
    
    fun createRoute(sensusId: Int) = "$route/$sensusId"
}
