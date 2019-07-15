package ${groupId}.restserver.model;

import lombok.extern.slf4j.Slf4j;
import lombok.Data;

/**
 * Person bo : bo for working with Person 
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
public class Person {

    private String firstName;
    private String lastName;
    private int age;

  
}
