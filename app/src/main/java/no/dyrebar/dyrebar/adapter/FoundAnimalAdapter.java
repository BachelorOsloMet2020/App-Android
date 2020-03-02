package no.dyrebar.dyrebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.Found;
import no.dyrebar.dyrebar.handler.TypeHandler;

public class FoundAnimalAdapter extends RecyclerView.Adapter<FoundAnimalAdapter.ViewHolder>
{
    private ArrayList<Found> items;
    private Context context;
    private ItemClickListener mListener;

    public FoundAnimalAdapter(Context context, ArrayList<Found> items)
    {
        this.context = context;
        this.items = items;
        if (context instanceof FoundAnimalAdapter.ItemClickListener)
            mListener = (FoundAnimalAdapter.ItemClickListener)context;
    }

    public FoundAnimalAdapter(Context context, ItemClickListener mListener, ArrayList<Found> items)
    {
        this.context = context;
        this.mListener = mListener;
        this.items = items;
    }

    @NonNull
    @Override
    public FoundAnimalAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_found, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FoundAnimalAdapter.ViewHolder holder, int position)
    {
        Found found = items.get(position);
        Picasso.get().load(found.getImage())
                .placeholder(R.drawable.chili)
                .error(R.drawable.ic_dyrebarlogo_black).into(holder.image);
        String cat = found.getColor() + " " + new TypeHandler().getAnimalType(context, found.getAnimalType());
        holder.color_animalType.setText(cat);
        if (found.getExtras() != null)
            holder.animalTypeExtras.setText(found.getExtras());
        else
            holder.animalTypeExtras.setVisibility(View.GONE);
        holder.sex.setText(new TypeHandler().getSex(context, found.getSex()));
        holder.date.setText(getTime(found.getTime()));

        if (found.getArea() != null)
            holder.area.setText(found.getArea());
        else
            holder.area.setText(context.getString(R.string.unknown_location));
    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }

    private String getTime(long time)
    {
        SimpleDateFormat timeSdf = new SimpleDateFormat("dd.MM.yy HH:mm");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(time);
        String date = timeSdf.format(calendar.getTime());
        return date;
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private ImageView image;
        private TextView color_animalType;
        private TextView animalTypeExtras;
        private TextView sex;
        private TextView area;
        private TextView date;

        public ViewHolder(@NonNull View view)
        {
            super(view);
            image = view.findViewById(R.id.adapter_missing_image);
            color_animalType = view.findViewById(R.id.adapter_missing_color_animalType);
            animalTypeExtras = view.findViewById(R.id.adapter_missing_animalTypeExtras);
            sex = view.findViewById(R.id.adapter_missing_sex);
            area = view.findViewById(R.id.adapter_missing_area);
            date = view.findViewById(R.id.adapter_missing_date);
            view.setOnClickListener(v -> {
                Found found = items.get(getAdapterPosition());
                mListener.onItemClicked(found);
            });
        }
    }

    public interface ItemClickListener
    {
        void onItemClicked(Found found);
    }
}
