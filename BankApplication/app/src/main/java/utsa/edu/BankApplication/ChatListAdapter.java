package utsa.edu.BankApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import utsa.edu.BankApplication.API.ChatList;

/**
 * Created by Matthew on 4/17/2016.
 */

/* Adapter used to create the chat list.
 */
public class ChatListAdapter extends ArrayAdapter<ChatList.Chat> {

    public ChatListAdapter(Context context, int resource, ArrayList<ChatList.Chat> chats) {
        super(context, resource, chats);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.activity_chat_list, null);
        }

        ChatList.Chat c = getItem(position);

        if (c != null) {
            TextView topic = (TextView) v.findViewById(R.id.topic);
            TextView createdAt = (TextView) v.findViewById(R.id.createdAt);

            if (topic != null) {
                topic.setText(c.id);
            }

            if (createdAt != null) {
                createdAt.setText(c.createdAt);
            }
        }

        return v;
    }
}
