package io.eatda.api.controller.promotion

import io.eatda.api.dto.promotion.request.CreatePromotionRuleRequest
import io.eatda.api.dto.common.IdResponse
import io.eatda.api.dto.promotion.response.GetPromotionRulesResponse
import io.eatda.core.facade.promotion.PromotionRuleFacade
import io.eatda.core.handler.handleApi
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/api/v1/promotions")
class PromotionRuleController(
    private val promotionRuleFacade: PromotionRuleFacade
) {
    @PostMapping("/{promotionId}/rules")
    fun createPromotionRuleApi(
        @Valid @RequestBody body: CreatePromotionRuleRequest,
        @PathVariable promotionId: UUID,
    ): ResponseEntity<IdResponse> =
        handleApi(
            status = HttpStatus.CREATED,
            supplier = { promotionRuleFacade.registerPromotionRule(body, promotionId) },
        )

    @GetMapping("/{promotionId}/rules")
    fun getPromotionRuleApi(
        @PathVariable promotionId: UUID
    ): ResponseEntity<GetPromotionRulesResponse> =
        handleApi(
            status = HttpStatus.OK,
            supplier = { promotionRuleFacade.getPromotionRules(promotionId) },
        )

}