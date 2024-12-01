package edu.sjsu.android.jams.entries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.android.jams.R;

/**
 * A fragment representing a list of Journal Entries.
 */
public class EntryListFragment extends Fragment {
    List<EntryListItemDateContent> data = new ArrayList<>();
    EntryListItemDateContentAdapter adapter;
    RecyclerView recyclerView;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EntryListFragment() {
    }

    /**
     * Starting point for fragment
     * @param savedInstanceState If the fragment is being re-created from
     * a previous saved state, this is the state.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        data = generateSampleData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // code for inflation: initializes layout (UI) for fragment
        View view = inflater.inflate(R.layout.fragment_entry_item_list, container, false);
        // initialize receiver view
        recyclerView = view.findViewById(R.id.entry_list_date_and_content_recycler_view);
        // construct adapter with data argument
        adapter = new EntryListItemDateContentAdapter(data);
        // set the adapter for RecyclerView
        recyclerView.setAdapter(adapter);
        return view;
    }

    /**
     * Sample data to replace with database values
     * @return ArrayList of journal entries containing date and content
     */
    private List<EntryListItemDateContent> generateSampleData() {
        List<EntryListItemDateContent> sampleData = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            List<EntryListItemContent> entries = new ArrayList<>();
            for (int j = 1; j <= 3; j++) {
                entries.add(new EntryListItemContent("Title " + j, "content preview for the entry"));
            }
            sampleData.add(new EntryListItemDateContent("Date " + i, entries));
        }
        return sampleData;
    }
}