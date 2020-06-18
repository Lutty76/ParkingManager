package fr.lutty.parkingManager.service
import org.springframework.beans.factory.annotation.Value
import khttp.get;
import org.slf4j.Logger
import org.slf4j.LoggerFactory
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
    lateinit var gpioParking: String

    var logger: Logger = LoggerFactory.getLogger(GateService::class.java)

    fun openCar(){

        logger.info("Car Opened !")
        get("http://$ipGate/control?cmd=Pulse%2C$gpioGateCar%2C0,500")
    }
    fun openPed(){

        logger.info("Ped Opened !")
        get("http://$ipGate/control?cmd=Pulse%2C$gpioGatePed%2C0,500")
    }
    fun openParking(){

        logger.info("Garage Opened !")
        get("http://$ipParking/control?cmd=Pulse%2C$gpioParking%2C0,500")
    }
}