package com.thorrism.designtools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.thorrism.designtools.R;

/**
 * Simple View used for drawing text with a shadow. Similar to Android's default
 * TextView, but with an added shadow. Not a part of Google's Material Design
 * guide lines, but could be an interesting tool.
 * <p/>
 * Created by Lucas Crawford on 6/23/2015.
 */
public class TextViewShadow extends View {

    private Paint mShadowPaint;
    private float mTextSize;
    private int mTextColor;
    private int mShadowWidth;
    private CharSequence mText;
    private final Rect mTextBounds = new Rect(); //don't new this up in a draw method

    public TextViewShadow(Context context) {
        super(context);
        init();
    }

    public TextViewShadow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TextViewShadow(Context context, AttributeSet attrs, int id) {
        super(context, attrs, id);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.TextViewShadow, id, 0);
        mText = a.getString(R.styleable.TextViewShadow_text);
        mTextColor = a.getColor(R.styleable.TextViewShadow_textColor, Color.BLACK);
        mTextSize = (int) a.getDimension(R.styleable.TextViewShadow_textSize,
                context.getResources().getDimension(R.dimen.textSizeMedium));
        mShadowWidth = (int) a.getDimension(R.styleable.TextViewShadow_textElevation,
                context.getResources().getDimension(R.dimen.textShadowMedium));
        a.recycle();
        init();
    }

    public float getTextSize() {
        return mTextSize;
    }

    public int getTextColor() {
        return mTextColor;
    }

    public CharSequence getText() {
        return mText;
    }

    public void setText(String s) {
        mText = s;
        invalidate();
        requestLayout();
    }

    private void init() {
        mShadowPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mShadowPaint.setTextSize(getTextSize());
        mShadowPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        mShadowPaint.setColor(getTextColor());
        mShadowPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mShadowPaint.setStrokeWidth(1.0f);
        mShadowPaint.setShadowLayer(mShadowWidth, 2.5f, 2.5f, Color.parseColor("#999999"));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mShadowPaint.getTextBounds(mText.toString(), 0, mText.length(), mTextBounds);
        canvas.drawText(getText().toString(),
                ((getWidth() / 2) - (mTextBounds.exactCenterX() + mShadowWidth)),
                ((getHeight() / 2) - (mTextBounds.exactCenterY() + mShadowWidth)),
                mShadowPaint);

    }

    @Override
    public void onMeasure(int measuredWidth, int measuredHeight) {
        super.onMeasure(measuredWidth, measuredHeight);
        mShadowPaint.getTextBounds(mText.toString(), 0, mText.length(), mTextBounds);
        int extra = (int) (mShadowWidth * 2.75);
        setMeasuredDimension(mTextBounds.width() + extra, (int) getTextSize() + extra);
    }
}
