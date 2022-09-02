package bdd.hellocucumber;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * TODO: function description
 *
 * @author neon2021 on 2022/5/1
 */
public class MyStepdefs {
    @Given("今天是<周几{int}>")
    public void 今天是周几(int arg0) {
    }

    @When("我问今天是否为<周几{int}>")
    public void 我问今天是否为周几(int arg0) {
    }

    @Then("我应该被告知的是——<结果YN>")
    public void 我应该被告知的是结果YN() {
    }
}