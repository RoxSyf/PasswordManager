package he2b.esi.g41077.passwordmanager.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;

import he2b.esi.g41077.passwordmanager.R;
import he2b.esi.g41077.passwordmanager.model.Entry;

public class EntryListAdapter extends FirebaseRecyclerAdapter<Entry, EntryListAdapter.EntryViewHolder> {

    private Context mContext;

    public EntryListAdapter(Context mContext, DatabaseReference dbRef) {
        super(Entry.class, R.layout.entry_list_item, EntryViewHolder.class, dbRef);
        this.mContext = mContext;
    }

    @Override
    public EntryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        EntryViewHolder entryViewHolder = super.onCreateViewHolder(parent, viewType);
        return entryViewHolder;
    }

    @Override
    protected void populateViewHolder(EntryViewHolder viewHolder, Entry model, int position) {
        viewHolder.setmEntry(model);
        viewHolder.setmEntryName(model.getmName());
    }

    public static class EntryViewHolder extends RecyclerView.ViewHolder {

        private Entry mEntry;
        private TextView mEntryName;

        public EntryViewHolder(View itemView) {
            super(itemView);
            mEntryName = (TextView) itemView.findViewById(R.id.tv_site_name);

            // set click on item
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // todo
                }
            });
        }

        public void setmEntry(Entry entry) {
            mEntry = entry;
        }

        public void setmEntryName(String entrySiteName) {
            if (mEntryName != null)
                mEntryName.append(entrySiteName);

        }
    }
}
