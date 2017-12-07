package he2b.esi.g41077.passwordmanager.controller;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import he2b.esi.g41077.passwordmanager.R;

public class MainActivity extends SingleFragmentActivity {
    @Override
    protected Fragment createFragment() {
        return new EntryListFragment();
    }
}
