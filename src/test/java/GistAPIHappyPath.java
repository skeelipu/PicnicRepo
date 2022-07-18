import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.http.ContentType;

import static util.ConfigReader.getURI;

import static util.ConfigReader.getAuth;


public class GistAPIHappyPath {


	@Test
	public void successPath() throws Exception {

		baseURI = getURI();

		byte[] input = Files.readAllBytes(Paths.get("Post_Gist.json"));

		String input_file = new String(input);

		System.out.println("1. Create a gist PUBLIC using POST Method and extract id from the response");
		
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
		
		
		System.out.println("2. Get gist using GET Method");
		
		//Get gist using GET Method
		given()
			.get(baseURI+"/"+id)
		.then()
			.statusCode(200)
		.body("owner.login", equalTo("skeelipu")).log().all();

		System.out.println("Step 2 successful with statusCode 200");
		
		
		System.out.println("3. Update gist using PATCH Method and verify description");
		
		//Update gist using PATCH Method and verify description
		JSONObject request = new JSONObject();

		request.put("description", "Picnic");

		String desc = given()
						.contentType(ContentType.JSON)
						.accept(ContentType.JSON)
						.header("Content-Type", "application/json")
						.header("Accept", "application/vnd.github+json")
						.header("Authorization", getAuth())
						.body(request.toJSONString())
					.when()
						.patch(baseURI+"/"+id)
					.then()
						.statusCode(200).log().all().extract().path("description");

		Assert.assertEquals(desc, "Picnic");

		System.out.println("Step 3 successful with statusCode 200");
		

		System.out.println("4. Create a gist SECRET using POST Method and extract id from the response");
		
		byte[] input2 = Files.readAllBytes(Paths.get("Post_Gist_Secret.json"));

		String input_file2 = new String(input2);

		//Create a gist using POST Method and extract id from the response
		String id2 = given()
						.contentType(ContentType.JSON)
						.accept(ContentType.JSON)
						.header("Content-Type", "application/json")
						.header("Accept", "application/vnd.github+json")
						.header("Authorization", getAuth())
						.body(input_file2)
					.when()
						.post(baseURI)
					.then()
						.statusCode(201).log().all().extract().path("id");

		System.out.println("id (Secret Gist) is: "+id2);

		System.out.println("Step 4 successful with statusCode 201");

		System.out.println("5. List gists for the authenticated user");
		
		//List gists for the authenticated user
				given()
					.contentType(ContentType.JSON)
					.accept(ContentType.JSON)
					.header("Content-Type", "application/json")
					.header("Accept", "application/vnd.github+json")
					.header("Authorization", getAuth())
				.when()
					.get(baseURI)
				.then()
					.statusCode(200);
			
				
		System.out.println("Step 5 successful with statusCode 200");
				
		System.out.println("6. List public gists");
		
		//List public gists
				given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/vnd.github+json")
				.header("Authorization", getAuth())
			.when()
				.get(baseURI+"/public")
			.then()
				.statusCode(200);
				
		System.out.println("Step 6 successful with statusCode 200");
					
				
		System.out.println("7. List starred gists");
						
		//List starred gists
				given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/vnd.github+json")
				.header("Authorization", getAuth())
			.when()
				.get(baseURI+"/starred")
			.then()
				.statusCode(200);
				
			
		System.out.println("Step 7 successful with statusCode 200");
				
		System.out.println("8. List gist commits");
						
		//List gist commits
				given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/vnd.github+json")
				.header("Authorization", getAuth())
			.when()
				.get(baseURI+"/"+id+"/commits")
			.then()
				.statusCode(200);
				
				
		System.out.println("Step 8 successful with statusCode 200");
				

		System.out.println("9. List gist forks");
						
		//List gist forks
				given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/vnd.github+json")
				.header("Authorization", getAuth())
			.when()
				.get(baseURI+"/"+id+"/forks")
			.then()
				.statusCode(200);
					
		System.out.println("Step 9 successful with statusCode 200");
				
		System.out.println("10. Star a gist");
					
		//Star a gist
				given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/vnd.github+json")
				.header("Authorization", getAuth())
			.when()
				.put(baseURI+"/"+id+"/star")
			.then()
				.statusCode(204);
		
		System.out.println("Step 10 successful with statusCode 204");
				
		
		System.out.println("11. Check if a gist is starred");			
		
		//Check if a gist is starred
				given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/vnd.github+json")
				.header("Authorization", getAuth())
			.when()
				.get(baseURI+"/"+id+"/star")
			.then()
				.statusCode(204);
		
		System.out.println("Step 11 successful with statusCode 204");
				
		System.out.println("12. Unstar a gist");			
						
		//Unstar a gist
				given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/vnd.github+json")
				.header("Authorization", getAuth())
			.when()
				.delete(baseURI+"/"+id+"/star")
			.then()
				.statusCode(204);
		
		System.out.println("Step 12 successful with statusCode 204");
				
				
		System.out.println("13. Check if a gist is NOT starred");			
				
		//Check if a gist is NOT starred
				given()
				.contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Content-Type", "application/json")
				.header("Accept", "application/vnd.github+json")
				.header("Authorization", getAuth())
			.when()
				.get(baseURI+"/"+id+"/star")
			.then()
				.statusCode(404);	
				
		System.out.println("Step 13 successful with statusCode 404");
				
			
				
		System.out.println("14. delete the gist created in step 1 and 4");			
						
		//delete the gist created in step 1 and 4 using DELETE Method
				given()
					.header("Authorization", getAuth())
				.when()
					.delete(baseURI+"/"+id)
				.then()
					.statusCode(204).log().all();
				
		given()
				.header("Authorization", getAuth())
			.when()
				.delete(baseURI+"/"+id2)
			.then()
				.statusCode(204).log().all();
				
		System.out.println("Step 14 successful with statusCode 204");
				
	}


}
