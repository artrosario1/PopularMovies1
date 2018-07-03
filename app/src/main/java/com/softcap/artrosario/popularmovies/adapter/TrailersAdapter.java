package com.softcap.artrosario.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softcap.artrosario.popularmovies.R;
import com.softcap.artrosario.popularmovies.model.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {

    private final List<Trailer> trailerList;
    private final int layout;
    private final Context context;
   //private static final String YOUTUBE_URL_BASE_PATH = "https://www.youtube.com/watch?v=";
    private static final String YOUTUBE_IMG_BASE_PATH = "https://img.youtube.com/vi/";

    private final TrailerAdapterOnClickHandler mTrailerClickHandler;

    public interface TrailerAdapterOnClickHandler{
        void onClick(String id);
    }

    public TrailersAdapter(List<Trailer> trailerList, int layout, Context context, TrailerAdapterOnClickHandler mTrailerClickHandler) {
        this.trailerList = trailerList;
        this.layout = layout;
        this.context = context;
        this.mTrailerClickHandler = mTrailerClickHandler;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        holder.trailerTitle.setText(trailerList.get(position).getName());
        String idForTrailer = trailerList.get(position).getKey();
        String image_url = YOUTUBE_IMG_BASE_PATH + idForTrailer + "/mqdefault.jpg";
        String size = Integer.toString(trailerList.size());
        String number = Integer.toString(position + 1) ;
        holder.trailerNumber.setText(number + "/"  + size );
        Picasso.with(context)
                .load(image_url)
                .placeholder(R.drawable.placeholder_image)
                .error(R.drawable.placeholder_image)
                .into(holder.trailerThumbnail);

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        final LinearLayout trailersLayout;
        final TextView trailerTitle;
        final ImageView trailerThumbnail;
        final TextView trailerNumber;

        TrailerViewHolder(View view) {
            super(view);
            trailerTitle = view.findViewById(R.id.tv_trailer_title);
            trailersLayout = view.findViewById(R.id.trailers_layout);
            trailerThumbnail = view.findViewById(R.id.iv_trailer_Thumbnail);
            trailerNumber = view.findViewById(R.id.tv_trailer_number);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            String idForTrailer = trailerList.get(adapterPosition).getKey();

            mTrailerClickHandler.onClick(idForTrailer);
        }
    }
}