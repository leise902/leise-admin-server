package com.leise.provider.admin.base.exception;

import com.leise.core.base.exception.CRException;

public class AdminBizException extends CRException {

    private static final long serialVersionUID = -7644216949155751833L;

    public AdminBizException(String errorCode) {
        super(errorCode);
    }

}
