package io.eatda.core.common.status

import io.eatda.core.common.code.BaseErrorCode
import org.springframework.http.HttpStatus

enum class WaitingErrorStatus(
    override val status: HttpStatus,
    override val message: String,
) : BaseErrorCode {
    WAITING_IS_ALREADY_EXISTED(HttpStatus.CONFLICT, "Waiting is already existed"),
    NO_MORE_WAITING(HttpStatus.NOT_FOUND, "Waiting is not existed"),
    WAITING_IS_NOT_EXIST(HttpStatus.NOT_FOUND, "Waiting is not found"),
    WAITING_STATUS_IS_NOT_WAITING(HttpStatus.BAD_REQUEST, "Waiting status is not WAITING"),
    OWNER_ACCESS_DENIED(HttpStatus.FORBIDDEN, "Only the store owner is authorized to process entries."),
    ;

}
