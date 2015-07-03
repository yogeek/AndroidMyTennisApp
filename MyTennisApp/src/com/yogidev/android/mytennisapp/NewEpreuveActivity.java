package com.yogidev.android.mytennisapp;

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

public class NewEpreuveActivity extends Activity {

	private Player player;

	private EditText editTextNomEpreuve;
	private Spinner spinnerTypeEpreuve;
	private Button buttonValider;
	private Button buttonAnnuler;
	private String nomEpreuve;
	private String typeEpreuve;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//on lui associe le layout affichagecreateplayer.xml
		setContentView(R.layout.affichagecreateepreuve);

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
		editTextNomEpreuve = (EditText) findViewById(R.id.EditTextNomEpreuve);

		// liste déroulante pour les types d'épreuve
		spinnerTypeEpreuve = (Spinner) findViewById(R.id.SpinnerTypeEpreuve);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (String typeString : Constants.LISTE_TYPES_EPREUVE) {
			adapter.add(typeString);
		}
		spinnerTypeEpreuve.setAdapter(adapter);

		//récupération du bouton Valider grâce à son ID
		buttonValider = (Button) findViewById(R.id.ButtonEpreuveValider);

		//on applique un écouteur d'évenement au clic sur le bouton Valider
		buttonValider.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							//on réupère le texte écrit dans l'EditText
							nomEpreuve = editTextNomEpreuve.getText().toString();
							if (nomEpreuve.isEmpty()) {
								throw new MandatoryParameterException("Vous devez saisir un nom d'épreuve.");
							}
							typeEpreuve = (String)spinnerTypeEpreuve.getSelectedItem();

							Epreuve epreuve = new Epreuve(nomEpreuve.trim(), typeEpreuve);
							player.addEpreuve(epreuve);
							SerialTool.savePlayer(player, getApplicationContext());

							/* on affiche "Hello votrePrenom !" dans un textView que l'on a créé tout à l'heure
							 * et dont on avait pas précisé la valeur de son texte il s'agit du dernier TextView dans le fichier main.xml
							 * De toute façon grâce à l'ID vous devrez facilement le trouver dans le fichier main.xml
							 */
							((TextView)findViewById(R.id.TextViewConfirm)).setText("L'épreuve " + nomEpreuve + " a bien été créée !");
							setResult(3);
							finish();
						} catch (MandatoryParameterException e) {
							Toast.makeText(NewEpreuveActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
		);

		//récupération du bouton Annuler grâce à son ID
		buttonAnnuler = (Button) findViewById(R.id.ButtonEpreuveAnnuler);

		//on applique un écouteur d'évenement au clic sur le bouton Annuler
		buttonAnnuler.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						setResult(4);
						finish();
					}
				}
		);

	}

}