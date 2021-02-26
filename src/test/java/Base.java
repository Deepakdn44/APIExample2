import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;

import files.Reusable;
import files.payload;

public class Base {

	public static void main(String[] args) throws IOException {
		
		//POST method Adding the address
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\deepak.nataraja\\Desktop\\AddPlace.JSON")))).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
		.body("scope", equalTo("APP")).header("Server", "Apache/2.4.18 (Ubuntu)")
		.extract().response().asString();
		
		System.out.println(response);
		
		JsonPath js = Reusable.rawToJson(response);
		String placeID = js.getString("place_id");
		
		System.out.println(placeID);
		
		// PUT method Update the address
		String Address = "44 Washington DC";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n" + 
				"    \"place_id\": \""+placeID+"\",\r\n" + 
				"    \"address\": \""+Address+"\",\r\n" + 
				"    \"key\": \"qaclick123\"\r\n" + 
				"}").
		when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated")).header("Server", "Apache/2.4.18 (Ubuntu)");
		
		//GET Method Getting the address
		
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeID).when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js1 = Reusable.rawToJson(getResponse);
		String actualAdress = js1.getString("address");
		
		System.out.println(actualAdress);
		
		Assert.assertEquals(actualAdress, Address);
		
	}


}
