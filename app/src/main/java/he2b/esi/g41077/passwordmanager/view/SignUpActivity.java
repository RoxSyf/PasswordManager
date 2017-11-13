package he2b.esi.g41077.passwordmanager.view;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import he2b.esi.g41077.passwordmanager.R;

public class SignUpActivity extends Activity implements View.OnClickListener {

    // firebase authentificator
    private FirebaseAuth mAuth;

    // ui
    private EditText mEtEmail;
    private EditText mEtPassword;
    private Button mBtCreateAccount;
    private TextView mTvForgot;
    private TextView mTvSignUp;

    // activity
    private ConstraintLayout mActivitySignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // view
        mEtEmail = findViewById(R.id.et_email_signup);
        mEtPassword = findViewById(R.id.et_password_signup);
        mBtCreateAccount = findViewById(R.id.bt_register);
        mTvForgot = findViewById(R.id.tv_forgot_password);
        mTvSignUp = findViewById(R.id.tv_signin);

        // activity
        mActivitySignUp = findViewById(R.id.activity_sign_up);

        // binding
        mBtCreateAccount.setOnClickListener(this);
        mTvForgot.setOnClickListener(this);
        mTvSignUp.setOnClickListener(this);

        // init firebase auth
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.bt_register) {
            signIn(mEtEmail.getText().toString(), mEtPassword.getText().toString());
        } else if (view.getId() == R.id.tv_forgot_password) {
            startActivity(new Intent(SignUpActivity.this, ForgotPasswordActivity.class));
            finish();
        } else if (view.getId() == R.id.tv_signin) {
            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            finish();
        }
    }

    private void signIn(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Snackbar mSnackbar = Snackbar.make(mActivitySignUp, "Error: " + task.getException(), Snackbar.LENGTH_SHORT);
                            mSnackbar.show();
                        } else {
                            Snackbar mSnackbar = Snackbar.make(mActivitySignUp, "Account has been successfully created!", Snackbar.LENGTH_SHORT);
                            mSnackbar.show();
                        }
                    }
                });
    }
}
