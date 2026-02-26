package io.eatda.api.dto.store.response

import io.eatda.infrastructure.persistance.entity.store.Store
import io.eatda.infrastructure.persistance.entity.user.User
import java.util.UUID

data class GetStoresResponse(
    val owner: GetStoreUserResponse,
    val stores: List<GetStoreResponse>
) {
    data class GetStoreResponse(
        val id: UUID,
        val name: String,
        val maxWaitingSize: Int,
        val isOpen: Boolean
    ){
        companion object {
            fun of(store: Store) = GetStoreResponse(
                id = store.id,
                name = store.name,
                maxWaitingSize = store.maxWaitingSize,
                isOpen = store.isOpen
            )
        }
    }

    data class GetStoreUserResponse(
        val id: UUID,
        val name: String
    ){
        companion object {
            fun of(owner: User) = GetStoreUserResponse(
                id = owner.id,
                name = owner.name
            )
        }
    }

    companion object {
        fun of(stores: List<Store>, owner: User) = GetStoresResponse(
            owner = GetStoreUserResponse.of(owner),
            stores = stores.map { GetStoreResponse.of(it) }
        )
    }
}
