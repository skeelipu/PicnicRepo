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

public class UpdateAPIErrorsTesting {
	
	@Test
	public void patchError() throws Exception {

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

		System.out.println("2. Update gist with no Payload");
		
		//PATCH Method with no payload, 422 Unprocessed Entity error
		JSONObject request = new JSONObject();

			given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/vnd.github+json")
				.header("Authorization", getAuth())
				.body(request.toJSONString())
			.when()
				.patch(baseURI+"/"+id)
			.then()
				.statusCode(422).log().all();

		System.out.println("Step 2 successful with statusCode 422");

		System.out.println("3. Update gist with no gist id");

		//PATCH Method with no gist ID, 404 error
		request.put("description", "Picnic");
		
		
		given()
						.contentType(ContentType.JSON)
						.accept(ContentType.JSON)
						.header("Content-Type", "application/json")
						.header("Accept", "application/vnd.github+json")
						.header("Authorization", getAuth())
						.body(request.toJSONString())
					.when()
						.patch(baseURI+"/")
					.then()
						.statusCode(404).log().all();
		
		System.out.println("Step 3 successful with statusCode 404");

		
		System.out.println("4. Update gist with no Auth and no gist id");
		
		
		//PATCH Method with no Auth and no gist id, 400 error
	given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.header("Content-Type", "application/json")
		.header("Accept", "application/vnd.github+json")
		.header("No Auth", getAuth())
		.body(request.toJSONString())
	.when()
		.patch(baseURI+"/")
	.then()
		.statusCode(400).log().all();
	
	System.out.println("Step 4 successful with statusCode 400");

	System.out.println("5. Update gist with incorrect accept-type (pdf)");
	
	
	//PATCH Method with incorrect accept-type (pdf), 415 error
	given()
		.contentType(ContentType.JSON)
		.accept(ContentType.JSON)
		.header("Content-Type", "application/json")
		.header("Accept", "application/pdf")
		.header("Authorization", getAuth())
		.body(request.toJSONString())
	.when()
		.patch(baseURI+"/")
	.then()
		.statusCode(415).log().all();
	
	System.out.println("Step 5 successful with statusCode 415");

	
	System.out.println("6. Delete gist created in step 1");
	
	
	//delete the gist created above using DELETE Method
			given()
				.header("Authorization", getAuth())
			.when()
				.delete(baseURI+"/"+id)
			.then()
				.statusCode(204).log().all();
			
	System.out.println("Step 6 successful with statusCode 204");

		
	}


}
