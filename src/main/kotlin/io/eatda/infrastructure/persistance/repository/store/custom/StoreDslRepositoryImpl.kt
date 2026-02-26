package io.eatda.infrastructure.persistance.repository.store.custom

import com.querydsl.jpa.impl.JPAQueryFactory
import io.eatda.infrastructure.persistance.entity.store.QStore
import io.eatda.infrastructure.persistance.entity.store.Store
import io.eatda.infrastructure.persistance.entity.user.QUser
import io.eatda.infrastructure.persistance.entity.user.User
import io.eatda.infrastructure.persistance.repository.dsl.CommonDsl
import org.springframework.stereotype.Repository

@Repository
class StoreDslRepositoryImpl(
    private val queryFactory: JPAQueryFactory,
): StoreDslRepository, CommonDsl() {

    private val store = QStore.store
    private val user = QUser.user
    override fun findUserStores(name: String?, isOpen: Boolean?, owner: User): List<Store> {
        return queryFactory.selectFrom(store)
            .innerJoin(store.owner, user)
            .where(
                eq(store.owner.id, owner.id),
                eq(store.name, name),
                eq(store.isOpen, isOpen)
            )
            .fetch();
    }
}