package fr.lutty.parkingManager.exception

import org.springframework.dao.DataIntegrityViolationException

import org.springframework.http.HttpStatus

import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.server.MethodNotAllowedException


@ControllerAdvice
internal class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(ForbiddenException::class)
    fun handleForbidden() {
        // Nothing to do
    }


    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized() {
        // Nothing to do
    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleNotAllowed() {
        // Nothing to do
    }
}