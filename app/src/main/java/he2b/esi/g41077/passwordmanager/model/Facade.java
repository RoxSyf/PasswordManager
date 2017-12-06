package he2b.esi.g41077.passwordmanager.model;

public interface Facade {

    void createEntry(Entry entry);

    void deleteEntry(Entry selectedEntry);

    void updateEntry(Entry selectedEntry);

    String getCurrentUser();

}
