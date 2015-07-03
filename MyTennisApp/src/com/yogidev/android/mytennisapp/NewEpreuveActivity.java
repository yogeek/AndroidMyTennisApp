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

		//r�cup�ration de l'EditText gr�ce � son ID
		editTextNomEpreuve = (EditText) findViewById(R.id.EditTextNomEpreuve);

		// liste d�roulante pour les types d'�preuve
		spinnerTypeEpreuve = (Spinner) findViewById(R.id.SpinnerTypeEpreuve);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (String typeString : Constants.LISTE_TYPES_EPREUVE) {
			adapter.add(typeString);
		}
		spinnerTypeEpreuve.setAdapter(adapter);

		//r�cup�ration du bouton Valider gr�ce � son ID
		buttonValider = (Button) findViewById(R.id.ButtonEpreuveValider);

		//on applique un �couteur d'�venement au clic sur le bouton Valider
		buttonValider.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							//on r�up�re le texte �crit dans l'EditText
							nomEpreuve = editTextNomEpreuve.getText().toString();
							if (nomEpreuve.isEmpty()) {
								throw new MandatoryParameterException("Vous devez saisir un nom d'�preuve.");
							}
							typeEpreuve = (String)spinnerTypeEpreuve.getSelectedItem();

							Epreuve epreuve = new Epreuve(nomEpreuve.trim(), typeEpreuve);
							player.addEpreuve(epreuve);
							SerialTool.savePlayer(player, getApplicationContext());

							/* on affiche "Hello votrePrenom !" dans un textView que l'on a cr�� tout � l'heure
							 * et dont on avait pas pr�cis� la valeur de son texte il s'agit du dernier TextView dans le fichier main.xml
							 * De toute fa�on gr�ce � l'ID vous devrez facilement le trouver dans le fichier main.xml
							 */
							((TextView)findViewById(R.id.TextViewConfirm)).setText("L'�preuve " + nomEpreuve + " a bien �t� cr��e !");
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

		//r�cup�ration du bouton Annuler gr�ce � son ID
		buttonAnnuler = (Button) findViewById(R.id.ButtonEpreuveAnnuler);

		//on applique un �couteur d'�venement au clic sur le bouton Annuler
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