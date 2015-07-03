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
import com.yogidev.android.mytennisapp.exceptions.PlayerAlreadyExistException;
import com.yogidev.android.mytennisapp.exceptions.UnknownSerieException;

public class NewPlayerActivity extends Activity {

	private EditText editTextPrenom;
	private EditText editTextNom;
	private Spinner spinnerClassement;
	private Spinner spinnerNaissance;
	private Button buttonValider;
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
		setContentView(R.layout.affichagecreateplayer);

		//récupération de l'EditText grâce à son ID
		editTextPrenom = (EditText) findViewById(R.id.EditTextPrenom);
		editTextNom = (EditText) findViewById(R.id.EditTextNom);

		// liste déroulante pour les classements
		spinnerClassement = (Spinner) findViewById(R.id.SpinnerClassement);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (String classementString : Constants.LISTE_CLASSEMENTS) {
			adapter.add(TennisCalculRules.getClassementAsStringAffichable(TennisCalculRules.getClassementAsNumber(classementString)));
		}
		spinnerClassement.setAdapter(adapter);

		// liste déroulante pour les années de naissance
		spinnerNaissance = (Spinner) findViewById(R.id.SpinnerNaissance);
		ArrayAdapter<String> adapterNaissance = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapterNaissance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (int i = 1920; i < Calendar.getInstance().get(Calendar.YEAR); i++ ) {
			adapterNaissance.add(String.valueOf(i));
		}
		spinnerNaissance.setAdapter(adapterNaissance);
		CommonTools.setItemSelectedOnSpinnerClassement(spinnerNaissance, "1983");

		//récupération du bouton Valider grâce à son ID
		buttonValider = (Button) findViewById(R.id.ButtonValider);

		//on applique un écouteur d'évenement au clic sur le bouton Valider
		buttonValider.setOnClickListener(
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
							//p = CommonTools.testPlayer();
							
							// On vérifie si le joueur n'existe pas déjà
							for (int i = 0; i < fileList().length; i++) {
								String playerFile = fileList()[i].replaceFirst("-", " ").replaceFirst(Constants.SERIAL_FILE, "");
								if (playerFile.equals(p.getNomComplet()))
									throw new PlayerAlreadyExistException("Attention le joueur " + p.getNomComplet() + " existe déjà !");
							}
														
							SerialTool.savePlayer(p, getApplicationContext());

							//on affiche "Hello votrePrenom ! " dans une petite pop-up qui s'affiche quelques seconde en bas d'écran
							Toast.makeText(NewPlayerActivity.this, "Welcome " + prenom + " !", Toast.LENGTH_LONG).show();

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
						} catch (PlayerAlreadyExistException e) {
							Toast.makeText(NewPlayerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
						} catch (MandatoryParameterException e) {
							Toast.makeText(NewPlayerActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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