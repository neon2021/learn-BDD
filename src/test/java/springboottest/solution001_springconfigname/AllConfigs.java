package springboottest.solution001_springconfigname;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

/**
 * TODO: function description
 *
 * @author neon2021 on 2022/7/4
 */
//@ContextConfiguration
//@ActiveProfiles("special")
//@Profile("special")
@Configuration
@ComponentScans(
        @ComponentScan(basePackages = {"springboottest"})
)
//@Import({Solution001Config.class})
public class AllConfigs {

}
