package he2b.esi.g41077.passwordmanager.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class FacadeImplementation implements Facade {

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private List<Entry> entryList = new ArrayList<>();

    private final String key = "Pass41077Manager";


    public FacadeImplementation() {
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

        // todo refactor
        getDatabaseReference().child("users").child(getCurrentUser()).child("entries").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (entryList != null) {
                    entryList.clear();
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Entry entry = postSnapshot.getValue(Entry.class);
                        entryList.add(entry);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void createEntry(Entry entry) {
        String currentUser = firebaseAuth.getCurrentUser().getUid();
        databaseReference.child("users").child(currentUser).child("entries").child(entry.getmUid()).setValue(entry);
    }

    @Override
    public void deleteEntry(Entry selectedEntry) {
        databaseReference.child("users").child(getCurrentUser()).child("entries").child(selectedEntry.getmUid()).removeValue();
    }

    @Override
    public void updateEntry(Entry selectedEntry) {
        databaseReference.child("users").child(getCurrentUser()).child("entries").child(selectedEntry.getmUid()).setValue(selectedEntry);
    }

    @Override
    public Entry getEntry(DataSnapshot dataSnapshot) {
        return dataSnapshot.getValue(Entry.class);
    }

    @Override
    public String getCurrentUser() {
        return firebaseAuth.getCurrentUser().getUid();
    }

    @Override
    public List<Entry> getUserEntries() {
        return entryList;
    }

    @Override
    public String cipherPassword(String password) {
        String encryptedPassword = password;
        try {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // encrypt the text
            cipher.init(Cipher.ENCRYPT_MODE, aesKey);
            byte[] encrypted = cipher.doFinal(password.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : encrypted) {
                sb.append((char) b);
            }

            // the encrypted String
            encryptedPassword = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedPassword;
    }

    @Override
    public String decipherPassword(String encryptedPassword) {
        String decryptedPassword = encryptedPassword;
        try {
            // Create key and cipher
            Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
            Cipher cipher = Cipher.getInstance("AES");
            // now convert the string to byte array
            // for decryption
            byte[] bb = new byte[encryptedPassword.length()];
            for (int i = 0; i < encryptedPassword.length(); i++) {
                bb[i] = (byte) encryptedPassword.charAt(i);
            }
            // decrypt the text
            cipher.init(Cipher.DECRYPT_MODE, aesKey);
            decryptedPassword = new String(cipher.doFinal(bb));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedPassword;
    }

    public final DatabaseReference getDatabaseReference() {
        return databaseReference;
    }
}
