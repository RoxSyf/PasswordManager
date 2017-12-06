package he2b.esi.g41077.passwordmanager.model;

import android.content.Context;
import android.widget.ListView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FacadeImplementation implements Facade {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public FacadeImplementation(Context context) {
        FirebaseApp.initializeApp(context);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
    }

    @Override
    public void createEntry(Entry entry) {
        String currentUser = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("users").child(currentUser).child("entries").child(entry.getmUid()).setValue(entry);
    }

    @Override
    public void deleteEntry(Entry selectedEntry) {
        String currentUser = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("users").child(currentUser).child("entries").child(selectedEntry.getmUid()).removeValue();
    }

    @Override
    public void updateEntry(Entry selectedEntry) {
        // todo
    }

    @Override
    public String getCurrentUser() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    public final DatabaseReference getDatabaseReference() {
        return databaseReference;
    }

}
