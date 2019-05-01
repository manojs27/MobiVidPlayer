package com.mobiplayer.mobiplayer.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.mobiplayer.mobiplayer.R;

public class LoginFragment extends Fragment {
    private EditText mEmailId;
    private EditText mPassword;
    private FirebaseAuth mFirebaseAuth;
    private ProgressDialog mProgressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFirebaseAuth = FirebaseAuth.getInstance();
    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.activity_main, container, false);
        mProgressDialog = new ProgressDialog(getActivity());
        initUI(lView);
        return lView;
    }

    private void initUI(View lView) {
        mEmailId = lView.findViewById(R.id.email);
        mPassword = lView.findViewById(R.id.password);
        Button mBtnSignUp = lView.findViewById(R.id.btnSignUp);
        Button mBtnSignIn = lView.findViewById(R.id.btnLogin);
        mBtnSignUp.setOnClickListener(mSignUpListener);
        mBtnSignIn.setOnClickListener(mSignInListener);
    }

    private View.OnClickListener mSignUpListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String emailID = mEmailId.getText().toString();
            String paswd = mPassword.getText().toString();
            if (emailID.isEmpty()) {
                mEmailId.setError("Provide your Email first!");
                mEmailId.requestFocus();
            } else if (paswd.isEmpty()) {
                mPassword.setError("Set your password");
                mPassword.requestFocus();
            } else if (emailID.isEmpty() && paswd.isEmpty()) {
                Toast.makeText(getActivity(), "Fields Empty!", Toast.LENGTH_SHORT).show();
            } else if (!(emailID.isEmpty() && paswd.isEmpty())) {
                mProgressDialog.show();
                mFirebaseAuth.createUserWithEmailAndPassword(emailID, paswd).addOnCompleteListener(getActivity(),
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (!task.isSuccessful()) {
                                    mProgressDialog.dismiss();
                                    Toast.makeText(getActivity().getApplicationContext(),
                                            "SignUp unsuccessful:" + task.getException().getMessage(),
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    };

    private View.OnClickListener mSignInListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String userEmail = mEmailId.getText().toString();
            String userPaswd = mPassword.getText().toString();
            if (userEmail.isEmpty()) {
                mEmailId.setError("Provide your Email first!");
                mEmailId.requestFocus();
            } else if (userPaswd.isEmpty()) {
                mPassword.setError("Enter Password!");
                mPassword.requestFocus();
            } else if (userEmail.isEmpty() && userPaswd.isEmpty()) {
                Toast.makeText(getActivity(), "Fields Empty!", Toast.LENGTH_SHORT).show();
            } else if (!(userEmail.isEmpty() && userPaswd.isEmpty())) {
                mProgressDialog.show();
                mFirebaseAuth.signInWithEmailAndPassword(userEmail, userPaswd)
                        .addOnCompleteListener(getActivity(),
                                new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        mProgressDialog.dismiss();
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "Login Not successful", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (getActivity() != null) {
                                                getActivity().getSupportFragmentManager().beginTransaction()
                                                        .replace(R.id.container, VideoListFragment.newInstance())
                                                        .commitNow();
                                            }
                                        }
                                    }
                                });
            } else {
                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
    };
}
