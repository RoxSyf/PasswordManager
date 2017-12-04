package he2b.esi.g41077.passwordmanager.model;


public class Entry {

    private String mUid;
    private String mName;
    private String mLogin;
    private String mPassword;

    public Entry() {
    }

    public Entry(String mUid, String mName, String mLogin, String mPassword) {
        this.mUid = mUid;
        this.mName = mName;
        this.mLogin = mLogin;
        this.mPassword = mPassword;
    }

    public String getmUid() {
        return mUid;
    }

    public String getmName() {
        return mName;
    }

    public String getmLogin() {
        return mLogin;
    }

    public String getmPassword() {
        return mPassword;
    }
}
