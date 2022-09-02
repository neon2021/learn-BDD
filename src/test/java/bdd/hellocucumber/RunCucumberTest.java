package bdd.hellocucumber;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(plugin = {"pretty"}, features = {"classpath:bdd/hellocucumber/is_it_friday_yet.feature"})
public class RunCucumberTest {
}
