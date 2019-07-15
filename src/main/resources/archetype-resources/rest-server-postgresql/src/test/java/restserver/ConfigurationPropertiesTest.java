package ${groupId}.restserver;

import ${groupId}.restserver.model.PersonsList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.context.annotation.PropertySource;

@SpringBootTest(properties = {
        "spring.config.location=classpath:/bootstrap.yml,classpath:/global.yml"
})
@RunWith(SpringRunner.class)
public class ConfigurationPropertiesTest {

    @Autowired
    PersonsList personsList;

    @Test
    public void test() {
        Assert.assertEquals(3, personsList.getPersons().size());
        Assert.assertEquals("John", personsList.getPersons().get(0).getFirstName());
        Assert.assertEquals("Tom", personsList.getPersons().get(1).getFirstName());
        Assert.assertEquals("Kate", personsList.getPersons().get(2).getFirstName());
    }

}
