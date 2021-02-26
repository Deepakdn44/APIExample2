import static io.restassured.RestAssured.*;

import java.util.Iterator;
import java.util.List;

import files.Reusable;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.API;
import pojo.GetCourse;

public class Explore {

	public static void main(String[] args) {
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AY0e-g5HuHb0U-CHoLmZvbT5bQ0pm7sRCXMHr83p-4I81j1rHplhib6n_L--07_WU9YbMg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String url1 = url.split("code=")[1];
		String code = url1.split("&scope")[0];
		
		String AccessTokenResponse = given().urlEncodingEnabled(false).
		 queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().post("https://www.googleapis.com/oauth2/v4/token")
		.then().extract().response().asString();
		
		JsonPath js = Reusable.rawToJson(AccessTokenResponse);
		String access_token = js.getString("access_token");
		
		
		GetCourse gc = given().queryParam("access_token", access_token)
		.expect().defaultParser(Parser.JSON)
		.when().get("https://rahulshettyacademy.com/getCourse.php")
		.as(GetCourse.class);
		
		System.out.println(gc.getCourses().getApi().get(1).getCourseTitle());
		
		List<API> courseList = gc.getCourses().getApi();
		Iterator<API> itr = courseList.iterator();
		
		while(itr.hasNext()) {
			API api = itr.next();
			if(api.getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				System.out.println(api.getPrice());
			}
		}
		
		
	}
}
