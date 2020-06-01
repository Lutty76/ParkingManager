package fr.lutty.parkingManager.repository

import fr.lutty.parkingManager.data.AccessToken
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccessTokenRepository : JpaRepository<AccessToken, Long>{

 fun findOneByToken(token: String): AccessToken

}