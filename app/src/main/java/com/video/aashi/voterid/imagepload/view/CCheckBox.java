package com.video.aashi.voterid.imagepload.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.util.AttributeSet;

import com.video.aashi.voterid.R;


public class CCheckBox extends AppCompatCheckBox {

    public CCheckBox(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    public CCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    public CCheckBox(Context context) {
        super(context);
        if (!isInEditMode()) {
            init(null);
        }
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CCheckBox);
            String fontName = a.getString(R.styleable.CCheckBox_fontName);
            if (fontName != null) {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }

    public void setFontFace(String fontFace) {
        if (fontFace != null) {
            Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), fontFace);
            setTypeface(myTypeface);
        }
    }
}
