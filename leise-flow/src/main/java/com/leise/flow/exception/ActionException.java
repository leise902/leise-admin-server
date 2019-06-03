package com.leise.flow.exception;

/**
 * Created by JY-IT-D001 on 2018/7/6.
 */
public class ActionException extends FlowException {

    /**
     * 串行号
     */
    private static final long serialVersionUID = 6197429339940889354L;

    private int errorCode = 899999;

    private Throwable cause;

    public ActionException(String errorMsg) {
        this(899999, errorMsg);
    }

    public ActionException(int errorCode, String errorMsg) {
        super(errorCode, errorMsg);
        this.errorCode = errorCode;
    }

    public ActionException(int errorCode, String errorMsg, Throwable cause) {
        super(errorCode, errorMsg, cause);
        this.errorCode = errorCode;
        this.cause = cause;
    }

    @Override
    public String getErrorMessage() {
        return super.getErrorMessage();
    }

    @Override
    public String getCauseMessage() {
        return super.getCauseMessage();
    }

    @Override
    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    @Override
    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
