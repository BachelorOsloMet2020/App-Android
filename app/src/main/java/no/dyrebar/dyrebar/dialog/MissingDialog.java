package no.dyrebar.dyrebar.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.View;
import android.widget.TextView;

import no.dyrebar.dyrebar.R;

public class MissingDialog
{
    private Activity activity;
    private Dialog dialog;
    private View v;

    public MissingDialog(Activity activity, String title)
    {
        this.activity = activity;
        dialog = make();
        ((TextView)v.findViewById(R.id.dialog_missing_title)).setText(title);

    }

    private Dialog make()
    {
        v = View.inflate(activity, R.layout.dialog_missing, null);

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
