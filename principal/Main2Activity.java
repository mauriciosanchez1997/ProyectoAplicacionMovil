package com.example.webservicesimagealdo.principal;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.example.webservicesimagealdo.R;
import com.example.webservicesimagealdo.fragments.BienvenidaFragment;
import com.example.webservicesimagealdo.interfaces.IFragments;
import com.example.webservicesimagealdo.fragments.RegistrarPractica;
import com.example.webservicesimagealdo.fragments.ConsultarPracticaUrlUsuarioEliminarEditar;
import com.example.webservicesimagealdo.fragments.FragementEstradisticasBarras;
public class Main2Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        IFragments {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();


        Fragment miFragment=new BienvenidaFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main2,miFragment).commit();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view2);
        navigationView.setNavigationItemSelectedListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }
    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        Fragment miFragment=null;
        boolean fragmentSeleccionado=false;
        if(id==R.id.nav_home)
        {
            miFragment=new BienvenidaFragment();
            fragmentSeleccionado=true;
        }
            if (id == R.id.nav_gallery) {
                miFragment=new RegistrarPractica();
                fragmentSeleccionado=true;
            }
        if (id == R.id.nav_slideshow) {
            miFragment=new ConsultarPracticaUrlUsuarioEliminarEditar();
            fragmentSeleccionado=true;
        }
        if (id == R.id.nav_slideshow) {
            miFragment=new ConsultarPracticaUrlUsuarioEliminarEditar();
            fragmentSeleccionado=true;
        }

            if (fragmentSeleccionado==true){
                getSupportFragmentManager().beginTransaction().replace(R.id.content_main2,miFragment).commit();
            }



            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout2);
            drawer.closeDrawer(GravityCompat.START);

        return true;
    }

}
