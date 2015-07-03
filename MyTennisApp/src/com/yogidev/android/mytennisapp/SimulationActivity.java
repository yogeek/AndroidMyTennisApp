package com.yogidev.android.mytennisapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yogidev.android.mytennisapp.exceptions.UnknownSerieException;

public class SimulationActivity extends Activity {

	protected static final int CODE_DE_MON_ACTIVITE = 3;

	private Player player;
	
	private ListView victoiresListView;
	private Button buttonValider;
	private Button buttonAnnuler;
	private TextView classementCalcule;
	private TextView victoiresRetenues;
	

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//on lui associe le layout main.xml
		setContentView(R.layout.mainsimulation);

		//On récupère l'objet Bundle envoyé par l'autre Activity
		final Bundle objetbunble  = this.getIntent().getExtras();

        // Récupération du joueur courant
        try {
        	player = SerialTool.restorePlayer(objetbunble.getString("currentPlayerName"), getApplicationContext());
        } catch (Exception e) {
        	setResult(10);
			finish();
			return;
		}

		if (player != null) {
			
			classementCalcule = (TextView) findViewById(R.id.classementCalcule);
			try {
				classementCalcule.setText("Classement calculé : " + TennisCalculRules.getClassementCalcule(player), TextView.BufferType.NORMAL);
			} catch (UnknownSerieException e) {
				e.printStackTrace();
			}
			
			victoiresRetenues = (TextView) findViewById(R.id.victoiresRetenues);

			// S'il y a des victoires
			if (player.getNbVictoires() != 0) {
				
				// Affichage du nombre de victoires retenues
				int nbVictoiresRetenues = TennisCalculRules.getNbTotalVictoiresPrisesEnCompte(player);
				victoiresRetenues.setText("Victoires retenues (" + nbVictoiresRetenues + ")", TextView.BufferType.NORMAL);
			
				// Récupération de la listview créée dans le fichier main.xml
				victoiresListView = (ListView) findViewById(R.id.listviewvictoiresretenues);
	
				// Création de la ArrayList qui nous permettra de remplir la listView
				ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();
	
				// On déclare la HashMap qui contiendra les informations pour un item
				HashMap<String, String> map;
	
				for (Rencontre r : TennisCalculRules.getBestVictoiresPrisesEnCompte(player)) {
	
					map = new HashMap<String, String>();
					map.put("imgMatch", String.valueOf(R.drawable.victorytiny));
					map.put("nomAdversaire", r.getAdversaire().getNomComplet());
					map.put("pointsGagnes", String.valueOf(player.applyCoeff(r,player.getPointsVictoire(r)) + "pts" + (player.getBonusVictoire(r, false)==0?"":" (+" + Constants.BONUS_CHAMPIONNAT_INDIVIDUEL + " pts bonus)")));
					map.put("classementAdversaire", TennisCalculRules.getClassementAsStringAffichable(r.getAdversaire().getClassement()));
	
					listItem.add(map);
				}
	
				// Création d'un SimpleAdapter qui se chargera de mettre les items présent dans notre list (listItem) dans la vue affichageitem
				SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(),listItem, R.layout.affichageitemvictoiresretenues, 
						new String[] { "imgMatch", "nomAdversaire", "pointsGagnes", "classementAdversaire" }, new int[] { R.id.imgMatch, R.id.nomAdversaire, R.id.pointsGagnes, R.id.classementAdversaire });
	
				// On attribue à notre listView l'adapter que l'on vient de créer
				victoiresListView.setAdapter(mSchedule);
				
			}
			// Aucune victoire => Message de descente
			else {
				victoiresRetenues.setText("Aucune victoire (descente automatique d'un échelon)", TextView.BufferType.NORMAL);
				
			}

			//récupération du bouton Valider grâce à son ID
			buttonValider = (Button) findViewById(R.id.ButtonSimulationValider);

			//on applique un écouteur d'évenement au clic sur le bouton Valider
			buttonValider.setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							setResult(RESULT_OK);
							finish();
						}
					}
			);

			//récupération du bouton Annuler grâce à son ID
			buttonAnnuler = (Button) findViewById(R.id.ButtonSimulationAnnuler);

			//on applique un écouteur d'évenement au clic sur le bouton Annuler
			buttonAnnuler.setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							setResult(RESULT_CANCELED);
							finish();
						}
					}
			);

		}
		else {
			setResult(RESULT_CANCELED);
			finish();
		}

	}

}