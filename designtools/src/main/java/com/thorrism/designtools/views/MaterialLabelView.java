package com.thorrism.designtools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thorrism.designtools.R;

/**
 * Created by Lucas Crawford on 7/7/2015.
 */
public class MaterialLabelView extends TextView {
    private int mTextPadding;

    //Attributes for the Label View
    private int mBackgroundColor;

    public MaterialLabelView(Context context) {
        super(context);
        init();
    }

    public MaterialLabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialLabelView(Context context, AttributeSet attrs, int resourceId) {
        super(context, attrs, resourceId);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MaterialLabelView, resourceId, 0);
        mBackgroundColor = a.getColor(R.styleable.MaterialLabelView_labelColor, getCurrentTextColor());
        mTextPadding = (int) a.getDimension(R.styleable.MaterialLabelView_sidePadding,
                getContext().getResources().getDimension(R.dimen.text_padding_left));
        a.recycle();

        init();
    }

    private void init() {
        setText(getText().toString().toUpperCase());

        //Add padding inward for the text
        setPadding(getPaddingLeft() + mTextPadding, getPaddingTop() + mTextPadding,
                getPaddingRight() + mTextPadding, getPaddingBottom() + mTextPadding);
        setBackgroundColor(mBackgroundColor);
    }

    @Override
    protected void onMeasure(int measuredWidth, int measuredHeight) {
        super.onMeasure(measuredWidth, measuredHeight);

        int height = getMeasuredHeight();
        int width = getMeasuredWidth();
        setMeasuredDimension(width, height);
    }
}
