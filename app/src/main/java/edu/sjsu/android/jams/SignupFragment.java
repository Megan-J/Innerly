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

public class SignupFragment extends Fragment {


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
        Button signup = view.findViewById(R.id.signupButton_inSignup);
        signup.setOnClickListener(this::onClick);

        return view;
    }

    private void onClick(View view) {
        Log.d("test", "clicked signup button in signup fragment");
        NavController controller = Navigation.findNavController(view);
        controller.navigate(R.id.action_signupFragment_to_homepageFragment);
    }
}