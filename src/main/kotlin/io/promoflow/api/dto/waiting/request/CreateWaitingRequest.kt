package io.promoflow.api.dto.waiting.request

import io.promoflow.core.validator.annotations.MinMax
import io.promoflow.core.validator.annotations.NotSpecial
import io.promoflow.infrastructure.persistance.entity.user.User
import io.promoflow.infrastructure.persistance.entity.user.UserStatus
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

data class CreateWaitingRequest(
    @field:Min(value = 1, message = "Waiting party size must be bigger than 1")
    val partySize: Int,
)
