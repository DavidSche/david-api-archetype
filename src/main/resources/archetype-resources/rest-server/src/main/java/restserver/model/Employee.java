package ${groupId}.restserver.model;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.util.UUID;

/**
 * Employee bo : bo for working with Employee 
 * 
 * @Author:David.che :
 * 
 * @version ${version}
 * 
 *          ${groupId}:${artifactId}
 *
 * @see lombok.Data Generates getters for all fields, a useful toString method, and hashCode and equals implementations that check
 * 
 */

@Slf4j
@Data
public class Employee {

	//	private Long id;
	@NotEmpty
	private String id = UUID.randomUUID().toString();
	private Long organizationId;
	private Long departmentId;
	private String name;
	private int age;
	private String position;

	public Employee() {

	}

	public Employee(Long organizationId, Long departmentId, String name, int age, String position) {
		this.organizationId = organizationId;
		this.departmentId = departmentId;
		this.name = name;
		this.age = age;
		this.position = position;
	}


	@Override
	public String toString() {
		return "Employee [id=" + id + ", organizationId=" + organizationId + ", departmentId=" + departmentId
				+ ", name=" + name + ", position=" + position + "]";
	}

}
