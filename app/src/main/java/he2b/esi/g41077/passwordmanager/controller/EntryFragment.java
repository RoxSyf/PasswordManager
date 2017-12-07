package he2b.esi.g41077.passwordmanager.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import he2b.esi.g41077.passwordmanager.R;
import he2b.esi.g41077.passwordmanager.model.FacadeImplementation;

public class EntryFragment extends Fragment {

    private FacadeImplementation util;

    private EditText mEntryName;
    private EditText mEntryLogin;
    private EditText mEntryPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new FacadeImplementation();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_entry, container, false);

        mEntryName = v.findViewById(R.id.et_entry_name);
        mEntryLogin = v.findViewById(R.id.et_entry_login);
        mEntryPassword = v.findViewById(R.id.et_entry_pwd);

        return v;
    }
}
