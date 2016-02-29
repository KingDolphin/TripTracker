package com.leonmontealegre.triptracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoadingCallback<T> implements AsyncCallback<T> {

    public static final String TAG = "LoadingCallback";

    private Context context;
    private ProgressDialog progressDialog;

    public LoadingCallback(Context context, String message) {
        this.context = context;
        this.progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void handleResponse(T response) {
        progressDialog.dismiss();
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        Toast.makeText(context, "Server reported an error – " + fault.getMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

//    private void alert(String title, String message) {
//        new AlertDialog.Builder(context)
//                .setTitle(title)
//                .setMessage(message)
//                .setIcon(R.drawable.ic_error_outline_black_24dp)
//                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .create()
//                .show();
//    }

}
