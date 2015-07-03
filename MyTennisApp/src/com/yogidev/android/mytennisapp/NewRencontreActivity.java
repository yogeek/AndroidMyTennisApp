package com.yogidev.android.mytennisapp;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.yogidev.android.mytennisapp.exceptions.MandatoryParameterException;

public class NewRencontreActivity extends Activity {

	static final int DATE_DIALOG_ID = 0;
	private Player player;

	private EditText editTextNomAdversaire;
	private EditText editTextPrenomAdversaire;
	private ToggleButton victoireToggle;
	private EditText editTextDate;
	private Spinner spinnerClassement;
	private Spinner spinnerEpreuve;
	private Spinner spinnerFormat;
	private Button buttonValider;
	private Button buttonAnnuler;
	private String nomAdversaire;
	private String prenomAdversaire;
	private String classementAdversaire;
	private String nomEpreuve;
	private String format;
	private Date dateRencontre;
	private boolean isVictoire;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//on lui associe le layout affichagecreateplayer.xml
		setContentView(R.layout.affichagecreaterencontre);

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
		editTextNomAdversaire = (EditText) findViewById(R.id.EditTextNomAdversaire);
		editTextPrenomAdversaire = (EditText) findViewById(R.id.EditTextPrenomAdversaire);

		// liste déroulante pour les classements
		spinnerClassement = (Spinner) findViewById(R.id.SpinnerClassement);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (String classementString : Constants.LISTE_CLASSEMENTS) {
			adapter.add(TennisCalculRules.getClassementAsStringAffichable(TennisCalculRules.getClassementAsNumber(classementString)));
		}
		spinnerClassement.setAdapter(adapter);

		// liste déroulante pour les épreuves
		spinnerEpreuve = (Spinner) findViewById(R.id.SpinnerEpreuve);
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (Epreuve epreuve : player.getEpreuves()) {
			adapter2.add(epreuve.getNom());
		}
		spinnerEpreuve.setAdapter(adapter2);

		// liste déroulante pour les formats de rencontre
		spinnerFormat = (Spinner) findViewById(R.id.SpinnerFormat);
		if (player.getSerie() == Constants.QUATRIEME_SERIE) {
			ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
			adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			for (String format : Constants.LISTE_FORMATS_COURTS_RENCONTRE) {
				adapter3.add(format);
			}
			spinnerFormat.setAdapter(adapter3);
		}
		else {
			findViewById(R.id.TextViewFormatRencontre).setVisibility(View.INVISIBLE);
			spinnerFormat.setVisibility(View.INVISIBLE);
		}

		// toggle button : victoire ou défaite ?
		victoireToggle = (ToggleButton) findViewById(R.id.ToggleVictoire);
		victoireToggle.setChecked(true);
		isVictoire = true;
		victoireToggle.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Perform action on clicks
				if (victoireToggle.isChecked()) {
					isVictoire = true;
				} else {
					isVictoire = false;
				}
			}
		});

//		//récupération du bouton pour la date
//		buttonDate = (Button) findViewById(R.id.buttonDate);
//
//		buttonDate.setOnClickListener(
//				new OnClickListener() {
//					@Override
//					public void onClick(View v) {
//						showDialog(DATE_DIALOG_ID);
//					}
//				}
//		);
		
		editTextDate = (EditText) findViewById(R.id.EditTextDate);

		editTextDate.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						showDialog(DATE_DIALOG_ID);
					}
				}
		);

		//récupération du bouton Valider grâce à son ID
		buttonValider = (Button) findViewById(R.id.ButtonEpreuveValider);

		//on applique un écouteur d'évenement au clic sur le bouton Valider
		buttonValider.setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						try {
							//on réupère le texte écrit dans l'EditText
							nomAdversaire = editTextNomAdversaire.getText().toString();
							prenomAdversaire = editTextPrenomAdversaire.getText().toString();
							if (nomAdversaire.isEmpty() && prenomAdversaire.isEmpty()) {
								throw new MandatoryParameterException("Vous devez saisir au moins un nom OU un prénom.");
							}
							classementAdversaire = (String)spinnerClassement.getSelectedItem();
							nomEpreuve = (String)spinnerEpreuve.getSelectedItem();
							if (spinnerFormat.getVisibility()==View.VISIBLE) {
								format = (String)spinnerFormat.getSelectedItem();
							}

							Epreuve epreuve = null;
							for (Epreuve e : player.getEpreuves()) {
								if (e.getNom().equals(nomEpreuve))
									epreuve = e;
							}

							if (epreuve == null)
								epreuve = new Epreuve("Epreuve sans nom");

							Rencontre rencontre = new Rencontre(new Player(prenomAdversaire, nomAdversaire, classementAdversaire.replace('/', '_')),isVictoire,format);
							rencontre.setType(epreuve.getType());
							rencontre.setDate(dateRencontre);
							player.addRencontre(epreuve,rencontre);
							SerialTool.savePlayer(player, getApplicationContext());

							((TextView)findViewById(R.id.TextViewConfirm)).setText("La rencontre a bien été créée !");
							setResult(5);
							finish();
						} catch (MandatoryParameterException e) {
							Toast.makeText(NewRencontreActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
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
						setResult(6);
						finish();
					}
				}
		);

	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		// onDateSet method
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
			String date_selected = String.valueOf(monthOfYear+1)+" /"+String.valueOf(dayOfMonth)+" /"+String.valueOf(year);
			editTextDate = (EditText) findViewById(R.id.EditTextDate);
			dateRencontre = new Date(year, monthOfYear, dayOfMonth);
			Locale locale = Locale.getDefault();
			DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT, locale);
			SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy");
			try {
				dateRencontre = sdf.parse(dateFormat.format(dateRencontre));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			editTextDate.setText(dateFormat.format(dateRencontre));
		}
	};

	protected Dialog onCreateDialog(int id) {
		Calendar c = Calendar.getInstance();
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);
		int cday = c.get(Calendar.DAY_OF_MONTH);
		switch (id) {
		case DATE_DIALOG_ID:
			return new DatePickerDialog(this,  mDateSetListener,  cyear, cmonth, cday);
		}
		return null;
	}

}