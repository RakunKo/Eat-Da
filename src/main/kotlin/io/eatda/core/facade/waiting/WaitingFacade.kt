package io.eatda.core.facade.waiting

import io.eatda.api.dto.common.IdResponse
import io.eatda.api.dto.waiting.request.CreateWaitingRequest
import io.eatda.api.dto.waiting.response.GetWaitingStatusResponse
import io.eatda.core.common.exception.ApiException
import io.eatda.core.common.status.WaitingErrorStatus
import io.eatda.core.service.store.StoreService
import io.eatda.core.service.waiting.WaitingService
import io.eatda.infrastructure.persistance.entity.user.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class WaitingFacade(
    private val waitingService: WaitingService,
    private val storeService: StoreService
) {
    @Transactional
    fun register(
        request: CreateWaitingRequest,
        id: UUID,
        user: User
    ): IdResponse {
        val store = storeService.getStoreById(id)

        user.isConsumer()

        val waiting = waitingService.registerWaiting(
            store, user, request.partySize
        )

        return IdResponse.of(waiting.id)
    }

    fun getWaitingStatus(
        id: UUID,
        user: User
    ): GetWaitingStatusResponse {
        val store = storeService.getStoreById(id)
        val waiting = waitingService.getWaitingByUser(store.id, user.id)

        val myRank = waitingService.getWaitingRank(store, user)
        val totalCount = waitingService.getWaitingCount(store)

        return GetWaitingStatusResponse.of(store, waiting, myRank, totalCount)
    }

    @Transactional
    fun cancelWaiting(
        id: UUID,
        user: User
    ): IdResponse {
        val store = storeService.getStoreById(id)
        val waiting = waitingService.cancelWaiting(store, user)

        //알림

        return IdResponse.of(waiting.id)
    }

    @Transactional
    fun callWaiting(
        id: UUID,
        user: User
    ): IdResponse {
        user.isOwner()
        val store = storeService.getStoreById(id)
        if(user.id != store.owner.id) throw ApiException(WaitingErrorStatus.OWNER_ACCESS_DENIED)

        val waiting = waitingService.callWaiting(store)

        return IdResponse.of(waiting.id)
    }

}