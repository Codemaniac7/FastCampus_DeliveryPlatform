package org.delivery.api.common.exception;

import lombok.Getter;
import org.delivery.api.common.error.ErrorCodeIfs;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionIfs{

    private final ErrorCodeIfs errorCodeIfs;
    private final String errorDescrption;
    public ApiException(ErrorCodeIfs errorCodeIfs) {
        super(errorCodeIfs.getDescrption());
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescrption = errorCodeIfs.getDescrption();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, String errorDescrption) {
        super(errorCodeIfs.getDescrption());
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescrption = errorDescrption;
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx) {
        super(tx);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescrption = errorCodeIfs.getDescrption();
    }

    public ApiException(ErrorCodeIfs errorCodeIfs, Throwable tx, String errorDescrption) {
        super(tx);
        this.errorCodeIfs = errorCodeIfs;
        this.errorDescrption = errorDescrption;
    }
}
