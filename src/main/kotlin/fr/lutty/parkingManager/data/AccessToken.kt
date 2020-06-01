package fr.lutty.parkingManager.data

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class AccessToken (@Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long?, val token:String, val dest:String, val car:Boolean, val unlimited:Boolean, val expDate: LocalDateTime)