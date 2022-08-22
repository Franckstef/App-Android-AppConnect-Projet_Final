package com.colibri.appconnect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Objects;

public class HistoireActivity extends AppCompatActivity {

    private TextView titre, sousTitre1, sousTitre2, text1, text2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_histoire);

        Toolbar toolbar = findViewById(R.id.toolbarHistoire);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("Histoire");
        toolbar.setTitleTextAppearance(this, R.style.toolbar);

        titre = findViewById(R.id.text1);
        sousTitre1 = findViewById(R.id.text2);
        sousTitre2 = findViewById(R.id.text4);
        text1 = findViewById(R.id.text3);
        text2 = findViewById(R.id.text5);

        titre.setText("Colibris Féroce");
        sousTitre1.setText("NOTRE MISSION :");
        text1.setText("Développer une famille d’individus talentueux à l’intérieur d’une culture de réussite, de sécurité " +
                "et de qualité, afin de satisfaire nos clients dans la réalisation de projets de construction et " +
                "d’entretien électriques et mécaniques.\n");
        sousTitre2.setText("NOS VALEURS :");
        text2.setText("Innovation: On grandit et on s’améliore continuellement.\n\n" +
                "Bienveillance: On veille les uns sur les autres.\n\n" +
                "Agilité: On ajuste nos façons de faire pour améliorer la qualité du travail.\n\n" +
                "Colibris Féroce offre des services efficaces de fabrication, de construction, d’entretien et d’arrêt\n" +
                "pour les secteurs industriels et commerciaux.\n" +
                "Nous possédons une vaste expérience dans une variété d’industries, y compris le pétrole et le\n" +
                "gaz, les mines et la métallurgie, la pétrochimie et les produits chimiques, la production\n" +
                "d’énergie, ainsi que dans les secteurs commerciaux et institutionnels.\n\n" +
                "Nous offrons des services multidisciplinaires, y compris la fabrication hors site, le travail " +
                "pluridisciplinaire sur place (mécanique, tuyauterie, électricité, instrumentation), l’ingénierie, " +
                "l’approvisionnement, la gestion de projets, la mise en service, ainsi que des services-conseils " +
                "dans chacun des secteurs mentionnés.\n\n" +
                "Nous sommes engagés à atteindre les normes les plus élevées en matière de santé, de " +
                "sécurité, de protection de l’environnement et de qualité.\n\n" +
                "Nous sommes un employeur soucieux de ses employés. Grâce à ses origines d’entreprise " +
                "familiale, Colibris Féroce perpétue la tradition de traiter ses employés comme des membres de " +
                "la famille.\n\n" +
                "Choisissez Colibris Féroce pour l’agilité et la performance à échelle humaine.\n" +
                "Nous offrons des services intégrés et efficaces de construction industrielle et commerciale. " +
                "Forts de nos connaissances approfondies, de notre vaste expérience et de notre succès " +
                "éprouvé dans de nombreux marchés partout au Canada et à l’étranger, nous bâtissons des " +
                "partenariats durables avec nos clients.");

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}