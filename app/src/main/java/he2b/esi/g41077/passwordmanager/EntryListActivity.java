package he2b.esi.g41077.passwordmanager;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import he2b.esi.g41077.passwordmanager.model.Entry;

public class EntryListActivity extends SingleFragmentActivity {

    private EntryListFragment mEntryListFragment;

    @Override
    protected Fragment createFragment() {
        mEntryListFragment = new EntryListFragment();
        return mEntryListFragment;
    }
}
