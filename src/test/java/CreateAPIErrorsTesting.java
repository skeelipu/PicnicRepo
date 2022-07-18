import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static util.ConfigReader.getAuth;
import static util.ConfigReader.getURI;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class CreateAPIErrorsTesting {
	
	@Test
	public void postError() throws Exception {

		baseURI = getURI();

		byte[] input = Files.readAllBytes(Paths.get("Post_Gist.json"));

		String input_file = new String(input);

		
		System.out.println("1. Create a gist using POST Method without Auth");
		
		//POST Method without Auth to get 401 Unauthorized Error (no auth token)
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Content-Type", "application/json")
			.header("Accept", "application/vnd.github+json")
			.body(input_file)
		.when()
			.post(baseURI)
		.then()
			.statusCode(401);
		
		System.out.println("Step 1 successful with statusCode 401");

		System.out.println("2. Create a gist using POST Method without body");
		
		
		//POST Method without body to get 422 Unprocessable Entity Error (no body)
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Content-Type", "application/json")
			.header("Accept", "application/vnd.github+json")
			.header("Authorization", getAuth())
		.when()
			.post(baseURI)
		.then()
			.statusCode(422);
		
		System.out.println("Step 2 successful with statusCode 422");

		
		System.out.println("3. Create a gist using POST Method incorrect URI");
		
		//POST Method with incorrect URI to get 404 not found (incorrect URI)
				given()
					.header("Authorization", getAuth())
					.body(input_file)
				.when()
					.post("https://api.github.com")
				.then()
					.statusCode(404);
				
		System.out.println("Step 3 successful with statusCode 404");

				
		System.out.println("4. Create a gist using POST Method with No Auth");	
				
		//POST Method with No Auth to get 400 Bad Request
				given()
					.header("No Auth", getAuth())
					.body(input_file)
				.when()
					.post(baseURI)
				.then()
					.statusCode(400);
		
		System.out.println("Step 4 successful with statusCode 400");

	}


}
