package ${groupId}.common.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @Author:David.che
 * @version: ${version}
 *
 * ${groupId}:${artifactId}
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorMessage {

    private Date date;
    private String message;
    private String description;

}