package he2b.esi.g41077.passwordmanager.model;

import java.util.List;

public interface Facade {

    void createEntry(Entry entry);

    void deleteEntry(Entry selectedEntry);

    void updateEntry(Entry selectedEntry);

    String getCurrentUser();

    List<Entry> getUserEntries();

}
