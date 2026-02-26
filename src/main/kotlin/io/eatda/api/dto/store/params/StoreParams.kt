package io.eatda.api.dto.store.params

import io.eatda.api.dto.common.Pagination

data class StoreParams(
    val name: String?,
    val isOpen: Boolean?,
): Pagination()
