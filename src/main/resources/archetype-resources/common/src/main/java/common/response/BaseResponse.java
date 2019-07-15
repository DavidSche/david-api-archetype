package ${groupId}.common.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Defining Base response
 * 
 * @Author:David.che
 * 
 * @version: ${version}
 * 
 * @category ${groupId}:${artifactId}
 *
 */

@Data
@EqualsAndHashCode
public class BaseResponse {

	private String message;

	private int status;

}
