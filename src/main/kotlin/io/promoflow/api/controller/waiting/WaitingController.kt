package io.promoflow.api.controller.waiting

import io.promoflow.api.dto.common.IdResponse
import io.promoflow.api.dto.promotion.response.GetPromotionRulesResponse
import io.promoflow.api.dto.waiting.request.CreateWaitingRequest
import io.promoflow.api.dto.waiting.response.GetWaitingStatusResponse
import io.promoflow.core.facade.WaitingFacade
import io.promoflow.core.handler.handleApi
import io.promoflow.infrastructure.persistance.entity.user.User
import io.promoflow.infrastructure.security.annotations.AuthUser
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
@RequestMapping("/api/v1/waitings")
class WaitingController(
    private val waitingFacade: WaitingFacade
) {
    @PostMapping("/{storeId}")
    fun createWaitingApi(
        @Valid @RequestBody body: CreateWaitingRequest,
        @PathVariable storeId: UUID,
        @AuthUser user: User
    ): ResponseEntity<IdResponse> =
        handleApi(
            status = HttpStatus.CREATED,
            supplier = { waitingFacade.register(body, storeId, user) },
        )

    @GetMapping("/{storeId}/status")
    fun getWaitingStatusApi(
        @PathVariable storeId: UUID,
        @AuthUser user: User
    ): ResponseEntity<GetWaitingStatusResponse> =
        handleApi(
            status = HttpStatus.OK,
            supplier = { waitingFacade.getWaitingStatus(storeId, user) },
        )

}