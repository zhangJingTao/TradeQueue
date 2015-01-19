package exception;

/**
 * init配置异常.
 * 
 * @author harry.zu
 *
 */
public final class IniConfigException extends RuntimeException {
	/**
	 * 构�?�方�?.
	 * 
	 * @param message 异常消息
	 */
	public IniConfigException(final String message) {
		super(message);
	}

	/**
	 * 构�?�方�?.
	 * 
	 * @param message 异常消息
	 * @param cause 父异�?
	 */
	public IniConfigException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
