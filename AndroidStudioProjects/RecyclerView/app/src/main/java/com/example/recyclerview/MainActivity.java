package com.example.recyclerview;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickListener {

    private RecyclerView recyclerView;
    private CustomAdapter adapter;
    private View timelineBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);
        timelineBar = findViewById(R.id.timeline_bar);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<ItemData> dataList = generateDummyData();
        adapter = new CustomAdapter(dataList, this, this);
        recyclerView.setAdapter(adapter);

        recyclerView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                recyclerView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // Adjust the timeline bar's height after the RecyclerView's content is set
                timelineBar.getLayoutParams().height = recyclerView.computeVerticalScrollRange();
                timelineBar.requestLayout();
            }
        });
    }

    private List<ItemData> generateDummyData() {
        List<ItemData> dataList = new ArrayList<>();
        dataList.add(new ItemData("Topic 1", "2 hours left", "5 sites"));
        dataList.add(new ItemData("Topic 2", "1 hour left", "3 sites"));
        dataList.add(new ItemData("Topic 3", "30 minutes left", "7 sites"));
        dataList.add(new ItemData("Topic 4", "15 minutes left", "0 sites"));

        return dataList;
    }

    @Override
    public void click(int index) {
        // Handle item click here
        Toast.makeText(this, "Item clicked at position: " + index, Toast.LENGTH_SHORT).show();
    }
}
