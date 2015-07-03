package com.yogidev.android.mytennisapp;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class DisplayCalculRules extends Activity {

	// Déclaration de la WebView
	private WebView myWebView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.affichagereglescalcul);

		// On récupère notre WebView
		myWebView= (WebView) findViewById(R.id.WebviewReglesCalcul);

		// Chargement du HTML dans la WebView avec définition
		// du Mime Type et du type d'encodage comme dans un
		// fichier HTML de base (en général à déclarer dans le head)
		//myWebView.loadData(myHtmlCode, "text/html", "utf-8");
		try {
			myWebView.loadUrl("file:///android_asset/" + getString(R.string.reglesCalculFileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
