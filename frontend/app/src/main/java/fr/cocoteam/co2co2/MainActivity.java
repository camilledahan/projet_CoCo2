package fr.cocoteam.co2co2;

import android.app.Activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import fr.cocoteam.co2co2.view.ContactFragment;
import fr.cocoteam.co2co2.view.FindCarFragment;
import fr.cocoteam.co2co2.view.MapFragment;
import fr.cocoteam.co2co2.view.ProfilFragment;

import static com.google.android.material.bottomnavigation.BottomNavigationView.*;

public class MainActivity extends AppCompatActivity implements OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);
    }

    private boolean loadFragment (Fragment fragment){
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container,fragment)
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

        return  loadFragment(fragment);
    }
}
