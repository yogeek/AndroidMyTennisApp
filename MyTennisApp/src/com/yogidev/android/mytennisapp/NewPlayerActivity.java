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

		//r�cup�ration de l'EditText gr�ce � son ID
		editTextPrenom = (EditText) findViewById(R.id.EditTextPrenom);
		editTextNom = (EditText) findViewById(R.id.EditTextNom);

		// liste d�roulante pour les classements
		spinnerClassement = (Spinner) findViewById(R.id.SpinnerClassement);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (String classementString : Constants.LISTE_CLASSEMENTS) {
			adapter.add(TennisCalculRules.getClassementAsStringAffichable(TennisCalculRules.getClassementAsNumber(classementString)));
		}
		spinnerClassement.setAdapter(adapter);

		// liste d�roulante pour les ann�es de naissance
		spinnerNaissance = (Spinner) findViewById(R.id.SpinnerNaissance);
		ArrayAdapter<String> adapterNaissance = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapterNaissance.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (int i = 1920; i < Calendar.getInstance().get(Calendar.YEAR); i++ ) {
			adapterNaissance.add(String.valueOf(i));
		}
		spinnerNaissance.setAdapter(adapterNaissance);
		CommonTools.setItemSelectedOnSpinnerClassement(spinnerNaissance, "1983");

		//r�cup�ration du bouton Valider gr�ce � son ID
		buttonValider = (Button) findViewById(R.id.ButtonValider);

		//on applique un �couteur d'�venement au clic sur le bouton Valider
		buttonValider.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							//on r�up�re le texte �crit dans l'EditText
							prenom = editTextPrenom.getText().toString();
							nom = editTextNom.getText().toString();
							if (prenom.isEmpty() && nom.isEmpty()) {
								throw new MandatoryParameterException("Vous devez saisir au moins un nom OU un pr�nom.");
							}
							classement = (String)spinnerClassement.getSelectedItem();
							anneeNaissance = (String)spinnerNaissance.getSelectedItem();

							Player p = new Player(prenom.trim(), nom.trim(), classement.replace('/', '_'));
							p.setAnneeNaissance(Integer.parseInt(anneeNaissance));
							//p = CommonTools.testPlayer();
							
							// On v�rifie si le joueur n'existe pas d�j�
							for (int i = 0; i < fileList().length; i++) {
								String playerFile = fileList()[i].replaceFirst("-", " ").replaceFirst(Constants.SERIAL_FILE, "");
								if (playerFile.equals(p.getNomComplet()))
									throw new PlayerAlreadyExistException("Attention le joueur " + p.getNomComplet() + " existe d�j� !");
							}
														
							SerialTool.savePlayer(p, getApplicationContext());

							//on affiche "Hello votrePrenom ! " dans une petite pop-up qui s'affiche quelques seconde en bas d'�cran
							Toast.makeText(NewPlayerActivity.this, "Welcome " + prenom + " !", Toast.LENGTH_LONG).show();

							/* on affiche "Hello votrePrenom !" dans un textView que l'on a cr�� tout � l'heure
							 * et dont on avait pas pr�cis� la valeur de son texte il s'agit du dernier TextView dans le fichier main.xml
							 * De toute fa�on gr�ce � l'ID vous devrez facilement le trouver dans le fichier main.xml
							 */
							((TextView)findViewById(R.id.TextViewConfirm)).setText("Le joueur " + p.getNomComplet() + " a bien �t� cr�e !");

							// Sauvegarde du joueur dans l'extra de l'intent appelante
							getIntent().putExtra("currentPlayerName", p.getNomComplet());

							// On retourne le r�sultat � l'activity appelante
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

		//r�cup�ration du bouton Annuler gr�ce � son ID
		buttonAnnuler = (Button) findViewById(R.id.ButtonAnnuler);

		//on applique un �couteur d'�venement au clic sur le bouton Annuler
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