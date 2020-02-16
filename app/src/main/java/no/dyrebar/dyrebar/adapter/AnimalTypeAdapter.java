package no.dyrebar.dyrebar.adapter;

import android.content.Context;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import no.dyrebar.dyrebar.R;

public class AnimalTypeAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<Pair<Integer, String> > items;

    public AnimalTypeAdapter(Context context, ArrayList<Pair<Integer, String>> items)
    {
        this.context = context;
        this.items = items;
    }



    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Object getItem(int position)
    {
        return items.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        if (view == null)
            view = LayoutInflater.from(context).inflate(R.layout.adapter_animal_type, parent, false);

        Pair<Integer, String> item = items.get(position);
        ((TextView)view.findViewById(R.id.adapter_animal_type_title)).setText(item.second);

        return view;
    }
}
