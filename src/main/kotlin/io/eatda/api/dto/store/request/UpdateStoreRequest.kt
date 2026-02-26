package io.eatda.api.dto.store.request

import jakarta.validation.constraints.*

data class UpdateStoreRequest(
    @field:Size(max = 50, message = "Store name must be lower than 50")
    val name: String?,

    @field:Size(max = 1000, message = "Store description must be lower than 1000")
    val description: String?,

    @field:Min(1)
    @field:Max(50)
    val maxWaitingSize: Int?,

    val isOpen: Boolean?,
)