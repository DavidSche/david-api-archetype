package ${groupId}.common.exception;

/**
 * 系统异常
 *
 * @Author:David.che
 * @version: ${version}
 *
 * ${groupId}:${artifactId}
 *
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = -6916154462432027437L;

    public FebsException(String message) {
        super(message);
    }
}
