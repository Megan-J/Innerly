package edu.sjsu.android.jams;

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
import android.widget.Toast;

import edu.sjsu.android.jams.Utils.DatabaseHandler;

public class LoginFragment extends Fragment {

    private EditText emailText, passwordText;
    private Button loginButton;
    private DatabaseHandler databaseHandler;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        loginButton = view.findViewById(R.id.loginButton_inLogin);
//        loginButton.setOnClickListener(this::onClick);

        emailText = view.findViewById(R.id.inputEmail_login);
        passwordText = view.findViewById(R.id.inputPassword_login);

        databaseHandler = new DatabaseHandler(getContext());
        databaseHandler.openDatabase();

        loginButton.setOnClickListener(this::loginUser);

        return view;
    }

    private void loginUser(View view) {
        String email = emailText.getText().toString().trim();
        String password = passwordText.getText().toString().trim();

        if(!email.isEmpty() && !password.isEmpty()){
            boolean isValidUser = databaseHandler.validateUser(email, password);
            if(isValidUser){
                Toast.makeText(getContext(), "Login successful!", Toast.LENGTH_SHORT).show();
                onClick(view);
            }
            else{
                Toast.makeText(getContext(), "Invalid email or password. Try again!", Toast.LENGTH_SHORT).show();
            }
        }
        else{
            Toast.makeText(getContext(), "Please fill all fields.", Toast.LENGTH_SHORT).show();
        }

    }

    private void onClick(View view) {
        Log.d("test", "clicked login button in login fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_loginFragment_to_homepageFragment);
    }
}