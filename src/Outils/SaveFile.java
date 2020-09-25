package Outils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;

public class SaveFile {

/**
 * Sauvegarde d'un String dans un fichier
 * @param fichier //L'adresse de notre fichier à creer
 * @param contenu // Le contenu de notre fichier à creer
 * 
 * Exemple :
 * String monadresse= "C:\\monfichier.txt";
 * String moncontenu= "Kikou (ligne1)\nLigne 2\nLigne 3 : azertyuiopqsdfghjklmwxcvbn1234567890\nCeci est la 4eme ligne de mon string enregistré à l'adresse monfichier.txt\n";
 * SaveFile sauvegarde = new SaveFile(monadresse,moncontenu);
 */
	public SaveFile(String fichier, String contenu){
		try {
			FileWriter fw = new FileWriter (fichier);
			BufferedWriter bw = new BufferedWriter (fw);
			PrintWriter fichierSortie = new PrintWriter (bw); 
				fichierSortie.println (contenu); 
			fichierSortie.close();
			System.out.println("Le fichier " + fichier + " a été créé !"); 
		}
		catch (Exception e){
			System.out.println(e.toString());
		}		
	}

}
