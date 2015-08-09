package com.thorrism.designtools.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Simple ImageView shaped into a square for effectively displaying
 * images in a fixed sized square based on the width.
 * <p/>
 * Created by Lucas Crawford on 6/27/2015.
 */
public class SquareImageView extends ImageView {

    private AnimationDrawable mAnim;
    private BitmapDrawable mBitmapDrawable;

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SquareImageView(Context context, AttributeSet attrs, int id) {
        super(context, attrs, id);
    }

    /**
     * Set an animation drawable placeholder. Targeted for use with Picasso / Glide for
     * downloading images into memory.
     *
     * @param anim - animation targeted for the placeholder.
     */
    public void setPlaceHolder(AnimationDrawable anim) {
        BitmapDrawable drawable = (BitmapDrawable) getDrawable();
        if (drawable != null)
            mBitmapDrawable = drawable;

        if (Build.VERSION.SDK_INT >= 16)
            setBackground(anim);
        else
            setBackgroundDrawable(anim);

        mAnim = anim;
        mAnim.start();
    }

    /**
     * Remove a placeholder animation post loading of an image. Targeted for use with Picasso /Glide
     * for downloading images into memory.
     */
    public void removePlaceHolder() {
        mAnim.stop();
        if (mBitmapDrawable != null) {
            if (Build.VERSION.SDK_INT >= 16)
                setBackground(mBitmapDrawable);
            else
                setBackgroundDrawable(mBitmapDrawable);
        }
    }

    @Override
    public void onMeasure(int measuredWidth, int measuredHeight) {
        super.onMeasure(measuredWidth, measuredHeight);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }
}
