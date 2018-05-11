package com.example.laure.applijson;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ImageDesPersos extends AppCompatActivity {

    private Integer[] imgPersoIds = {
            R.drawable.hinata, R.drawable.jiraya,
            R.drawable.kakashi, R.drawable.killerbee,
            R.drawable.madara, R.drawable.naruto,
            R.drawable.obito, R.drawable.orochimaru,
            R.drawable.pain, R.drawable.sakura,
            R.drawable.sasuke, R.drawable.tsunade,
    };
    ImageView imgPerso;
    TextView nomTV;
    TextView topoTV;
    ArrayList<String> lesNoms = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_des_persos);
        GestionBD sgbd = new GestionBD(this);
        sgbd.open();
        lesNoms=sgbd.donneLesNoms();
        Intent intent = getIntent();
        int laPosition = intent.getIntExtra("leChoix",0);
        Toast.makeText(this, " le choix : " + laPosition , Toast.LENGTH_LONG).show();
        Log.i("dans activ3","la position : "+laPosition);
        String persoChoisi =lesNoms.get(laPosition);
        Log.i("dans activ3","le nom : "+persoChoisi);
        Personnage lechoix = sgbd.donneUnPerso(persoChoisi);
        imgPerso = (ImageView) findViewById(R.id.lePersonnage);
        imgPerso.setImageResource(imgPersoIds[laPosition]);
        //imgPerso.setImageResource(R.drawable.naruto);
        nomTV = (TextView) findViewById(R.id.nomTV);
        topoTV = (TextView) findViewById(R.id.topoTV);
        //nomTV.setText("un perso");
        //topoTV.setText("son topo");
        nomTV.setText(lechoix.getNom());
        topoTV.setText(lechoix.getTopo());
    }
}
