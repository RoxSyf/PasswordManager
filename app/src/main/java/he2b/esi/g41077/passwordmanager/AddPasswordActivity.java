package he2b.esi.g41077.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import he2b.esi.g41077.passwordmanager.database.DatabaseManager;

/**
 * Created by g41077 on 31/10/2017.
 */

public class AddPasswordActivity extends Activity implements OnClickListener {

    private Button mAddButton;
    private EditText mLoginEditText;
    private EditText mPwdEditText;

    private DatabaseManager mDBManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Add record");
        setContentView(R.layout.activity_add_record);

        mLoginEditText = (EditText) findViewById(R.id.et_login);
        mPwdEditText = (EditText) findViewById(R.id.et_pwd);
        mAddButton = (Button) findViewById(R.id.bt_add_pwd);

        mDBManager = new DatabaseManager(this);
        mDBManager.open();
        mAddButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_add_pwd:

                final String login = mLoginEditText.getText().toString();
                final String pwd = mPwdEditText.getText().toString();

                mDBManager.insert(login, pwd);

                Intent main = new Intent(AddPasswordActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                startActivity(main);
                break;
        }
    }

}
