package zamir.com.smartmeters.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import zamir.com.smartmeters.R;
import zamir.com.smartmeters.activities.BillDetailsActivity;
import zamir.com.smartmeters.app.Config;
import zamir.com.smartmeters.model.BillItem;

/**
 * Created by engsa on 02/07/2017.
 */

public class BillAdapter extends RecyclerView.Adapter<BillAdapter.NotificationViewHolder> {

    ArrayList<BillItem> billItemList = new ArrayList<>();
    Context mContext;
    BillItem billItem;

    public BillAdapter(ArrayList<BillItem> billItemList, Context mContext) {
        this.billItemList = billItemList;
        this.mContext = mContext;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView billMonth;
        public TextView billAmount;

        public NotificationViewHolder(View view) {
            super(view);
            billMonth = (TextView) view.findViewById(R.id.billMonth);
            billAmount = (TextView) view.findViewById(R.id.billAmount);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BillDetailsActivity.class);
                    intent.putExtra(Config.KEY__BILL_apartment_bill, billItem.getValue());
                    intent.putExtra(Config.KEY__BILL_apartment_code, billItem.getMeterCode());
                    intent.putExtra(Config.KEY__BILL_total_consumption, billItem.getTotalConsumption());
                    intent.putExtra(Config.KEY__BILL_Month, billItem.getMonth());
                    intent.putExtra(Config.KEY__BILL_most_used_devices, billItem.getMostUsedDevices());
                    mContext.startActivity(intent);
                }
            });
        }
    }

    @Override
    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.bill_item, parent, false);
        return new NotificationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        BillItem billItem = billItemList.get(position);
        this.billItem = billItem;
        holder.billAmount.setText(billItem.getValue());
        holder.billMonth.setText(billItem.getMonth());
    }

    @Override
    public int getItemCount() {
        return billItemList.size();
    }


}
