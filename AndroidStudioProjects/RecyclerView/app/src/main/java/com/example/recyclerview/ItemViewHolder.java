package com.example.recyclerview;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView MainTopic;
    TextView TimeLeft;
    TextView NoOfSites;
    View view;

    public ItemViewHolder(View itemView) {
        super(itemView);
        MainTopic = itemView.findViewById(R.id.MainTopic);
        TimeLeft = itemView.findViewById(R.id.TimeLeft);
        NoOfSites = itemView.findViewById(R.id.NoOfSites);
        view = itemView;
    }
}
