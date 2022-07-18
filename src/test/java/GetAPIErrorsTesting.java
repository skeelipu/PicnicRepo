import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static util.ConfigReader.getAuth;
import static util.ConfigReader.getURI;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.http.ContentType;

public class GetAPIErrorsTesting {
	
	@Test
	public void getError() throws Exception {

		baseURI = getURI();

		byte[] input = Files.readAllBytes(Paths.get("Post_Gist.json"));

		String input_file = new String(input);

		System.out.println("1. Create a gist using POST Method");
		
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

		System.out.println("2. Get gist with incorrect gist id");
		
		//Get gist using GET Method with incorrect gist id to get 404 Not Found Error
		given()
			.get(baseURI+"/asywdegrhnj")
		.then()
			.statusCode(404).log().all();

		System.out.println("Step 2 successful with statusCode 404");

		
		System.out.println("3. Get gist with No Auth");
		
		
		//Get gist using GET Method with No Authorization to get 400 Bad Request Error
				given()
					.header("No Auth", getAuth())
					.get(baseURI+"/"+id)
				.then()
					.statusCode(400).log().all();
				
		System.out.println("Step 3 successful with statusCode 400");
		
		
		System.out.println("4. Get gist with incorrect Accept type (pdf)");
		
		//Get gist using GET Method with incorrect incorrect Accept type (pdf) to get 415 Error
		given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.header("Content-Type", "application/json")
			.header("Accept", "application/pdf")
			.header("Authorization", getAuth())
			.get(baseURI+"/"+id)
		.then()
			.statusCode(415).log().all();

		System.out.println("Step 4 successful with statusCode 415");

		
		System.out.println("5. Delete the gist created in step 1");
				
		//delete the gist created above using DELETE Method
				given()
					.header("Authorization", getAuth())
				.when()
					.delete(baseURI+"/"+id)
				.then()
					.statusCode(204).log().all();
				
		System.out.println("Step 5 successful with statusCode 204");

				
	}

}
