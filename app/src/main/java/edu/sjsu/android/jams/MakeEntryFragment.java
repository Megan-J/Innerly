package edu.sjsu.android.jams;

import android.app.DatePickerDialog;
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

import java.util.Calendar;
import java.util.Objects;

public class MakeEntryFragment extends Fragment {

    private Button categoryButton;
    private Button dateBtn;
    private TextView dateText;

    public MakeEntryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        categoryButton.setOnClickListener(this::onClickChangePrompt);
        backArrow.setOnClickListener(this::onClickBackArrow);
        dateBtn.setOnClickListener(this::onClickDateBtn);

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
}