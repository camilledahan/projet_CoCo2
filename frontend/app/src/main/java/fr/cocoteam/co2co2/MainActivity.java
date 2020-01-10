package fr.cocoteam.co2co2;

import android.app.Activity;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import fr.cocoteam.co2co2.view.ConnectionFragment;
import fr.cocoteam.co2co2.view.ContactFragment;
import fr.cocoteam.co2co2.view.FindCarFragment;
import fr.cocoteam.co2co2.view.MapFragment;
import fr.cocoteam.co2co2.view.ProfilFragment;

import static com.google.android.material.bottomnavigation.BottomNavigationView.*;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener, ConnectionFragment.OnHeadlineSelectedListener {

    public BottomNavigationView navigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        updateMenuVisibility(false);

        ConnectionFragment connectionFragment = new ConnectionFragment();
        connectionFragment.setOnHeadlineSelectedListener(this);
        loadFragment(connectionFragment, R.id.startContainer);

        connectToAzureBUs();
    }

    private void connectToAzureBUs() {

    }

    public void updateMenuVisibility(boolean visibility) {
        MenuItem map = navigation.getMenu().findItem(R.id.nav_map);
        MenuItem contact =  navigation.getMenu().findItem(R.id.nav_contact);
        MenuItem car =  navigation.getMenu().findItem(R.id.nav_find_car);
        MenuItem contract =  navigation.getMenu().findItem(R.id.nav_contract);
        MenuItem profil =  navigation.getMenu().findItem(R.id.nav_profil);
        MenuItem menuItems[] = {map,
                contact,
                car,
                contract,
                profil};

        for (MenuItem item : menuItems){
            item.setVisible(visibility);
        }
    }

    private boolean loadFragment (Fragment fragment, int container){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(container,fragment)
                    .commit();

            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
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
                fragment = new ProfilFragment();
                break;
        }

        return  loadFragment(fragment, R.id.fragment_container);
    }

    private void toast(String texte){
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, texte, duration);
        toast.show();

    }


    @Override
    public void onUserConnected(String username) {
        updateMenuVisibility(true);
        loadFragment(new MapFragment(), R.id.fragment_container);
        toast("Welcome " + username);

    }


}
