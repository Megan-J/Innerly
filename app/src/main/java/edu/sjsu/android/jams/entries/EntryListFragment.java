package edu.sjsu.android.jams.entries;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.sjsu.android.jams.R;
import edu.sjsu.android.jams.Utils.DatabaseHandler;

/**
 * A fragment representing a list of Journal Entries.
 */
public class EntryListFragment extends Fragment {
    private List<EntryListItemDateContent> data = new ArrayList<>();
    private EntryListItemDateContentAdapter adapter;
    private RecyclerView recyclerView;

    private DatabaseHandler db;

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
        db = new DatabaseHandler(getContext());
        db.openDatabase();
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

        ImageView backArrow = view.findViewById(R.id.back_arrow);
        Button freeWriteBtn = view.findViewById(R.id.free_write_btn);
        Button promptsBtn = view.findViewById(R.id.prompts_btn);
        backArrow.setOnClickListener(this::onClickBackArrow);
        freeWriteBtn.setOnClickListener(this::onClickFreeWrite);
        promptsBtn.setOnClickListener(this::onClickPrompts);
        return view;
    }

    /**
     * Sample data to replace with database values
     * @return ArrayList of journal entries containing date and content
     */
    private List<EntryListItemDateContent> generateSampleData() {
        List<EntryListItemDateContent> sampleData = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            List<Entry> entries = new ArrayList<>();
            for (int j = 1; j <= 3; j++) {
                entries.add(new Entry("Title " + j, "content preview for the entry"));
            }
            sampleData.add(new EntryListItemDateContent("Date " + i, entries));
        }
        return sampleData;
    }

    private void onClickBackArrow(View view) {
        Log.d("test", "clicked back arrow");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_entryListFragment_to_homepageFragment);
    }

    private void onClickFreeWrite(View view) {
        Log.d("test", "clicked free write button");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_entryListFragment_to_makeEntryFragment);
    }

    private void onClickPrompts(View view) {
        Log.d("test", "clicked prompts button");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_entryListFragment_to_promptAllFragment);
    }
}