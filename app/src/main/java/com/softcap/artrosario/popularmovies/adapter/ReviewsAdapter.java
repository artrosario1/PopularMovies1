package com.softcap.artrosario.popularmovies.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softcap.artrosario.popularmovies.R;
import com.softcap.artrosario.popularmovies.model.Review;

import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    private final List<Review> reviewsList;
    private final int layout;
    private final Context context;

    public ReviewsAdapter(List<Review> reviewsList, int layout, Context context) {
        this.reviewsList = reviewsList;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.reviewAuthor.setText(reviewsList.get(position).getAuthor());
        holder.reviewContent.setText(reviewsList.get(position).getContent());

    }

    @Override
    public int getItemCount() {

        return reviewsList.size();
    }

    public class ReviewViewHolder extends RecyclerView.ViewHolder {
        final LinearLayout reviewsLayout;
        final TextView reviewAuthor;
        final TextView reviewContent;

        ReviewViewHolder(View view){
            super(view);
            reviewsLayout = view.findViewById(R.id.reviews_layout);
            reviewAuthor = view.findViewById(R.id.tv_author);
            reviewContent = view.findViewById(R.id.tv_review);
        }

    }

}
