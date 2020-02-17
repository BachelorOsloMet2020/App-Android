package no.dyrebar.dyrebar.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import no.dyrebar.dyrebar.R;

public class IndicatorDialog
{
    private Activity activity;
    private Dialog dialog;
    private View v;

    public IndicatorDialog(Activity activity, String title, String message)
    {
        this.activity = activity;
        dialog = make();
        ((TextView)v.findViewById(R.id.dialog_indicator_title)).setText(title);
        ((TextView)v.findViewById(R.id.dialog_indicator_message)).setText(message);
    }

    private Dialog make()
    {
        v = View.inflate(activity, R.layout.dialog_indicator, null);
        Dialog dialog = new Dialog(activity, R.style.DialogIndicator);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(v);
        return dialog;
    }

    public void Show()
    {
        if (dialog != null)
            dialog.show();
    }

    public void Hide()
    {
        if (dialog != null)
            dialog.hide();
    }

    public boolean isVisible()
    {
        if (dialog == null)
            return false;
        return dialog.isShowing();
    }

    public void Destroy()
    {
        dialog.dismiss();
    }


}
