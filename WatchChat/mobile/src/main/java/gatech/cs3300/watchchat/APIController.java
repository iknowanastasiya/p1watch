package gatech.cs3300.watchchat;

import android.app.Activity;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class APIController extends Activity {

    public static final String API_ROOT = "http://cs3300.elasticbeanstalk.com";

    //*To authenticate a user, send a GET request to /auth with the parameter name set to your username.
    // This request is responded by a unique int : userId. -1 is returned if the username does not exist*//
    public void authenticateUser(String username) {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("username", username);
        client.get(API_ROOT + "/auth", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                if (response.equals("-1")) {
                    Toast.makeText(getApplicationContext(), "Requested username not found",
                            Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), response,
                            Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                  Throwable error) {
                try {
                    Toast.makeText(getApplicationContext(), new String(responseBody),
                            Toast.LENGTH_LONG).show();
                } catch (NullPointerException e) {
                    Toast.makeText(getApplicationContext(), "Requested username not found",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    //*To request a list of all existing users, send a GET request to /users with no parameters.
    // This will return a JSON list of user's usernames as Strings *//
    public ArrayList<String> getAllUsers() {
        //TODO complete
        return null;
    }

    //*To request information regarding an existing user, send a GET request to /users
    // with the parameter userId.*//
    public void getUserInfo(int userId) {
        //TODO complete
    }

    //*To request information regarding which groups a user is in, send a GET request to
    // /users//groups with the parameter userId.
    // This will return a JSON list of groupnames and groupIds.*//
    public ArrayList<String> getAllGroupsUserIsIn(int userId) {
        //TODO complete
        return null;
    }

    //*To create a user, send a POST request to /users passing the parameter username.
    // The username and userId will be returned.*//
    public void createUser(String username) {
        //TODO complete
    }

    //*To update information of an existing user, send a POST request
    // to /users//update with the parameter username.*//
    public void updateUserInfo(String username) {
        //TODO complete
    }

    //*To delete a user, send a DELETE request to /users passing the parameter userId.
    // The username and userId will be returned.*//
    public void deleteUser(int userId) {
        //TODO complete
    }

    //*To request a list of all existing groups, send a GET request to /groups/all.
    // Note that whenever a message is sent a group is created,
    // so not all groups will have groupnames *//
    public ArrayList<String> getAllGroups() {
        //TODO complete
        return null;
    }

    //*Get a group's info by groupId *//
    public ArrayList<String> getAllGroupInfo() {
        //TODO complete
        return null;
    }

    //*Get all users in a group *//
    public ArrayList<String> getAllUsersInAGroup() {
        //TODO complete
        return null;
    }


    //*Get all messages in a group *//
    public ArrayList<String> getAllMessagesInAGroup() {
        //TODO complete
        return null;
    }

    //*To create a group, send a POST request to /groups with the parameter groupname*//
    public void createGroup(String groupname) {
        //TODO complete
    }

    public void addUserToGroup(String groupname) {
        //TODO complete
    }

    //* To send a message, send a POST request to /message as a
    // JSON object containing the parameters: senderId, recipientIds, and content.*//
    public void sendMessage(int senderId, int groupId, String content) {
        //TODO complete

    }
    //* The client should periodically send GET requests to /notifications/{userId}
    // to retrieve notifications on messages sent to that user.*//
    public void getNotification(int senderId, int groupId, String content) {
        //TODO complete
    }
}