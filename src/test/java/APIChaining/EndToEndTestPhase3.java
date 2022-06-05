package APIChaining;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndTestPhase3 {

	Response response;
	String BaseURI = "http://3.80.46.146:8088/employees";

	@Test
	public void testall() {
		// GET Method
		response = GetMethodAll();
		Assert.assertEquals(response.getStatusCode(), 200);

		// POST Method
		response = PostMethod("Madhuri", "aaa", "32000", "madhuri@gmail.com");
		Assert.assertEquals(response.getStatusCode(), 201);
		JsonPath Jpath = response.jsonPath();
		int EmpId = Jpath.get("id");
		System.out.println("Emp id" + EmpId);

		// PUT Method
		response = PutMethod(EmpId, "Ketaki", "Rao", "4400", "XYZ@gmail.com");
		Assert.assertEquals(response.getStatusCode(), 200);
		Jpath = response.jsonPath();
		Assert.assertEquals(Jpath.get("firstName"), "Ketaki");

		// DELETE Method

		response = DeleteMethod(EmpId);
		Assert.assertEquals(response.getStatusCode(), 200);

		// NEW GET Method
		response = NewGetMethod(EmpId);
		Assert.assertEquals(response.getStatusCode(), 400);
		Jpath = response.jsonPath();
		Assert.assertEquals(Jpath.get("message"), "Entity Not Found");
	}

////All the Methods for API Chaining ////

	// ****** GET Method :-- Get All the Employees ***** //
	public Response GetMethodAll() {

		RestAssured.baseURI = BaseURI;

		RequestSpecification request = RestAssured.given();

		Response response = request.get();
		String Responsebody = response.getBody().asString();
		System.out.println(Responsebody);
		return response;

	}

	// ****** POST Method :-- Add new Employee ***** //
	public Response PostMethod(String firstName, String lastName, String salary, String email) {

		RestAssured.baseURI = BaseURI;

		JSONObject jobj = new JSONObject();
		jobj.put("firstName", firstName);
		jobj.put("lastName", lastName);
		jobj.put("salary", salary);
		jobj.put("email", email);

		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON).body(jobj.toString()).post();

		return response;

	}

	// ****** PUT Method :-- Update the new Employee ***** //
	public Response PutMethod(int EmpId, String firstName, String lastName, String salary, String email) {

		RestAssured.baseURI = BaseURI;

		JSONObject jobj = new JSONObject();
		jobj.put("firstName", firstName);
		jobj.put("lastName", lastName);
		jobj.put("salary", salary);
		jobj.put("email", email);

		RequestSpecification request = RestAssured.given();
		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON).body(jobj.toString())
				.put("/" + EmpId);

		return response;
	}

	// ****** DELETE Method :-- Delete the Updated new Employee ***** //
	public Response DeleteMethod(int EmpId) {
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();

		Response response = request.delete("/" + EmpId);

		return response;

	}

	// ****** NewGET Method :-- Validate the Deleted new Employee ***** //
	public Response NewGetMethod(int EmpId) {

		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		Response response = request.get("/" + EmpId);

		return response;
	}
}
