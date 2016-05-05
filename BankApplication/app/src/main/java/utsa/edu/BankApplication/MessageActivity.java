package utsa.edu.BankApplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utsa.edu.BankApplication.API.MainActivity;
import utsa.edu.BankApplication.API.MessageList;

/* This class is the message class.
 * The actual chat view.
 * Send and view messages.
 */

public class MessageActivity extends ListActivity {
    public EditText mMessage;
    public String firstName;
    MessageList.Message[] messages;
    ArrayAdapter<MessageList.Message> adapter;
    ArrayList<MessageList.Message> messageList;
    Bundle extras;
    ListView listView;


    /* Populates view with all messages.
     * Shows all messages that deal with the current user in that chat Id.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.firstName = MainActivity.profile.getEmail();

        if (getIntent().getExtras() != null) {
            extras = getIntent().getExtras();
            MainActivity.service.listMessages(MainActivity.token, extras.getString("chatID")).enqueue(new Callback<MessageList>() {
                @Override
                public void onResponse(Call<MessageList> call, Response<MessageList> response) {
                    if (response.isSuccessful()) {
                        messages = response.body().messages;
                        if (messages != null) {
                            setContentView(R.layout.activity_message);
                            listView = getListView();
                            messageList = new ArrayList<>(Arrays.asList(messages));
                            Log.d("Array: ", messageList.toString());
                            adapter = new MessageListAdapter(MessageActivity.this, R.layout.adapter_message_list, messageList);
                            listView.setAdapter(adapter);
                        }
                    }
                    Log.d("MessageListResponse", response.toString());
                }

                @Override
                public void onFailure(Call<MessageList> call, Throwable t) {
                    Log.d("MessageListFailure", t.getMessage());
                }
            });
        } else {
            MainActivity.service.listMessages(MainActivity.token, MainAccess.chatId).enqueue(new Callback<MessageList>() {
                @Override
                public void onResponse(Call<MessageList> call, Response<MessageList> response) {
                    if (response.isSuccessful()) {
                        messages = response.body().messages;
                        if (messages != null) {
                            setContentView(R.layout.activity_message);
                            listView = getListView();
                            messageList = new ArrayList<>(Arrays.asList(messages));
                            Log.d("Array: ", messageList.toString());
                            adapter = new MessageListAdapter(MessageActivity.this, R.layout.adapter_message_list, messageList);
                            listView.setAdapter(adapter);
                        }
                    }
                    Log.d("MessageListResponse", response.toString());
                }

                @Override
                public void onFailure(Call<MessageList> call, Throwable t) {
                    Log.d("MessageListFailure", t.getMessage());
                }
            });
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /* Called when "SEND" is clicked.
     * Posts message and sends the message content with the chat id to the server.
     */
    public void sendMessage(View view) {
        mMessage = (EditText) findViewById(R.id.message_et);
        final String message = mMessage.getText().toString();
        if (message.equals("")) return;
        mMessage.setText("");
        if (extras == null) {
            MainActivity.service.postMessage(MainActivity.token, MainAccess.chatId, message).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    updateMessageList();
                    Log.d("SendonResponse", response.toString());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    CharSequence text = "Cannot connect to the server at the moment";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(MessageActivity.this, text, duration);
                    toast.show();
                    t.printStackTrace();
                }
            });
        } else {
            MainActivity.service.postMessage(MainActivity.token, extras.getString("chatID"), message).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    updateMessageList();
                    Log.d("PostonResponse", response.toString());
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    CharSequence text = "Cannot connect to the server at the moment";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(MessageActivity.this, text, duration);
                    toast.show();
                    t.printStackTrace();
                }
            });
        }
    }

    /* Called when "Send" is clicked.
     * Updates current message view with the updated message.
     */
    private void updateMessageList() {
        if (extras != null) {
            MainActivity.service.listMessages(MainActivity.token, extras.getString("chatID")).enqueue(new Callback<MessageList>() {
                @Override
                public void onResponse(Call<MessageList> call, Response<MessageList> response) {
                    if (response.isSuccessful()) {
                        messages = response.body().messages;
                        if (messages != null) {
                            setContentView(R.layout.activity_message);
                            listView = getListView();
                            messageList = new ArrayList<>(Arrays.asList(messages));
                            Log.d("Array: ", messageList.toString());
                            adapter = new MessageListAdapter(MessageActivity.this, R.layout.adapter_message_list, messageList);
                            listView.setAdapter(adapter);
                        }
                    }
                    Log.d("UpdateListResponse", response.toString());
                }

                @Override
                public void onFailure(Call<MessageList> call, Throwable t) {
                    Log.d("UpdateListFailure", t.getMessage());
                }
            });
        } else {
            MainActivity.service.listMessages(MainActivity.token, MainAccess.chatId).enqueue(new Callback<MessageList>() {
                @Override
                public void onResponse(Call<MessageList> call, Response<MessageList> response) {
                    if (response.isSuccessful()) {
                        messages = response.body().messages;
                        if (messages != null) {
                            setContentView(R.layout.activity_message);
                            listView = getListView();
                            messageList = new ArrayList<>(Arrays.asList(messages));
                            Log.d("Array: ", messageList.toString());
                            adapter = new MessageListAdapter(MessageActivity.this, R.layout.adapter_message_list, messageList);
                            listView.setAdapter(adapter);
                            //adapter.notifyDataSetChanged();
                        }
                    }
                    Log.d("UpdateListResponse", response.toString());
                }

                @Override
                public void onFailure(Call<MessageList> call, Throwable t) {
                    Log.d("UpdateListFailure", t.getMessage());
                }
            });
        }
    }
}