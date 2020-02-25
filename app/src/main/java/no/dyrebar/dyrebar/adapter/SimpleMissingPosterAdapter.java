package no.dyrebar.dyrebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.ProfileAnimal;

public class SimpleMissingPosterAdapter extends BaseAdapter
{
    private ArrayList<ProfileAnimal> items;
    private Context context;

    public SimpleMissingPosterAdapter(Context context, ArrayList<ProfileAnimal> item)
    {
        this.context = context;
        this.items = item;
    }

    @Override
    public int getCount()
    {
        return items.size();
    }

    @Override
    public Object getItem(int i)
    {
        return items.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if (view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_simple_missing_poster, viewGroup, false);
        }
        ProfileAnimal profileAnimal = items.get(i);
        ImageView img = (ImageView) view.findViewById(R.id.img_pet_simple);
        Picasso.get().load(profileAnimal.getImage())
                .placeholder(R.drawable.chili)
                .error(R.drawable.ic_broken_image_black_24dp)
                .into(img);
        ((TextView) view.findViewById(R.id.txt_animal_name)).setText(profileAnimal.getName());
        return view;
    }
}
