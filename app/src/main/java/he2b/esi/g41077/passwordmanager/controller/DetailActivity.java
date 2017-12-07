package he2b.esi.g41077.passwordmanager.controller;

import android.support.v4.app.Fragment;

public class DetailActivity extends SingleFragmentActivity {

    @Override
    protected Fragment createFragment() {
        return new EntryFragment();
    }
}
