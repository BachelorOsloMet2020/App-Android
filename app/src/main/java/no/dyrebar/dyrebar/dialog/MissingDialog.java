package no.dyrebar.dyrebar.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Pair;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import no.dyrebar.dyrebar.App;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.adapter.SimpleMissingPosterAdapter;
import no.dyrebar.dyrebar.classes.ProfileAnimal;
import no.dyrebar.dyrebar.json.jProfileAnimal;
import no.dyrebar.dyrebar.web.Api;
import no.dyrebar.dyrebar.web.Source;

public class MissingDialog extends AppCompatActivity
{
    private Dialog dialog;
    private View v;
    private Context context;

    public MissingDialog(Context context, String title)
    {
        this.context = context;
        dialog = make();
        ((TextView)v.findViewById(R.id.dialog_missing_title)).setText(title);
        ListView lv = (ListView) v.findViewById(R.id.dialog_listview_animals);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Missing m = parent.getAdapter().getItem(position);
            }
        });

        //v.findViewById(R.id.dialog_missing_btn_confirm).setOnClickListener();
        //v.findViewById(R.id.dialog_missing_btn_cancel).setOnClickListener();

        AsyncTask.execute(()-> {
            String ani = new Api().Get(Source.Api, new ArrayList<Pair<String, ?>>()
            {{
                add(new Pair<>("request", "animals"));
                add(new Pair<>("uid", App.profile.getId()));
            }});
            ArrayList<ProfileAnimal> animals = new jProfileAnimal().decodeArray(ani);
            runOnUiThread((()-> loadMyAnimals(animals)));
        });
    }

    private void loadMyAnimals(ArrayList<ProfileAnimal> animals)
    {
        ListView lv = (ListView) v.findViewById(R.id.dialog_listview_animals);
        //SimpleMissingPosterAdapter simpleMissingPosterAdapter = new SimpleMissingPosterAdapter(this, animals);
        lv.setAdapter(new SimpleMissingPosterAdapter(context, animals));
        //lv.setAdapter(simpleMissingPosterAdapter);

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
