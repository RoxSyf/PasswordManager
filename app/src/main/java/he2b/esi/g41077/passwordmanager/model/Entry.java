package he2b.esi.g41077.passwordmanager.model;


public class Entry {

    private String mUid;
    private String mName;
    private String mLogin;
    private String mPassword;
    private boolean mFavorite;

    public Entry() {
    }

    public Entry(String mUid, String mName, String mLogin, String mPassword) {
        this.mUid = mUid;
        this.mName = mName;
        this.mLogin = mLogin;
        this.mPassword = mPassword;
        this.mFavorite = false;
    }

    public Entry(String mUid, String mName, String mLogin, String mPassword, boolean mFavorite) {
        this.mUid = mUid;
        this.mName = mName;
        this.mLogin = mLogin;
        this.mPassword = mPassword;
        this.mFavorite = mFavorite;
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

    public boolean ismFavorite() {
        return mFavorite;
    }

    public void setmFavorite(boolean mFavorite) {
        this.mFavorite = mFavorite;
    }
}
