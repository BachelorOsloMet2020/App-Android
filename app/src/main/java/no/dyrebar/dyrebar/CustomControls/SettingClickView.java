package no.dyrebar.dyrebar.CustomControls;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import no.dyrebar.dyrebar.R;

public class SettingClickView extends ConstraintLayout implements View.OnClickListener
{
    private int Image;
    private String Text;
    private int ImageClick;

    public SettingClickView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_settingclick, this);

        if (attrs != null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SettingClickView);
            if (a.hasValue(R.styleable.SettingClickView_text))
            {
                Text = a.getString(R.styleable.SettingClickView_text);
                ((TextView) findViewById(R.id.view_settingclick_textview)).setText(Text);
            }
            if (a.hasValue(R.styleable.SettingClickView_src))
            {
                Image = a.getResourceId(R.styleable.SettingClickView_src, 0);
                if (Image != 0)
                    ((ImageView) findViewById(R.id.view_settingclick_imageview)).setImageResource(Image);
            }
            if (a.hasValue(R.styleable.SettingClickView_clickSrc))
            {
                ImageClick = a.getResourceId(R.styleable.SettingClickView_clickSrc, 0);
                if (ImageClick != 0)
                    ((ImageView) findViewById(R.id.view_settingclick_imageclick)).setImageResource(ImageClick);
            }

            a.recycle();
        }
    }

    @Override
    public void onClick(View v)
    {

    }
}
