package no.dyrebar.dyrebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.Found;
import no.dyrebar.dyrebar.classes.Missing;

public class PetPosterAdapter extends RecyclerView.Adapter<PetPosterAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Object> items;
    private ItemClickListener mListener;

    public PetPosterAdapter(Context context, ArrayList<Object> items)
    {
        this.context = context;
        this.items = items;

        if(context instanceof ItemClickListener)
            mListener = (ItemClickListener)context;
        else
            throw new RuntimeException("ItemClickListener not implemented in context");
    }

    public PetPosterAdapter(Context context, ItemClickListener mListener, ArrayList<Object> items)
    {
        this.context = context;
        this.items = items;
        this.mListener = mListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_home_poster_pet, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Object o = items.get(position);
        /*Picasso.get().load(animal.getImage())
                .placeholder(R.drawable.chili)
                .error(R.drawable.ic_broken_image_black_24dp).into(holder.image);*/

        /**
         * Her må FOUND/MISSING settes basert på annonse!
         */
        //holder.title.setText();
        //holder.animalTypeExtra.setText(animal.getExtras());
        /**
         * Lokasjon må hentes tilknyttet til dyreprofil/annonsen
         */
        //holder.location.setText(animal.);
        /**
         * Distansje baser på avstand fra brukers lokasjon må implementeres
         */
        //holder.distance.setText();

    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;
        private TextView title;
        private TextView animalTypeExtra;
        private TextView distance;
        private TextView location;
        private Button contact;
        public ViewHolder(@NonNull View v)
        {
            super(v);
            image = v.findViewById(R.id.adapter_home_image);
            title = v.findViewById(R.id.adapter_home_txt_title);
            animalTypeExtra = v.findViewById(R.id.adapter_home_txt_color_animalType);
            distance = v.findViewById(R.id.adapter_home_txt_distance);
            location = v.findViewById(R.id.adapter_home_txt_location);
            contact = v.findViewById(R.id.adapter_home_btn_contact);
            v.setOnClickListener( view -> {
                Object o = items.get(getAdapterPosition());
                if(o instanceof Missing)
                    mListener.onMissingItemClicked((Missing)o);
                else if(o instanceof Found)
                    mListener.onFoundItemClicked((Found)o);

            });

            contact.setOnClickListener(view -> {
                /**
                 * Skal vi ha dette tilgjenelig i Hjem? Eller kun i selve annonsen?
                 */
            });

        }
    }

    public interface ItemClickListener
    {
        void onFoundItemClicked(Found found);
        void onMissingItemClicked(Missing missing);
    }
}
