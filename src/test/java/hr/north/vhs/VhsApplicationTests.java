package hr.north.vhs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class VhsApplicationTests {

	@Test
	public void createPerson_then201() {

		RestAssured.baseURI = "http://localhost:8080";

		String jsonPayload = "{"
				+ "\"firstName\": \"Luka\","
				+ "\"lastName\": \"Cavalli\","
				+ "\"userName\": \"drugi\","
				+ "\"password\": \"abc123\""
				+ "}";

		// Send the POST request and assert the response
		given()
				.contentType(ContentType.JSON)
				.body(jsonPayload)
				.when()
				.post("/api/persons")
				.then()
				.statusCode(HttpStatus.CREATED.value())
				.body("firstName", equalTo("Luka"))
				.body("lastName", equalTo("Cavalli"))
				.body("userName", equalTo("drugi"))
				.body("password", equalTo("abc123"));
	}

	@Test
	public void checkUsernameAlreadyTakenException() {

		RestAssured.baseURI = "http://localhost:8080";

		String jsonPayload1 = "{"
				+ "\"firstName\": \"Luka\","
				+ "\"lastName\": \"Cavalli\","
				+ "\"userName\": \"prvi\","
				+ "\"password\": \"abc123\""
				+ "}";

		String jsonPayload2 = "{"
				+ "\"firstName\": \"a\","
				+ "\"lastName\": \"b\","
				+ "\"userName\": \"prvi\","
				+ "\"password\": \"12345678\""
				+ "}";

		// Send the first POST request and assert the response
		given()
				.contentType(ContentType.JSON)
				.body(jsonPayload1)
				.when()
				.post("/api/persons")
				.then()
				.statusCode(HttpStatus.CREATED.value());

		// Send the second POST request and assert the response
		given()
				.contentType(ContentType.JSON)
				.body(jsonPayload2)
				.when()
				.post("/api/persons")
				.then()
				.statusCode(HttpStatus.BAD_REQUEST.value());

	}
}
