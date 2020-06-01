package fr.lutty.parkingManager.service

import fr.lutty.parkingManager.repository.AccessTokenRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
@EnableScheduling
class TokenService(private val accessTokenRepository: AccessTokenRepository) {
    var logger: Logger = LoggerFactory.getLogger(TokenService::class.java)
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
    private val tokenLength: Int = 64
    fun generateToken(): String =
        (1..tokenLength)
                .map { _ -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString("");

    @Scheduled(cron = "0 */15 * * * ?")
    fun cleanOldToken(){
        logger.info("Token Cleaner started !")
        accessTokenRepository.findAll().map{

            logger.debug(it.expDate.toString() +"<"+LocalDateTime.now())
            if (!it.unlimited && it.expDate<LocalDateTime.now()) {
                logger.debug("deleted :"+ it.id)
                accessTokenRepository.delete(it)
            }
        }
        logger.info("Token Cleaner finished !")
    }

}