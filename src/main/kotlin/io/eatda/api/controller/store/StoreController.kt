package io.eatda.api.controller.store

import io.eatda.api.dto.common.IdResponse
import io.eatda.api.dto.store.params.StoreParams
import io.eatda.api.dto.store.request.CreateStoreRequest
import io.eatda.api.dto.store.request.UpdateStoreRequest
import io.eatda.api.dto.store.response.GetStoresResponse
import io.eatda.core.facade.store.StoreFacade
import io.eatda.core.handler.handleApi
import io.eatda.infrastructure.persistance.entity.user.User
import io.eatda.infrastructure.security.annotations.AuthUser
import jakarta.validation.Valid
import org.springdoc.core.annotations.ParameterObject
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/stores")
class StoreController(
    private val storeFacade: StoreFacade
) {
    @PostMapping
    fun createStoreApi(
        @Valid @RequestBody body: CreateStoreRequest,
        @AuthUser user: User
    ): ResponseEntity<IdResponse> =
        handleApi(
            status = HttpStatus.CREATED,
            supplier = { storeFacade.register(body, user) },
        )

    @GetMapping
    fun getStoresApi(
        @Valid @ParameterObject params: StoreParams,
        @AuthUser user: User
    ): ResponseEntity<GetStoresResponse> =
        handleApi(
            status = HttpStatus.OK,
            supplier = { storeFacade.getStores(params, user) },
        )

    @PatchMapping("/{storeId}")
    fun updateStoreApi(
        @Valid @RequestBody body: UpdateStoreRequest,
        @PathVariable storeId: UUID,
        @AuthUser user: User
    ): ResponseEntity<IdResponse> =
        handleApi(
            status = HttpStatus.OK,
            supplier = { storeFacade.updateStore(body, storeId, user) },
        )
}