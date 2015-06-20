package gatech.cs3300.watchchat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    public void attemptSignIn(){
        EditText Username = (EditText) findViewById(R.id.Username);
        if(TextUtils.isEmpty(Username.getText())){
            Toast.makeText(getApplicationContext(), "Empty Username!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "Signing In!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, GroupsActivity.class));
        }
    }

    public void attemptRegister(){
        EditText Username = (EditText) findViewById(R.id.Username);
        if(TextUtils.isEmpty(Username.getText())){
            Toast.makeText(getApplicationContext(), "Empty Username!", Toast.LENGTH_SHORT).show();
        } else{
            Toast.makeText(getApplicationContext(), "Signing In!", Toast.LENGTH_SHORT).show();
        }
    }
}
