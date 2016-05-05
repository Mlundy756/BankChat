package utsa.edu.BankApplication;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utsa.edu.BankApplication.API.Auth;
import utsa.edu.BankApplication.API.JWTResponse;
import utsa.edu.BankApplication.API.MainActivity;

/* The User login activity
 */

public class UserLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    /* Called when the user clicks login.
     * The application pulls the credentials entered and verifies it with the server
     * that the account has been authenticated. If not, they need to go back and authenticate.
     * If authenticated, it will switch to the Main Access page.
     */
    public void onLogin(View view) {
        EditText userView = (EditText) findViewById(R.id.userLoginText);
        String userText = userView.getText().toString();
        MainActivity.profile.setEmail(userText);

        EditText passView = (EditText) findViewById(R.id.passLoginText);
        String passText = passView.getText().toString();
        MainActivity.profile.setPassword(passText);

        final String basicAuth = Auth.genBasicAuth(userText, passText);

        MainActivity.service.auth(basicAuth, Build.MODEL).enqueue(new Callback<JWTResponse>() {
            @Override
            public void onResponse(Call<JWTResponse> call, Response<JWTResponse> response) {
                if(!response.isSuccessful()){
                    CharSequence text = "You need to authenticate your account first!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(UserLogin.this, text, duration);
                    toast.show();
                    Log.d("AuthLoginonFailure", "Account hasn't been authorized");
                    return;
                } else {
                    MainActivity.token = Auth.genJWTAuth(response.body().access_token);//)){
                    Log.d("UJWTTOKEN", MainActivity.token);
                    Intent intent = new Intent(UserLogin.this, MainAccess.class);
                    startActivity(intent);
                    Log.d("AuthLoginonResponse", response.toString());
                }
            }

            @Override
            public void onFailure(Call<JWTResponse> call, Throwable t) {
                CharSequence text = "You need to authenticate your account first!";
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(UserLogin.this, text, duration);
                toast.show();
                Log.d("AuthLoginonFailure", t.getMessage());
            }
        });
    }

}
