package com.leonmontealegre.triptracker;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class LoadingCallback<T> implements AsyncCallback<T> {

    public static final String TAG = "LoadingCallback";

    private Context context;
    private OnResponseListener<T> responseListener;
    private OnErrorListener errorListener;
    private ProgressDialog progressDialog;
    private boolean alerted = false;

    public LoadingCallback(Context context, String message, OnResponseListener<T> responseListener) {
        this(context, message, responseListener, null);
    }

    public LoadingCallback(Context context, String message, OnResponseListener<T> responseListener, OnErrorListener errorListener) {
        this.context = context;
        this.responseListener = responseListener;
        this.errorListener = errorListener;
        this.progressDialog = new ProgressDialog(context);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void handleResponse(T response) {
        if (responseListener != null)
            responseListener.onResponse(response);
        progressDialog.dismiss();
    }

    @Override
    public void handleFault(BackendlessFault fault) {
        if (errorListener != null)
            errorListener.onError(fault);
        else
            Toast.makeText(context, "Server reported an error – " + fault.getMessage(), Toast.LENGTH_SHORT).show();
        progressDialog.dismiss();
    }

    private void alert(String title, String message) {
        Log.e(TAG, "WHAT");
        alerted = true;
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setIcon(R.drawable.ic_error_outline_black_24dp)
                .setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create()
                .show();
    }

    public interface OnResponseListener<T> {
        void onResponse(T response);
    }

    public interface OnErrorListener {
        void onError(BackendlessFault fault);
    }

}
