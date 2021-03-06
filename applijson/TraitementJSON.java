package com.example.laure.applijson;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by JCC on 06/12/2015.
 */
public class TraitementJSON extends AsyncTask<String, Void, Boolean>{

    private List<Personnage> lesPersonnages = new ArrayList<Personnage>();
    Context context;
    JSONObject jObj = null;
    URL url;
    HttpURLConnection connexion;
    GestionBD sgbd;


    public TraitementJSON(Context context) {
        this.context = context;
        sgbd = new GestionBD(context);
    }

    @Override
    protected Boolean doInBackground(String... urls) {

        Log.i("doInBack","le départ : ");
        sgbd.open();
        //sgbd.supprimepersos();
        try {
            url = new URL(urls[0]);
            Log.i("doInBack","le fichier distant : "+urls[0]);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        Boolean result = false;
        try {
            String ficPersonnages;
            //Récupération du contenu du fichier json sur serveur
            ficPersonnages = lectureFichierDistant();
            Log.i("doInBack","le fichier distant : "+ficPersonnages);
            //Transformation du fichier obtenu en objet JSON
            JSONObject jsonPersonnages = parsePersonnages(ficPersonnages);
            //Traitement de l'objet JSON pour obtenir des instances de Personnages
            Log.i("doInBack","le fichier json : "+jsonPersonnages.toString());
            //result = recPersonnages(jsonPersonnages);
            recPersonnages(jsonPersonnages);
            Log.i("doInback","nombre de persos : "+lesPersonnages.size());
            int num = 1;
            StringBuilder message = new StringBuilder("les persos : ");
            long id;
            for (Personnage perso : lesPersonnages) {
                message.append(perso.getNom());
                message.append("   :   ");
                message.append(perso.getTopo());
                message.append("\n");
                num++;
                id=sgbd.ajoutePerso(perso);
            }
            Log.i("doInback","message : "+message);
            sgbd.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void recPersonnages(JSONObject jsonPersonnages) {

            JSONArray lesPersos = null;
            try {

                lesPersos = jsonPersonnages.getJSONArray("lespersonnages");
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("recper","erreurJSArray");;
            }

            for(int i = 0; i < lesPersos.length(); i++){
                JSONObject nuplet = null;
                String nom, topo;
                Long id;
                Personnage perso;
                try {
                    nuplet = lesPersos.getJSONObject(i);
                   // Log.i("rec","nuplet"+nuplet.toString());
                    nom = nuplet.getString("nom");
                  //  Log.i("rec","nom : "+nom);
                    topo = nuplet.getString("topo");
                 //   Log.i("rec","topo : "+topo);
                    perso = new Personnage(nom,topo);
                    lesPersonnages.add(perso);
                    id=sgbd.ajoutePerso(perso);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
       // return null;
    }

    private JSONObject parsePersonnages(String textPersonnages) {
        if (textPersonnages != null) {
            try {
                jObj = new JSONObject(textPersonnages);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.i("parper","erreurJSObj");;
            }
            return jObj;
        }
        return null;
    }

    private String lectureFichierDistant() {
        StringBuilder builder = new StringBuilder();

        // adresse du serveur ( modification pour passage en production
        try {
            connexion = (HttpURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
                String line;
                BufferedReader br= null;
                try {
                    br = new BufferedReader(new InputStreamReader(connexion.getInputStream()));
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
        try {
            while ((line=br.readLine()) != null) {
                builder.append(line).append("\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
        }

        public List<Personnage> getLesPersonnages() {

            return lesPersonnages;
        }

    public String getLesNoms(){
        String liste = "";
        for (Personnage perso : lesPersonnages)
            liste += perso.getNom()+"\n";
        return liste;
    }
}
