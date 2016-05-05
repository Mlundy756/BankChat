package utsa.edu.BankApplication;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utsa.edu.BankApplication.API.ChatList;
import utsa.edu.BankApplication.API.MainActivity;

/* This populates the screen with the list of chats.
 */

public class ChatActivity extends ListActivity {

    ChatList.Chat[] chats;
    ListView listView1;
    ChatListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainActivity.service.listSupportChats(MainActivity.token)
                .enqueue(new Callback<ChatList>() {
                             @Override
                             public void onResponse(Call<ChatList> call, Response<ChatList> response) {
                                 if (response.isSuccessful()) {
                                     chats = response.body().chats;

                                     if (chats == null) {
                                         Log.d("chats == null", "NULL");
                                         finish();
                                     } else {
                                         setContentView(R.layout.activity_chat);

                                         listView1 = getListView();
                                         ArrayList<ChatList.Chat> chatList = new ArrayList<>(Arrays.asList(chats));
                                         Log.d("Array: ", chatList.toString());
                                         adapter = new ChatListAdapter(ChatActivity.this, R.layout.activity_chat_list, chatList);
                                         listView1.setAdapter(adapter);
                                         listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                             @Override
                                             public void onItemClick(AdapterView<?> l, View v, int position, long id) {

                                                 AlertDialog.Builder adb = new AlertDialog.Builder(ChatActivity.this);
                                                 final int pos = position;

                                                 adb.setTitle("Chat options");
                                                 adb.setMessage("Delete entry or continue?");

                                                 adb
                                                         .setCancelable(false)
                                                         .setPositiveButton("Open Chat",
                                                                 new DialogInterface.OnClickListener() {
                                                                     public void onClick(DialogInterface dialog, int id) {
                                                                         Log.d("ListView", "You clicked Item: " + id + " at position:" + pos);
                                                                         Intent intent = new Intent();
                                                                         intent.setClass(ChatActivity.this, MessageActivity.class);
                                                                         intent.putExtra("chatID", chats[pos].id);
                                                                         intent.putExtra("chatTimeStamp", chats[pos].createdAt);
                                                                         intent.putExtra("position", pos);
                                                                         intent.putExtra("id", id);
                                                                         startActivity(intent);
                                                                     }
                                                                 })
                                                         .setNegativeButton("Delete",
                                                                 new DialogInterface.OnClickListener() {
                                                                     public void onClick(DialogInterface dialog, int id) {
                                                                         MainActivity.service.removeSupportChat(MainActivity.token, chats[pos].id).enqueue(new Callback<Void>() {
                                                                             @Override
                                                                             public void onResponse(Call<Void> call, Response<Void> response) {
                                                                                 Log.d("ChatStartResponse", response.toString());
                                                                                 if (response.isSuccessful()) {
                                                                                     finish();
                                                                                     startActivity(getIntent());
                                                                                 } else {
                                                                                     CharSequence text = "Something went wrong with deleting the chat!";
                                                                                     int duration = Toast.LENGTH_SHORT;
                                                                                     Toast toast = Toast.makeText(ChatActivity.this, text, duration);
                                                                                     toast.show();
                                                                                 }
                                                                             }

                                                                             @Override
                                                                             public void onFailure(Call<Void> call, Throwable t) {
                                                                                 Log.d("ChatStartFailure", t.getMessage());
                                                                             }
                                                                         });
                                                                     }
                                                                 });

                                                 AlertDialog alertDialog = adb.create();
                                                 alertDialog.show();
                                             }
                                         });
                                     }
                                 }
                                 Log.d("ChatListResponse", response.toString());
                             }

                             @Override
                             public void onFailure(Call<ChatList> call, Throwable t) {
                                 Log.d("ChatListFailure", t.getMessage());
                             }
                         }
                );
    }
}
