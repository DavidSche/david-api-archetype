/*
 * Copyright (c) 2019-2029, David.che.
 */

package ${groupId}.common.response;

import java.io.Serializable;

/**
 * 状态码接口
 * @Author:David.che
 * @version: ${version}
 * @category ${groupId}:${artifactId}
 */
public interface IStateCode extends Serializable {
	/**
	 * 返回的code码
	 * @return code
	 */
	int getCode();
	/**
	 * 返回的消息
	 * @return 消息
	 */
	String getMsg();
}
