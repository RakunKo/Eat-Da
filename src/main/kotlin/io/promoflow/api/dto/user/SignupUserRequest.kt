package io.promoflow.api.dto.user

import io.promoflow.core.validator.annotations.NotSpecial
import io.promoflow.infrastructure.persistance.entity.user.User
import io.promoflow.infrastructure.persistance.entity.user.UserStatus
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class SignupUserRequest(
    @field:NotBlank(message = "Name must not be blank")
    @field:Size(max = 20, message = "Name must be at most 20 characters")
    @field:NotSpecial(message = "Name must not contain special characters")
    val name: String,

    @field:NotBlank(message = "Email must not be blank")
    @field:Size(max = 50, message = "Email must be at most 50 characters")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Provider Id must not be blank")
    @field:Size(max = 100, message = "Provider Id must be at most 100 characters")
    val providerId: String,

    @field:Size(max = 255, message = "Profile image must be at most 255 characters")
    val profileImg: String?,

    @field:NotBlank(message = "Phone number must not be blank")
    @field:Pattern(
        regexp = "^01[016789][0-9]{7,8}$",
        message = "Phone number is invalid form."
    )
    val phoneNumber: String
) {
    fun toEntity(): User =
        User(
            name = name,
            email = email,
            providerId = providerId,
            activeStatus =  UserStatus.ACTIVE,
            phoneNumber = phoneNumber
        )
}
