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
import android.widget.ImageButton;

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
    private ImageButton mButtonFav;

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
        initView(v);
        mButtonFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isSelected()) {
                    view.setSelected(false);
                } else {
                    mButtonFav.setSelected(false);
                    view.setSelected(true);
                }
            }
        });
        isEntryEdit(v);
        return v;
    }

    private void isEntryEdit(View v) {
        if (isEntryEdit) {
            setViewEntryEdit();
            mEntryDelete = v.findViewById(R.id.bt_add_cancel_or_remove);
            mEntryDelete.setText("Delete");
            initToolbarEdit(v);
            setOnClickDeleteButton();
            setOnClickConfirmButtonEdit();
        } else {
            mEntryCancel = v.findViewById(R.id.bt_add_cancel_or_remove);
            mEntryCancel.setText("Cancel");
            initToolbarAdd(v);
            setOnClickCancelButton();
            setOnClickConfirmButtonAdd();
        }
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
                String encryptedPassword = util.cipherPassword(mEntryPassword.getText().toString());
                String uid = UUID.randomUUID().toString();
                String name = mEntryName.getText().toString();
                String login = mEntryLogin.getText().toString();
                util.createEntry(new Entry(uid, name, login, encryptedPassword, mButtonFav.isSelected()));
                getActivity().finish();
            }
        });
    }

    private void setOnClickConfirmButtonEdit() {
        mEntryConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String encryptedPassword = util.cipherPassword(mEntryPassword.getText().toString());
                mEntry = new Entry(entryUid.toString(),
                        mEntryName.getText().toString(),
                        mEntryLogin.getText().toString(),
                        encryptedPassword);
                mEntry.setmFavorite(mButtonFav.isSelected());
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
                        String encryptedPassword = util.decipherPassword(mEntry.getmPassword());
                        mEntryLogin.setText(mEntry.getmLogin());
                        mEntryPassword.setText(encryptedPassword);
                        mButtonFav.setSelected(mEntry.ismFavorite());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    private void initToolbarEdit(View v) {
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle("Edit entry");
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initToolbarAdd(View v) {
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle("Add entry");
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initView(View v) {
        mEntryName = v.findViewById(R.id.et_entry_name);
        mEntryLogin = v.findViewById(R.id.et_entry_login);
        mEntryPassword = v.findViewById(R.id.et_entry_pwd);
        mEntryConfirm = v.findViewById(R.id.bt_add_confirm);
        mButtonFav = v.findViewById(R.id.ib_fav);
    }
}
