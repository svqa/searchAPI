package SupportClass;

import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.RestAssured.*;

public class Specs {

public static void setBaseURI (String baseUri){
RestAssured.baseURI= baseUri;
}

public static void setBasePath (String basePath){
RestAssured.basePath= basePath;
}

public static void resetBaseURI(){
RestAssured.baseURI= null;
}

public static void resetBasePath(){
RestAssured.basePath= null;
}
}
