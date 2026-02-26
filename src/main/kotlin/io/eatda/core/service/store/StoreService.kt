package io.eatda.core.service.store

import io.eatda.api.dto.store.request.CreateStoreRequest
import io.eatda.api.dto.store.request.UpdateStoreRequest
import io.eatda.core.common.exception.ApiException
import io.eatda.core.common.status.StoreErrorStatus
import io.eatda.infrastructure.persistance.entity.store.Store
import io.eatda.infrastructure.persistance.entity.user.User
import io.eatda.infrastructure.persistance.repository.store.StoreRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class StoreService(
    private val storeRepository: StoreRepository
) {
    fun registerStore(
        request: CreateStoreRequest,
        user: User
    ): Store {
        user.isOwner()
        val store = Store.create(request, user)

        return storeRepository.save(store);
    }

    fun getUserStores(
        name: String?,
        isOpen: Boolean?,
        user: User
    ): List<Store> {
        user.isOwner()
        return storeRepository.findUserStores(
            name = name,
            isOpen = isOpen,
            owner = user
        )
    }

    fun updateStore(
        request: UpdateStoreRequest,
        store: Store,
        user: User
    ): Store {
        user.isOwner()
        store.updateStore(request);

        return store;
    }

    fun getStoreById(id: UUID): Store =  storeRepository.findByIdOrNull(id)
        ?: throw ApiException(StoreErrorStatus.STORE_IS_NOT_EXIST)
}