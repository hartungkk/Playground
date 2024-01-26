import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Clock;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiTestWithoutPojo {

    @Test
    public void testCompareDateWhenUpdate(){
        Specification.installSpecification(Specification.requestSpecification("https://reqres.in"),Specification.responseSpecification(200));
        Map<String,String> user = new HashMap<String,String>();
        user.put("name","morpheus");
        user.put("job","zion resident");

        String response =  given()
                .body(user)
                .put("/api/users/2")
                .then().log().all().extract().body().jsonPath().getString("updatedAt");
        String localtime = Clock.systemUTC().instant().toString();
        Assert.assertEquals(response.replaceAll("(.{5})$",""),localtime.replaceAll("(.{5})$",""));
    }

    @Test()
    public void testsForGetUsers(){
        Specification.installSpecification(Specification.requestSpecification("https://reqres.in"),Specification.responseSpecification(200));
        Response response = given()
                .when()
                .get("/api/users?page=2")
                .then().log().all()
                .body("page",equalTo(2))
                .body("data.id",notNullValue())
                .extract().response();

        JsonPath json = response.jsonPath();
        List<String> emails = json.getList("data.email");
        for (int i =0;emails.size()<i;i++) {
            Assert.assertTrue(emails.stream().allMatch(x->x.endsWith("@reqres.in")));
        }
    }
    @Test
    public void test(){
        Specification.installSpecification(Specification.requestSpecification("https://reqres.in"),Specification.responseSpecification(200));
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";

        Map<String,String> userdata = new HashMap<String,String>();
        userdata.put("email","eve.holt@reqres.in");
        userdata.put("password","pistol");
                given()
                        .body(userdata)
                        .when()
                        .post("/api/register")
                        .then()
                        .body("id",equalTo(id))
                        .body("token",equalTo(token));
    }
}
