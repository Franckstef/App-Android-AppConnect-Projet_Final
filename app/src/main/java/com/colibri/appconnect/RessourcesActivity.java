package com.colibri.appconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import java.util.ArrayList;
import java.util.Objects;

public class RessourcesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ressources);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Ressources");

        GridView menu = findViewById(R.id.gridRessources);

        ArrayList<com.colibri.appconnect.MenuItem> list = new ArrayList<>();
        list.add(new com.colibri.appconnect.MenuItem("Santé et sécurité", R.drawable.sante_securite));
        list.add(new com.colibri.appconnect.MenuItem("Politique d'uniformes", R.drawable.uniforme));
        list.add(new com.colibri.appconnect.MenuItem("Politique d'accident", R.drawable.accident));
        list.add(new com.colibri.appconnect.MenuItem("Code de conduite", R.drawable.conduite));
        list.add(new com.colibri.appconnect.MenuItem("Politique d'égalité", R.drawable.egalite));
        list.add(new com.colibri.appconnect.MenuItem("Politique de confidientalité", R.drawable.confidentialite));

        RessourcesAdapter adapter = new RessourcesAdapter(RessourcesActivity.this, list);
        menu.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}