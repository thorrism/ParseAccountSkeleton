package com.thorrism.designtools.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thorrism.designtools.R;

/**
 * Created by Lucas Crawford on 6/4/2015.
 */
public class LabelView extends TextView {
    private Paint mLinePaint;
    private float mLineThickness;
    private float mLinePadding;
    private int mTextPaddingLeft;

    //Attributes for the Label View
    private int mLineColor = Color.RED;

    public LabelView(Context context) {
        super(context);
        init();
    }

    public LabelView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(Context context, AttributeSet attrs, int resourceId) {
        super(context, attrs, resourceId);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LabelView, resourceId, 0);
        mLineColor = a.getColor(R.styleable.LabelView_lineColor, getCurrentTextColor());
        mLineThickness = a.getDimension(R.styleable.LabelView_lineThickness,
                getContext().getResources().getDimension(R.dimen.label_line_thickness));
        mLinePadding = (int) a.getDimension(R.styleable.LabelView_linePadding,
                getContext().getResources().getDimension(R.dimen.label_line_padding));
        mTextPaddingLeft = (int) a.getDimension(R.styleable.LabelView_textPadding,
                getContext().getResources().getDimension(R.dimen.text_padding_left));
        a.recycle();

        init();
    }

    private void init() {
        setText(getText().toString().toUpperCase());

        //Init paint for the line under the text
        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStrokeWidth(mLineThickness);
        mLinePaint.setStyle(Paint.Style.STROKE);

        //Add padding inward for the text
        setPadding(getPaddingLeft() + mTextPaddingLeft, getPaddingTop(),
                getPaddingRight(), getPaddingBottom());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawLine(getPaddingLeft() - mTextPaddingLeft, getMeasuredHeight() - mLineThickness,
                getMeasuredWidth(), getMeasuredHeight() - mLineThickness,
                mLinePaint);
    }

    @Override
    protected void onMeasure(int measuredWidth, int measuredHeight) {
        super.onMeasure(measuredWidth, measuredHeight);

        View v = (View) getParent();
        ViewGroup.LayoutParams lp = v.getLayoutParams();
        int height = (int) (getMeasuredHeight() + mLinePadding);
        int width = lp.width;
        setMeasuredDimension(width, height);
    }

}