package ${groupId}.restserver.model;

/**
 * test model :Greeting
 * 
 * @Author:David.che version: ${version} Spring Boot APP
 *                   ${groupId}:${artifactId}
 *
 */

public class Greeting {

    private final long id;
    private final String content;

    public Greeting(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}