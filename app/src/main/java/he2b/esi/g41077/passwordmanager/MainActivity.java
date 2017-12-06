package he2b.esi.g41077.passwordmanager;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import he2b.esi.g41077.passwordmanager.adapters.EntryListViewAdapter;
import he2b.esi.g41077.passwordmanager.model.Entry;
import he2b.esi.g41077.passwordmanager.model.FacadeImplementation;

public class MainActivity extends AppCompatActivity {

    private EditText etName;
    private ListView entryList;

    private FacadeImplementation util;

    private List<Entry> mEntryList = new ArrayList<>();

    private Entry selectedEntry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        util = new FacadeImplementation(this);

        // add toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Manager");
        setSupportActionBar(toolbar);

        // view
        etName = (EditText) findViewById(R.id.et_name);
        entryList = (ListView) findViewById(R.id.lv_entries);
        entryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Entry entry = (Entry) adapterView.getItemAtPosition(i);
                selectedEntry = entry;
                etName.setText(entry.getmName());
            }
        });

        addEventFirebaseListener();

    }

    private void addEventFirebaseListener() {
        entryList.setVisibility(View.VISIBLE);

        util.getDatabaseReference().child("users").child(util.getCurrentUser()).child("entries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (mEntryList != null) {
                    if (mEntryList.size() > 0)
                        mEntryList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Entry entry = postSnapshot.getValue(Entry.class);
                        mEntryList.add(entry);
                    }
                }
                EntryListViewAdapter adapter = new EntryListViewAdapter(MainActivity.this, mEntryList);
                entryList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            createEntry();
        } else if (item.getItemId() == R.id.menu_remove) {
            deleteEntry(selectedEntry);
        } else if (item.getItemId() == R.id.menu_save) {
            Entry entry = new Entry(UUID.randomUUID().toString(), etName.getText().toString(), "placeholder", "********");
            updateEntry(entry);
        }
        return true;
    }

    private void deleteEntry(Entry selectedEntry) {
        util.deleteEntry(selectedEntry);
        clearEditText();
    }

    private void updateEntry(Entry entry) {
        util.updateEntry(entry);
    }

    private void createEntry() {
        Entry entry = new Entry(UUID.randomUUID().toString(), etName.getText().toString(), "placeholder", "********");
        util.createEntry(entry);
        clearEditText();
    }

    private void clearEditText() {
        etName.setText("");
    }
}
