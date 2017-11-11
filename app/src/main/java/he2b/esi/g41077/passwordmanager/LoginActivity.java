package he2b.esi.g41077.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity implements View.OnClickListener {

    // firebase authentificator
    private FirebaseAuth mAuth;

    // ui
    private Button mBtLogin;
    private EditText mEtEmail;
    private EditText mEtPassword;
    private TextView mTvForgot;
    private TextView mTvSignup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivy_login);

        // view
        mBtLogin = findViewById(R.id.bt_login);
        mEtEmail = findViewById(R.id.et_email);
        mEtPassword = findViewById(R.id.et_password);
        mTvForgot = findViewById(R.id.tv_forgot_password);
        mTvSignup = findViewById(R.id.tv_signup);

        // binding
        mBtLogin.setOnClickListener(this);
        mTvForgot.setOnClickListener(this);
        mTvSignup.setOnClickListener(this);

        // init firebase auth
        mAuth = FirebaseAuth.getInstance();

        // check if user is already connected
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        }
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
                        if(!task.isSuccessful()){

                        }
                    }
                });
    }

}
