package zamir.com.smartmeters.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import zamir.com.smartmeters.R;

import static zamir.com.smartmeters.fragments.ProfileFragment.user;

/**
 * Created by MahmoudSamir on 3/28/2017.
 */

public class AddDeviceDialog extends DialogFragment {

    Button addDevice, cancelAddDevice;
    EditText deviceName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_add_device, container, false);
        addDevice = (Button) view.findViewById(R.id.add_action);
        cancelAddDevice = (Button) view.findViewById(R.id.cancel_action);
        deviceName = (EditText) view.findViewById(R.id.device_name);
        addDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deviceName.getText().toString().isEmpty()) {
                    deviceName.setError("Empty field");
                } else {
                    Toast.makeText(getActivity(), deviceName.getText().toString(), Toast.LENGTH_SHORT).show();
                    user.addDevice(deviceName.getText().toString());
                    AddDeviceDialog.this.dismiss();
                }
            }
        });

        cancelAddDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddDeviceDialog.this.dismiss();
            }
        });
        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // Get the layout inflater
//        LayoutInflater inflater = getActivity().getLayoutInflater();
//
//        builder.setView(inflater.inflate(R.layout.activity_add_device, null));
//        addDevice = (Button)
//
//        return builder.create();
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }
}
