package edu.sjsu.android.jams.entries;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import edu.sjsu.android.jams.R;
import edu.sjsu.android.jams.Utils.DatabaseHandler;

public class MakeEntryFragment extends Fragment {

    private Button categoryButton;
    private Button dateBtn;
    private TextView dateText;
    private TextView titleText;
    private TextView contentText;
    private Button saveBtn;
    private Button deleteBtn;

    private DatabaseHandler db;
    private int userID;
    private Entry entry;
    private int latestInsertedEntryID = -1;

    public MakeEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        userID = sharedPreferences.getInt("user_id", -1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_make_entry, container, false);
        categoryButton = view.findViewById(R.id.change_prompt);
        ImageView backArrow = view.findViewById(R.id.back_arrow);
        dateBtn = view.findViewById(R.id.date_btn);
        dateText = view.findViewById(R.id.date);
        titleText = view.findViewById(R.id.title);
        contentText = view.findViewById(R.id.entry_content);
        saveBtn = view.findViewById(R.id.save_btn);
        deleteBtn = view.findViewById(R.id.delete_btn);

        categoryButton.setOnClickListener(this::onClickChangePrompt);
        backArrow.setOnClickListener(this::onClickBackArrow);
        dateBtn.setOnClickListener(this::onClickDateBtn);
        saveBtn.setOnClickListener(this::onClickSaveBtn);
        deleteBtn.setOnClickListener(this::onClickDeleteBtn);

        db = new DatabaseHandler(this.getContext());
        db.openDatabase();

        Bundle argument = getArguments();
        if (argument != null) {
            // Check if a prompt was passed back from the PromptSpecificFragment
            String selectedPrompt = argument.getString("selected_question");
            if (selectedPrompt != null) {
                updatePrompt(selectedPrompt);
            }
            // Check if an entry was passed back from EntryListFragment
            String selectedEntry = requireContext().getString(R.string.entry_key);
            // populate make entry page with appropriate data on selection of an entry from entries list page
            entry = argument.getParcelable(selectedEntry);
            if (entry != null) {
                populateEntry(entry);
            }
        }

        return view;
    }

    private void onClickChangePrompt(View view) {
        // Show confirmation dialog
        new AlertDialog.Builder(getContext())
                .setTitle("Change Prompt")
                .setMessage("Do you want to change the prompt? Unsaved changes will be lost.")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // If "Yes" is clicked, navigate to PromptAllFragment
                    Log.d("MakeEntryFragment", "Clicked change prompt");
                    NavController controller = Navigation.findNavController(view);
                    controller.navigate(R.id.action_makeEntryFragment_to_promptAllFragment);
                })
                .setNegativeButton("No", (dialog, which) -> {
                    // If "No" is clicked, simply dismiss the dialog
                    dialog.dismiss();
                })
                .show();
    }

    private void onClickBackArrow(View view) {
        Log.d("MakeEntryFragment", "Clicked back arrow");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_makeEntryFragment_to_entryListFragment);
    }

    // You will update this method to update the prompt
    public void updatePrompt(String selectedPrompt) {
        categoryButton.setText(selectedPrompt);
    }

    public void populateEntry(Entry entry) {
        categoryButton.setText(entry.getPrompt());
        dateText.setText(entry.getDate());
        titleText.setText(entry.getTitle());
        contentText.setText(entry.getContent());
    }

    private void onClickDateBtn(View view) {
        Calendar cal = Calendar.getInstance();
        // Get current year, month, and day
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        // Date picker dialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // format date
                        @SuppressLint("DefaultLocale") String newDateText = String.format("%04d-%02d-%02d", year, monthOfYear + 1, dayOfMonth);
                        dateText.setText(newDateText);
                    }
                },
                year, month, day);
        datePickerDialog.show();
    }

    private void onClickSaveBtn(View view) {
        String date = dateText.getText().toString();
        String prompt = categoryButton.getText().toString();
        String title = titleText.getText().toString();
        String content = contentText.getText().toString();
        if (entry == null && latestInsertedEntryID == -1) {
            // insert entry into database
            entry = new Entry(userID, date, prompt, title, content);
            if (date.isEmpty()) {
                Toast.makeText(view.getContext(), "Select a date to save entry.",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                latestInsertedEntryID = db.insertEntry(entry);
                entry.setEntryID(latestInsertedEntryID);
                Toast.makeText(view.getContext(), "Journal entry saved.",
                        Toast.LENGTH_SHORT).show();
            }
        }
        else {
            // update database
            int entryID = entry.getEntryID();
            entry = new Entry(entryID, userID, date, prompt, title, content);
            db.updateEntry(entry);
            Toast.makeText(view.getContext(), "Journal entry saved.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    private void onClickDeleteBtn(View view) {
        if ((latestInsertedEntryID != -1) || (entry != null)) {
            new AlertDialog.Builder(getContext())
                    .setTitle("Delete Entry")
                    .setMessage("Are you sure you want to delete this journal entry? No take backsies.")
                    .setPositiveButton("Delete", (dialog, which) -> {
                        // If "Delete" is clicked, delete journal entry from database
                        db.deleteEntry(entry.getEntryID());
                        latestInsertedEntryID = -1;
                        onClickBackArrow(view);
                    })
                    .setNegativeButton("Cancel", (dialog, which) -> {
                        // If "No" is clicked, simply dismiss the dialog
                        dialog.dismiss();
                    })
                    .show();
        }
        else {
            // if entry not in database, toast
            Toast.makeText(view.getContext(), "Journal entry not saved.",
                    Toast.LENGTH_SHORT).show();
        }
    }
}