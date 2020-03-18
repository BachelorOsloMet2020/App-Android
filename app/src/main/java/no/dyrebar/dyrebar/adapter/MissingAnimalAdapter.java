package no.dyrebar.dyrebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.Missing;
import no.dyrebar.dyrebar.handler.TypeHandler;

public class MissingAnimalAdapter extends RecyclerView.Adapter<MissingAnimalAdapter.ViewHolder>
{
    private ArrayList<Missing> items;
    private Context context;
    private ItemClickListener mListener;

    public MissingAnimalAdapter(Context context, ArrayList<Missing> items)
    {
        this.context = context;
        this.items = items;
        if (context instanceof MissingAnimalAdapter.ItemClickListener)
            mListener = (MissingAnimalAdapter.ItemClickListener)context;
        else
            throw new RuntimeException("ItemClickListener not implemented in context");
    }

    public MissingAnimalAdapter(Context context, ItemClickListener mListener, ArrayList<Missing> items)
    {
        this.context = context;
        this.items = items;
        this.mListener = mListener;
    }

    public void setItems(ArrayList<Missing> items)
    {
        if (this.items != items)
        {
            this.items = items;
            notifyDataSetChanged();
        }
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_missing, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Missing m = items.get(position);
        Picasso.get().load(m.getImage())
                .placeholder(R.drawable.chili)
                .error(R.drawable.ic_dyrebarlogo_black).into(holder.image);
        String cat = m.getColor() + " " + new TypeHandler().getAnimalType(context, m.getAnimalType());
        holder.color_animalType.setText(cat);
        if (m.getExtras() != null)
            holder.animalTypeExtras.setText(m.getExtras());
        else
            holder.animalTypeExtras.setVisibility(View.GONE);
        holder.sex.setText(new TypeHandler().getSex(context, m.getSex()));
        holder.date.setText(getTime(m.getTime()));

        if (m.getArea() != null)
            holder.area.setText(m.getArea());
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
        SimpleDateFormat timeSdf = new SimpleDateFormat("dd.MMM.yy HH:mm");
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
                Missing m = items.get(getAdapterPosition());
                mListener.onItemClicked(m);
            });
        }
    }

    public interface ItemClickListener
    {
        void onItemClicked(Missing missing);
    }

}
