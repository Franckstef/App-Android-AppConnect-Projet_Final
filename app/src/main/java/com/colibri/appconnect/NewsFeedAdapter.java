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

public class NewsFeedAdapter extends RecyclerView.Adapter<NewsFeedAdapter.ViewHolder> {

    private final List<News> listNews;
    private Context context;
    private OnItemClicked onClick;

    public NewsFeedAdapter(List<News> listNews, Context context) {
        this.context = context;
        this.listNews = listNews;
    }

    public interface OnItemClicked {
        void onItemClick(News news);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView titre;
        public ImageView image;
        public TextView date;
        public androidx.cardview.widget.CardView layout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.titre = itemView.findViewById(R.id.textTitre);
            this.image = itemView.findViewById(R.id.imageView);
            this.date = itemView.findViewById(R.id.textDescription);
            this.layout = itemView.findViewById(R.id.layout);

            itemView.setOnClickListener(view -> { });
        }
    }

    @NonNull
    @Override
    public NewsFeedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View listItem = inflater.inflate(R.layout.newsfeed_item, parent, false);
        return new NewsFeedAdapter.ViewHolder(listItem);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(NewsFeedAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        News myListData = listNews.get(position);
        String uri = "@drawable/" + myListData.getImage();
        int imageResource = holder.itemView.getResources().getIdentifier(uri, null, holder.itemView.getContext().getPackageName());
        holder.titre.setText(myListData.getTitre());
        holder.image.setImageResource(imageResource);
        holder.date.setText(myListData.getDate());

        holder.layout.setOnClickListener(v -> {
            onClick.onItemClick(myListData);
            //Toast.makeText(v.getContext(),"click on item: " + myListData.getTitre(),Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public int getItemCount() {
        return listNews.size();
    }

    public void setOnClick(OnItemClicked onClick){
        this.onClick=onClick;
    }

}