package springboottest.solution001_springconfigname;

import com.google.common.collect.ImmutableMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.Map;

/**
 * TODO: function description
 *
 * @author neon2021 on 2022/7/4
 */
@Configuration
@Profile("special")
public class Solution001Config {
    @Bean(name = "testResource")
    public Map<String, String> testResourceMap() {
        return ImmutableMap.of("resource", "springconfigname");
    }
}
