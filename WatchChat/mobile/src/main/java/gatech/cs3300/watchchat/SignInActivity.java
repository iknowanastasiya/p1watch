package gatech.cs3300.watchchat;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

public class SignInActivity extends ActionBarActivity {

    // Views
    private EditText mEmailView;
    private EditText mPasswordView;
    private Button mSignInButton;
    private ProgressBar mSignInActivityIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmailView = (EditText) findViewById(R.id.email_text_field);
        mPasswordView = (EditText) findViewById(R.id.password_text_field);
        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInActivityIndicator = (ProgressBar) findViewById(R.id.sign_in_activity_indicator);

        mSignInActivityIndicator.setVisibility(View.GONE);

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSignIn();
            }
        });

        mEmailView.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void attemptSignIn() {
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);


        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        View errorView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_password_field_required));
            errorView = mPasswordView;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_password_field_invalid));
            errorView = mPasswordView;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_email_field_required));
            errorView = mEmailView;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_email_field_invalid));
            errorView = mEmailView;
        }

        if (errorView != null) {
            errorView.requestFocus();
        } else {
            performSignIn(email, password);
        }
    }

    private void performSignIn(String email, String password) {
        mSignInActivityIndicator.setVisibility(View.VISIBLE);

        // Perform Sign In
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 1;
    }
}
