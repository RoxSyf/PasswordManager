package he2b.esi.g41077.passwordmanager.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import he2b.esi.g41077.passwordmanager.R;

public class AddEntryFragment extends Fragment {

    private EditText mEntryName;
    private EditText mEntryLogin;
    private EditText mEntryPassword;
    private Button mEntryCancel;
    private Button mEntryConfirm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_entry, container, false);
    }

}