package com.yogidev.android.mytennisapp;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemLongClickListener;

public class PalmaresActivity extends ListActivity {

	protected static final int CODE_DE_MON_ACTIVITE = 2;
	
	private Player player;
	
	private ListView palmaresListView;
	private ArrayList<Rencontre> listRencontres;
	private RencontreAdapter rencontreAdapter;
	private Button buttonSimuler;
	private Button buttonAnnuler;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//on lui associe le layout mainpalmares.xml
		setContentView(R.layout.mainpalmares);
		
		//On r�cup�re l'objet Bundle envoy� par l'autre Activity
        final Bundle objetbunble  = getIntent().getExtras();
		
        // R�cup�ration du joueur courant
        try {
        	player = SerialTool.restorePlayer(objetbunble.getString("currentPlayerName"), getApplicationContext());
        } catch (Exception e) {
        	setResult(10);
			finish();
			return;
		}
		
		if (player != null) {
			
			// R�cup�ration de la listview cr��e dans le fichier mainp.xml
			palmaresListView = getListView();
			
			// Liste des rencontres
			listRencontres = new ArrayList<Rencontre>(player.getRencontres());
			
			this.rencontreAdapter = new RencontreAdapter(this, R.layout.affichageitempalmares, listRencontres);
            setListAdapter(this.rencontreAdapter);
			
			// On autorise le "clic long" sur les �l�ments de la liste
			palmaresListView.setLongClickable(true);
			// On applique un listener au clic long sur un �l�ment de la liste
			palmaresListView.setOnItemLongClickListener(new OnItemLongClickListener() {
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
					ListView listView = (ListView) parent;
	                ListAdapter listAdapter = listView.getAdapter();
	                Object obj = listAdapter.getItem(position);
	                if (obj != null && obj instanceof Rencontre) {
	                	// TODO : Delete menu
	                	System.out.println(((Rencontre)obj).getAdversaire());
	                }
	                return false;
				}
			});
			
			//r�cup�ration du bouton Valider gr�ce � son ID
			buttonSimuler = (Button) findViewById(R.id.ButtonPalmaresSimulation);

			//on applique un �couteur d'�venement au clic sur le bouton Valider
			buttonSimuler.setOnClickListener(
					new OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent intentSimulation = new Intent(PalmaresActivity.this, SimulationActivity.class);
							intentSimulation.putExtras(getIntent().getExtras());
							startActivityForResult(intentSimulation, CODE_DE_MON_ACTIVITE);
						}
					}
			);
			
			//r�cup�ration du bouton Annuler gr�ce � son ID
			buttonAnnuler = (Button) findViewById(R.id.ButtonPalmaresAnnuler);

			//on applique un �couteur d'�venement au clic sur le bouton Annuler
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