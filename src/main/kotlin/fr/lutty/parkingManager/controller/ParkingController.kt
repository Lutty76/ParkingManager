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

    @GetMapping("/open/{token}")
    fun openGateForm(@PathVariable token: String): String {

        val aToken = accessTokenRepository.findOneByToken(token)
        return "<h1>Welcome ${aToken.dest}, click the button for open the gate</h1>" +
                "<h2>Remember, this will work only one time !</h2>" +
                "<form method='post' action='.'><input type='hidden' name='token' value='"+token+"' /><input style='border:none;background:#22AA77;width:100%;max-width:400px;font-size:32px;' type='submit' value='Open' /></form>"
    }

    @PostMapping("/open/")
    fun openGate(@RequestParam token: String): String {

        val aToken = accessTokenRepository.findOneByToken(token)
        if (aToken.car) gateService.openCar() else gateService.openPed()
        if (! aToken.unlimited) accessTokenRepository.delete(aToken)
        return "<h1>Welcome ${aToken.dest}, I will open the gate for you!</h1><img src='https://media1.tenor.com/images/832290ffe40003b345af4a838953afc3/tenor.gif' />"
    }

    @PostMapping("/openGarage/")
    fun openGarage(@RequestParam auth: String): String {

        if (apiAuth == auth) {
            gateService.openParking()
        }
        return "<h1>Welcome, I will open the garage for you!</h1>"
    }

    @PostMapping("/generateToken/")
    fun generateToken(@RequestParam auth: String, @RequestParam dest: String, @RequestParam car: Boolean, @RequestParam exp: Long): AccessToken =
            if (apiAuth == auth) {
                val atoken: AccessToken = if (exp < 0)
                {
                    AccessToken(null, tokenService.generateToken(), dest, car, true, LocalDateTime.now().plusHours(exp))
                }else{
                    AccessToken(null, tokenService.generateToken(), dest, car, false, LocalDateTime.now().plusHours(exp))
                }
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

    @RequestMapping(value = ["/robots.txt", "/robot.txt"])
    @ResponseBody
    fun getRobotsTxt(): String? {
        return """
            User-agent: *
            Disallow: /
            
            """.trimIndent()
    }
}