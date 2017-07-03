package zamir.com.smartmeters.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.model.Notification;

/**
 * Created by engsa on 02/07/2017.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {

    ArrayList<Notification> notificationList = new ArrayList<>();
    Context mContext;

    public NotificationAdapter(ArrayList<Notification> notificationList, Context mContext) {
        this.notificationList = notificationList;
        this.mContext = mContext;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView message;

        public NotificationViewHolder(View view) {
            super(view);
            message = (TextView) view.findViewById(R.id.list_item);
        }
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.notification_item, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        Notification movie = notificationList.get(position);
        holder.message.setText(movie.getMessage());
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}
