package he2b.esi.g41077.passwordmanager.view;

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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import he2b.esi.g41077.passwordmanager.MainActivity;
import he2b.esi.g41077.passwordmanager.R;

public class LoginActivity extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;

    private Button mBtLogin;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private TextView mTvForgot;
    private TextView mTvSignup;

    // activity
    private ConstraintLayout mActivityLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_login);
        mAuth = FirebaseAuth.getInstance();
        initView();
        initBindings();
        isUserConnected();
    }

    private void isUserConnected() {
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        }
    }

    private void initBindings() {
        mBtLogin.setOnClickListener(this);
        mTvForgot.setOnClickListener(this);
        mTvSignup.setOnClickListener(this);
    }

    private void initView() {
        mBtLogin = findViewById(R.id.bt_login);
        mEtEmail = findViewById(R.id.et_email);
        mEtPassword = findViewById(R.id.et_password);
        mTvForgot = findViewById(R.id.tv_forgot_password);
        mTvSignup = findViewById(R.id.tv_signup);
        mActivityLogin = findViewById(R.id.activity_login);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_forgot_password) {
            startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
            finish();
        } else if (view.getId() == R.id.tv_signup) {
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            finish();
        } else if (view.getId() == R.id.bt_login) {
            signIn(mEtEmail.getText().toString(), mEtPassword.getText().toString());
        }
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            if (mEtPassword.length() < 6) {
                                Snackbar snackBar = Snackbar.make(mActivityLogin, "Password must be at least 6 characters long!", Snackbar.LENGTH_SHORT);
                                snackBar.show();
                            }
                        } else {
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        }
                    }
                });
    }

}
