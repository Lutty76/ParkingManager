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

    @Value("\${esp8266.gpio_gate_car}")
    lateinit var gpioGateCar: String

    @Value("\${esp8266.gpio_gate_ped}")
    lateinit var gpioGatePed: String

    @Value("\${esp8266.gpio_parking}")
    lateinit var gpioGate: String


    fun openCar(){
        get("http://$ipGate/control?cmd=Pulse%2C$gpioGateCar%2C0,500")
    }
    fun openPed(){
        get("http://$ipGate/control?cmd=Pulse%2C$gpioGatePed%2C0,500")
    }
    fun openParking(){
        get("http://$ipParking/control?cmd=Pulse%2C$gpioGate%2C0,500")
    }
}