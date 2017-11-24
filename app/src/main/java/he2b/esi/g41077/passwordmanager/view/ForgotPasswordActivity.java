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
import com.google.firebase.auth.FirebaseAuth;

import he2b.esi.g41077.passwordmanager.R;

public class ForgotPasswordActivity extends Activity implements View.OnClickListener {

    // firebase authentification
    private FirebaseAuth mAuth;

    // ui
    private EditText mEtEmail;
    private Button mBtReset;
    private TextView mTvBack;

    // activity
    private ConstraintLayout mActivityForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        // view
        mEtEmail = findViewById(R.id.et_email_forgot);
        mBtReset = findViewById(R.id.bt_reset_password);
        mTvBack = findViewById(R.id.tv_back);
        mActivityForgotPassword = findViewById(R.id.activity_forgot_password);

        // init firebase auth
        mAuth = FirebaseAuth.getInstance();

        // binding
        mBtReset.setOnClickListener(this);
        mTvBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_back) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else if (view.getId() == R.id.bt_reset_password) {
            resetPassword(mEtEmail.getText().toString());
        }
    }

    private void resetPassword(final String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Snackbar snackBar = Snackbar.make(mActivityForgotPassword, "Email sent to " + email + " with reset instructions", Snackbar.LENGTH_SHORT);
                            snackBar.show();
                        } else {
                            Snackbar snackBar = Snackbar.make(mActivityForgotPassword, "Failed to sent password reset instructions", Snackbar.LENGTH_SHORT);
                            snackBar.show();
                        }
                    }
                });
    }
}
