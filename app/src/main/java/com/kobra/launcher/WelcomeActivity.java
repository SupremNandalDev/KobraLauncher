package com.kobra.launcher;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.kobra.launcher.callbacks.WelcomeCallbacks;

public class WelcomeActivity extends AppCompatActivity implements WelcomeCallbacks {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Toolbar toolbar = findViewById(R.id.welcomeToolbar);
        setSupportActionBar(toolbar);

        navController = Navigation.findNavController(this, R.id.welcomeFragmentContainer);
        NavigationUI.setupWithNavController(toolbar, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.welcome_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return NavigationUI.onNavDestinationSelected(item, navController)
                || super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}