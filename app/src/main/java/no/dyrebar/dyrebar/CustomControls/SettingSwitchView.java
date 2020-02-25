package no.dyrebar.dyrebar.CustomControls;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.handler.SettingsHandler;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;



public class SettingSwitchView extends ConstraintLayout
{
    private int Image;
    private String Text;
    private SettingsHandler sh;

    public SettingSwitchView(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
        sh = new SettingsHandler(context.getApplicationContext());
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
        ((Switch)findViewById(R.id.view_settingswitch_swtich)).setOnCheckedChangeListener(switched);
    }

    private String Setting;
    public void RegisterSetting(String setting)
    {
        this.Setting = setting;
        if (sh != null && setting != null)
        {
            ((Switch)findViewById(R.id.view_settingswitch_swtich)).setChecked(sh.getBooleanSetting(setting, false));
        }
    }

    private CompoundButton.OnCheckedChangeListener switched = new  CompoundButton.OnCheckedChangeListener()
    {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
        {
            if (sh == null || Setting == null)
            {
                new Handler().postDelayed(() -> buttonView.setChecked(false), 250);
                Log.e(getClass().getName(), "Settings value is not registered with SettingSwitchView, Please register by calling RegisterSetting(String setting)");
                return;
            }
            sh.setBooleanSetting(Setting, isChecked);
        }
    };

}
