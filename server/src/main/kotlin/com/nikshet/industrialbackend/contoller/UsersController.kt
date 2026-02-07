package com.nikshet.industrialbackend.contoller

import com.nikshet.industrialbackend.dto.request.UpdateProfileRequest
import com.nikshet.industrialbackend.dto.request.UserRegistrationRequest
import com.nikshet.industrialbackend.dto.response.UserInfoResponse
import com.nikshet.industrialbackend.service.UsersService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/users")
class UsersController(
    private val usersService: UsersService
) {

    @PostMapping("/registration")
    fun registerUser(@Valid @RequestBody request: UserRegistrationRequest): ResponseEntity<UserInfoResponse> {
        val user = usersService.registerUser(
            phoneNumber = request.phoneNumber,
            fullName = request.fullName,
            department = request.department,
            password = request.password
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(UserInfoResponse.from(user))
    }

    @PutMapping("/profile")
    fun updateProfile(
        @AuthenticationPrincipal userId: UUID,
        @Valid @RequestBody request: UpdateProfileRequest
    ): ResponseEntity<UserInfoResponse> {
        val updatedUser = usersService.updateUserProfile(userId, request)
        return ResponseEntity.ok(UserInfoResponse.from(updatedUser))
    }
}