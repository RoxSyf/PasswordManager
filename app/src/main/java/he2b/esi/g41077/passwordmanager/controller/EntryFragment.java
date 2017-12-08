package he2b.esi.g41077.passwordmanager.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import he2b.esi.g41077.passwordmanager.R;
import he2b.esi.g41077.passwordmanager.model.Entry;
import he2b.esi.g41077.passwordmanager.model.FacadeImplementation;

public class EntryFragment extends Fragment {

    private FacadeImplementation util;

    private Entry mEntry;
    private UUID entryUid;

    private EditText mEntryName;
    private EditText mEntryLogin;
    private EditText mEntryPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String tmp = getActivity().getIntent().getSerializableExtra("entry_uid").toString();
        entryUid = UUID.fromString(tmp);

        util = new FacadeImplementation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry, container, false);
        initToolbar(v);
        initView(v);
        setView();
        return v;
    }

    private void setView() {
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
        // add toolbar
        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setTitle("Edit entry");
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initView(View v) {
        mEntryName = v.findViewById(R.id.et_entry_name);
        mEntryLogin = v.findViewById(R.id.et_entry_login);
        mEntryPassword = v.findViewById(R.id.et_entry_pwd);
    }
}
