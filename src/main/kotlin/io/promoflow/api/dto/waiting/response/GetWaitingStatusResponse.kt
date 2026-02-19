package io.promoflow.api.dto.waiting.response

import io.promoflow.infrastructure.persistance.entity.store.Store
import io.promoflow.infrastructure.persistance.entity.waiting.Waiting
import java.time.Duration
import java.time.Instant
import java.util.UUID

data class GetWaitingStatusResponse(
    val store: GetWaitingStoreResponse,
    val id: UUID,
    val myRank: Long,
    val totalCount: Long,
    val createdAt: Instant,
    val waitingTime: Int
) {
    data class GetWaitingStoreResponse(
        val id: UUID,
        val name: String
    ) {
        companion object {
            fun of(store: Store) = GetWaitingStoreResponse(
                id = store.id,
                name = store.name
            )
        }
    }

    companion object {
        fun of(store: Store, waiting: Waiting, myRank: Long, totalCount: Long) = GetWaitingStatusResponse(
            store = GetWaitingStoreResponse.of(store),
            id = waiting.id,
            myRank = myRank,
            totalCount = totalCount,
            createdAt = waiting.createdAt,
            waitingTime = Duration.between(
                waiting.createdAt,
                Instant.now()
            ).toMinutes().toInt()
        )
    }
}
