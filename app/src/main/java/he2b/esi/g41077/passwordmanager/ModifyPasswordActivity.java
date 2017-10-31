package he2b.esi.g41077.passwordmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import he2b.esi.g41077.passwordmanager.database.DatabaseManager;

/**
 * Created by g41077 on 31/10/2017.
 */

public class ModifyPasswordActivity extends Activity implements View.OnClickListener {

    private Button mBtUpdate, mBtDelete;
    private EditText mLoginEditText;
    private EditText mPwdEditText;

    private DatabaseManager mDBManager;
    private Long mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle("Modify record");

        setContentView(R.layout.activity_modify_record);

        mDBManager = new DatabaseManager(this);
        mDBManager.open();

        mLoginEditText = (EditText) findViewById(R.id.et_login);
        mPwdEditText = (EditText) findViewById(R.id.et_pwd);

        mBtUpdate = (Button) findViewById(R.id.bt_update);
        mBtDelete = (Button) findViewById(R.id.bt_delete);

        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        String login = intent.getStringExtra("login");
        String pwd = intent.getStringExtra("password");
        mId = Long.parseLong(id);

        mLoginEditText.setText(login);
        mPwdEditText.setText(pwd);

        mBtUpdate.setOnClickListener(this);
        mBtDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_update:
                String title = mLoginEditText.getText().toString();
                String desc = mPwdEditText.getText().toString();

                mDBManager.update(mId, title, desc);
                this.returnHome();
                break;

            case R.id.bt_delete:
                mDBManager.delete(mId);
                this.returnHome();
                break;
        }
    }

    private void returnHome() {
        Intent home_intent = new Intent(getApplicationContext(), MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(home_intent);
    }


}
