package com.colibri.appconnect;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private final List<Event> listEvents;
    private Context context;
    private EventAdapter.OnItemClicked onClick;

    public EventAdapter(List<Event> listEvents, Context context) {
        this.context = context;
        this.listEvents = listEvents;
    }

    public interface OnItemClicked {
        void onItemClick(Event events);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titre;
        public ImageView image;
        public TextView date;
        public TextView lieux;
        public androidx.cardview.widget.CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.titre = itemView.findViewById(R.id.textTitre);
            this.image = itemView.findViewById(R.id.imageView);
            this.date = itemView.findViewById(R.id.textDate);
            this.lieux = itemView.findViewById(R.id.textLieux);
            this.cardView = itemView.findViewById(R.id.cardViewLayout);

            itemView.setOnClickListener(view -> { });
        }
    }

    @NonNull
    @Override
    public EventAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.activity_item, parent, false);
        return new EventAdapter.ViewHolder(listItem);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(EventAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Event myListData = listEvents.get(position);
        String uri = "@drawable/" + myListData.getImage();
        int imageResource = holder.itemView.getResources().getIdentifier(uri, null, holder.itemView.getContext().getPackageName());
        holder.titre.setText(myListData.getTitre());
        holder.image.setImageResource(imageResource);
        holder.date.setText(myListData.getDate());
        holder.lieux.setText(myListData.getLieux());

        holder.cardView.setOnClickListener(v -> {
            if (onClick != null) {
                onClick.onItemClick(myListData);
            }

            //Toast.makeText(v.getContext(),"click on item: " + myListData.getTitre(),Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public int getItemCount() {
        return listEvents.size();
    }

    public void setOnClick(EventAdapter.OnItemClicked onClick){
        this.onClick=onClick;
    }
}
