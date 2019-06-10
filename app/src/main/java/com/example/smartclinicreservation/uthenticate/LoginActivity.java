package com.example.smartclinicreservation.uthenticate;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;
import com.example.smartclinicreservation.patient.HomeScreenPatient;
import com.example.smartclinicreservation.R;
import com.example.smartclinicreservation.doctor.HomeScreenDoctor;

public class LoginActivity extends AppCompatActivity {
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;
    EditText etPhone,etPassword;
    Button btnLogin,btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);
        etPhone =findViewById(R.id.etPhone);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        getSupportActionBar().hide();
        showProgress(true);
        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {
                if(response){
                    String UserObjectId= UserIdStorageFactory.instance().getStorage().get();
                    Backendless.Data.of(BackendlessUser.class).findById(UserObjectId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            String phone=Backendless.UserService.CurrentUser().getProperty("phone").toString().trim();
                            if (phone.equals("01096427890")){
                                startActivity(new Intent(LoginActivity.this, HomeScreenDoctor.class));
                                LoginActivity.this.finish();
                                }
                            else{
                                startActivity(new Intent(LoginActivity.this, HomeScreenPatient.class));
                                LoginActivity.this.finish();
                            }
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showProgress(false);
                        }
                    });

                }else{
                    showProgress(false);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showProgress(false);
            }
        });
        String UserObjectId= UserIdStorageFactory.instance().getStorage().get();

        Backendless.UserService.findById(UserObjectId, new AsyncCallback<BackendlessUser>() {

            @Override

            public void handleResponse(BackendlessUser response) {

                if (response != null) {

                    Backendless.UserService.setCurrentUser(response);


                }

            }

            @Override

            public void handleFault(BackendlessFault fault) {

            }

        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etPhone.getText().toString().isEmpty()||etPassword.getText().toString().isEmpty()){

                }else {
                    final String Phone= etPhone.getText().toString().trim();
                    String Password=etPassword.getText().toString().trim();

                    tvLoad.setText("Signning in !!");
                    showProgress(true);
                    Backendless.UserService.login(Phone, Password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {

                            Toast.makeText(LoginActivity.this, "Login Successufully", Toast.LENGTH_SHORT).show();
                            if (Phone.equals("01096427890")){
                                startActivity(new Intent(LoginActivity.this, HomeScreenDoctor.class));
                                LoginActivity.this.finish();
                            }
                            else{
                                startActivity(new Intent(LoginActivity.this, HomeScreenPatient.class));
                                LoginActivity.this.finish();
                            }
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showProgress(false);

                        }
                    }, true);
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));

            }
        });


    }

    /**
     * Shows the progress UI and hides the login form.
     */
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