package ${groupId}.restserver.repository;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ${groupId}.restserver.model.Employee;



/**
 * EmployeeRepository: DB Repository for working with test
 * 
 * @Author:David.che :
 * 
 * @version ${version}
 * 
 *          ${groupId}:${artifactId}
 *
 */

@Slf4j
@Component
public class EmployeeRepository {

	private List<Employee> employees = new ArrayList<>();

	public EmployeeRepository() {
		super();
		Employee employee1 = new Employee(Long.valueOf(1), Long.valueOf(2), "david", 16, "jn_china");
		Employee employee2 = new Employee(Long.valueOf(2), Long.valueOf(2), "jack", 13, "jn_china");
		employees.add(employee1);
		employees.add(employee2);

	}

	public Employee add(Employee employee) {
//        employee.setId((long) (employees.size() + 1));
		employees.add(employee);
		return employee;
	}

	public Employee findById(String id) {
		Optional<Employee> employee = employees.stream().filter(a -> a.getId().equals(id)).findFirst();
		if (employee.isPresent())
			return employee.get();
		else
			return null;
	}

	public List<Employee> findAll() {
		return employees;
	}

	public List<Employee> findByDepartment(Long departmentId) {
		return employees.stream().filter(a -> a.getDepartmentId().equals(departmentId)).collect(Collectors.toList());
	}

	public List<Employee> findByOrganization(Long organizationId) {
		return employees.stream().filter(a -> a.getOrganizationId().equals(organizationId)).collect(Collectors.toList());
	}

}
