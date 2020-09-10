package com;

import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.junit.Test;
import org.apache.http.HttpStatus;
import static org.hamcrest.CoreMatchers.equalTo;
import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;


/**
 * Unit test for simple App.
 */
public class Request extends PrepareToRequest
{
    private RequestSpecification requestSpecification;
   @Test
    public void testImdbID() //Search.Title[1] equal to "Harry Potter and the Sorcerer's Stone".
    {
        testDoRequestForStatusCode(getFilmID( 1));
        testSearchWithID(getFilmID(1));
        testMovieYear( getFilmID(1));
        testMovieReleaseYear(getFilmID(1));
    }

    //return specific film
    public String getFilmID(int index)
    {
        Response response = dataSearch(wantedData).when().get(baseURI).then().extract().response();
        String findFilm = new StringBuilder().append("Search[").append(index).append("].imdbID").toString();
        String film = response.jsonPath().getString(findFilm);
        return film;
    }
    // request with search data
    private RequestSpecification dataSearch(String wantedData)
    {
        requestSpecification = given();
        requestSpecification.
                param(Data.API_KEY.getData(), "98c87939").
                param(Data.FILM_NAME.getData(), wantedData).
                param(Data.TYPE.getData(), "movie").
                param(Data.YEAR.getData(), "").
                param(Data.DATA_TYPE.getData(), "json").
                param(Data.PAGE.getData(), "1").
                param(Data.CALLBACK.getData(), "").
                param(Data.APIVERSION.getData(), "1");
        return requestSpecification;
    }

    //search film title with id
    public void testSearchWithID(String imdbID)
    {
        dataID(imdbID).when().get(baseURI).then().body("Title", equalTo("Harry Potter and the Sorcerer's Stone"));

    }

    // Status 200(Success)
    public void testDoRequestForStatusCode(String imdbID){
        dataID(imdbID).when().get(baseURI).then().statusCode(HttpStatus.SC_OK);
    }
    // any response year field
    public void testMovieYear(String imdbID){

        dataID(imdbID).when().get(baseURI).then().body("Year",equalTo("2001"));
    }
    //any response released field
    public void testMovieReleaseYear(String imdbID){

      dataID(imdbID).when().get(baseURI).then().body("Released",equalTo("16 Nov 2001"));
    }
    //request with id
    public RequestSpecification dataID(String id)
    {
        requestSpecification = given();
        requestSpecification.
                param(Data.API_KEY.getData(), "98c87939").
                param(Data.ID.getData(), id).
                param(Data.TITLE.getData(), "").
                param(Data.TYPE.getData(), "").
                param(Data.YEAR.getData(), "").
                param(Data.PLOT.getData(), "short").
                param(Data.DATA_TYPE.getData(), "json").
                param(Data.CALLBACK.getData(), "").
                param(Data.APIVERSION.getData(), "1");
        return requestSpecification;
    }


}
