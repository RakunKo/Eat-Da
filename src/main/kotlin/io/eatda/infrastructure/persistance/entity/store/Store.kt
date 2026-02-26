package io.eatda.infrastructure.persistance.entity.store

import io.eatda.api.dto.store.request.CreateStoreRequest
import io.eatda.api.dto.store.request.UpdateStoreRequest
import io.eatda.infrastructure.persistance.base.BaseEntity
import io.eatda.infrastructure.persistance.entity.user.User
import jakarta.persistence.*
import java.time.Instant

@Entity
class Store(
    @Column(nullable = false, length = 50)
    var name: String,

    @Column(nullable = false, length = 500)
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var owner : User,

    @Column(nullable = false)
    var maxWaitingSize: Int = 50,

    @Column(nullable = false)
    var isOpen: Boolean = false,
): BaseEntity() {

    companion object {
        fun create(request: CreateStoreRequest, user: User): Store {
            return Store(
                name = request.name,
                isOpen = request.isOpen,
                maxWaitingSize = request.maxWaitingSize,
                description = request.description,
                owner = user
            )
        }
    }

    //fun setOwner(user: User) {
    //    owner = user;
    //}

    fun updateStore(request: UpdateStoreRequest) {
        request.name?.let { name = it }
        request.maxWaitingSize?.let { maxWaitingSize = it }
        request.isOpen?.let { isOpen = it }
        request.description?.let { description = it }
    }

    fun open() {
        isOpen = true
        updatedAt = Instant.now()
    }

    fun close() {
        isOpen = false
        updatedAt = Instant.now()
    }
}
