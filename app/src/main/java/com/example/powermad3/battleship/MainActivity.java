package com.example.powermad3.battleship;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.powermad3.battleship.fragments.friends_list.FriendFragmentMain;
import com.example.powermad3.battleship.fragments.multiplayer.MultiFragmentMain;
import com.example.powermad3.battleship.fragments.settings.SettingsFragmentMain;
import com.example.powermad3.battleship.fragments.single_player.SingleFragmentMain;
import com.example.powermad3.battleship.models.User;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private GoogleApiClient mGoogleApiClient;
    private DatabaseReference userReference;
    private BottomNavigationView mBottomNavView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();

        mTextMessage=findViewById(R.id.message);
        mBottomNavView= findViewById(R.id.bottom_navigation);
        mBottomNavView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment frag=null;
                switch(menuItem.getItemId()){
                    case R.id.single_player:
                        frag= SingleFragmentMain.newInstance();
                        break;
                    case R.id.multiple_players:
                        frag= MultiFragmentMain.newInstance();
                        break;
                    case R.id.setting:
                        frag= SettingsFragmentMain.newInstance();
                        break;
                    case R.id.friends_list:
                        frag= FriendFragmentMain.newInstance();
                        break;
                }
                if(frag==null)return false;
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, frag);
                transaction.commit();
                return true;

            }
        });
        loadUserInformation();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, SingleFragmentMain.newInstance());
        transaction.commit();
    }

    private void loadUserInformation() {
        // Load Character from Database
        final String firebaseUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        userReference=(FirebaseDatabase.getInstance().getReference("userList").child(firebaseUid));
        userReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!(dataSnapshot.exists())) {
                    Bundle bundle=getIntent().getBundleExtra("signInArgs");
                    String firstName=bundle.getString("firstName");
                    String lastName=bundle.getString("lastName");
                    String displayName=bundle.getString("displayName");
                    User userToAdd = new User(firebaseUid, firstName,lastName,displayName);
                    userReference.setValue(userToAdd);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
