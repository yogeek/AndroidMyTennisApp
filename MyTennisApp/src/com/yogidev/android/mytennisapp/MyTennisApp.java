package com.yogidev.android.mytennisapp;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class MyTennisApp extends Activity {

	private static final int CODE_DE_MON_ACTIVITE = 1;

	private static String mCurrentPlayerName;
	private Spinner spinnerProfil;
	private ListView maListViewPerso;
	Bundle objetbunble;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//SharedPreferences mPrefs = getSharedPreferences("mytennisapp.pref",MODE_PRIVATE);

		setContentView(R.layout.main);

		objetbunble = new Bundle();

		// R�cup�ration des joueurs d�j� cr�es


		// liste d�roulante pour les profils
		spinnerProfil = (Spinner) findViewById(R.id.SpinnerProfil);
		ArrayAdapter<String> adapterProfil = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapterProfil.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (int i = 0; i < fileList().length; i++) {
			String playerFile = fileList()[i].replaceFirst("-", " ").replaceFirst(Constants.SERIAL_FILE, "");
			adapterProfil.add(playerFile);
		}
		if (fileList().length==0) {
			adapterProfil.add(Constants.NO_PROFILE);
			spinnerProfil.setClickable(false);
		}
		spinnerProfil.setAdapter(adapterProfil);
		if (getmCurrentPlayerName() != null) {
			setItemSelectedOnSpinnerProfil(getmCurrentPlayerName());
		}

		spinnerProfil.setOnItemSelectedListener( 
				new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> parent, View arg1, int pos, long id) {
						String profil = parent.getItemAtPosition(pos).toString();
						if (!profil.equals(Constants.NO_PROFILE))
							objetbunble.putString("currentPlayerName", profil);
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
					}

				});

		/*
		 * ---------------------------------- LIST VIEW ------------------------------------
		 */

		// R�cup�ration de la listview cr��e dans le fichier main.xml
		maListViewPerso = (ListView) findViewById(R.id.listviewperso);

		// Cr�ation de la ArrayList qui nous permettra de remplir la listView
		ArrayList<HashMap<String, String>> listItem = new ArrayList<HashMap<String, String>>();

		// On d�clare la HashMap qui contiendra les informations pour un item
		HashMap<String, String> map;

		// Cr�ation d'une HashMap pour ins�rer les informations du premier item
		// de notre listView
		map = new HashMap<String, String>();
		// on ins�re un �l�ment titre que l'on r�cup�rera dans le textView titre
		// cr�� dans le fichier affichageitem.xml
		map.put("titre", "Gestion des profils");
		// on ins�re un �l�ment description que l'on r�cup�rera dans le textView
		// description cr�� dans le fichier affichageitem.xml
		map.put("description", "Consulter, cr�er ou supprimer des profils de joueurs");
		// on ins�re la r�f�rence � l'image (convertit en String car normalement
		// c'est un int) que l'on r�cup�rera dans l'imageView cr�� dans le
		// fichier affichageitem.xml
		map.put("img", String.valueOf(R.drawable.tennis1));
		// enfin on ajoute cette hashMap dans la arrayList
		listItem.add(map);

		// On refait la manip plusieurs fois avec des donn�es diff�rentes pour
		// former les items de notre ListView

		map = new HashMap<String, String>();
		map.put("titre", "Gestion des �preuves");
		map.put("description", "G�rer les �preuves du joueur courant");
		map.put("img", String.valueOf(R.drawable.tennis2));
		listItem.add(map);

		map = new HashMap<String, String>();
		map.put("titre", "Gestion des rencontres");
		map.put("description", "G�rer les rencontres du joueur courant");
		map.put("img", String.valueOf(R.drawable.match));
		listItem.add(map);

		map = new HashMap<String, String>();
		map.put("titre", "Palmar�s");
		map.put("description", "Voir le palmar�s du joueur courant");
		map.put("img", String.valueOf(R.drawable.tennis3));
		listItem.add(map);

		map = new HashMap<String, String>();
		map.put("titre", "R�gles de calcul");
		map.put("description", "Voir le d�tail du calcul du classement 2011");
		map.put("img", String.valueOf(R.drawable.tennis4));
		listItem.add(map);

		// Cr�ation d'un SimpleAdapter qui se chargera de mettre les items pr�sent dans notre list (listItem) dans la vue affichageitem
		SimpleAdapter mSchedule = new SimpleAdapter(this.getBaseContext(),listItem, R.layout.affichageitem, 
				new String[] { "img", "titre", "description" }, new int[] { R.id.img, R.id.titre, R.id.description });

		// On attribue � notre listView l'adapter que l'on vient de cr�er
		maListViewPerso.setAdapter(mSchedule);

		// Enfin on met un �couteur d'�v�nement sur notre listView
		maListViewPerso.setOnItemClickListener(new OnItemClickListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void onItemClick(AdapterView a, View v, int position, long id) {

				// on remplit le bundle pour que les autres activit�s connaissent le joueur courant
				if (objetbunble.getString("currentPlayerName") != null) {
					setmCurrentPlayerName(objetbunble.getString("currentPlayerName"));
				}

				// On cr�� l'Intent qui va nous permettre d'afficher l'autre Activity
				// Mettez le nom de l'Activity dans la quelle vous �tes actuellement
				Intent intentPlayer = new Intent(MyTennisApp.this, NewPlayerActivity.class);

				Intent intentEpreuve = new Intent(MyTennisApp.this, NewEpreuveActivity.class);
				Intent intentRencontre = new Intent(MyTennisApp.this, NewRencontreActivity.class);
				Intent intentPalmares = new Intent(MyTennisApp.this, PalmaresActivity.class);
				Intent intentDisplayCalculRules = new Intent(MyTennisApp.this, DisplayCalculRules.class);

				// On affecte � l'Intent le Bundle que l'on a cr��
				intentPlayer.putExtras(objetbunble);
				intentEpreuve.putExtras(objetbunble);
				intentRencontre.putExtras(objetbunble);
				intentPalmares.putExtras(objetbunble);

				// On d�marre l'autre Activity
				if (position == 0)
					startActivityForResult(intentPlayer, CODE_DE_MON_ACTIVITE);
				else if (position == 1)
					startActivityForResult(intentEpreuve, CODE_DE_MON_ACTIVITE);
				else if (position == 2)
					startActivityForResult(intentRencontre, CODE_DE_MON_ACTIVITE);
				else if (position == 3)
					startActivityForResult(intentPalmares, CODE_DE_MON_ACTIVITE);
				else if (position == 4)
					startActivityForResult(intentDisplayCalculRules, CODE_DE_MON_ACTIVITE);
			}
		});
	}

	@SuppressWarnings("unchecked")
	public void updateSpinnerProfil(String newProfil) {
		// On supprimer le profil NULL par d�faut
		if (spinnerProfil.getAdapter().getItem(0).equals(Constants.NO_PROFILE))
			((ArrayAdapter<String>)spinnerProfil.getAdapter()).remove(Constants.NO_PROFILE);
		// on ajoute le nouveau profil
		((ArrayAdapter<String>)spinnerProfil.getAdapter()).add(newProfil);
		if (spinnerProfil.getCount() > 1)
			spinnerProfil.setClickable(true);
		// on positionne la liste sur le profil venant d'�tre cr�e
		setItemSelectedOnSpinnerProfil(newProfil);
	}

	public void setItemSelectedOnSpinnerProfil(String itemValue) {
		for (int i = 0; i < spinnerProfil.getCount(); i++) {
			String value = spinnerProfil.getItemAtPosition(i).toString();
			if (value.equals(itemValue)) {
				spinnerProfil.setSelection(i);
			}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		//on regarde quelle Activity a r�pondu
		switch(requestCode){
		case CODE_DE_MON_ACTIVITE:

			//on cr�� une AlertDialog
			AlertDialog.Builder adbOK = new AlertDialog.Builder(this);
			adbOK.setTitle("Confirmation");
			adbOK.setPositiveButton("Ok", null);

			//on cr�� une AlertDialog
			AlertDialog.Builder adbCancel = new AlertDialog.Builder(this);
			adbCancel.setTitle("Annulation");
			adbCancel.setPositiveButton("Ok", null);

			//On regarde qu'elle est la r�ponse envoy�e et en fonction de la r�ponse on affiche un message diff�rent.
			switch(resultCode){
			case 1:
				adbOK.setMessage("Cr�ation de joueur effectu�e !");
				objetbunble = data.getExtras();
				if (objetbunble.getString("currentPlayerName") != null) {
					updateSpinnerProfil(objetbunble.getString("currentPlayerName"));
					setmCurrentPlayerName(objetbunble.getString("currentPlayerName"));
				}
				adbOK.show();
				return;
			case 2:
				adbCancel.setMessage("Cr�ation de joueur annul�e.");
				adbCancel.show();
				return;
			case 3:
				adbOK.setMessage("Cr�ation de l'�preuve effectu�e !");
				adbOK.show();
				return;
			case 4:
				adbCancel.setMessage("Cr�ation de l'�preuve annul�e.");
				adbCancel.show();
				return;
			case 5:
				adbOK.setMessage("Cr�ation de la rencontre effectu�e !");
				adbOK.show();
				return;
			case 6:
				adbCancel.setMessage("Cr�ation de la rencontre annul�e.");
				adbCancel.show();
				return;
			case 10:
				adbCancel.setMessage("Attention : aucun joueur cr�e !!!");
				adbCancel.show();
				return;
			}
		}
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// Save UI state changes to the savedInstanceState.
		// This bundle will be passed to onCreate if the process is killed and restarted.
		savedInstanceState.putString("currentPlayerName", mCurrentPlayerName);
		// etc.
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		// Restore UI state from the savedInstanceState.
		// This bundle has also been passed to onCreate.
		if (savedInstanceState == null) {
			Toast.makeText(this, "Welcome to MyTennisApp!", Toast.LENGTH_SHORT).show();
		} else {
			mCurrentPlayerName = savedInstanceState.getString("currentPlayerName");
			if (mCurrentPlayerName != null)
				Toast.makeText(this, "Welcome back " + mCurrentPlayerName + " !", Toast.LENGTH_SHORT).show();
		}
	}

	public static String getmCurrentPlayerName() {
		return mCurrentPlayerName;
	}

	public static void setmCurrentPlayerName(String mCurrentPlayerName) {
		MyTennisApp.mCurrentPlayerName = mCurrentPlayerName;
	}

}
