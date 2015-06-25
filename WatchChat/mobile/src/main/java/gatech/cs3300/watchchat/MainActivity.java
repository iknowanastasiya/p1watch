package gatech.cs3300.watchchat;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONObject;

import java.net.URL;


public class MainActivity extends Activity {

    private User currentUser;
    private SharedPreferences mPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().hasExtra("Logout")){
            currentUser = null;
        } else {
            mPrefs = getSharedPreferences("WATCHCHAT", MODE_PRIVATE);
            if (mPrefs != null && !mPrefs.getString("UserName", "").equals("")) {
                startGroupsActivity(mPrefs.getString("UserName", ""), mPrefs.getString("UserId", ""));
            }
        }

        setContentView(R.layout.activity_main);

        Button signIn = (Button) findViewById(R.id.SignInButton);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSignIn();
            }
        });

        Button register = (Button) findViewById(R.id.RegisterButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        if(mPrefs == null)
            mPrefs = getSharedPreferences("WATCHCHAT", MODE_PRIVATE);
        SharedPreferences.Editor editor = mPrefs.edit();
        if(currentUser != null) {
            editor.putString("UserName", currentUser.userName);
            editor.putString("UserId", currentUser.userId);
        } else {
            editor.putString("UserName", "");
            editor.putString("UserId", "");
        }
        editor.commit();

    }

    public void attemptSignIn(){
        EditText Username = (EditText) findViewById(R.id.Username);
        if(TextUtils.isEmpty(Username.getText())){
            Toast.makeText(getApplicationContext(), "Empty Username!", Toast.LENGTH_SHORT).show();
        } else{
            final Intent intent = new Intent(this, GroupsActivity.class);
            final String username = Username.getText().toString();
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("username", username);
            try{
                URL url = new URL("http://cs3300.elasticbeanstalk.com" + "/users/auth");
                client.post(url.toString(), params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            JSONObject response = new JSONObject(new String(responseBody));
                            if(response.getString("userId").equals("-1")){
                                Toast.makeText(getApplicationContext(), "Username does not exist!",
                                        Toast.LENGTH_SHORT).show();
                            } else{
                                Log.d("UserId", response.getString("userId"));
                                startGroupsActivity(response.getString("username"), response.getString("userId"));
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Parsing Error!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                          Throwable error) {
                        Log.e("Signin", error.getLocalizedMessage());
                        error.printStackTrace(System.out);
                        Toast.makeText(getApplicationContext(), "Network Error!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void attemptRegister(){
        EditText Username = (EditText) findViewById(R.id.Username);
        if(TextUtils.isEmpty(Username.getText())){
            Toast.makeText(getApplicationContext(), "Empty Username!", Toast.LENGTH_SHORT).show();
        } else{
            final Intent intent = new Intent(this, GroupsActivity.class);
            final String username = Username.getText().toString();
            AsyncHttpClient client = new AsyncHttpClient();
            RequestParams params = new RequestParams();
            params.put("username", username);

            try{
                URL url = new URL(new String("http://cs3300.elasticbeanstalk.com" + "/users"));
                client.post(url.toString(), params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        try {
                            JSONObject response = new JSONObject(new String(responseBody));

                            Log.d("UserId", response.getString("userId"));
                            startGroupsActivity(response.getString("username"), response.getString("userId"));
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Parsing Error!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody,
                                          Throwable error) {
                        if(statusCode == 405){
                            Toast.makeText(getApplicationContext(), "Username already exists!",
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("Register", error.getLocalizedMessage());
                            Toast.makeText(getApplicationContext(), "Network Error!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            } catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public void startGroupsActivity(String username, String userId){
        Intent intent = new Intent(this, GroupsActivity.class);
        currentUser = new User(username, userId);
        intent.putExtra("user", currentUser);
        startActivity(intent);
    }
}
