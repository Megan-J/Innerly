package edu.sjsu.android.jams.entries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import edu.sjsu.android.jams.R;

/**
 * A fragment representing a list of Items.
 */
public class EntryListItemContent extends Fragment {
    ArrayList<String> data = new ArrayList<>();
    EntryListItemAdapter adapter;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EntryListItemContent() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // code for inflation: initializes layout (UI) for fragment
        View view = inflater.inflate(R.layout.fragment_entry_content_item, container, false);
        // generate data from "Entry 0" to "Entry 5"
//        for (int i = 1; i <= 5; i++) {
//            data.add("Entry " + i);
//        }
//        // construct adapter with data argument
//        adapter = new EntryListItemAdapter(data);
//        // cast the view to RecyclerView
//        recyclerView = (RecyclerView) view;
//
//        // set the adapter for RecyclerView
//        recyclerView.setAdapter(adapter);
        return view;
    }
}