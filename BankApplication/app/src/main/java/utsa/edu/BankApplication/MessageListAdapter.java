package utsa.edu.BankApplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import utsa.edu.BankApplication.API.MainActivity;
import utsa.edu.BankApplication.API.MessageList;

/**
 * Created by Matthew on 4/17/2016.
 */


/* Class that sets the adapter for the message list
 */
public class MessageListAdapter extends ArrayAdapter<MessageList.Message> {

    Context mContext;
    ArrayList<MessageList.Message> mMessage;


    public MessageListAdapter(Context context, int resource, ArrayList<MessageList.Message> message) {
        super(context, resource, message);
        this.mContext = context;
        this.mMessage = message;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.adapter_message_list, null);
        }

        MessageList.Message m = getItem(position);

        if (m != null) {
            TextView mid = (TextView) v.findViewById(R.id.Mid);
            TextView createdAt = (TextView) v.findViewById(R.id.createdAt);
            TextView text = (TextView) v. findViewById(R.id.text);

            if (mid != null) {
                mid.setText(MainActivity.profile.getfirstName());
            }

            if (createdAt != null) {
                createdAt.setText(m.createdAt);
            }

            if(text != null) {
                text.setText(m.text);
            }
        }
        return v;
    }
}
