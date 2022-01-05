package user;

import static org.junit.Assert.*;
import org.junit.Test; 
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import org.junit.BeforeClass;
import io.restassured.http.ContentType;

import org.junit.Test;

public class ApiUserTest {
	
	@BeforeClass 
	public static void setup() {
		baseURI = "https://reqres.in/api/";
	}
	
	@Test
	public void listUsers() {
	given()
		.contentType("application/json")
	.when()
		.get("users?page=2")
	.then()
		.log().all()
		.statusCode(200)
		.body("page", is(2))
		.body("per_page", is(6))
		.body("total", is(12))
		.body("total_pages", is(2));
	}
	
	@Test
	public void userId() {
	given()
		.contentType("application/json")
	.when()
		.get("users/8")
	.then()
		.log().all()
		.statusCode(200)
		.body("data.id", is(8))
		.body("data.email", is("lindsay.ferguson@reqres.in"))
		.body("data.first_name", is("Lindsay"))
		.body("data.last_name", is("Ferguson"))
		.body("support.url", is("https://reqres.in/#support-heading"))
		.body("support.text", is("To keep ReqRes free, contributions towards server costs are appreciated!"));
	}
	
	@Test
	public void userIdNotFound() {
	given()
		.contentType("application/json")
	.when()
		.get("users/4000")
	.then()
		.log().all()
		.statusCode(404);
	}
	
	@Test
	public void crateUser() {
	given()
		.contentType("application/json")
		.body("{\r\n"
				+ "    \"name\": \"Lucas\",\r\n"
				+ "    \"job\": \"Test Leader\"\r\n"
				+ "}")
	.when()
		.post("users")
	.then()
		.log().all()
		.statusCode(201)
		.body("name", is("Lucas"))
		.body("job", is("Test Leader"));
	}
	
	@Test
	public void alterUser() {
	given()
		.contentType("application/json")
		.body("{\r\n"
				+ "    \"name\": \"Lucas\",\r\n"
				+ "    \"job\": \"Tester\"\r\n"
				+ "}")
	.when()
		.put("users/917")
	.then()
		.log().all()
		.statusCode(200)
		.body("name", is("Lucas"))
		.body("job", is("Tester"));
	}
	
	@Test
	public void deleteUser() {
	given()
		.contentType("application/json")
	.when()
		.delete("users/2")
	.then()
		.log().all()
		.statusCode(204);
	}
	
	@Test
	public void login() {
	given()
		.contentType("application/json")
		.body("{\r\n"
				+ "    \"email\": \"eve.holt@reqres.in\",\r\n"
				+ "    \"password\": \"cityslicka\"\r\n"
				+ "}")
	.when()
		.post("login")
	.then()
		.log().all()
		.statusCode(200)
		.body("token", is("QpwL5tke4Pnpja7X4"));
	}
	
	@Test
	public void loginUnsuccessful() {
	given()
		.contentType("application/json")
		.body("{\r\n"
				+ "    \"email\": \"eve.holt@reqres.in\",\r\n"
				+ "}")
	.when()
		.post("login")
	.then()
		.log().all()
		.statusCode(400);
	}
	
	@Test
	public void delay() {
	given()
		.contentType("application/json")
		.body("{\r\n"
				+ "    \"email\": \"eve.holt@reqres.in\"\r\n"
				+ "}")
	.when()
		.post("users?delay=3")
	.then()
		.log().all()
		.statusCode(201)
		.body("email", is("eve.holt@reqres.in"));
	}

}
