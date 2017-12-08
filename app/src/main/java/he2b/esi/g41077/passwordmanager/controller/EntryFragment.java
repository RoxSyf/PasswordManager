package he2b.esi.g41077.passwordmanager.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.UUID;

import he2b.esi.g41077.passwordmanager.R;
import he2b.esi.g41077.passwordmanager.model.Entry;
import he2b.esi.g41077.passwordmanager.model.FacadeImplementation;

public class EntryFragment extends Fragment {

    private FacadeImplementation util;

    private boolean isEntryEdit;

    private Entry mEntry;
    private UUID entryUid;

    private EditText mEntryName;
    private EditText mEntryLogin;
    private EditText mEntryPassword;
    private Button mEntryDelete;
    private Button mEntryCancel;
    private Button mEntryConfirm;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTypeOfActivity();
        util = new FacadeImplementation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry, container, false);
        initToolbar(v);
        initView(v);
        if (isEntryEdit) {
            setViewEntryEdit();
            mEntryDelete = v.findViewById(R.id.bt_add_cancel_or_remove);
            mEntryDelete.setText("Delete");
            setOnClickDeleteButton();
            setOnClickConfirmButtonEdit();
        } else {
            mEntryCancel = v.findViewById(R.id.bt_add_cancel_or_remove);
            mEntryCancel.setText("Cancel");
            setOnClickCancelButton();
            setOnClickConfirmButtonAdd();
        }
        return v;
    }


    private void initTypeOfActivity() {
        Serializable entry = getActivity().getIntent().getSerializableExtra("entry_uid");
        if (entry != null) {
            isEntryEdit = true;
            String entryStr = entry.toString();
            entryUid = UUID.fromString(entryStr);
        }
    }

    private void setOnClickConfirmButtonAdd() {
        mEntryConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uid = UUID.randomUUID().toString();
                String name = mEntryName.getText().toString();
                String login = mEntryLogin.getText().toString();
                String pwd = mEntryPassword.getText().toString();
                util.createEntry(new Entry(uid, name, login, pwd));
                getActivity().finish();
            }
        });
    }

    private void setOnClickConfirmButtonEdit() {
        mEntryConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEntry = new Entry(entryUid.toString(),
                        mEntryName.getText().toString(),
                        mEntryLogin.getText().toString(),
                        mEntryPassword.getText().toString());
                util.updateEntry(mEntry);
                getActivity().finish();
            }
        });
    }

    private void setOnClickDeleteButton() {
        mEntryDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                util.deleteEntry(mEntry);
                getActivity().finish();
            }
        });
    }

    private void setOnClickCancelButton() {
        mEntryCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
    }

    private void setViewEntryEdit() {
        util.getDatabaseReference()
                .child("users")
                .child(util.getCurrentUser())
                .child("entries")
                .child(entryUid.toString())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        mEntry = util.getEntry(snapshot);
                        mEntryName.setText(mEntry.getmName());
                        mEntryLogin.setText(mEntry.getmLogin());
                        mEntryPassword.setText(mEntry.getmPassword());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void initToolbar(View v) {
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle("Edit entry");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initView(View v) {
        mEntryName = v.findViewById(R.id.et_entry_name);
        mEntryLogin = v.findViewById(R.id.et_entry_login);
        mEntryPassword = v.findViewById(R.id.et_entry_pwd);
        mEntryConfirm = v.findViewById(R.id.bt_add_confirm);
    }


}
