package io.promoflow.api.dto.promotion.request

import io.promoflow.api.dto.promotion.sub.ConditionDto
import io.promoflow.api.dto.promotion.sub.EffectDto
import jakarta.validation.Valid
import jakarta.validation.constraints.NotNull

data class UpdatePromotionRuleRequest(
    @field:NotNull(message = "Promotion rule's priority must not be null")
    val priority: Int,

    @field:Valid
    val conditions: List<ConditionDto> = emptyList(),

    @field:Valid
    @field:NotNull(message = "Promotion effect must be defined")
    val effect: EffectDto
)
