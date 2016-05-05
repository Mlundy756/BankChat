package utsa.edu.BankApplication.API;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utsa.edu.BankApplication.Profile;
import utsa.edu.BankApplication.R;
import utsa.edu.BankApplication.UserLogin;


/* The beginning activity. Also the create user activity.
 */
public class MainActivity extends AppCompatActivity {

    public static final Profile profile = new Profile();
    public static final MessageService service = Client.getClient();
    public static String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onResume() {
        super.onResume();
        /*

        // for cus
        String cusFirst = "Taylor", cusMiddle = "Aldus", cusLast = "Perrin";
        String cusEmail = "taylor.aldus.perrin@maildrop.cc";
        String cusPassword = "notTaylor";
        String cusBasicAuth =  Auth.genBasicAuth(cusEmail, cusPassword);
        */

    }

    /* Method called when login is clicked.
     */
    public void onLogin(View view) {
        Intent intent = new Intent(this, UserLogin.class);
        startActivity(intent);
    }

    /* Method called when create user is clicked.
     * This sends the credentials entered by the user to the server to create and authenticate
     * the user. It will create the user in the server and then authenticate according to the email.
     */
    public void onCreateUser(View view) {
        EditText firstNameView = (EditText) findViewById(R.id.firstNameText);
        String firstNameText = firstNameView.getText().toString();
        profile.setfirstName(firstNameText);

        EditText middleNameView = (EditText) findViewById(R.id.middleNameUserText);
        String middleNameText = middleNameView.getText().toString();
        profile.setmiddleName(middleNameText);

        EditText lastNameView = (EditText) findViewById(R.id.lastNameUserText);
        String lastNameText = lastNameView.getText().toString();
        profile.setlastName(lastNameText);

        EditText emailView = (EditText) findViewById(R.id.emailUserText);
        String emailText = emailView.getText().toString();
        profile.setEmail(emailText);

        EditText passView = (EditText) findViewById(R.id.passUserText);
        String passText = passView.getText().toString();
        profile.setPassword(passText);

        final String basicAuth = Auth.genBasicAuth(emailText, passText);

        boolean check;

        if (firstNameText.length() < 2 || passText.length() < 2 || middleNameText.length() < 2 || lastNameText.length() < 2 || emailText.length() < 2) {
            check = false;
            CharSequence text = "Something went wrong. Try Again!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, text, duration);
            toast.show();
        } else {
            check = true;
        }

        if (check) {
            service.createUser(firstNameText, middleNameText, lastNameText, emailText, passText).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.d("CreateonResponse", response.toString());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    CharSequence text = "Cannot connect to the server at the moment";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(MainActivity.this, text, duration);
                    toast.show();
                    t.printStackTrace();
                }
            });

            // returns the token
            // takes the basicAuth
            service.auth(basicAuth, Build.MODEL).enqueue(new Callback<JWTResponse>() {
                @Override
                public void onResponse(Call<JWTResponse> call, Response<JWTResponse> response) {
                    Log.d("GenAuth", response.toString());
                    Intent intent = new Intent(MainActivity.this, UserLogin.class);
                    startActivity(intent);
                    Log.d("AuthonResponse", response.toString());
                }

                @Override
                public void onFailure(Call<JWTResponse> call, Throwable t) {
                    t.printStackTrace();
                }
            });
        }
    }
}
