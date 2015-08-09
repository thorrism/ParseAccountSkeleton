package com.thorrism.designtools.views;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.thorrism.designtools.R;


/**
 * Created by Lucas Crawford on 6/28/2015.
 */
public class FlatButton extends AppCompatButton {

    public FlatButton(Context context) {
        super(context);
        init();
    }

    public FlatButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlatButton(Context context, AttributeSet attrs, int id) {
        super(context, attrs, id);
        init();
    }

    private void init() {
//        TypedArray a = getContext().obtainStyledAttributes(new int[]{android.R.attr.selectableItemBackground});
//        int res = a.getResourceId(0, 0);
//        a.recycle();
//
//        //Set background as transparent
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
//            setBackground(getContext().getResources().getDrawable(res));
//        } else {
//            setBackgroundDrawable(getContext().getResources().getDrawable(res));
//        }


        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB) {
            setBackground(getContext().getResources().getDrawable(R.drawable.flat_button));
        } else {
            setBackgroundDrawable(getContext().getResources().getDrawable(R.drawable.flat_button));
        }
        int innerPadding = (int) getContext().getResources().getDimension(R.dimen.dialog_button_padding);
        setPadding(innerPadding, innerPadding, innerPadding, innerPadding);
        setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
    }

    public void addClickListener(OnClickListener listener) {
        setOnClickListener(listener);
    }
}
