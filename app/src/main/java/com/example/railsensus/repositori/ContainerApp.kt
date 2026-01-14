package com.example.railsensus.repositori

import com.example.railsensus.apiservice.ServiceApi

interface AppContainer{
    val serviceApi: ServiceApi
}

class ContainerApp: AppContainer{

}