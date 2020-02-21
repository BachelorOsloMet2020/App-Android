package no.dyrebar.dyrebar.CustomControls;

import no.dyrebar.dyrebar.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;



public class SettingSwitchView extends ConstraintLayout
{
    private int Image;
    private String Text;

    public SettingSwitchView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.view_settingswitch, this);

        if (attrs != null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.SettingSwitchView);
            if (a.hasValue(R.styleable.SettingSwitchView_text))
            {
                Text = a.getString(R.styleable.SettingSwitchView_text);
                ((TextView)findViewById(R.id.view_settingswitch_textview)).setText(Text);
            }
            if (a.hasValue(R.styleable.SettingSwitchView_src))
            {
                Image = a.getResourceId(R.styleable.SettingSwitchView_src, 0);
                if (Image != 0)
                    ((ImageView)findViewById(R.id.view_settingswitch_imageview)).setImageResource(Image);
            }

            a.recycle();
        }



    }
}
