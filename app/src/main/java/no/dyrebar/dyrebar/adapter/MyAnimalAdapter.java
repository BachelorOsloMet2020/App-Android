package no.dyrebar.dyrebar.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.activity.AnimalManageActivity;
import no.dyrebar.dyrebar.activity.AnimalProfileActivity;
import no.dyrebar.dyrebar.classes.ProfileAnimal;

public class MyAnimalAdapter extends RecyclerView.Adapter<MyAnimalAdapter.ViewHolder>
{
    private ArrayList<ProfileAnimal> items;
    private Context context;

    public MyAnimalAdapter(ArrayList<ProfileAnimal> items, Context context) {
        this.items = items;
        this.context = context;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_animal_card, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        ProfileAnimal animal = items.get(position);
        Picasso.get().load(animal.getImage())
                .placeholder(R.drawable.chili)
                .error(R.drawable.ic_broken_image_black_24dp).into(holder.image);
        holder.name.setText(animal.getName());
        holder.animalTypeExtra.setText(animal.getExtras());

    }


    @Override
    public int getItemCount()
    {
        return items.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;
        private TextView name;
        private TextView animalTypeExtra;
        private Button edit;
        public ViewHolder(@NonNull View v)
        {
            super(v);
            image = v.findViewById(R.id.adapter_animal_card_image);
            name = v.findViewById(R.id.adapter_animal_card_name);
            animalTypeExtra = v.findViewById(R.id.adapter_animal_card_animalTypeExtra);
            edit = v.findViewById(R.id.adapter_animal_card_edit);
            v.setOnClickListener(view -> {
                Intent intent = new Intent(context, AnimalProfileActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("animal", items.get(getAdapterPosition()));
                intent.putExtras(b);
                context.startActivity(intent);
            });
            edit.setOnClickListener(view -> {
                Intent i = new Intent(context, AnimalManageActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("animal_profile", items.get(getAdapterPosition()));
                i.putExtras(b);
                context.startActivity(i);
            });
        }
    }

}
