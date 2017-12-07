package he2b.esi.g41077.passwordmanager.controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import he2b.esi.g41077.passwordmanager.R;

public class DashboardActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private ConstraintLayout mActivityDashboard;
    private TextView mTvWelcome;
    private EditText mEtNewPassword;
    private Button mBtChangePassword;
    private Button mBtLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth = FirebaseAuth.getInstance();
        initView();
        initBindings();
        initWelcomeMessage();
    }

    private void initWelcomeMessage() {
        if (mAuth.getCurrentUser() != null) {
            mTvWelcome.setText("Welcome, " + mAuth.getCurrentUser().getEmail());
        }
    }

    private void initBindings() {
        mBtChangePassword.setOnClickListener(this);
        mBtLogout.setOnClickListener(this);
    }

    private void initView() {
        mActivityDashboard = findViewById(R.id.activity_dashboard);
        mTvWelcome = findViewById(R.id.tv_welcome_dashboard);
        mEtNewPassword = findViewById(R.id.et_new_password);
        mBtChangePassword = findViewById(R.id.bt_change_password);
        mBtLogout = findViewById(R.id.bt_logout);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_change_password) {
            if (mEtNewPassword.getText().toString().trim().length() != 0)
                changePassword(mEtNewPassword.getText().toString());
        } else if (view.getId() == R.id.bt_logout) {
            signOut();
        }
    }

    private void signOut() {
        mAuth.signOut();
        if (mAuth.getCurrentUser() == null) {
            startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void changePassword(String password) {
        FirebaseUser user = mAuth.getCurrentUser();
        user.updatePassword(password).addOnCompleteListener(this, new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Snackbar mSnackbar = Snackbar.make(mActivityDashboard, "Password changed", Snackbar.LENGTH_SHORT);
                mSnackbar.show();
            }
        });
    }
}
