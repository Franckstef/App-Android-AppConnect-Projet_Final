package com.colibri.appconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Objects;

public class RessourcesActivity extends AppCompatActivity {

    TextView tel1, tel2, tel3, email1, email2, email3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ressources);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Ressources");
        toolbar.setTitleTextAppearance(this, R.style.toolbar);

        tel1 = findViewById(R.id.textTel);
        tel2 = findViewById(R.id.textTel1);
        tel3 = findViewById(R.id.textTel2);
        email1 = findViewById(R.id.textEmail);
        email2 = findViewById(R.id.textEmail1);
        email3 = findViewById(R.id.textEmail2);

        tel1.setOnClickListener(onClickListener);
        tel2.setOnClickListener(onClickListener);
        tel3.setOnClickListener(onClickListener);
        email1.setOnClickListener(onClickListener);
        email2.setOnClickListener(onClickListener);
        email3.setOnClickListener(onClickListener);

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

    private final View.OnClickListener onClickListener = v -> {
        switch(v.getId()){
            case R.id.textTel:
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ "5148956500"));
                startActivity(intent);
                break;
            case R.id.textTel1:
                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ "5145896233"));
                startActivity(intent2);
                break;
            case R.id.textTel2:
                Intent intent3 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+ "5142471288"));
                startActivity(intent3);
                break;
            case R.id.textEmail:
                Intent intent4 = new Intent(Intent.ACTION_SENDTO);
                intent4.setData(Uri.parse("mailto:colibrisferoce@gmail.com"));
                startActivity(Intent.createChooser(intent4, "Send feedback"));
                break;
            case R.id.textEmail1:
                Intent intent5 = new Intent(Intent.ACTION_SENDTO);
                intent5.setData(Uri.parse("mailto:marielatulippe@gmail.com"));
                startActivity(Intent.createChooser(intent5, "Send feedback"));
                break;
            case R.id.textEmail2:
                Intent intent6 = new Intent(Intent.ACTION_SENDTO);
                intent6.setData(Uri.parse("mailto:bobleforgeron@gmail.com"));
                startActivity(Intent.createChooser(intent6, "Send feedback"));
                break;

        }

    };

}