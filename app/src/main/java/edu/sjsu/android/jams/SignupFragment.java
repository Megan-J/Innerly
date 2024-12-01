package edu.sjsu.android.jams;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import edu.sjsu.android.jams.Utils.DatabaseHandler;

public class SignupFragment extends Fragment {

    private EditText emailText, passwordText;
    private Button signupButton;
    private DatabaseHandler databaseHandler;

    public SignupFragment() {
        // Required empty public constructor
    }


    public static SignupFragment newInstance(String param1, String param2) {
        SignupFragment fragment = new SignupFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        signupButton = view.findViewById(R.id.signupButton_inSignup);
        TextView already_have_account = view.findViewById(R.id.already_have_account_text);
        already_have_account.setOnClickListener(this::navigateToLogin);
//        signupButton.setOnClickListener(this::onClick);

        emailText = view.findViewById(R.id.inputEmail_signup);
        passwordText = view.findViewById(R.id.inputPassword_signup);

        databaseHandler = new DatabaseHandler(getContext());
        databaseHandler.openDatabase();

        signupButton.setOnClickListener(this::signupUser);

        return view;
    }

    private void signupUser(View view){
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty()){
            boolean isInserted = databaseHandler.insertUser(email, password);
            if(isInserted){
                Toast.makeText(getContext(), "Signup successful!", Toast.LENGTH_SHORT).show();
                int userID = databaseHandler.getUserId(email, password);
                SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("user_id", userID); // Use the `user_id` obtained
                editor.apply();

                onClick(view);
            }
            else{
                Toast.makeText(getContext(), "Signup failed. Try again!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getContext(), "Please fill all fields.", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClick(View view) {
        Log.d("test", "clicked signup button in signup fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_signupFragment_to_homepageFragment);
    }

    private void navigateToLogin(View view) {
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_signupFragment_to_loginFragment);
    }

}