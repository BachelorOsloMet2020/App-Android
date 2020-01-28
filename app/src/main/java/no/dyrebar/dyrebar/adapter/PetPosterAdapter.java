package no.dyrebar.dyrebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.Profile;
import no.dyrebar.dyrebar.classes.ProfileAnimal;

public class PetPosterAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<ProfileAnimal> item;
    private ArrayList<Profile> itemUser;

    public PetPosterAdapter(Context context, ArrayList<ProfileAnimal> item, ArrayList<Profile> itemUser)
    {
        this.context = context;
        this.item = item;
        this.itemUser = itemUser;
    }

    @Override
    public int getCount()
    {
        return item.size();
    }

    @Override
    public Object getItem(int i)
    {
        return item.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        if(view == null)
        {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_home_poster_pet, viewGroup, false);
        }
        ProfileAnimal profileAnimal = item.get(i);
        Profile profile = itemUser.get(i);

        //Hvordan få image url til å postes i/til og vises riktig ImageView???
        //((TextView) view.findViewById(R.id.img_pet)).setText(profileAnimal.getImage());

        ((TextView) view.findViewById(R.id.txt_name)).setText(profileAnimal.getName());
        ((TextView) view.findViewById(R.id.txt_address)).setText(profile.getAddress());

        return view;
    }
}
