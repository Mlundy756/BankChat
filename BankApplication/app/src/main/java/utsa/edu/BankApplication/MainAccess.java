package utsa.edu.BankApplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utsa.edu.BankApplication.API.ChatResponse;
import utsa.edu.BankApplication.API.MainActivity;


/* This class is the main page after login
 * Prompts the user for 3 options.
 * View chat list
 * Logout
 * Start a new chat
 */
public class MainAccess extends AppCompatActivity {

    public static String topic;
    public static String chatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_access);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    /* This method is called when the user clicks on view chat list activity.
     * It switches to the chat list page.
     */
    public void onChatList(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        startActivity(intent);
    }


    /* This method is called when the user clicks logout.
     * It clears everything from the app including the saved token and returns to the create user page.
     */
    public void onLogOut(View view) {
        Intent i = getBaseContext().getPackageManager()
                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }


    /* This method is called when the user clicks on Start New Chat!
     * It will bring up a new dialog box prompting the user to enter in a topic
     * When the User enters in the topic then it will call server method startSupportchat to create the support chat.
     * It will then switch over to the chat list activity
     */
    public void onChat(View view) {
        LayoutInflater li = LayoutInflater.from(this);
        View promptsView = li.inflate(R.layout.channel_change, null);
        AlertDialog.Builder adb = new AlertDialog.Builder(this);

        adb.setView(promptsView);

        final EditText userInput = (EditText) promptsView.findViewById(R.id.editTextDialogUserInput);
        userInput.setText("");
        userInput.setSelection(userInput.getText().length());  // Move cursor to end

        adb
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                topic = userInput.getText().toString();
                                MainActivity.service.startSupportChat(MainActivity.token, topic).enqueue(new Callback<ChatResponse>() {
                                    @Override
                                    public void onResponse(Call<ChatResponse> call, Response<ChatResponse> response) {
                                        Log.d("ChatStartResponse", response.toString());
                                        if (response.isSuccessful()) {
                                            chatId = response.body().id;
                                            Intent intent = new Intent(MainAccess.this, ChatActivity.class);
                                            startActivity(intent);
                                        } else {
                                            CharSequence text = "Something went wrong with creating the chat!";
                                            int duration = Toast.LENGTH_SHORT;
                                            Toast toast = Toast.makeText(MainAccess.this, text, duration);
                                            toast.show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ChatResponse> call, Throwable t) {
                                        Log.d("ChatStartFailure", t.getMessage());
                                    }
                                });
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = adb.create();
        alertDialog.show();
    }

}
