package com.rampit.rask3.dailydiary;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rampit.rask3.dailydiary.Adapter.MyRecyclerViewAdapter;

import java.util.ArrayList;


//Updated on 25/01/2022 by RAMPIT
//sample recyclerview ( NOT USED )

public class Popnames extends AppCompatActivity {

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // data to populate the RecyclerView with
        ArrayList<String> animalNames = new ArrayList<>();
        animalNames.add("Horse");
        animalNames.add("Cow");
        animalNames.add("Camel");
        animalNames.add("Sheep");
        animalNames.add("Goat");

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        adapter = new MyRecyclerViewAdapter(this, animalNames, ID, 2);

        recyclerView.setAdapter(adapter);
    }


}
