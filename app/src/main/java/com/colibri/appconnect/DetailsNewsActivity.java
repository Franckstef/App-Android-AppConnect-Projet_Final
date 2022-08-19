package com.colibri.appconnect;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

public class DetailsNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_news);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Actualités");
        toolbar.setTitleTextAppearance(this, R.style.toolbar);

        ImageView image = findViewById(R.id.imageView);
        TextView titre = findViewById(R.id.textTitre);
        TextView intro = findViewById(R.id.textIntro);
        TextView article = findViewById(R.id.textArticle);
        TextView date = findViewById(R.id.textDate);

        Bundle bundle = getIntent().getExtras();
        titre.setText(bundle.getString("titre"));
        intro.setText(bundle.getString("intro"));
        article.setText(bundle.getString("article"));
        date.setText(bundle.getString("date"));

        String uri = "@drawable/" + bundle.getString("image");
        int imageResource = getResources().getIdentifier(uri, null, getPackageName());
        image.setImageResource(imageResource);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}