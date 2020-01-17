package fr.cocoteam.co2co2;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;



import com.google.android.material.bottomnavigation.BottomNavigationView;


import fr.cocoteam.co2co2.adapter.MatchUserRecyclerViewAdapter;
import fr.cocoteam.co2co2.model.UserMatch;
import fr.cocoteam.co2co2.view.ConnectionFragment;
import fr.cocoteam.co2co2.view.ContactFragment;
import fr.cocoteam.co2co2.view.ContractFragment;
import fr.cocoteam.co2co2.view.FindCarFragment;
import fr.cocoteam.co2co2.view.MapFragment;
import fr.cocoteam.co2co2.view.NewUserFragment;
import fr.cocoteam.co2co2.view.ProfilFragment;
import fr.cocoteam.co2co2.view.SettingFragment;
import fr.cocoteam.co2co2.view.SplashScreenFragment;
import fr.cocoteam.co2co2.view.UserMatchDescriptionFragment;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.google.android.material.bottomnavigation.BottomNavigationView.*;



public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener, ConnectionFragment.OnHeadlineSelectedListener, SplashScreenFragment.OnHeadlineSelectedListener, MatchUserRecyclerViewAdapter.OnHeadlineSelectedListener, NewUserFragment.OnHeadlineSelectedListener,SettingFragment.OnHeadlineSelectedListener,ProfilFragment.OnHeadlineSelectedListener {




    public BottomNavigationView navigation;
    public Fragment mapFragment;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        setRealm();

        if(savedInstanceState!=null){
            mapFragment = getSupportFragmentManager().getFragment(savedInstanceState, "mapFragment");

        }
        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);


        //updateMenuVisibility(false);

       ConnectionFragment connectionFragment = new ConnectionFragment();
        connectionFragment.setOnHeadlineSelectedListener(this);
       loadFragment(connectionFragment, R.id.startContainer);
        mapFragment = new MapFragment();
    }

    private void setRealm() {
        RealmConfiguration configuration = new RealmConfiguration.Builder()
                .schemaVersion(1)
                .name(getResources().getString(R.string.app_name)+".realm")
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(configuration);

    }

    public void updateMenuVisibility(boolean visibility) {
        MenuItem map = navigation.getMenu().findItem(R.id.nav_map);
        MenuItem contact = navigation.getMenu().findItem(R.id.nav_contact);
        MenuItem car = navigation.getMenu().findItem(R.id.nav_find_car);
        MenuItem contract = navigation.getMenu().findItem(R.id.nav_contract);
        MenuItem profil = navigation.getMenu().findItem(R.id.nav_profil);
        MenuItem menuItems[] = {map,
                contact,
                car,
                contract,
                profil};

        for (MenuItem item : menuItems) {
            item.setVisible(visibility);
        }
    }

    private boolean loadFragment(Fragment fragment, int container) {
        if (fragment != null) {

            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(container, fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.nav_map:
                fragment = mapFragment;
                break;

            case R.id.nav_contact:
                fragment = new ContactFragment();
                break;

            case R.id.nav_find_car:
                fragment = new FindCarFragment();
                break;

            case R.id.nav_contract:
                fragment = new ContractFragment();
                break;

            case R.id.nav_profil:
                ProfilFragment profilFragment = new ProfilFragment();
                profilFragment.setOnHeadlineSelectedListener(this);
                fragment = profilFragment;
                break;
        }

        return loadFragment(fragment, R.id.fragment_container);
    }

    private void toast(String texte) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, texte, duration);
        toast.show();

    }

    @Override
    public void onUserConnected(String username) {
        SplashScreenFragment splashScreenFragment = new SplashScreenFragment();
        splashScreenFragment.setOnHeadlineSelectedListener(this);
        loadFragment( splashScreenFragment, R.id.fragment_container);
        toast("Welcome " + username);
    }

    @Override
    public boolean onProfilOptionSelected(String mclass) {

        Fragment fragment = null;
        switch (mclass) {
            case "setting":
                SettingFragment frg = new SettingFragment();
                frg.setOnHeadlineSelectedListener(this);
                fragment = frg;
                break;
            case "logout":
                fragment = new ConnectionFragment();
                break;
        }
        return loadFragment(fragment, R.id.fragment_container);
    }

    @Override
    public boolean onSettingOptionSelected(String nclass) {

        Fragment fragment = null;
        switch (nclass) {
            case "back":
                ProfilFragment frg = new ProfilFragment();
                frg.setOnHeadlineSelectedListener(this);
                fragment = frg;
                break;
            case "valider":
                ProfilFragment frg2 = new ProfilFragment();
                frg2.setOnHeadlineSelectedListener(this);
                fragment = frg2;
                break;
        }
        return loadFragment(fragment, R.id.fragment_container);
    }



    @Override
    public void onDataLoaded() {
        updateMenuVisibility(true);
        loadFragment(mapFragment, R.id.fragment_container);
    }

    @Override
    public void openNewUserFragment() {
        NewUserFragment newUserFragment = new NewUserFragment();
        newUserFragment.setOnHeadlineSelectedListener(this);
        loadFragment(newUserFragment, R.id.fragment_container);

    }

    @Override
    public void openSplashScreen() {
        SplashScreenFragment splashScreenFragment = new SplashScreenFragment();
        splashScreenFragment.setOnHeadlineSelectedListener(this);
        loadFragment( splashScreenFragment, R.id.fragment_container);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onItemClicked(UserMatch userMatch) {
        loadFragment(new UserMatchDescriptionFragment(), R.id.fragment_container);
    }
}


