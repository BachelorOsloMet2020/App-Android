package no.dyrebar.dyrebar.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.Missing;

public class MissingDialog
{
    private Dialog dialog;
    private View v;
    private ListView listView;
    private Context context;

    public MissingDialog(Context context, String title)
    {
        this.context = context;
        dialog = make();
        ((TextView)v.findViewById(R.id.dialog_missing_title)).setText(title);
        listView.findViewById(R.id.dialog_listview_animals);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Missing m = parent.getAdapter().getItem(position);
            }
        });

        //v.findViewById(R.id.dialog_missing_btn_confirm).setOnClickListener();
        //v.findViewById(R.id.dialog_missing_btn_cancel).setOnClickListener();

    }

    private Dialog make()
    {
        v = View.inflate(context, R.layout.dialog_missing, null);
        Dialog dialog = new Dialog(context, R.style.DialogIndicator);
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
