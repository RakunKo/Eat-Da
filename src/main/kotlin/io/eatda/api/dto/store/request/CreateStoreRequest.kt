package io.eatda.api.dto.store.request

import jakarta.validation.constraints.*

data class CreateStoreRequest(
    @field:NotBlank
    @field:Size(max = 50, message = "Store name must be lower than 50")
    val name: String,

    @field:NotBlank
    @field:Size(max = 1000, message = "Store description must be lower than 1000")
    val description: String,

    @field:NotNull
    @field:Min(1)
    @field:Max(200)
    val maxWaitingSize: Int = 50,

    @field:NotNull
    val isOpen: Boolean = false,
)