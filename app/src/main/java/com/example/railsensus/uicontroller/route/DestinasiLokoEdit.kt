package com.example.railsensus.uicontroller.route

object DestinasiLokoEdit : DestinasiNavigasi {
    override val route = "edit_loko"
    override val titleRes = "Edit Lokomotif"
    
    const val lokoIdArg = "lokoId"
    val routeWithArgs = "$route/{$lokoIdArg}"
    
    fun createRoute(lokoId: Int) = "$route/$lokoId"
}
