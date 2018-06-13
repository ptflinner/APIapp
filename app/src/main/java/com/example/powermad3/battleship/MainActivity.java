package com.example.powermad3.battleship;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;
import android.widget.TextView;

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
    private BottomNavigationView.OnNavigationItemSelectedListener mBottomNavView=new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch(menuItem.getItemId()){
                case R.id.single_player:
                    mTextMessage.setText(R.string.title_activity_single_player);
                    return true;
                case R.id.multiple_players:
                    mTextMessage.setText((R.string.title_activity_multi_player));
                    return true;
                case R.id.setting:
                    mTextMessage.setText(R.string.title_activity_settings);
                    return true;
                case R.id.friends_list:
                    mTextMessage.setText(R.string.title_activity_friends_list);
                    return true;
            }
            return false;
        }
    };

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
        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(mBottomNavView);

        loadUserInformation();

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
