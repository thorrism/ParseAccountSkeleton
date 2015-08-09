package com.thorrism.designtools.views;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.thorrism.designtools.R;

/**
 * Created by Lucas Crawford on 6/28/2015.
 */
public class MaterialDialog extends DialogFragment {

    private String mTitle;
    private String mBody;
    private Context mContext;

    private SubmitListener mListener;
    private String mSubmitText;
    private String mCancelText;

    public MaterialDialog setSubmitListener(SubmitListener listener) {
        mListener = listener;
        return this;
    }

    public SubmitListener getSubmitListener() {
        return mListener;
    }

    /**
     * Set title of MaterialDialog based on id or String.
     */
    public MaterialDialog setTitle(int id) {
        mTitle = mContext.getResources().getString(id);
        return this;
    }

    public MaterialDialog setTitle(String title) {
        mTitle = title;
        return this;
    }

    /**
     * Set body of MaterialDialog based on id or String.
     */
    public MaterialDialog setBody(int id) {
        mBody = mContext.getResources().getString(id);
        return this;
    }

    public MaterialDialog setBody(String body) {
        mBody = body;
        return this;
    }

    /**
     * First statement needed to start creating a MaterialDialog.
     */
    public static MaterialDialog with(Context context) {
        MaterialDialog dialog = new MaterialDialog();
        dialog.mContext = context;
        return dialog;
    }

    public static MaterialDialog with(Activity activity) {
        MaterialDialog dialog = new MaterialDialog();
        dialog.mContext = activity.getApplicationContext();
        return dialog;
    }

    /**
     * Set the submit button text based on an id or String
     * value.
     */
    public MaterialDialog setSubmitText(String str) {
        mSubmitText = str;
        return this;
    }

    public MaterialDialog setSubmitText(int id) {
        mSubmitText = mContext.getResources().getString(id);
        return this;
    }

    /**
     * Set the cancel button text based on an id or String value.
     *
     * @param str
     * @return
     */
    public MaterialDialog setCancelText(String str) {
        mCancelText = str;
        return this;
    }

    public MaterialDialog setCancelText(int id) {
        mCancelText = mContext.getResources().getString(id);
        return this;
    }

    /**
     * Default constructor
     */
    public MaterialDialog() {
    }

    public AppCompatDialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());


        //Inflater layout from user if provided, default to mine otherwise
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.dialog_material, null);
        TextView tv = (TextView) v.findViewById(R.id.bodyTextView);
        tv.setText(mBody); //prevent user error.

        //set title if necessary
        if (mTitle != null) builder.setTitle(mTitle);

        //Attach click listeners
        FlatButton submitBtn = (FlatButton) v.findViewById(R.id.submit_btn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSubmitListener().onSubmit();
                getDialog().cancel();
            }
        });

        FlatButton cancelBtn = (FlatButton) v.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().cancel();
            }
        });

        //Set the text for the buttons. Check if the user has set custom text for these
        if (mCancelText != null) cancelBtn.setText(mCancelText);
        else mCancelText = getActivity().getResources().getString(R.string.cancel_text);

        if (mSubmitText != null) submitBtn.setText(mSubmitText);
        else mSubmitText = getActivity().getResources().getString(R.string.submit_text);

        builder.setView(v);
        return builder.create();
    }

    /**
     * Interface for letting the activity know the user has completed their actions
     * with this dialog.
     */
    public interface SubmitListener {
        public void onSubmit();
    }
}
