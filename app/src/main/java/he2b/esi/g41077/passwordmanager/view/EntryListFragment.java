package he2b.esi.g41077.passwordmanager.view;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import he2b.esi.g41077.passwordmanager.R;
import he2b.esi.g41077.passwordmanager.adapters.EntryListAdapter;

public class EntryListFragment extends Fragment {

    public EntryListFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.entry_list_fragment, container, false);

        // init firebase auth
        FirebaseAuth auth = FirebaseAuth.getInstance();

        // init firebase realtime database on user child node
        FirebaseDatabase fbDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbRef = fbDatabase.getReference().child(auth.getCurrentUser().getUid());

        // create a vertical layout to set the recycler view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, true);
        RecyclerView entryRecyclerView = view.findViewById(R.id.entry_recycler_view);
        entryRecyclerView.setLayoutManager(linearLayoutManager);

        // create list adapter
        EntryListAdapter entryListAdapter = new EntryListAdapter(getActivity(), dbRef);

        // link the recycler view with the adapter
        entryRecyclerView.setAdapter(entryListAdapter);

        return view;
    }
}
