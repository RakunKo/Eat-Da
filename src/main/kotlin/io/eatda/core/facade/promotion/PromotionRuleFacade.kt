package io.eatda.core.facade.promotion

import io.eatda.api.dto.promotion.request.CreatePromotionRuleRequest
import io.eatda.api.dto.promotion.response.GetPromotionRulesResponse
import io.eatda.api.dto.common.IdResponse
import io.eatda.core.service.promotion.PromotionConditionService
import io.eatda.core.service.promotion.PromotionEffectService
import io.eatda.core.service.promotion.PromotionRuleService
import io.eatda.core.service.promotion.PromotionService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class PromotionRuleFacade(
    private val promotionService: PromotionService,
    private val promotionRuleService: PromotionRuleService,
    private val promotionConditionService: PromotionConditionService,
    private val promotionEffectService: PromotionEffectService,
) {
    @Transactional
    fun registerPromotionRule(request: CreatePromotionRuleRequest, id: UUID): IdResponse {
        val promotion = promotionService.getPromotionById(id)
        val promotionRule = promotionRuleService.createPromotionRule(request, promotion)

        return IdResponse.of(promotionRule.id)
    }

    fun getPromotionRules(id: UUID): GetPromotionRulesResponse {
        val promotion = promotionService.getPromotionById(id)
        val promotionRules = promotionRuleService.getPromotionRulesByPromotion(promotion)

        return GetPromotionRulesResponse.of(promotion.id, promotionRules)
    }

}