package com.example.smartclinicreservation.uthenticate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.example.smartclinicreservation.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

public class RegisterActivity extends AppCompatActivity {
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    RadioGroup genderRadio;
    String patientgender="";

    EditText name,phone,password,repassword,birthday;
    Button Register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        name=findViewById(R.id.Name_txt);
        phone=findViewById(R.id.phone_txt);
        password=findViewById(R.id.password_txt);
        repassword=findViewById(R.id.repassword_txt);
        birthday=findViewById(R.id.birthDay_txt);
        genderRadio=findViewById(R.id.gender);
        Register=findViewById(R.id.Register);
        genderRadio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            //set a listener on the RadioGroup
            @Override
            public void onCheckedChanged(RadioGroup view, @IdRes int checkedId) {
                //checkedId refers to the selected RadioButton
                //Perform an action based on the option chosen
                if (checkedId == R.id.male) {
                    patientgender="male";
                } else {
                    patientgender="female";
                }
            }
        });


        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().isEmpty()||phone.getText().toString().isEmpty()||password.getText().toString().isEmpty()||repassword.getText().toString().isEmpty()||birthday.getText().toString().isEmpty()||patientgender.isEmpty())
                {
                    Toast.makeText(RegisterActivity.this, " plz fill all fields", Toast.LENGTH_SHORT).show();
                }else{
                    if (password.getText().toString().trim().equals(repassword.getText().toString().trim())){
                        BackendlessUser user=new BackendlessUser();
                        user.setPassword(password.getText().toString().trim());
                        user.setProperty("name",name.getText().toString().trim());
                        user.setProperty("age",birthday.getText().toString().trim());
                        user.setProperty("gender",patientgender);
                        user.setProperty("phone",phone.getText().toString().trim());


                        tvLoad.setText("Regestration......please wait!!");
                        showProgress(true);
                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {
                                Toast.makeText(RegisterActivity.this, "Registration successfel", Toast.LENGTH_SHORT).show();
                                    RegisterActivity.this.finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                showProgress(false);
                                Toast.makeText(RegisterActivity.this, ""+fault.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }
        });


    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
