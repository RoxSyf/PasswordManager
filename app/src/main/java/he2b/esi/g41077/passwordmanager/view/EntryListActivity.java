package he2b.esi.g41077.passwordmanager.view;

import android.app.Fragment;

public class EntryListActivity extends SingleFragmentActivity {

    private EntryListFragment mEntryListFragment;

    @Override
    protected Fragment createFragment() {
        mEntryListFragment = new EntryListFragment();
        return mEntryListFragment;
    }
}
