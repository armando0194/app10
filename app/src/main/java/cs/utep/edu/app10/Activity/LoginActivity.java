package cs.utep.edu.app10.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import cs.utep.edu.app10.R;
import cs.utep.edu.app10.Util.TimeUtil;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    EditText emailText;
    EditText passwordText;
    ImageView loginButton;
    TextView signupLink;
    Button skip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        skip = findViewById(R.id.skip);
        emailText = findViewById(R.id.email);
        passwordText = findViewById(R.id.password);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        checkForToken();
        setUpLogin();
//
//
//        _signupLink.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                // Start the Signup activity
////                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
////                startActivityForResult(intent, REQUEST_SIGNUP);
//            }
//        });
    }

    private void setUpLogin(){
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void checkForToken() {
        SharedPreferences sharedPrefs = getSharedPreferences("token", MODE_PRIVATE);

        if(sharedPrefs.contains("token_date") && !isTokenExpired(sharedPrefs)){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    private boolean isTokenExpired(SharedPreferences sharedPrefs){
        String strDate = sharedPrefs.getString("token_date", "empty");
        if(strDate.equals("empty"))
            return false;

        Date tokenDate = TimeUtil.str2Date(strDate);
        long difference =  TimeUtil.currDate().getTime() - tokenDate.getTime();
        long differenceDays = difference / (1000 * 60 * 60 * 24);

        return (differenceDays >= 30);
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }



    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
//        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

//        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = emailText.getText().toString();
        String password = passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("enter a valid email address");
            valid = false;
        } else {
            emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            passwordText.setError(null);
        }

        return valid;
    }
}
