package com.yogidev.android.mytennisapp;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Constants {
	
	// Fichier de sauvegarde 
	public final static String SERIAL_FILE = "_player.txt";
	
	// Chaine de la liste des profils par défaut
	public final static String NO_PROFILE = "Aucun profil crée";

	// Liste des séries
	public final static int QUATRIEME_SERIE = 4;
	public final static int TROISIEME_SERIE = 3;
	public final static int DEUXIEME_SERIE = 2;
	public final static int PREMIERE_SERIE = 1;

	// Liste des classements par série
	public final static List<String> CLASSEMENTS_QUATRIEME_SERIE = Arrays.asList("NC", "40", "30_5", "30_4", "30_3", "30_2", "30_1");
	public final static List<String> CLASSEMENTS_TROISIEME_SERIE = Arrays.asList("30", "15_5", "15_4", "15_3", "15_2", "15_1");
	public final static List<String> CLASSEMENTS_DEUXIEME_SERIE = Arrays.asList("15", "5_6", "4_6", "3_6", "2_6", "1_6", "0", "-2_6", "-4_6", "-15", "-30",
			"Promotion");
	public final static List<String> CLASSEMENTS_PREMIERE_SERIE = Arrays.asList("1ereSerie");

	// Liste complète des classements
	public final static List<String> LISTE_CLASSEMENTS = Arrays.asList("NC", "40", "30_5", "30_4", "30_3", "30_2", "30_1", "30", "15_5", "15_4", "15_3",
			"15_2", "15_1", "15", "5_6", "4_6", "3_6", "2_6", "1_6", "0", "-2_6", "-4_6", "-15", "-30", "Promotion", "1ereSerie");

	// Points rapportés par types de victoires
	public final static int VICTOIRE_2_ECHELON_SUPERIEUR_ET_PLUS = 150;
	public final static int VICTOIRE_1_ECHELON_SUPERIEUR = 100;
	public final static int VICTOIRE_ECHELON_EGAL = 50;
	public final static int VICTOIRE_1_ECHELON_INFERIEUR = 30;
	public final static int VICTOIRE_2_ECHELON_INFERIEUR = 20;
	public final static int VICTOIRE_3_ECHELON_INFERIEUR = 15;
	public final static int VICTOIRE_4_ECHELON_INFERIEUR_ET_PLUS = 0;

	// Types de défaites
	public final static int DEFAITE_ECHELON_SUPERIEUR = 0;
	public final static int DEFAITE_ECHELON_EGAL = 1;
	public final static int DEFAITE_1_ECHELON_INFERIEUR = 2;
	public final static int DEFAITE_2_ECHELON_INFERIEUR = 3;
	public final static int DEFAITE_3_ECHELON_INFERIEUR_ET_PLUS = 4;

	// Capitaux de départs
	public final static int CAPITAL_NC = 0;
	public final static int CAPITAL_40 = 2;
	public final static int CAPITAL_30_5 = 5;
	public final static int CAPITAL_30_4 = 10;
	public final static int CAPITAL_30_3 = 20;
	public final static int CAPITAL_30_2 = 30;
	public final static int CAPITAL_30_1 = 50;
	public final static int CAPITAL_30 = 80;
	public final static int CAPITAL_15_5 = 120;
	public final static int CAPITAL_15_4 = 160;
	public final static int CAPITAL_15_3 = 200;
	public final static int CAPITAL_15_2 = 240;
	public final static int CAPITAL_15_1 = 280;
	public final static int CAPITAL_15 = 330;
	public final static int CAPITAL_5_6 = 370;
	public final static int CAPITAL_4_6 = 410;
	public final static int CAPITAL_3_6 = 450;
	public final static int CAPITAL_2_6 = 490;
	public final static int CAPITAL_1_6 = 530;
	public final static int CAPITAL_0 = 570;
	public final static int CAPITAL_MOINS_2_6 = 620;
	public final static int CAPITAL_MOINS_4_6 = 660;
	public final static int CAPITAL_MOINS_15 = 700;
	public final static int CAPITAL_MOINS_30 = 740;
	public final static int CAPITAL_Promotion = 780;

	// Map des capitaux de départ par classements
	public static final Map<String, Integer> MAP_CAPITAUX_DEPART_PAR_CLASSEMENTS = Collections.unmodifiableMap(new HashMap<String, Integer>() {
		{
			put("NC", CAPITAL_NC);
			put("40", CAPITAL_40);
			put("30_5", CAPITAL_30_5);
			put("30_4", CAPITAL_30_4);
			put("30_3", CAPITAL_30_3);
			put("30_2", CAPITAL_30_2);
			put("30_1", CAPITAL_30_1);
			put("30", CAPITAL_30);
			put("15_5", CAPITAL_15_5);
			put("15_4", CAPITAL_15_4);
			put("15_3", CAPITAL_15_3);
			put("15_2", CAPITAL_15_2);
			put("15_1", CAPITAL_15_1);
			put("15", CAPITAL_15);
			put("5_6", CAPITAL_5_6);
			put("4_6", CAPITAL_4_6);
			put("3_6", CAPITAL_3_6);
			put("2_6", CAPITAL_2_6);
			put("1_6", CAPITAL_1_6);
			put("0", CAPITAL_0);
			put("-2_6", CAPITAL_MOINS_2_6);
			put("-4_6", CAPITAL_MOINS_4_6);
			put("-15", CAPITAL_MOINS_15);
			put("-30", CAPITAL_MOINS_30);
			put("Promotion", CAPITAL_Promotion);
		}
	});

	// Nombre de victoires prises en compte
	public final static int NB_VICTOIRES_NC = 5;
	public final static int NB_VICTOIRES_40 = 5;
	public final static int NB_VICTOIRES_30_5 = 5;
	public final static int NB_VICTOIRES_30_4 = 5;
	public final static int NB_VICTOIRES_30_3 = 6;
	public final static int NB_VICTOIRES_30_2 = 6;
	public final static int NB_VICTOIRES_30_1 = 6;
	public final static int NB_VICTOIRES_30 = 6;
	public final static int NB_VICTOIRES_15_5 = 6;
	public final static int NB_VICTOIRES_15_4 = 6;
	public final static int NB_VICTOIRES_15_3 = 7;
	public final static int NB_VICTOIRES_15_2 = 7;
	public final static int NB_VICTOIRES_15_1 = 7;
	public final static int NB_VICTOIRES_15 = 7;
	public final static int NB_VICTOIRES_5_6 = 7;
	public final static int NB_VICTOIRES_4_6 = 7;
	public final static int NB_VICTOIRES_3_6 = 8;
	public final static int NB_VICTOIRES_2_6 = 8;
	public final static int NB_VICTOIRES_1_6 = 8;
	public final static int NB_VICTOIRES_0 = 9;
	public final static int NB_VICTOIRES_MOINS_2_6 = 10;
	public final static int NB_VICTOIRES_MOINS_4_6 = 12;
	public final static int NB_VICTOIRES_MOINS_15 = 14;
	public final static int NB_VICTOIRES_MOINS_30 = 16;
	public final static int NB_VICTOIRES_Promotion = 18;

	// Map des nombres de victoires prises en compte
	public static final Map<String, Integer> MAP_NB_VICTOIRES_PAR_CLASSEMENTS = Collections.unmodifiableMap(new HashMap<String, Integer>() {
		{
			put("NC", NB_VICTOIRES_NC);
			put("40", NB_VICTOIRES_40);
			put("30_5", NB_VICTOIRES_30_5);
			put("30_4", NB_VICTOIRES_30_4);
			put("30_3", NB_VICTOIRES_30_3);
			put("30_2", NB_VICTOIRES_30_2);
			put("30_1", NB_VICTOIRES_30_1);
			put("30", NB_VICTOIRES_30);
			put("15_5", NB_VICTOIRES_15_5);
			put("15_4", NB_VICTOIRES_15_4);
			put("15_3", NB_VICTOIRES_15_3);
			put("15_2", NB_VICTOIRES_15_2);
			put("15_1", NB_VICTOIRES_15_1);
			put("15", NB_VICTOIRES_15);
			put("5_6", NB_VICTOIRES_5_6);
			put("4_6", NB_VICTOIRES_4_6);
			put("3_6", NB_VICTOIRES_3_6);
			put("2_6", NB_VICTOIRES_2_6);
			put("1_6", NB_VICTOIRES_1_6);
			put("0", NB_VICTOIRES_0);
			put("-2_6", NB_VICTOIRES_MOINS_2_6);
			put("-4_6", NB_VICTOIRES_MOINS_4_6);
			put("-15", NB_VICTOIRES_MOINS_15);
			put("-30", NB_VICTOIRES_MOINS_30);
			put("Promotion", NB_VICTOIRES_Promotion);
		}
	});

	// Bonus en cas d'absence de défaite significative
	// (à échelon inférieur ou égal)
	public final static int BONUS_ABSENCE_DEFAITE_SIGNIFICATIVE_4EME_SERIE = 50;
	public final static int BONUS_ABSENCE_DEFAITE_SIGNIFICATIVE_3EME_2EME_SERIE = 100;
	
	// Bonus par partie gagnée en championnats individuels jeunes, seniors et seniors plus 
	// dans les championnats régionaux de Ligue y compris dans les phases départementales disputées par élimination directe : +15 points. 
	// Bonification plafonnée à 45 points (3 victoires).
	public final static int BONUS_CHAMPIONNAT_INDIVIDUEL = 15;
	public final static int MAX_NB_BONUS_CHAMPIONNAT_INDIVIDUEL = 3;

	// Map des normes de maintien
	// Map des nombres de victoires prises en compte
	public static final Map<String, Integer> MAP_NORMES_MAINTIEN = Collections.unmodifiableMap(new HashMap<String, Integer>() {
		{
			put("40", 6);
			put("30_5", 50);
			put("30_4", 90);
			put("30_3", 145);
			put("30_2", 205);
			put("30_1", 245);
			put("30", 290);
			put("15_5", 325);
			put("15_4", 395);
			put("15_3", 465);
			put("15_2", 525);
			put("15_1", 565);
			put("15", 660);
			put("5_6", 700);
			put("4_6", 730);
			put("3_6", 820);
			put("2_6", 900);
			put("1_6", 930);
			put("0", 1070);
			put("-2_6", 1240);
			put("-4_6", 1390);
			put("-15", 1510);
			put("-30", 1580);
			put("Promotion", 1750);
		}
	});
	
	// Liste des types d'épreuve
	public final static String TOURNOI = "Tournoi individuel";
	public final static String CHAMPIONNAT_INDIVIDUEL = "Championnat individuel";
	public final static String CHAMPIONNAT_EQUIPE = "Championnat par équipe";
	public final static List<String> LISTE_TYPES_EPREUVE = Arrays.asList(TOURNOI, CHAMPIONNAT_INDIVIDUEL, CHAMPIONNAT_EQUIPE);
	
	// Liste des formats de rencontre
	public final static String FORMAT_NORMAL = "Normal (3 sets à 6 jeux)";
	public final static String FORMAT_COURT_TROIS_SETS_4_JEUX = "3 sets à 4 jeux - jeux décisif à 4/4";
	public final static String FORMAT_COURT_TROIS_SETS_3_JEUX = "3 sets à 3 jeux - jeux décisif à 2/2";
	public final static String FORMAT_COURT_TROIS_JEUX_DECISIFS = "3 jeux décisifs";
	
	public final static List<String> LISTE_FORMATS_COURTS_RENCONTRE = Arrays.asList(FORMAT_NORMAL, FORMAT_COURT_TROIS_SETS_4_JEUX, FORMAT_COURT_TROIS_SETS_3_JEUX, FORMAT_COURT_TROIS_JEUX_DECISIFS);
	
	// Map des coefficients pour les formats courts
	public static final Map<String, Float> MAP_COEFF_FORMAT_COURT = Collections.unmodifiableMap(new HashMap<String, Float>() {
		{
			put(FORMAT_NORMAL, 1f);
			put(FORMAT_COURT_TROIS_SETS_4_JEUX, 0.6f);
			put(FORMAT_COURT_TROIS_SETS_3_JEUX, 0.4f);
			put(FORMAT_COURT_TROIS_JEUX_DECISIFS, 0.2f);
		}
	});
	
}
