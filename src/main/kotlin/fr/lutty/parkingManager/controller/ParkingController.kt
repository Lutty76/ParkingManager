package fr.lutty.parkingManager.controller

import fr.lutty.parkingManager.data.AccessToken
import fr.lutty.parkingManager.exception.UnauthorizedException
import fr.lutty.parkingManager.repository.AccessTokenRepository
import fr.lutty.parkingManager.service.GateService
import fr.lutty.parkingManager.service.TokenService
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

@RestController
class ParkingController(private val accessTokenRepository: AccessTokenRepository, private val tokenService: TokenService, private val gateService: GateService) {
    @Value("\${api.auth}")
    lateinit var apiAuth: String

    @GetMapping("/")
    fun home(): String {
        return "Hello World"
    }

    @GetMapping("/clean")
    fun clean(): String {
        tokenService.cleanOldToken()
        return "Clean !"
    }

    @RequestMapping("/open/{token}")
    fun openGate(@PathVariable token: String): String {

        val aToken = accessTokenRepository.findOneByToken(token)
        if (aToken.car) gateService.openCar() else gateService.openPed()
        if (! aToken.unlimited) accessTokenRepository.delete(aToken)
        return "<h1>Welcome ${aToken.dest}, I will open the gate for you!</h1>"
    }

    @PostMapping("/generateToken/")
    fun generateToken(@RequestParam auth: String, @RequestParam dest: String, @RequestParam car: Boolean, @RequestParam exp: Long): AccessToken =
            if (apiAuth == auth) {
                val atoken: AccessToken = AccessToken(null, tokenService.generateToken(), dest, car, false, LocalDateTime.now().plusHours(exp))
                accessTokenRepository.save(atoken)
                atoken
            } else {
                throw UnauthorizedException()
            }


    @PostMapping("/tokens")
    fun getAllArticles(@RequestParam auth: String): List<AccessToken> =
            if (apiAuth == auth) {
                accessTokenRepository.findAll()
            } else {
                throw UnauthorizedException()
            }

}