package integrationtest.realenvironment;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import net.javacrumbs.jsonunit.core.Option;
import org.junit.Test;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;

/**
 * TODO: function description
 *
 * @author neon2021 on 2022/6/5
 */
public class TestRestApi {
    @Test
    public void testJSONApi_isAlive() {
        RestAssured.baseURI = "http://localhost:8080";
        Response isAliveResponse = RestAssured.get("/first-rest-api/is-alive").thenReturn();
        System.out.println(isAliveResponse.body().asString());

        JsonAssertions.assertThatJson(json(isAliveResponse.body().asString()))
                .when(Option.IGNORING_ARRAY_ORDER, Option.IGNORING_EXTRA_FIELDS)
                .isArray()
                .isEqualTo(json("{'a':'1','b':'2'}"));
    }
}
