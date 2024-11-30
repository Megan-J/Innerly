package edu.sjsu.android.jams.Goals;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.Objects;

import edu.sjsu.android.jams.R;
import edu.sjsu.android.jams.Utils.DatabaseHandler;

public class AddNewGoal extends BottomSheetDialogFragment {

    public static final String TAG = "ActionBottomDialog";

    private EditText newGoalText;
    private Button newGoalSaveButton;
    private DatabaseHandler db;

    boolean isUpdate;

    // return the object of this class to use elsewhere
    public static AddNewGoal newInstance(){
        return new AddNewGoal();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.DialogStyle);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState){
        View view = layoutInflater.inflate(R.layout.new_goal, container, false);
        Objects.requireNonNull(Objects.requireNonNull(getDialog()).getWindow())
                .setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        newGoalText = getView().findViewById(R.id.newGoalText);
        newGoalSaveButton = getView().findViewById(R.id.newGoalBtn);

        db = new DatabaseHandler(getContext());
        db.openDatabase();

        isUpdate = false;   // are we trying to update a task or create a new one
        final Bundle bundle = getArguments();

        if(bundle != null){
            isUpdate = true;
            String task = bundle.getString("task");
            newGoalText.setText(task);
            if(null!=task  && !task.isEmpty()){
                newGoalSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.green_theme_color));
            }
        }

        newGoalText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.toString().isEmpty()){
                    newGoalSaveButton.setEnabled(false);
                    newGoalSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.green_theme_color));
                }
                else {
                    newGoalSaveButton.setEnabled(true);
                    newGoalSaveButton.setTextColor(ContextCompat.getColor(getContext(), R.color.light_brown_theme_color));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });


        newGoalSaveButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                String text = newGoalText.getText().toString();
                if(isUpdate){
                    db.updateGoal(bundle.getInt("id"), text);
                }
                else {
                    Goal goal = new Goal();
                    goal.setGoalTitle(text);
                    goal.setGoalStatus(0);
                }
                dismiss();
            }
        });
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        Fragment fragment = getParentFragment();
        if (fragment instanceof DialogCloseListener) {
            ((DialogCloseListener) fragment).handleDialogClose(dialogInterface);
        }
    }


}
