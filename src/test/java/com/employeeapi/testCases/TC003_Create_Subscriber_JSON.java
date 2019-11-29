/******************************************************
Test Name:Get a single employee data
URI: http://dummy.restapiexample.com/api/v1/employee/{id}
Request Type: GET
Request Payload(Body): NA

 ********* Validations **********
Status Code : 200
Status Line : HTTP/1.1 200 OK
Content Type : text/html; charset=UTF-8
Server Type :  nginx/1.14.1
Content Encoding : gzip
Content Length <800
 *********************************************************/

package com.employeeapi.testCases;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.simple.JSONObject;
import org.testng.annotations.*;

import com.employeeapi.base.TestBase;
import com.employeeapi.utilities.CreateAccessToken;
import com.employeeapi.utilities.ReadPropertyFile;
import com.employeeapi.utilities.RestUtils;
import com.employeeapi.utilities.XLUtils;

import DataBase.DBActions;
import DataBase.DBConnection;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class TC003_Create_Subscriber_JSON extends TestBase{

	RequestSpecification httpRequest;
	Response response;
	Response response1;

	String empID;
	String empName=RestUtils.empName();
	String empSalary=RestUtils.empSal();
	String empAge=RestUtils.empAge();
	private RequestSpecification httpRequest1;
	@Test
	void createSubscriber() throws Exception
	{
		logger.info("*********Started TC003_Create_Subscriber_JSON **********");

		String username = null;
		String password = null;
		logger.info("*********Started TC003_Create_Subscriber_JSON **********");
		ReadPropertyFile readProperty = new ReadPropertyFile("E:/Bibek/Workspace/RestassuredAPITesting_Employee_Project/src/test/java/com/employeeapi/utilities/sqlqueries.properties");
		String fetchUserNamePassword = readProperty.getData("FetchUserNamePassword");
		String tmURL = readProperty.getData("tmURL");
		DBConnection dbObj = new DBConnection();
		DBActions dbActionObj = new DBActions();
		Connection con = dbObj.createConnection();
		ResultSet resultSet = dbActionObj.executeQuery(con,fetchUserNamePassword);
		resultSet.next();
		username = resultSet.getString(1);
		password = resultSet.getString(2);
		CreateAccessToken cat = new CreateAccessToken();
		String accessToken = cat.getAccessToken(username, password);


		RestAssured.baseURI = tmURL;
		httpRequest = RestAssured.given().log().all();

		// JSONObject is a class that represents a simple JSON. We can add Key-Value pairs using the put method
		//{"name":"John123X","salary":"123","age":"23"}
		JSONObject requestParams = new JSONObject();
		requestParams.put("accountNo", empSalary);

		// Add a header stating the Request body is a JSON
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Basic "+accessToken);
		// Add the Json to the body of the request
		httpRequest.body(requestParams.toJSONString());

		response = httpRequest.request(Method.POST, "/bep-mf-api/subscribers/");

		int responseCode1 = response.getStatusCode();
		String accountNumber = response.jsonPath().getString("accountNo");
		System.out.println(accountNumber);
		System.out.println("Response Code1 = " + response.getBody().asString());
		System.out.println("Response Code1 = " + responseCode1);
		Assert.assertEquals(201, responseCode1);

		Thread.sleep(5000);

	}


}
