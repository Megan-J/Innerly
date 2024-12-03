package edu.sjsu.android.jams.entries;

import android.content.Context;
import android.content.SharedPreferences;
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
    private int userID;

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
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt("user_id", -1);
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
        data = generateData(userID);
        // construct adapter with data argument
        adapter = new EntryListItemDateContentAdapter(this, data);
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
     * Data to display on My Entries page
     * @return ArrayList of journal entries containing date and entries of that date
     */
    private List<EntryListItemDateContent> generateData(int userID) {
        List<EntryListItemDateContent> data = new ArrayList<>();
        List<String> datesList = db.getEntryDatesList(userID);
        for (String date: datesList) {
            List<Integer> entryIDList = db.getEntryIDsForDate(userID, date);
            List<Entry> entries = new ArrayList<>();
            for (int entryID: entryIDList) {
                Log.d("entry", db.getEntryByID(entryID).getTitle());
                entries.add(db.getEntryByID(entryID));
            }
            data.add(new EntryListItemDateContent(date, entries));
        }
        return data;
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