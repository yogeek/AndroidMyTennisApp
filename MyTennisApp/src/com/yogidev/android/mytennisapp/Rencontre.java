package com.yogidev.android.mytennisapp;

import java.io.Serializable;
import java.util.Date;


public class Rencontre implements java.lang.Comparable<Rencontre>, Serializable {

	private static final long serialVersionUID = -7328776825609280907L;

	private Player adversaire;
	private boolean isVictoire;
	private boolean isWO;
	private String type;
	private String format;
	private String lieu;
	private String score;
	private String surface;
	private Date date;
	private boolean isBonus;

	/**
	 * Constructeur
	 * 
	 * @param adversaire
	 * @param isVictoire
	 */
	public Rencontre(Player adversaire, boolean isVictoire) {
		super();
		this.adversaire = adversaire;
		this.isVictoire = isVictoire;
	}
	
	public Rencontre(Player adversaire, boolean isVictoire, String format) {
		super();
		this.adversaire = adversaire;
		this.isVictoire = isVictoire;
		this.format = format;
	}

	public Player getAdversaire() {
		return adversaire;
	}

	public void setAdversaire(Player adversaire) {
		this.adversaire = adversaire;
	}

	public boolean isVictoire() {
		return isVictoire;
	}

	public void setVictoire(boolean isVictoire) {
		this.isVictoire = isVictoire;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getLieu() {
		return lieu;
	}

	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getSurface() {
		return surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

	public boolean isWO() {
		return isWO;
	}

	public void setWO(boolean isWO) {
		this.isWO = isWO;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public boolean isBonus() {
		return isBonus;
	}

	public void setBonus(boolean isBonus) {
		this.isBonus = isBonus;
	}

	public int compareTo(Rencontre other) {
		int nombre1 = (other).getAdversaire().getClassement();
		int nombre2 = this.getAdversaire().getClassement();
		if (nombre1 > nombre2)
			return -1;
		else if (nombre1 == nombre2)
			return 0;
		else
			return 1;
	}

}
