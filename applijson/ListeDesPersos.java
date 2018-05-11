package com.example.laure.applijson;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Laure on 10/12/2015.
 */
public class ListeDesPersos extends ListActivity implements AdapterView.OnItemClickListener {


    //private String[] lesValeurs = {"Naruto","Sakura","Sasuke","kakachi","obito","tsunade","hinata","madara"};
    ArrayList<String>  lesValeurs = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_activity);
        Intent intent = getIntent();
        /*TextView loginDisplay = (TextView) findViewById(R.id.email_display);
        TextView passwordDisplay = (TextView) findViewById(R.id.password_display);

        if (intent != null) {
            loginDisplay.setText(intent.getStringExtra(EXTRA_LOGIN));
            passwordDisplay.setText(intent.getStringExtra(EXTRA_PASSWORD));
        }*/
        GestionBD sgbd = new GestionBD(this);
        sgbd.open();

        lesValeurs = sgbd.donneLesNoms();
        Log.i("liste2","les noms : "+lesValeurs);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lesValeurs);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
        sgbd.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent image = new Intent(this, ImageDesPersos.class);
        Log.i("clic","après avoir cliqué : "+position);
        Toast.makeText(this, " position  : " + position , Toast.LENGTH_LONG).show();
        image.putExtra("leChoix", position);
        startActivity(image);
    }

}
