package com.nikshet.industrialbackend.handler

import com.nikshet.industrialbackend.dto.response.ErrorResponse
import com.nikshet.industrialbackend.exception.ServiceException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(ServiceException::class)
    fun handleIllegalArgument(ex: ServiceException): ResponseEntity<ErrorResponse> {
        print(ex)
        print(ex.message)
        print(ex.stackTrace)

        return ResponseEntity.status(ex.statusCode)
            .body(ErrorResponse(message = ex.message ?: "Something went wrong"))
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<ErrorResponse> {
        print(ex)
        print(ex.message)
        print(ex.stackTrace)

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(ErrorResponse(message = "Something went wrong"))
    }
}