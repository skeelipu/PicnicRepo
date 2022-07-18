import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static util.ConfigReader.getAuth;
import static util.ConfigReader.getURI;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class DeleteAPIErrorsTesting {

	
	@Test
	public void successPath() throws Exception {

		baseURI = getURI();

		byte[] input = Files.readAllBytes(Paths.get("Post_Gist.json"));

		String input_file = new String(input);

		System.out.println("1. Create a gist using POST Method and extract id from the response");
		
		//Create a gist using POST Method and extract id from the response
		String id = given()
						.contentType(ContentType.JSON)
						.accept(ContentType.JSON)
						.header("Content-Type", "application/json")
						.header("Accept", "application/vnd.github+json")
						.header("Authorization", getAuth())
						.body(input_file)
					.when()
						.post(baseURI)
					.then()
						.statusCode(201).log().all().extract().path("id");

		System.out.println("id is: "+id);

		System.out.println("Step 1 successful with statusCode 201");

		
		System.out.println("2. Delete gist with No Auth header, 400");
		
		//delete the gist with no Auth header
		given()
			.header("No Auth", getAuth())
		.when()
			.delete(baseURI+"/"+id)
		.then()
			.statusCode(400).log().all();
		
		System.out.println("Step 2 successful with statusCode 400");

		System.out.println("3. Delete gist with No Authorization header, 404");
		
		given()
		.when()
			.delete(baseURI+"/"+id)
		.then()
			.statusCode(404).log().all();
		
		System.out.println("Step 3 successful with statusCode 404");

		System.out.println("4. Delete gist with No gist id in URI, 404");
		
		given()
			.header("Authorization", getAuth())
		.when()
			.delete(baseURI+"/")
		.then()
			.statusCode(404).log().all();
		
		System.out.println("Step 4 successful with statusCode 404");

		System.out.println("5. Delete gist created in step 1, 204");
		
		given()
			.header("Authorization", getAuth())
		.when()
			.delete(baseURI+"/"+id)
		.then()
			.statusCode(204).log().all();
				
		System.out.println("Step 5 successful with statusCode 204");

	}
}
