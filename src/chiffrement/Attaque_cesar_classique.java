package chiffrement;
/**
 * Marche correctement uniquement si le message crypté est très long ...
 * @author Quecg2
 *
 */
public class Attaque_cesar_classique {
	private char freq_fr [] = {'e','s','a','i','t','n','r','u','l','o','d','c','p','m','v','q','f','b','g','h','j','x','y','z','w','k'};
	private char freq_en [] = {'e','t','a','o','n','i','s','r','h','l','d','c','u','m','f','p','w','g','b','y','v','k','x','j','q','z'};
	public int cleflaplusprobable;
	public int deuxiemecleflaplusprobable;
	public int troisiemecleflaplusprobable;

	public Attaque_cesar_classique(String messagecrypte,String langue){
		messagecrypte=messagecrypte.toLowerCase(); // ne pas faire la difference entre majuscules et minuscules
		char freq []; // Choix de notre reference de fréquence pour les lettres
		if(langue=="Francais" ||langue=="fr" ||langue=="Fr" ||langue=="French" ||langue=="francais") freq = freq_fr;
		else freq = freq_en;

		// Creation d'un tableau contenant le nombre de fois que chaque lettre apparait
		int tab []={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		for(int i=0;i<messagecrypte.length();i++){ // On parcours le message
			for(int j=0;j<26;j++){ // On parcours les lettres pour trouver la bonne
				if(freq[j]==messagecrypte.charAt(i)) {tab[j]+=1; j=27; /*Pour sortir de la boucle*/}
			}
		}
		//for(int j=0;j<26;j++) System.out.print(tab[j]);

		// Recherche des 3 lettres les + utilisées :
		int elementmax1=0,elementmax2=0,elementmax3=0;
		int max1=0,max2=0,max3=0;
		for(int k=0;k<26;k++) {
			if(tab[k]>max1){elementmax1=k; max1=tab[k];}; // On regarde si ca marche bien pour le moment (il ne reste plus qu'a chercher les 2-3 lettres les + utilisées, en déduire les clefs les + probables et les montrer à l'utilisateur.
		}
		for(int k=0;k<26;k++) {
			if(tab[k]>max2 && k!=elementmax1){elementmax2=k; max2=tab[k];}; // On regarde si ca marche bien pour le moment (il ne reste plus qu'a chercher les 2-3 lettres les + utilisées, en déduire les clefs les + probables et les montrer à l'utilisateur.
		}
		for(int k=0;k<26;k++) {
			if(tab[k]>max3 && k!=elementmax1 && k!=elementmax2){elementmax3=k; max3=tab[k];}; // On regarde si ca marche bien pour le moment (il ne reste plus qu'a chercher les 2-3 lettres les + utilisées, en déduire les clefs les + probables et les montrer à l'utilisateur.
		}
		//System.out.println(freq[elementmax1]+" "+freq[elementmax2]+" "+freq[elementmax3]);

		// Il ne reste plus qu'à afficher les clefs les plus probables
		//int cleflaplusprobable=0;

		if(freq[elementmax1]-freq[0]<0) cleflaplusprobable=26+(freq[elementmax1]-freq[0]);
		else cleflaplusprobable=(freq[elementmax1]-freq[0]);

		if(freq[elementmax2]-freq[0]<0) deuxiemecleflaplusprobable=26+(freq[elementmax2]-freq[0]);
		else deuxiemecleflaplusprobable=(freq[elementmax2]-freq[0]);

		if(freq[elementmax3]-freq[0]<0) troisiemecleflaplusprobable=26+(freq[elementmax3]-freq[0]);
		else troisiemecleflaplusprobable=(freq[elementmax3]-freq[0]);

		//System.out.println("Clef la plus probable : "+cleflaplusprobable+"\n"+"2eme clef la plus probable : "+deuxiemecleflaplusprobable+"\n"+"3eme clef la plus probable : "+troisiemecleflaplusprobable+"\n");

	}
}
