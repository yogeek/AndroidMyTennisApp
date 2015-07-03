package com.yogidev.android.mytennisapp;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.yogidev.android.mytennisapp.exceptions.MandatoryParameterException;
import com.yogidev.android.mytennisapp.exceptions.UnknownSerieException;

public class ModifyPlayerActivity extends Activity {

	private Player player;
	private EditText editTextPrenom;
	private EditText editTextNom;
	private Spinner spinnerClassement;
	private Spinner spinnerNaissance;
	private Button buttonModifier;
	private Button buttonAnnuler;
	private String prenom;
	private String nom;
	private String classement;
	private String anneeNaissance;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//on lui associe le layout affichagecreateplayer.xml
		setContentView(R.layout.affichagemodifyplayer);
		
		//On récupère l'objet Bundle envoyé par l'autre Activity
		final Bundle objetbunble  = getIntent().getExtras();
		
		// Récupération du joueur courant
		try {
			player = SerialTool.restorePlayer(objetbunble.getString("currentPlayerName"), getApplicationContext());
		} catch (Exception e) {
			setResult(10);
			finish();
			return;
		}

		//récupération de l'EditText grâce à son ID
		editTextPrenom = (EditText) findViewById(R.id.EditTextPrenom);
		editTextPrenom.setText(player.getPrenom());
		editTextNom = (EditText) findViewById(R.id.EditTextNom);
		editTextNom.setText(player.getNom());

		// liste déroulante pour les classements
		spinnerClassement = (Spinner) findViewById(R.id.SpinnerClassement);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (String classementString : Constants.LISTE_CLASSEMENTS) {
			adapter.add(TennisCalculRules.getClassementAsStringAffichable(TennisCalculRules.getClassementAsNumber(classementString)));
		}
		spinnerClassement.setAdapter(adapter);
		CommonTools.setItemSelectedOnSpinnerClassement(spinnerClassement, TennisCalculRules.getClassementAsStringAffichable(player.getClassement()));

		// liste déroulante pour les années de naissance
		spinnerNaissance = (Spinner) findViewById(R.id.SpinnerNaissance);
		ArrayAdapter<String> adapterNaissance = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapterNaissance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (int i = 1900; i < Calendar.getInstance().get(Calendar.YEAR); i++ ) {
			adapterNaissance.add(String.valueOf(i));
		}
		spinnerNaissance.setAdapter(adapterNaissance);
		CommonTools.setItemSelectedOnSpinnerClassement(spinnerNaissance, String.valueOf(player.getAnneeNaissance()));

		//récupération du bouton Valider grâce à son ID
		buttonModifier = (Button) findViewById(R.id.ButtonModifier);

		//on applique un écouteur d'évenement au clic sur le bouton Valider
		buttonModifier.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							//on réupère le texte écrit dans l'EditText
							prenom = editTextPrenom.getText().toString();
							nom = editTextNom.getText().toString();
							if (prenom.isEmpty() && nom.isEmpty()) {
								throw new MandatoryParameterException("Vous devez saisir au moins un nom OU un prénom.");
							}
							classement = (String)spinnerClassement.getSelectedItem();
							anneeNaissance = (String)spinnerNaissance.getSelectedItem();

							Player p = new Player(prenom.trim(), nom.trim(), classement.replace('/', '_'));
							p.setAnneeNaissance(Integer.parseInt(anneeNaissance));
							SerialTool.savePlayer(p, getApplicationContext());

							//on affiche "Hello votrePrenom ! " dans une petite pop-up qui s'affiche quelques seconde en bas d'écran
							Toast.makeText(ModifyPlayerActivity.this, "Welcome " + prenom + " !", Toast.LENGTH_LONG).show();

							/* on affiche "Hello votrePrenom !" dans un textView que l'on a créé tout à l'heure
							 * et dont on avait pas précisé la valeur de son texte il s'agit du dernier TextView dans le fichier main.xml
							 * De toute façon grâce à l'ID vous devrez facilement le trouver dans le fichier main.xml
							 */
							((TextView)findViewById(R.id.TextViewConfirm)).setText("Le joueur " + p.getNomComplet() + " a bien été crée !");

							// Sauvegarde du joueur dans l'extra de l'intent appelante
							getIntent().putExtra("currentPlayerName", p.getNomComplet());

							// On retourne le résultat à l'activity appelante
							setResult(1, getIntent());
							finish();
						} catch (MandatoryParameterException e) {
							Toast.makeText(ModifyPlayerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
						} catch (UnknownSerieException e) {
							e.printStackTrace();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
		);

		//récupération du bouton Annuler grâce à son ID
		buttonAnnuler = (Button) findViewById(R.id.ButtonAnnuler);

		//on applique un écouteur d'évenement au clic sur le bouton Annuler
		buttonAnnuler.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						setResult(2);
						finish();
					}
				}
		);

	}

}