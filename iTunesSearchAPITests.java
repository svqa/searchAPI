package com.iapple.iTunesSearchAPI;
import java.util.ArrayList;
import java.util.Map;
import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
importcom.jayway.restassured.specification.RequestSpecification;
import static com.jayway.restassured.RestAssured.given;
import java.io.File;
import java.io.IOException;
import SupportClass.*;

public class iTunesSearchAPI_Tests  {

@BeforeClass

public void setup () {
	Specs.setBasePath("/search");
        Specs.setBaseURI("https://itunes.apple.com");
        Specs.setContentType(ContentType.JSON);
       }

@Test(dataProvider="getResponseStatusCode")
	public void verifyResponseStatusCode(String term, String country, String media,int limit,int statusCode){
    Response resp = 
       given().params("term",term).params(country",country).params("media",media).params("limit",limit).when().get("RestAssured.baseURI + RestAssured.basePath");	
Assert.assertEquals(resp.getStatusCode(), statusCode);
}	
@DataProvider
public Object[][] getResponseStatusCode(){
Object data[][] = new Object[][];
data[][]= {
          {"michael jackson","US","music",51,200},
          {"michael jackson","US","all",50, 200},
          {"","US","music",50, 200},
          {"michael jackson","US","featureFilm",50,400}
    };
return data;
}

@Test(dataProvider="getResponseResultCount")
	public void verifyResponseResultCount(String term, String country, String media,int limit,int size){
    Response resp = 
       given().params("term",term).params(country",country).params("media",media).params("limit",limit).when().get("RestAssured.baseURI + RestAssured.basePath");		
List<String> items = resp.jsonPath().getList("items");
Assert.assertEquals(items.size(), size);
}
@DataProvider
public Object[][] getResponseResultCount(){
Object data[][] = new Object[][];
data[][]= {
          {"michael jackson","US","music",51,51},
          {"michael jackson","US","all",50, 50},
          {"","US","music",50, 0},
          {"michael jackson","US","",50,50}
    };
return data;
}

@Test(dataProvider="getResponseErrorMessage")
	public void verifyResponseErrorMessage(String term, String country, String media,int limit,String errMsg){
    Response resp = 
       given().params("term",term).params(country",country).params("media",media).params("limit",limit).when().get("RestAssured.baseURI + RestAssured.basePath");		
String errorMessage = resp.jsonPath().get("errMsg");
Assert.assertEquals(errorMessage, errMsg);
}

@DataProvider
public Object[][] getResponseErrorMessage(){
Object data[][] = new Object[][];
data[][]= {
          {"michael jackson","US","music",51, null},
          {"michael jackson","US","all",50, null},
          {"michael jackson","US","featureFilm",50,"Invalid value(s) for key(s): [mediaType]},
          {"","SSOT","music",50,"Invalid value(s) for key(s): [country]",}
    };
return data;
}

@AfterClass
public void tearDown (){
     Specs.resetBaseURI();
     Specs.resetBasePath();
}
}
