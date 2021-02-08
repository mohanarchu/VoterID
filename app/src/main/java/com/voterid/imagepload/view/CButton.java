package com.voterid.imagepload.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import androidx.appcompat.widget.AppCompatButton;
import android.util.AttributeSet;

import com.voterid.aashi.voterid.R;

public class CButton extends AppCompatButton {

    public CButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    public CButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            init(attrs);
        }
    }

    public CButton(Context context) {
        super(context);
        if (!isInEditMode()) {
            init(null);
        }
    }

    private void init(AttributeSet attrs) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CButton);
            String fontName = a.getString(R.styleable.CButton_fontName);
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
