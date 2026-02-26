package io.eatda.infrastructure.persistance.repository.store.custom

import io.eatda.infrastructure.persistance.entity.store.Store
import io.eatda.infrastructure.persistance.entity.user.User

interface StoreDslRepository {
    fun findUserStores(
        name: String?,
        isOpen: Boolean?,
        owner: User,
    ): List<Store>
}