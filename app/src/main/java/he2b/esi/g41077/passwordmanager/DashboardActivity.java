package he2b.esi.g41077.passwordmanager;

import android.app.Activity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class DashboardActivity extends Activity {

    // firebase authentificator
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }
}
