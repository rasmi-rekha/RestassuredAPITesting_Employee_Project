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

import org.testng.annotations.*;

import com.employeeapi.base.TestBase;
import com.employeeapi.utilities.ReadPropertyFile;
import com.employeeapi.utilities.XLUtils;

import DataBase.DBActions;
import DataBase.DBConnection;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.Assert;

public class TC002_Get_Single_Employee_Record extends TestBase{

	RequestSpecification httpRequest;
	Response response;
	Response response1;

	String empID;
	private RequestSpecification httpRequest1;
	@Test
	void getEmployeeData() throws Exception
	{
		String macadress = null;
		String externalID = null;
		logger.info("*********Started TC002_Get_Single_Employee_Record **********");
		ReadPropertyFile readProperty = new ReadPropertyFile("E:/Bibek/Workspace/RestassuredAPITesting_Employee_Project/src/test/java/com/employeeapi/utilities/sqlqueries.properties");
		String fetchQuery = readProperty.getData("FetchMacaddressandExternalId");
		String requestBody = readProperty.getData("AddWishListPostBody");
		String baseURL = readProperty.getData("baseURL");
		DBConnection dbObj = new DBConnection();
		DBActions dbActionObj = new DBActions();
		Connection con = dbObj.createConnection();
		ResultSet resultSet = dbActionObj.executeQuery(con,fetchQuery);
		RestAssured.baseURI = baseURL;
		httpRequest = RestAssured.given().log().all();
		while(resultSet.next()){
			macadress = resultSet.getString(1);
			externalID = resultSet.getString(2);
			requestBody = requestBody.replace("${externalID}", externalID);
			httpRequest.header("Content-Type", "application/xml");
			httpRequest.body(requestBody);
			response1 = httpRequest.request(Method.POST, "/broker/bta/addToWishList?MAC="+macadress+"&InterfaceVersion=4.2.0");
			String responseBody = response1.getBody().asString();
			int responseCode = response1.getStatusCode();
			System.out.println("responseBody is " + responseBody);
			System.out.println("Response Code = " + responseCode);
			httpRequest1 = RestAssured.given().log().all();
			String xmlPath  = response1.xmlPath().getString("ID");
			System.out.println(xmlPath);
			response = httpRequest1.request(Method.GET, "/broker/bta/getWishList?MAC="+macadress+"&fc_Language=nl&fc_HDCapable=true");
			String responseBody1 = response.getBody().asString();
			int responseCode1 = response.getStatusCode();
			System.out.println("responseBody1 is " + responseBody1);
			System.out.println("Response Code1 = " + responseCode1);
			Assert.assertEquals(200, responseCode);
			Assert.assertTrue(responseBody1.contains(xmlPath));
			
		}
		/*resultSet.next();
		response = httpRequest.request(Method.GET, "/broker/bta/getWishList?MAC="+resultSet.getString(1));
		String responseBody = response.getBody().asString();
		System.out.println("responseBody is " + responseBody);*/
		dbObj.closeConnection(con);


		Thread.sleep(7000);
	}
	/*
	@Test
	void checkResposeBody()
	{
		String responseBody = response.getBody().asString();
		Assert.assertEquals(responseBody.contains(this.empID), true);
	}

	@Test
	void checkStatusCode()
	{
		int statusCode = response.getStatusCode(); // Gettng status code
		Assert.assertEquals(statusCode, 200);
	}

	@Test
	void checkResponseTime()
	{
		long responseTime = response.getTime(); // Getting status Line
		Assert.assertTrue(responseTime<6000);

	}

	@Test
	void checkstatusLine()
	{
		String statusLine = response.getStatusLine(); // Gettng status Line
		Assert.assertEquals(statusLine, "HTTP/1.1 200 OK");

	}

	@Test
	void checkContentType()
	{
		String contentType = response.header("Content-Type");
		Assert.assertEquals(contentType, "text/html; charset=UTF-8");
	}

	@Test
	void checkserverType()
	{
		String serverType = response.header("Server");
		Assert.assertEquals(serverType, "nginx/1.16.0");
	}

	@Test
	void checkContentLenght()
	{
		String contentLength = response.header("Content-Length");
		Assert.assertTrue(Integer.parseInt(contentLength)<1500);
	}
	 */
	@AfterClass
	void tearDown()
	{
		logger.info("*********  Finished TC002_Get_Single_Employee_Record **********");
	}

	@DataProvider(name = "empdataprovider")
	String[][] getEmpData() throws IOException

	{
		// D:\workspace\RestAsuredApiTesting\src\test\java\dataDrivenPackage\empdata.xlsx
		// String
		// path=System.getProperty("user.dir")+"/src/test/java/dataDrivenPackage/empdata.xlsx";

		String xls = System.getProperty("user.dir")
				+ "/src/test/java/com/employeeapi/utilities/empdata.xlsx";

		int rowcount = XLUtils.getRowCount(xls, "Sheet1");
		int colcount = XLUtils.getCellCount(xls, "Sheet1", 1);

		String empdata[][] = new String[rowcount][colcount];

		for (int i = 1; i <= rowcount; i++)

		{

			for (int j = 0; j < colcount; j++) {

				empdata[i - 1][j] = XLUtils.getCellData(xls, "Sheet1", i, j);
			}

		}
		return empdata;

	}

}
