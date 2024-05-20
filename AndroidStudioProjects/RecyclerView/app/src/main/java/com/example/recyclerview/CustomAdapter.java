package com.example.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.Collections;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<ItemData> list = Collections.emptyList();
    private Context context;
    private ClickListener listener;

    public CustomAdapter(List<ItemData> list, Context context, ClickListener listener) {
        this.list = list;
        this.context = context;
        this.listener = listener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View photoView = inflater.inflate(R.layout.item_view, parent, false);

        return new ItemViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(final ItemViewHolder viewHolder, final int position) {
        viewHolder.MainTopic.setText(list.get(position).topic);
        viewHolder.TimeLeft.setText(list.get(position).time);
        viewHolder.NoOfSites.setText(String.valueOf(list.get(position).sites));

        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.click(viewHolder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
