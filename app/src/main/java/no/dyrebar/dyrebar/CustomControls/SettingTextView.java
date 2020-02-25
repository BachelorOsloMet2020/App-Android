package no.dyrebar.dyrebar.CustomControls;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import no.dyrebar.dyrebar.R;

public class SettingTextView extends ConstraintLayout
{
    private int Image;
    private String TopText;
    private String BottomText;

    public SettingTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_settingtext, this);

        if (attrs != null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SettingTextView);
            if (a.hasValue(R.styleable.SettingTextView_textTop))
            {
                TopText = a.getString(R.styleable.SettingTextView_textTop);
                ((TextView)findViewById(R.id.view_settingtext_textview1)).setText(TopText);
            }
            if (a.hasValue(R.styleable.SettingTextView_textBottom))
            {
                BottomText = a.getString(R.styleable.SettingTextView_textBottom);
                ((TextView)findViewById(R.id.view_settingtext_textview2)).setText(BottomText);
            }
            if (a.hasValue(R.styleable.SettingTextView_src))
            {
                Image = a.getResourceId(R.styleable.SettingTextView_src, 0);
                if (Image != 0)
                    ((ImageView)findViewById(R.id.view_settingtext_imageview)).setImageResource(Image);
            }

            a.recycle();
        }
    }

    public void setTextTop(String text)
    {
        if (text != null)
            ((TextView)findViewById(R.id.view_settingtext_textview1)).setText(text);
    }

    public void setTextBottom(String text)
    {
        if (text != null)
            ((TextView)findViewById(R.id.view_settingtext_textview2)).setText(text);
    }

}
