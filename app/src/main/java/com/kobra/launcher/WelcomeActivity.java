package com.kobra.launcher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.kobra.launcher.callbacks.WelcomeCallbacks;

public class WelcomeActivity extends AppCompatActivity implements WelcomeCallbacks {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Toolbar toolbar = findViewById(R.id.welcomeToolbar);
        setSupportActionBar(toolbar);

        NavController navController = Navigation.findNavController(this, R.id.welcomeFragmentContainer);
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}