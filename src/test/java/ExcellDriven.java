import static io.restassured.RestAssured.*;

import org.testng.annotations.Test;

import files.Reusable;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class ExcellDriven {

	@Test
	public void getData() {
		
		RestAssured.baseURI="http://216.10.245.166";
		String res = given().header("Content-Type","application/json")
		.body("{\\r\\n\\r\\n\\\"name\\\":\\\"Learn Selenium Automation with Java\\\",\\r\\n\\\"isbn\\\":\\\"abcdeg\\\",\\r\\n\\\"aisle\\\":\\\"244\\\",\\r\\n\\\"author\\\":\\\"Deepak N\\\"\\r\\n}")
		.when().post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = Reusable.rawToJson(res);
		String id = js.get("ID");
		System.out.println(id);
	}
}
