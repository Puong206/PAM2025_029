package com.example.railsensus.uicontroller.route

object DestinasiKeretaEdit : DestinasiNavigasi {
    override val route = "edit_kereta"
    override val titleRes = "Edit Kereta"
    
    const val keretaIdArg = "keretaId"
    val routeWithArgs = "$route/{$keretaIdArg}"
    
    fun createRoute(keretaId: Int) = "$route/$keretaId"
}
