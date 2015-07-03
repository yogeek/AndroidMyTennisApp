package com.yogidev.android.mytennisapp;

import java.io.Serializable;


public class Epreuve implements Serializable {
	
	private static final long serialVersionUID = 2525944159770100866L;
	
	private String nom;
	private String type;
	
	/**
	 * Constructeur
	 * 
	 * @param nom
	 */
	public Epreuve(String nom) {
		super();
		this.nom = nom;
	}
	
	public Epreuve(String nom, String type) {
		super();
		this.nom = nom;
		this.type = type;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}