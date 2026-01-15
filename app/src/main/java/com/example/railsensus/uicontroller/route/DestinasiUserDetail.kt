package com.example.railsensus.uicontroller.route

object DestinasiUserDetail : DestinasiNavigasi {
    override val route = "detail_user"
    override val titleRes = "Detail User"
    
    const val userIdArg = "userId"
    val routeWithArgs = "$route/{$userIdArg}"
    
    fun createRoute(userId: Int) = "$route/$userId"
}
