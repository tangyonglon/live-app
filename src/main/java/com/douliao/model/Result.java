package com.douliao.model;

public class Result <T> {
	 /**
     * true, 执行成功
     */
    private boolean success = true;

    /**
     * 承载的数据
     */
    private T object;

    /**
     * 错误码
     */
    private int errorCode;

    /**
     * 错误value提示信息， 否则为null
     */
    private String errorMsg;

    /**
     * 成功提示
     */
    private String successMsg;

    /**
     * 创建result对象
     *
     * @param <T>
     * @return
     */
    public static <T> Result<T> newResult() {
        return new Result<T>();
    }

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getSuccessMsg() {
		return successMsg;
	}

	public void setSuccessMsg(String successMsg) {
		this.successMsg = successMsg;
	}
	
}
