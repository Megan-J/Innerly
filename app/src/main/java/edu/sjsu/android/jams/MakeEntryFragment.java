package edu.sjsu.android.jams;

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

import edu.sjsu.android.jams.Utils.DatabaseHandler;
import edu.sjsu.android.jams.entries.Entry;

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

        // Check if a prompt was passed back from the PromptSpecificFragment
        if (getArguments() != null) {
            String selectedPrompt = getArguments().getString("selected_question");
            if (selectedPrompt != null) {
                updatePrompt(selectedPrompt);
            }
        }

        return view;
    }

    private void onClickChangePrompt(View view) {
        // Navigate to PromptAllFragment
        Log.d("MakeEntryFragment", "Clicked change prompt");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_makeEntryFragment_to_promptAllFragment);
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
                        String newText = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        dateText.setText(newText);
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
        Entry entry = new Entry(userID, date, prompt, title, content);
        if (date.isEmpty()) {
            Toast.makeText(view.getContext(), "Select a date to save entry.",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            db.insertEntry(entry);
            Toast.makeText(view.getContext(), "Journal entry saved.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    
    private void onClickDeleteBtn(View view) {

    }

//    private Date stringToDate(String dateStr) {
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        Date date;
//        try {
//            date = format.parse(dateStr);
//        } catch (ParseException e) {
//            throw new RuntimeException(e);
//        }
//        return date;
//    }
}