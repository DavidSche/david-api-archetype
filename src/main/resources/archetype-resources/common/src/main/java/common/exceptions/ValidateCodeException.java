package ${groupId}.common.exception;

/**
 * 验证码类型异常
 *
 * @Author:David.che
 * @version: ${version}
 *
 * ${groupId}:${artifactId}
 *
 */
public class ValidateCodeException extends Exception {

    private static final long serialVersionUID = 7514854456967620043L;

    public ValidateCodeException(String message) {
        super(message);
    }
}
