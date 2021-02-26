package files;

import static io.restassured.RestAssured.given;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class dynamicJson {
	
	@Test(dataProvider = "BookData")
	public static void addBook(String isdn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";
		String response = given().log().all().header("Content-Type","application/json")
		.body(payload.AddBook(isdn,aisle))
		.when().post("Library/Addbook.php").then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = Reusable.rawToJson(response);
		String ID = js.get("ID");
		System.out.println("Book Id is -- "+ID);
		
		given().log().all().header("Content-Type", "application/json")
		.body(payload.deleteBook(ID))
		.when().delete("Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200);
		
		System.out.println("Book deleted successfully");
	}
		
		
	
	@DataProvider(name = "BookData")
	public Object[][] getData(){
		return new Object[][] {{"sahja","54643"},{"sjahda","8797"},{"uryg","9735"}};
	}
}
