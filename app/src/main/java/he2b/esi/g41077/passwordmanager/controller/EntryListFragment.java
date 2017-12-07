package he2b.esi.g41077.passwordmanager.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import he2b.esi.g41077.passwordmanager.R;
import he2b.esi.g41077.passwordmanager.model.Entry;
import he2b.esi.g41077.passwordmanager.model.FacadeImplementation;

public class EntryListFragment extends Fragment {

    private RecyclerView mEntryRecyclerView;
    private EntryAdapter mEntryAdapter;
    private FacadeImplementation util;
    private List<Entry> entries;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        entries = new ArrayList<>();
        util = new FacadeImplementation();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry_list, container, false);
//        util.registerObserver(this);
        mEntryRecyclerView = v.findViewById(R.id.list_entry_recycler);
        mEntryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        util.getDatabaseReference().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                updateUI();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                updateUI();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                updateUI();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        return v;
    }

    private void updateUI() {
        if (mEntryAdapter == null) {
            mEntryAdapter = new EntryAdapter();
            mEntryRecyclerView.setAdapter(mEntryAdapter);
        } else {
            mEntryAdapter.notifyDataSetChanged();
        }
    }

    private class EntryHolder extends RecyclerView.ViewHolder {
        private Entry mEntry;
        private TextView mEntryName;

        public EntryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.entry_item, parent, false));
            mEntryName = (TextView) itemView.findViewById(R.id.tv_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(getActivity(), CrimeActivity.class);
//                    intent.putExtra("crime_id", mCrime.getmId());
//                    startActivity(intent);
                }
            });
        }

        public void bind(Entry entry) {
            mEntry = entry;
            mEntryName.setText(entry.getmName());
        }
    }

    private class EntryAdapter extends RecyclerView.Adapter<EntryHolder> {

        private List<Entry> mEntries;

        public EntryAdapter() {
            mEntries = util.getUserEntries();
        }

        @Override
        public EntryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new EntryHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(EntryHolder holder, int position) {
            Entry entry = mEntries.get(position);
            holder.bind(entry);
        }

        @Override
        public int getItemCount() {
            return mEntries.size();
        }

    }
}
