package cs.utep.edu.app10.Helpers;

import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.net.Proxy;


public class APIHelper {

    /** To notify a requested grade, or an error. */
    public interface SchedulerListener<T> {

        /** Called when a grade is found. This method is
         * invoked in the caller's thread.
         *
         * @param result The posting date of the grade..
         */
        void onSuccess(T result);

        /** Called when an error is encountered. This method is invoked
         * in the caller's thread. */
        void onError(String msg);
    }


    private class JsonResponse<E> {
        @SerializedName("status")
        public String status;

        @SerializedName("message")
        public String message;

        @SerializedName("data")
        public E data;
    }

    private enum REQUEST_TYPE {
        POST, DELETE, GET, PUT
    }


    /** JSON header **/
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    private static final String JSON_EMPTY = "{}";

    /** JSON response with type FoodContainer **/
//    private static final Type foodCotainerType = new TypeToken<JsonResponse<FoodContainer>>(){}.getType();

    /** JSON response with type String **/
    private static final Type stringType = new TypeToken<JsonResponse<String>>(){}.getType();

    /** JSON response with type FoodContainer **/
//    private static final Type scheduleType = new TypeToken<JsonResponse<Schedule>>(){}.getType();

    /** Authorization header name **/
    private static final String AUTHORIZATION_HEADER = "Authorization";

    /** Status success **/
    private static final String STATUS_SUCCESS = "success";

    /** Status success **/
    private static final String STATUS_FAIL = "fail";

    /** Path to the food api **/
    private static final String CONTAINER_API_PATH = "/food/";

    /** Path to the schedule api **/
    private static final String SCHEDULE_API_PATH = "/schedule/";

    /** Path to the schedule api **/
    private static final String LOGIN_API_PATH = "/login/";


    /** Rest API url **/
    private static final String URL = "http://10.0.2.2:5000/api";

    /** Google library to parse JSON  **/
    private static final Gson g = new Gson();

    /** Http client to make rest api calls **/
    private static final OkHttpClient client = new OkHttpClient();

    String auth = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpYXQiOjE1NTU5MDkxNjksInN1YiI6MSwiZXhwIjoxNTU4NTAxMTc0fQ._5FJnqwWN5NxKBmodX3v1OONc3Wpq5gMTk5fRXNST1I";

    /** Sets authenticator   **/
    public APIHelper(){

    }

    private Response genRequest(String url, String json, REQUEST_TYPE type) throws IOException{
        Request.Builder request = new Request.Builder()
                .url(url)
                .addHeader(AUTHORIZATION_HEADER, auth);

        RequestBody body = RequestBody.create(JSON, json);

        switch (type){
            case GET:
                request.get();
                break;
            case PUT:
                request.put(body);
                break;
            case POST:
                request.post(body);
                break;
            case DELETE:
                request.delete();
                break;
        }

        return client.newCall(request.build()).execute();
    }

    private Response genRequest(String url, REQUEST_TYPE type) throws IOException{
        return genRequest(url, JSON_EMPTY, type);
    }

    public void signIn(String json, SchedulerListener<String> listener){
        new Thread(() -> {
            try {
                Response response = genRequest(URL + LOGIN_API_PATH, json, REQUEST_TYPE.POST);
                if (response.code() == 404)
                    listener.onError("404 Not Found");
                String jsonString = response.body().string();
                JsonResponse result = g.fromJson(jsonString, stringType);

                if (result.status.equals(STATUS_SUCCESS))
                    listener.onSuccess(result.message);
                else
                    listener.onError(result.message);

            } catch (IOException e) {
                listener.onError("Could not communicate with the server");
            }
        }).start();
    }

}

