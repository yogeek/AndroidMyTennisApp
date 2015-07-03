package com.yogidev.android.mytennisapp;

import android.widget.Spinner;

import com.yogidev.android.mytennisapp.exceptions.UnknownSerieException;

public class CommonTools {


	/**
	 * 
	 * Méthode permettant de positionner une spinner liste sur la chaine en paramètre. 
	 * 
	 * @param spinner
	 * @param itemValue
	 */
	public static void setItemSelectedOnSpinnerClassement(Spinner spinner, String itemValue) {
		for (int i = 0; i < spinner.getCount(); i++) {
			String value = spinner.getItemAtPosition(i).toString();
			if (value.equals(itemValue)) {
				spinner.setSelection(i);
			}
		}
	}
	
	public static Player testPlayer() throws UnknownSerieException {
		Player p = new Player("Guillaume", "Dupin", "15_2");
		p.setAnneeNaissance(1983);

		Epreuve frouzins = new Epreuve("Frouzins");
		frouzins.setType("Tournoi individuel");
		p.addEpreuve(frouzins);
		Rencontre match1 = new Rencontre(new Player("Sébastien", "Larquetoux", "15_2"), true);
		Rencontre match2 = new Rencontre(new Player("Prénom", "Lefort", "15_1"), true);
		Rencontre match3 = new Rencontre(new Player("Paul", "Cave", "15_1"), false);
		p.addRencontre(frouzins, match1);
		p.addRencontre(frouzins, match2);
		p.addRencontre(frouzins, match3);

		Epreuve castelginest = new Epreuve("Castelginest");
		castelginest.setType("Tournoi individuel");
		p.addEpreuve(castelginest);
		Rencontre match4 = new Rencontre(new Player("Fabrice", "Maroulaz", "15_5"), true);
		Rencontre match5 = new Rencontre(new Player("Alexandre", "Garcia", "15_1"), true);
		Rencontre match6 = new Rencontre(new Player("Grégoire", "Beaufume", "15_1"), true);
		Rencontre match7 = new Rencontre(new Player("Franck", "Villard", "15"), true);
		Rencontre match8 = new Rencontre(new Player("Olivier", "Piquemal", "15"), false);
		p.addRencontre(castelginest, match4);
		p.addRencontre(castelginest, match5);
		p.addRencontre(castelginest, match6);
		p.addRencontre(castelginest, match7);
		p.addRencontre(castelginest, match8);

		Epreuve lacroixFalgarde = new Epreuve("Lacroix-Falgarde");
		lacroixFalgarde.setType("Tournoi individuel");
		p.addEpreuve(lacroixFalgarde);
		Rencontre match9 = new Rencontre(new Player("Ioan", "Labourdette", "15_4"), false);
		p.addRencontre(lacroixFalgarde, match9);

		Epreuve championnatEquipe = new Epreuve("Championnat par équipes");
		championnatEquipe.setType("Championnat Régional Senior Messieurs");
		p.addEpreuve(championnatEquipe);
		Rencontre match10 = new Rencontre(new Player("Frédéric", "Salfati", "15_2"), true);
		Rencontre match11 = new Rencontre(new Player("Romain", "Cassair", "15_1"), false);
		Rencontre match12 = new Rencontre(new Player("Alexandre", "Piard", "15_1"), false);
		Rencontre match13 = new Rencontre(new Player("Bertrand ", "Chaigneau", "15"), false);
		p.addRencontre(championnatEquipe, match10);
		p.addRencontre(championnatEquipe, match11);
		p.addRencontre(championnatEquipe, match12);
		p.addRencontre(championnatEquipe, match13);

		Epreuve fonsorbes = new Epreuve("Fonsorbes");
		fonsorbes.setType("Tournoi individuel");
		p.addEpreuve(fonsorbes);
		Rencontre match14_WO = new Rencontre(new Player("noName", "noName", "15_4"), true);
		match14_WO.setWO(true);
		p.addRencontre(fonsorbes, match14_WO);
		Rencontre match14 = new Rencontre(new Player("Morad", "Rachedi", "15_1"), false);
		p.addRencontre(fonsorbes, match14);

		Epreuve saintJean = new Epreuve("Saint-Jean");
		saintJean.setType("Tournoi individuel");
		p.addEpreuve(saintJean);
		Rencontre match15 = new Rencontre(new Player("Romain", "Rossi", "15_3"), true);
		p.addRencontre(saintJean, match15);
		Rencontre match16 = new Rencontre(new Player("Cyril", "Verdoux", "15_1"), true);
		p.addRencontre(saintJean, match16);

		return p;
	}
}
