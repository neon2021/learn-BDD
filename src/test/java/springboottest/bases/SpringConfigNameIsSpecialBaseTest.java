package springboottest.bases;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import springboottest.solution001_springconfigname.AllConfigs;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;

/**
 * TODO: function description
 *
 * @author neon2021 on 2022/7/4
 */
@ActiveProfiles("special")
public abstract class SpringConfigNameIsSpecialBaseTest {
    @BeforeClass
    public static void init() {
        System.setProperty("spring.config.name", "special");
    }

//    public abstract static String getSpringConfigName(); // illegal statement, abstract conflicts with static
//
//    @BeforeClass
//    public static void init() {
//        System.setProperty("spring.config.name", getSpringConfigName());
//    }

    @AfterClass
    public static void destroy() {
        System.clearProperty("spring.config.name");
    }
}
