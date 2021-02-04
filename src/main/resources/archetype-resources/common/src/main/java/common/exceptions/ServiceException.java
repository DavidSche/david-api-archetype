/*
 * Copyright (c) 2019-2029, David.che.
 */

package ${groupId}.common.exceptions;

import ${groupId}.common.response.IStateCode;
import ${groupId}.common.response.R;
import org.springframework.lang.Nullable;

/**
 * 业务异常
 *
 * @author  David.che.
 */
public class ServiceException extends RuntimeException {
	private static final long serialVersionUID = 2359767895161832954L;

	@Nullable
	private final R<?> result;

	public ServiceException(R<?> result) {
		super(result.getMsg());
		this.result = result;
	}

	public ServiceException(IResultCode rCode) {
		this(rCode, rCode.getMsg());
	}

	public ServiceException(IResultCode rCode, String message) {
		super(message);
		this.result = R.fail(rCode, message);
	}

	public ServiceException(String message) {
		super(message);
		this.result = null;
	}

	public ServiceException(Throwable cause) {
		this(cause.getMessage(), cause);
	}

	public ServiceException(String message, Throwable cause) {
		super(message, cause);
		doFillInStackTrace();
		this.result = null;
	}

	@Nullable
	@SuppressWarnings("unchecked")
	public <T> R<T> getResult() {
		return (R<T>) result;
	}

	/**
	 * 提高性能
	 * @return Throwable
	 */
	@Override
	public Throwable fillInStackTrace() {
		return this;
	}

	public Throwable doFillInStackTrace() {
		return super.fillInStackTrace();
	}

}
