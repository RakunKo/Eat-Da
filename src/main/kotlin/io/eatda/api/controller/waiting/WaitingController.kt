package io.eatda.api.controller.waiting

import io.eatda.api.dto.common.IdResponse
import io.eatda.api.dto.waiting.request.CreateWaitingRequest
import io.eatda.api.dto.waiting.response.GetWaitingStatusResponse
import io.eatda.core.facade.waiting.WaitingFacade
import io.eatda.core.handler.handleApi
import io.eatda.infrastructure.persistance.entity.user.User
import io.eatda.infrastructure.security.annotations.AuthUser
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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

    @PatchMapping("/{storeId}/cancel")
    fun cancelWaitingApi(
        @PathVariable storeId: UUID,
        @AuthUser user: User
    ): ResponseEntity<IdResponse> =
        handleApi (
            status = HttpStatus.OK,
            supplier = { waitingFacade.cancelWaiting(storeId, user) },
        )

    @PatchMapping("/{storeId}/enter")
    fun callWaitingApi(
        @PathVariable storeId: UUID,
        @AuthUser user: User
    ): ResponseEntity<IdResponse> =
        handleApi (
            status = HttpStatus.OK,
            supplier = { waitingFacade.callWaiting(storeId, user) },
        )
}