package com.thorrism.designtools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.thorrism.designtools.R;

/**
 * Custom CardView used to implement a raised button that works
 * across early Android APIs (API 7+). This view's design is
 * intended to be modeled after Google's Material Design guidelines
 * for a "raised button".
 * <p/>
 * Created by Lucas Crawford on 6/21/2015.
 */
public class RaisedButton extends CardView {

    //View properties for the raised button
    private int mBtnPadding;      //Inner padding for button's text spacing
    private int mBtnElevation;    //Elevation of the button
    private int mBtnCornerRadius; //Radius for button's rounded corners.
    private int mBtnColor;
    private int mTextColor;
    private AppCompatTextView mTextView;
    private FrameLayout mWrapper;
    private String mText;

    //Static variables for testing
    public static String DEFAULT_TEXT = "Raised Button";

    public RaisedButton(Context ctx) {
        super(ctx);
        init();
    }

    public RaisedButton(Context ctx, AttributeSet attrs) {
        this(ctx, attrs, 0);
        init();
    }

    public RaisedButton(Context ctx, AttributeSet attrs, int id) {
        super(ctx, attrs, id);

        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.RaisedButton, id, 0);

        //Obtain user attributes
        mBtnCornerRadius = (int) a.getDimension(R.styleable.RaisedButton_cornerRadius,
                getContext().getResources().getDimension(R.dimen.raised_button_corner_radius));
        mBtnElevation = (int) a.getDimension(R.styleable.RaisedButton_raisedElevation,
                getContext().getResources().getDimension(R.dimen.raised_button_elevation));
        mBtnPadding = (int) a.getDimension(R.styleable.RaisedButton_innerPadding,
                getContext().getResources().getDimension(R.dimen.raised_button_padding));
        mBtnColor = a.getColor(R.styleable.RaisedButton_backgroundColor, Color.WHITE);
        mTextColor = a.getColor(R.styleable.RaisedButton_contentColor, Color.BLACK);
        mText = a.getString(R.styleable.RaisedButton_content);
        a.recycle();

        init();
    }

    private void init() {
        setCardBackgroundColor(mBtnColor);
        setCardElevation(mBtnElevation);
        setRadius(mBtnCornerRadius);

        //Set parameters for the wrapper layout that will hold the textview in the card view
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        mWrapper = new FrameLayout(getContext());
        mWrapper.setLayoutParams(params);

        //Set parameters for the text view in the raised button
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.CENTER;
        params.setMargins(mBtnPadding, mBtnPadding, mBtnPadding, mBtnPadding);
        mTextView = new AppCompatTextView(getContext());
        mTextView.setLayoutParams(params);
        mTextView.setText(mText);
        mTextView.setTextColor(mTextColor);
        mTextView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD);
        mTextView.setSingleLine(true);
        mTextView.setAllCaps(true);
        mTextView.setLines(1);

        //Add TextView to the wrapper layout, and wrapper to the Card.
        mWrapper.addView(mTextView);
        addView(mWrapper);
    }

    public void setBackgroundColor(int color) {
        setCardBackgroundColor(color);
        invalidate();
    }

    public void setTextColor(int color) {
        mTextView.setTextColor(color);
        invalidate();
    }

    public void setEnabled(boolean enabled) {
        if (enabled) {
            setClickable(true);
            setCardBackgroundColor(mBtnColor);
            mTextView.setTextColor(mTextColor);
        } else {
            setClickable(false);
            setCardBackgroundColor(getResources().getColor(R.color.disabled_color));
            mTextView.setTextColor(getResources().getColor(R.color.disabled_font));
        }
        invalidate();
    }

    /**
     * Add a new click listener for the card view
     *
     * @param listener - click listener for the raised button
     */
    public void addClickListener(OnClickListener listener) {
        setOnClickListener(listener);
    }

    /**
     * Update the text for the raised buttons text.
     *
     * @param str - updated text in the button
     */
    public void setText(String str) {
        //Fixes bug that happens when updating text.
        removeAllViews();
        invalidate();

        //Update the text in the text view by re-adding the wrapper.
        mTextView.setText(str);
        addView(mWrapper);
        invalidate();
    }

}
