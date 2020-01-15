package fr.cocoteam.co2co2;

import android.app.Activity;

import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;


import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;

import fr.cocoteam.co2co2.view.ConnectionFragment;
import fr.cocoteam.co2co2.view.ContactFragment;
import fr.cocoteam.co2co2.view.FindCarFragment;
import fr.cocoteam.co2co2.view.MapFragment;
import fr.cocoteam.co2co2.view.PaymentFragment;
import fr.cocoteam.co2co2.view.PaymentaddcardFragment;
import fr.cocoteam.co2co2.view.ProfilFragment;
import fr.cocoteam.co2co2.view.SettingFragment;
import fr.cocoteam.co2co2.view.SplashScreenFragment;
import io.realm.Realm;
import io.realm.RealmConfiguration;

import static com.google.android.material.bottomnavigation.BottomNavigationView.*;


public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener, ConnectionFragment.OnHeadlineSelectedListener,
        ProfilFragment.OnHeadlineSelectedListener ,SettingFragment.OnHeadlineSelectedListener ,PaymentFragment.OnHeadlineSelectedListener,
        PaymentaddcardFragment.OnHeadlineSelectedListener, SplashScreenFragment.OnHeadlineSelectedListener{



    public BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Realm.init(this);
        setRealm();

        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        //updateMenuVisibility(false);

        ConnectionFragment connectionFragment = new ConnectionFragment();
        connectionFragment.setOnHeadlineSelectedListener(this);
        //loadFragment(connectionFragment, R.id.startContainer);

        SplashScreenFragment splashScreenFragment = new SplashScreenFragment();
        splashScreenFragment.setOnHeadlineSelectedListener(this);
        loadFragment(splashScreenFragment, R.id.fragment_container);

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
                fragment = new MapFragment();
                break;

            case R.id.nav_contact:
                fragment = new ContactFragment();
                break;

            case R.id.nav_find_car:
                fragment = new FindCarFragment();
                break;

            case R.id.nav_contract:
                fragment = new ContactFragment();
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
        loadFragment(new SplashScreenFragment(), R.id.fragment_container);
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
            case "payment":
                PaymentFragment frg1 = new PaymentFragment();
                frg1.setOnHeadlineSelectedListener(this);
                fragment = frg1;
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
        }
        return loadFragment(fragment, R.id.fragment_container);
    }

    @Override
    public boolean onPaymentOptionSelected(String lclass) {

        Fragment fragment = null;
        switch (lclass) {
            case "back":
                ProfilFragment frg3 = new ProfilFragment();
                frg3.setOnHeadlineSelectedListener(this);
                fragment = frg3;
                break;
            case "addcardpage":
                PaymentaddcardFragment frg = new PaymentaddcardFragment();
                frg.setOnHeadlineSelectedListener(this);
                fragment = frg;
                break;

        }
        return loadFragment(fragment, R.id.fragment_container);
    }

    @Override
    public boolean onPaymentaddcardOptionSelected(String gclass) {

        Fragment fragment = null;
        switch (gclass) {
            case "back":
                PaymentFragment frg = new PaymentFragment();
                frg.setOnHeadlineSelectedListener(this);
                fragment = frg;
                break;
            case "valideraddcard":
                ProfilFragment frg1 = new ProfilFragment();
                frg1.setOnHeadlineSelectedListener(this);
                fragment = frg1;;
                break;


        }
        return loadFragment(fragment, R.id.fragment_container);
    }


    @Override
    public void onDataLoaded() {
        updateMenuVisibility(true);
        loadFragment(new MapFragment(), R.id.fragment_container);
    }
}


