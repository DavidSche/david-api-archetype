package ${groupId}.common.exceptions;

/**
 * @Author:David.che
 * @version: ${version}
 * <p>
 * ${groupId}:${artifactId}
 */

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String s) {
        super(s);
    }
}