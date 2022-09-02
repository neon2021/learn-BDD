package springboottest.solution001_springconfigname;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import springboottest.bases.SpringConfigNameIsSpecialBaseTest;

import javax.annotation.Resource;
import java.util.Map;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * TODO: function description
 *
 * @author neon2021 on 2022/7/4
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AllConfigs.class, webEnvironment = NONE)
public class AllConfigsTest extends SpringConfigNameIsSpecialBaseTest {
    @Resource(name = "testResource")
    Map<String, String> testResourceMap;
    @Autowired
    Environment environment;

    @Test
    public void test() {
        System.out.println(testResourceMap);
        System.out.println(environment);
    }
}
