package com.yogidev.android.mytennisapp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class DisplayCalculRules extends Activity {

	// D�claration de la WebView
	private WebView myWebView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.affichagereglescalcul);

		// On r�cup�re notre WebView
		myWebView= (WebView) findViewById(R.id.WebviewReglesCalcul);

		// Chargement du HTML dans la WebView avec d�finition
		// du Mime Type et du type d'encodage comme dans un
		// fichier HTML de base (en g�n�ral � d�clarer dans le head)
		//myWebView.loadData(myHtmlCode, "text/html", "utf-8");
		try {
			myWebView.loadUrl("file:///android_asset/" + getString(R.string.reglesCalculFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
