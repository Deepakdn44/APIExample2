package files;

import org.testng.Assert;

import io.restassured.path.json.JsonPath;

public class ComplexJson {
	
	public static void main(String[] args) {
		JsonPath js = new JsonPath(payload.course());
		//Print No of courses
		int NoOfCourses = js.getInt("courses.size()");
		System.out.println(NoOfCourses);
		//Print purchase Amount
		int Purchase = js.getInt("dashboard.purchaseAmount");
		System.out.println(Purchase);
		//Print title of the first course
		String firstCourse = js.get("courses[0].title");
		System.out.println(firstCourse);
		//Print all course titles and respective prices
		for(int i=0;i<NoOfCourses;i++) {
			System.out.println("Title is --->"+js.get("courses["+i+"].title"));
			System.out.println("Price is ---->"+js.getInt("courses["+i+"].price"));
		}
		//Print no of courses sold by RPA Course
		
		
		for(int j=0;j<NoOfCourses;j++) {
			String course = js.get("courses["+j+"].title");
			if(course.equalsIgnoreCase("RPA")) {
				int copies = js.get("courses["+j+"].copies");
				System.out.println("No of copies sold by RPA is-->"+copies);
				break;
			}
		}
		
		//Verify sum of all course price matches the purchase amount
		int finalAmount = 0;
		for(int i=0;i<NoOfCourses;i++) {
			int price = js.getInt("courses["+i+"].price");
			int copies = js.getInt("courses["+i+"].copies");
			int amount = price*copies;
			finalAmount = finalAmount + amount;
		}
		
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(finalAmount, purchaseAmount);
		
		if(finalAmount==js.getInt("dashboard.purchaseAmount")) {
			System.out.println("Final amount is--->"+finalAmount);
			System.out.println("Purchase amount matches the Final amount");
		}else {
			System.out.println("Final amount is--->"+finalAmount);
			System.out.println("Does not match");
		}
	}
		
	
}
