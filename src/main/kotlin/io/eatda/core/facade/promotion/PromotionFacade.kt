package io.eatda.core.facade.promotion

import io.eatda.api.dto.promotion.request.CreatePromotionRequest
import io.eatda.api.dto.promotion.request.UpdatePromotionDateRequest
import io.eatda.api.dto.promotion.request.UpdatePromotionNameRequest
import io.eatda.api.dto.promotion.response.GetPromotionResponse
import io.eatda.api.dto.promotion.response.GetPromotionsResponse
import io.eatda.api.dto.common.IdResponse
import io.eatda.api.dto.promotion.params.PromotionParams
import io.eatda.core.service.promotion.PromotionConditionService
import io.eatda.core.service.promotion.PromotionEffectService
import io.eatda.core.service.promotion.PromotionRuleService
import io.eatda.core.service.promotion.PromotionService
import io.eatda.infrastructure.persistance.entity.promotion.PromotionStatus
import io.eatda.infrastructure.persistance.entity.user.User
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Component
class PromotionFacade(
    private val promotionService: PromotionService,
    private val promotionRuleService: PromotionRuleService,
    private val promotionConditionService: PromotionConditionService,
    private val promotionEffectService: PromotionEffectService,
) {
    @Transactional
    fun registerPromotion(request: CreatePromotionRequest, user: User): IdResponse {
        val promotion = promotionService.createPromotion(request, user)

        return IdResponse.of(promotion.id)
    }

    fun getPromotions(params: PromotionParams): GetPromotionsResponse {
        val promotions = promotionService.getPromotions(params)

        return GetPromotionsResponse.of(promotions)
    }

    fun getPromotionDetail(id: UUID): GetPromotionResponse {
        val promotion = promotionService.getPromotionById(id)

        return GetPromotionResponse.of(promotion)
    }

    @Transactional
    fun modifyPromotionName(
        id: UUID,
        request: UpdatePromotionNameRequest,
        user: User
    ): IdResponse {
        val promotion = promotionService.updatePromotionName(
            promotionService.getPromotionById(id),
            request,
            user
        )

        return IdResponse.of(promotion.id)
    }

    @Transactional
    fun modifyPromotionDate(
        id: UUID,
        request: UpdatePromotionDateRequest,
        user: User
    ): IdResponse {
        val promotion = promotionService.updatePromotionDate(
            promotionService.getPromotionById(id),
            request,
            user
        )

        return IdResponse.of(promotion.id)
    }

    @Transactional
    fun deletePromotion(
        id: UUID,
        user: User
    ): IdResponse {
        val promotion = promotionService.getPromotionById(id)
        val rules = promotion.rules

        val ruleIds = rules.map { it.id }
        val conditionIds = rules.flatMap { rule -> rule.conditions.map { it.id } }
        val effectIds = rules.flatMap { rule -> rule.effects.map { it.id } }

        if (conditionIds.isNotEmpty()) promotionConditionService.deletePromotionConditions(conditionIds)
        if (effectIds.isNotEmpty())promotionEffectService.deletePromotionEffects(effectIds)
        if (rules.isNotEmpty())promotionRuleService.deletePromotionRules(ruleIds)

        val deletedPromotion = promotionService.deletePromotion(promotion, user)

        return IdResponse.of(deletedPromotion.id)
    }

    @Transactional
    fun modifyPromotionStatus(
        id: UUID,
        status: PromotionStatus,
        user: User
    ): IdResponse {
        val promotion = promotionService.updatePromotionStatus(
            promotionService.getPromotionById(id),
            status,
            user
        )

        return IdResponse.of(promotion.id)
    }
}