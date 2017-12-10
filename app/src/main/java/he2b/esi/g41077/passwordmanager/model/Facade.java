package he2b.esi.g41077.passwordmanager.model;

import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.UUID;

public interface Facade {

    void createEntry(Entry entry);

    void deleteEntry(Entry selectedEntry);

    void updateEntry(Entry selectedEntry);

    Entry getEntry(DataSnapshot dataSnapshot);

    String getCurrentUser();

    List<Entry> getUserEntries();

    List<Entry> getUserFavoriteEntries();

    String cipherPassword(String password);

    String decipherPassword(String encryptedPassword);

}
