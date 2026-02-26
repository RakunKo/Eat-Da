package io.eatda.core.facade.store

import io.eatda.api.dto.common.IdResponse
import io.eatda.api.dto.store.params.StoreParams
import io.eatda.api.dto.store.request.CreateStoreRequest
import io.eatda.api.dto.store.request.UpdateStoreRequest
import io.eatda.api.dto.store.response.GetStoresResponse
import io.eatda.core.common.exception.ApiException
import io.eatda.core.common.status.StoreErrorStatus
import io.eatda.core.service.store.StoreService
import io.eatda.infrastructure.persistance.entity.store.Store
import io.eatda.infrastructure.persistance.entity.user.User
import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import java.util.UUID

@Component
class StoreFacade(
    private val storeService: StoreService
) {
    @Transactional
    fun register(
        request: CreateStoreRequest,
        user: User
    ): IdResponse {
        val new = storeService.registerStore(request, user)

        return IdResponse.of(new.id);
    }

    fun getStores(
        params: StoreParams,
        user: User
    ): GetStoresResponse {
        val stores = storeService.getUserStores(
            params.name,
            params.isOpen,
            user
        )

        return GetStoresResponse.of(stores, user)
    }

    @Transactional
    fun updateStore(
        request: UpdateStoreRequest,
        id: UUID,
        user: User
    ): IdResponse {
        val store = storeService.getStoreById(id)

        if(store.owner.id != user.id) throw ApiException(StoreErrorStatus.STORE_IS_NOT_OWNED, user.id)
        val new = storeService.updateStore(request, store, user)

        return IdResponse.of(new.id)
    }
}