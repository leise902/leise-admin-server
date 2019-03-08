package com.leise.provider.admin.base.exception;

import com.leise.core.base.exception.CRException;

public class RuleEngineException extends CRException {

    private static final long serialVersionUID = -7644216949155751833L;

    public RuleEngineException(String errorCode) {
        super(errorCode);
    }

}
