package com.allofmex.myapplication;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.Vh> {
    @NonNull
    @Override
    public Adapter.Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView view = new TextView(parent.getContext());
        return new Vh(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapter.Vh holder, int position) {
        ((TextView) holder.itemView).setText("Pos and id "+position);
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    @Override
    public long getItemId(int position) {
        // itemId = position
        return position;
    }

    static class Vh extends RecyclerView.ViewHolder {
        public Vh(@NonNull View itemView) {
            super(itemView);
        }
    }
}
