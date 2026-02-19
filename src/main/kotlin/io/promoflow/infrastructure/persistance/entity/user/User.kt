package io.promoflow.infrastructure.persistance.entity.user

import io.promoflow.core.common.exception.ApiException
import io.promoflow.core.common.status.UserErrorStatus
import io.promoflow.infrastructure.persistance.base.BaseEntity
import jakarta.persistence.*

@Entity
@Table(name = "users")
class User(
    @Column(nullable = false, length = 25)
    var name: String,

    @Column(nullable = false, length = 100)
    var email: String,

    @Column(nullable = false, length = 20)
    var phoneNumber: String,

    @Column(nullable = false)
    var providerId: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var activeStatus: UserStatus = UserStatus.ACTIVE,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var type: UserType = UserType.CONSUMER
): BaseEntity() {
    fun isConsumer() {
        if(type != UserType.CONSUMER) throw ApiException(UserErrorStatus.USER_IS_NOT_CONSUMER)
    }
    fun isOwner() {
        if(type != UserType.OWNER) throw ApiException(UserErrorStatus.USER_IS_NOT_OWNER)
    }
}