package com.nikshet.industrialbackend.service

import com.nikshet.industrialbackend.entity.UserEntity
import com.nikshet.industrialbackend.exception.PhoneAlreadyUsedError
import com.nikshet.industrialbackend.repository.UsersRepository
import org.springframework.stereotype.Service


@Service
class UsersService(
    private val usersRepository: UsersRepository,
    private val passwordService: PasswordService
) {
    fun registerUser(
        phoneNumber: String,
        fullName: String,
        department: String,
        password: String
    ): UserEntity {
        if (usersRepository.findByPhoneNumber(phoneNumber) != null) {
            throw PhoneAlreadyUsedError()
        }

        val passwordHash = passwordService.hashPassword(password)

        val user = UserEntity(
            phoneNumber = phoneNumber,
            fullName = fullName,
            department = department,
            passwordHash = passwordHash
        )

        return usersRepository.save(user)
    }

    fun authenticate(phoneNumber: String, rawPassword: String): UserEntity? {
        val user = usersRepository.findByPhoneNumber(phoneNumber)
            ?: return null

        val isPasswordValid = passwordService.verifyPassword(rawPassword, user.passwordHash)
        return if (isPasswordValid) user else null
    }

    fun findByPhoneNumber(phoneNumber: String): UserEntity? =
        usersRepository.findByPhoneNumber(phoneNumber)
}