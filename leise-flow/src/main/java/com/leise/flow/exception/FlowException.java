package com.leise.flow.exception;

/**
 * Created by JY-IT-D001 on 2018/7/4.
 */
public class FlowException extends RuntimeException {

    private static final long serialVersionUID = -7644216949155751833L;

    private int errorCode = 999999;

    private Throwable cause;

    public FlowException(String errorMsg) {
        super(errorMsg);
    }

    public FlowException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
    }

    public FlowException(int errorCode, String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorCode = errorCode;
        this.cause = cause;
    }

    public String getErrorMessage() {
        String errorMessage = super.getMessage();
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("[errorCode:").append(this.errorCode).append(", errorMsg:").append(errorMessage).append("]");
        return sbuffer.toString();
    }

    public String getCauseMessage() {
        String errorMessage = super.getMessage();
        StringBuffer sbuffer = new StringBuffer();
        sbuffer.append("[errorCode:").append(this.errorCode).append(", errorMsg:").append(errorMessage).append("]")
                .append(" cause by:");
        if (this.cause != null) {
            sbuffer.append(this.cause.getMessage());
        } else {
            sbuffer.append("系统内部错误，原因未知");
        }
        return sbuffer.toString();
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void setCause(Throwable cause) {
        this.cause = cause;
    }
}
