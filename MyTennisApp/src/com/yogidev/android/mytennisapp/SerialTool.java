package com.yogidev.android.mytennisapp;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import android.content.Context;
import android.widget.Toast;

public class SerialTool {

	public static String getPlayerSerialFile(Player p) {
		return p.getPrenom() + "-" + p.getNom() + Constants.SERIAL_FILE;
	}

	public static String getPlayerSerialFile(String nom, String prenom) {
		return prenom + "-" + nom + Constants.SERIAL_FILE;
	}

	public static String getPlayerSerialFile(String nomComplet) {
		return nomComplet.replaceAll(" ", "-") + Constants.SERIAL_FILE;
	}

	/**
	 * -------------------------------------------------------------------------------------
	 * SAVE functions
	 * -------------------------------------------------------------------------------------
	 */

	public static void savePlayer(Player p, Context ctx) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		
		try {
			fos = ctx.openFileOutput(getPlayerSerialFile(p), Context.MODE_PRIVATE);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(p); 
			oos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Sauvegarde",Toast.LENGTH_SHORT).show(); 
		}catch(IOException e){
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Sauvegarde",Toast.LENGTH_SHORT).show(); 
		} finally {
			try {
				oos.close();
				fos.close();
			} catch (IOException e) {
				Toast.makeText(ctx, "Erreur de Sauvegarde",Toast.LENGTH_SHORT).show(); 
			}

		}
	}

	/**
	 * -------------------------------------------------------------------------------------
	 * RESTORE functions
	 * -------------------------------------------------------------------------------------
	 */

	public static Player restorePlayer(Player p, Context ctx) {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try
		{
			fis = ctx.openFileInput(getPlayerSerialFile(p));
			ois = new ObjectInputStream(fis);
			p = (Player)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Chargement" + e,Toast.LENGTH_SHORT).show(); 
		}catch(IOException e){
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Chargement" + e,Toast.LENGTH_SHORT).show(); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Chargement" + e,Toast.LENGTH_SHORT).show(); 
		}

		return p;
	}

	public static Player restorePlayer(String nomComplet, Context ctx) {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Player p = null;
		try
		{
			fis = ctx.openFileInput(getPlayerSerialFile(nomComplet));
			ois = new ObjectInputStream(fis);
			p = (Player)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Chargement" + e,Toast.LENGTH_SHORT).show(); 
		}catch(IOException e){
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Chargement" + e,Toast.LENGTH_SHORT).show(); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Chargement" + e,Toast.LENGTH_SHORT).show(); 
		}

		return p;
	}

	public static Player restorePlayer(String nom, String prenom, Context ctx) {

		FileInputStream fis = null;
		ObjectInputStream ois = null;
		Player p = null;
		try
		{
			fis = ctx.openFileInput(getPlayerSerialFile(nom, prenom));
			ois = new ObjectInputStream(fis);
			p = (Player)ois.readObject();
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Chargement" + e,Toast.LENGTH_SHORT).show(); 
		}catch(IOException e){
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Chargement" + e,Toast.LENGTH_SHORT).show(); 
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			Toast.makeText(ctx, "Erreur de Chargement" + e,Toast.LENGTH_SHORT).show(); 
		}

		return p;
	}

	/**
	 * -------------------------------------------------------------------------------------
	 * DELETE functions
	 * -------------------------------------------------------------------------------------
	 */

	public static boolean deletePlayer(Player p, Context ctx) {
		return ctx.deleteFile(getPlayerSerialFile(p));
	}

	public static boolean deletePlayer(String nom, String prenom, Context ctx) {
		return ctx.deleteFile(getPlayerSerialFile(nom, prenom));
	}

	public static boolean deletePlayer(String nomComplet, Context ctx) {
		return ctx.deleteFile(getPlayerSerialFile(nomComplet));
	}

}