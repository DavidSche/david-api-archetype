package ${groupId}.common.constant;

/**
 * Defining response statuses
 * 
 * @Author:David.che
 * 
 * @version: ${version}
 * 
 * @category ${groupId}:${artifactId}
 *
 */

public enum ResponseStatus {

	SUCCESSFUL(0), FAIL(1);

	private int status;

	private ResponseStatus(int status) {
		this.status = status;
	}

	public int getStatus() {
		return status;
	}

}
