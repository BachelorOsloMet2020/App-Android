package no.dyrebar.dyrebar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

import no.dyrebar.dyrebar.R;
import no.dyrebar.dyrebar.classes.Found;
import no.dyrebar.dyrebar.classes.Missing;
import no.dyrebar.dyrebar.handler.TypeHandler;

public class MyPostersAdapter extends RecyclerView.Adapter<MyPostersAdapter.ViewHolder>
{
    private Context context;
    private ArrayList<Object> items;
    private OnMyPosterAction mListener;

    public MyPostersAdapter(Context context, ArrayList<Object> items)
    {
        this.context = context;
        this.items = items;
        if (context instanceof OnMyPosterAction)
            mListener = (OnMyPosterAction)context;
        else
            throw new RuntimeException("OnMyPosterAction not implemented in context");
    }

    public MyPostersAdapter(Context context, OnMyPosterAction mListener, ArrayList<Object> items)
    {
        this.context = context;
        this.items = items;
        this.mListener = mListener;
    }

    public void removeItemByPosition(int index)
    {
        if (items.isEmpty() || items.size() -1 < index)
            return;
        items.remove(index);
        notifyItemRemoved(index);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(context).inflate(R.layout.adapter_my_posters, parent, false);
        return new MyPostersAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        Object o = items.get(position);
        if (o instanceof Missing)
        {
            Missing m = (Missing) o;
            setImage(holder.image, m.getImage());
            setTitle(holder.title, m.getClass(), m.getAnimalType(), m.getColor());
            holder.sex.setText(new TypeHandler().getSex(context, m.getSex()));
            holder.area.setText(m.getArea());
            setTime(holder.time, m.getTime());
        }
        else if (o instanceof Found)
        {
            Found f = (Found) o;
            setImage(holder.image, f.getImage());
            setTitle(holder.title, f.getClass(), f.getAnimalType(), f.getColor());
            holder.sex.setText((new TypeHandler().getSex(context, f.getSex())));
            holder.area.setText(f.getArea());
            setTime(holder.time, f.getTime());
        }
    }

    private void setImage(ImageView iv, String url)
    {
        Picasso.get().load(url)
                .placeholder(R.drawable.chili)
                .error(R.drawable.ic_broken_image_black_24dp).into(iv);
    }

    private void setTitle(TextView tv, Class c, int animalType, String color)
    {
        String title = "";
        if (c == Missing.class)
            title += context.getString(R.string.missing);
        else if (c == Found.class)
            title += context.getString(R.string.found);
        title += " " + color;
        title += " " + new TypeHandler().getAnimalType(context, animalType);
        tv.setText(title);
    }

    private void setTime(TextView tv, long time)
    {
        SimpleDateFormat timeSdf = new SimpleDateFormat("dd.MMM.yy HH:mm");
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        calendar.setTimeInMillis(time);

        String date = timeSdf.format(calendar.getTime());
        tv.setText(date);
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
        private TextView sex;
        private TextView area;
        private TextView time;
        private ImageButton more;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.adapter_my_posters_image);
            title = itemView.findViewById(R.id.adapter_my_posters_type_color_animalType);
            animalTypeExtra = itemView.findViewById(R.id.adapter_my_posters_animalTypeExtras);
            sex = itemView.findViewById(R.id.adapter_my_posters_sex);
            area = itemView.findViewById(R.id.adapter_my_posters_area);
            time = itemView.findViewById(R.id.adapter_my_posters_date);
            more = itemView.findViewById(R.id.adapter_my_posters_more);

            more.setOnClickListener(v -> {
                PopupMenu pop = new PopupMenu(context, more);
                pop.getMenuInflater().inflate(R.menu.poster_my_actions_menu, pop.getMenu());
                pop.setOnMenuItemClickListener(menuItemClickListener);
                pop.show();
            });


        }

        private PopupMenu.OnMenuItemClickListener menuItemClickListener = item ->
        {
            Object o = items.get(getAdapterPosition());
            if (item.getItemId() == R.id.menu_item_solved)
            {
                mListener.onActionSolvedPoster(getAdapterPosition(), o);
                return true;
            }
            else if (item.getItemId() == R.id.menu_item_delete)
            {
                mListener.onActionDeletePoster(getAdapterPosition(), o);
                return true;
            }
            return false;
        };

    }

    public interface OnMyPosterAction
    {
        void onActionDeletePoster(int index, Object poster);
        void onActionSolvedPoster(int index, Object poster);
    }

}
