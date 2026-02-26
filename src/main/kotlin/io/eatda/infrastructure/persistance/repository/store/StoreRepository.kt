package io.eatda.infrastructure.persistance.repository.store

import io.eatda.infrastructure.persistance.entity.store.Store
import io.eatda.infrastructure.persistance.repository.store.custom.StoreDslRepository
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface StoreRepository : JpaRepository<Store, UUID>, StoreDslRepository {
}
