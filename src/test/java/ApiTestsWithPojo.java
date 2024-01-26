import POJO.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;

public class ApiTestsWithPojo {


    @Test
    public void testSuccesfulRegistration(){
        Specification.installSpecification(Specification.requestSpecification("https://reqres.in"),Specification.responseSpecification(200));
        Register user = new Register();
        Integer id = 4;
        String token = "QpwL5tke4Pnpja7X4";


        user.setEmail("eve.holt@reqres.in");
        user.setPassword("pistol");
        SuccesfulRegister succesfulRegister = given()
                .body(user)
                .post("/api/register")
                .then().log().all().extract().as(SuccesfulRegister.class);
        Assert.assertNotNull(succesfulRegister.getId());
        Assert.assertNotNull(succesfulRegister.getToken());
        Assert.assertEquals(id,succesfulRegister.getId());
        Assert.assertEquals(token,succesfulRegister.getToken());
    }

    @Test
    public void testUnsuccesfulRegistration(){
        Specification.installSpecification(Specification.requestSpecification("https://reqres.in"),Specification.responseSpecification(400));
        Register user = new Register();
        user.setEmail("sydney@fife");

        UnsuccesfulRegistration unsuccesfulRegister = given()
                .body(user)
                .post("/api/register")
                .then().log().all().extract().as(UnsuccesfulRegistration.class);
        Assert.assertEquals("Missing password",unsuccesfulRegister.getError());
    }


    @Test
    public void checkAvatarsAndId(){
        Specification.installSpecification(Specification.requestSpecification("https://reqres.in"),Specification.responseSpecification(200));
        List<UserData> userData = given()
                .get("/api/users?page=2")
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data",UserData.class);
        Assert.assertTrue(userData.stream().allMatch(x-> x.getEmail().endsWith("@reqres.in")));
        List<String> avatars = userData.stream().map(UserData::getAvatar).collect(Collectors.toList());
        List<String> ids = userData.stream().map(x->String.valueOf(x.getEmail())).collect(Collectors.toList());
        for (int i = 0; i< avatars.size();i++)
        {
            Assert.assertTrue( avatars.get(i).contains(ids.get(i)));
        }
    }
    @Test
    public void testCompareAndSortByYear(){
        Specification.installSpecification(Specification.requestSpecification("https://reqres.in"),Specification.responseSpecification(200));
        List<ListOfResources>  listOfResources = given()
                .get("/api/unknown")
                .then()
                .log().all()
                .extract().body().jsonPath().getList("data",ListOfResources.class);

        List<Integer> years = listOfResources.stream().map(ListOfResources::getYear).collect(Collectors.toList());

        List<Integer> sortedYears = years.stream().sorted().collect(Collectors.toList());
        System.out.println(sortedYears);
        System.out.println(years);

        Assert.assertEquals(sortedYears,years);
    }

    @Test
    public void testDeleteRequestIsSuccesful(){
        Specification.installSpecification(Specification.requestSpecification("https://reqres.in"),Specification.responseSpecification(204));
        given().delete("/api/users/2");
    }
}
