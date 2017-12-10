package he2b.esi.g41077.passwordmanager.controller;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
    private ProgressBar mCircularProgressBar;

    private int access = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        util = new FacadeImplementation();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_entry_list, container, false);
        initView(v);
        initToolbar(v);
        setChildEventListener();

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_add) {
            startActivity(new Intent(getActivity(), DetailActivity.class));
        } else if (item.getItemId() == R.id.menu_dashboard) {
            startActivity(new Intent(getActivity(), DashboardActivity.class));
        } else if (item.getItemId() == R.id.menu_favorite) {
            setAdapter();
        }
        return true;
    }

    private void setAdapter() {
        // not proud of this one - it's temporary of course
        mEntryAdapter = null;
        if (access % 2 == 0) {
            updateUIFav();
            access++;
        } else {
            updateUI();
            access++;
        }
    }

    private void setChildEventListener() {
        // charging the database
        mCircularProgressBar.setVisibility(View.VISIBLE);
        mEntryRecyclerView.setVisibility(View.INVISIBLE);
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
    }

    private void initToolbar(View v) {
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        toolbar.setTitle("Password Manager");
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }

    private void initView(View v) {
        mCircularProgressBar = v.findViewById(R.id.circular_progress_bar);
        mEntryRecyclerView = v.findViewById(R.id.list_entry_recycler);
        mEntryRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMenuFav = v.findViewById(R.id.menu_favorite);
    }

    private void updateUI() {
        setAdapter(util.getUserEntries());
        mCircularProgressBar.setVisibility(View.INVISIBLE);
        mEntryRecyclerView.setVisibility(View.VISIBLE);
    }

    private void updateUIFav() {
        setAdapter(util.getUserFavoriteEntries());
    }

    private void setAdapter(List<Entry> entries) {
        if (mEntryAdapter == null) {
            mEntryAdapter = new EntryAdapter(entries);
            mEntryRecyclerView.setAdapter(mEntryAdapter);
        } else {
            mEntryAdapter.notifyDataSetChanged();
        }
    }

    private class EntryHolder extends RecyclerView.ViewHolder {
        private Entry mEntry;
        private TextView mEntryName;
        private ImageView mFavorite;

        EntryHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.entry_item, parent, false));
            mEntryName = itemView.findViewById(R.id.tv_name);
            mFavorite = itemView.findViewById(R.id.iv_fav);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("entry_uid", mEntry.getmUid());
                    startActivity(intent);
                }
            });
        }

        void bind(Entry entry) {
            mEntry = entry;
            mEntryName.setText(entry.getmName());
            if (mEntry.ismFavorite())
                mFavorite.setVisibility(View.VISIBLE);
            else
                mFavorite.setVisibility(View.GONE);
        }
    }

    private class EntryAdapter extends RecyclerView.Adapter<EntryHolder> {

        private List<Entry> mEntries;

        EntryAdapter(List<Entry> entries) {
            mEntries = entries;
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
