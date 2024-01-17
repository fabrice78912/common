package org.example.common.helper;

import org.example.common.dto.OrderResponseDTO;
import org.example.common.exception.InternalServerErrorException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class RequestBuilder {

    public static HttpResponse<?> sendRequest(String requestURL) {
        try {
            HttpRequest.Builder requestBuilder = HttpRequest.newBuilder().GET().uri(URI.create(requestURL));
            HttpRequest request = requestBuilder.build();
            HttpClient client = HttpClient.newHttpClient();
            return client.send(request, HttpResponse.BodyHandlers.ofString());
        } catch (InterruptedException | IOException var4) {
            throw new InternalServerErrorException("echec de connexion au microservice. Erreur: " + var4.getStackTrace());
        }
    }


   /* public static OrderResponseDTO responseDTOBuilder(HttpResponse<String> response) {
        //Case if microservice sends error response
        String message = null;
        String title = null;
        String stringBody = response.body();

        if (response.statusCode() >= HttpStatus.BAD_REQUEST.value()) {
            //Error responses are always objects, do not declare object outside of if statements
            JSONObject jsonBody = new JSONObject(stringBody);
            String key = jsonBody.has("error")? "error":"description";
            throw new EmptyOrNullException(jsonHelper.getValueFromResponseKey(jsonBody, key));
        }

        if (response.statusCode() == HttpStatus.MULTIPLE_CHOICES.value()) {
            JSONObject jsonBody = new JSONObject(stringBody);
            String key = jsonBody.has("message") ? "message":"description";
            title = "Attention";
            message= jsonHelper.getValueFromResponseKey(jsonBody, key);
            if(!jsonBody.has("data")){
                stringBody=null;
            }
        }

        return responseDTOBuilder(response.statusCode(), title, message, stringBody);
    }*/


    public static OrderResponseDTO responseDTOBuilder(int status, String title, String message, String body ) {

        OrderResponseDTO orderResponseDTO;
        Object dataResponse;
        if(body != null){
            Object json = new JSONTokener(body).nextValue();

            //Check if JSON is array or object
            if (json instanceof JSONArray) {
                // It's an array
                dataResponse =(new JSONArray(body).toList());
            }
            else if(json instanceof JSONObject) {
                // It's an object
                JSONObject bodyJSON = new JSONObject(body);
                if(bodyJSON.has("data")){
                    bodyJSON = (JSONObject) bodyJSON.get("data");
                }

                dataResponse =bodyJSON.toMap();
            }
            else
                dataResponse=json;
        }
        else
            dataResponse=body;


        orderResponseDTO = OrderResponseDTO.builder()
                .status(String.valueOf(status))
                //.(message)
                //.data(dataResponse)
                .build();

        return orderResponseDTO;
    }
}
