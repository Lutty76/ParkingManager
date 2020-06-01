package fr.lutty.parkingManager.service
import org.springframework.beans.factory.annotation.Value
import khttp.get;
import org.springframework.stereotype.Component

@Component
class GateService {
    @Value("\${esp8266.gate}")
    lateinit var ipGate: String

    @Value("\${esp8266.parking}")
    lateinit var ipParking: String


    fun openCar(){
        get("http://$ipGate/car")
    }
    fun openPed(){
        get("http://$ipGate/ped")
    }
    fun openParking(){
        get("http://$ipParking/car")
    }
}