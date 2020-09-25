package chiffrement;
/**
 * Marche correctement uniquement si le message crypté est très long ...
 * @author Quecg2
 *
 */
public class Attaque_cesar_complet {

	private char freq_fr [] = {'e',' ','s','a','i','t','n','r','u','l','o','d','c','p','m','v','q','f','b','g','h','j','x','y','z','w','k',(char)33,(char)34,(char)35,(char)36,(char)37,(char)38,(char)39,(char)40,(char)41,(char)42,(char)43,(char)44,(char)45,(char)46,(char)47,(char)48,(char)49,(char)50,(char)51,(char)52,(char)53,(char)54,(char)55,(char)56,(char)57,(char)58,(char)59,(char)60,(char)61,(char)62,(char)63,(char)64,(char)65,(char)66,(char)67,(char)68,(char)69,(char)70,(char)71,(char)72,(char)73,(char)74,(char)75,(char)76,(char)77,(char)78,(char)79,(char)80,(char)81,(char)82,(char)83,(char)84,(char)85,(char)86,(char)87,(char)88,(char)89,(char)90,(char)91,(char)92,(char)93,(char)94,(char)95,(char)96,(char)123,(char)124,(char)125,(char)126,(char)127,(char)128,(char)129,(char)130,(char)131,(char)132,(char)133,(char)134,(char)135,(char)136,(char)137,(char)138,(char)139,(char)140,(char)141,(char)142,(char)143,(char)144,(char)145,(char)146,(char)147,(char)148,(char)149,(char)150,(char)151,(char)152,(char)153,(char)154,(char)155,(char)156,(char)157,(char)158,(char)159,(char)160,(char)161,(char)162,(char)163,(char)164,(char)165,(char)166,(char)167,(char)168,(char)169,(char)170,(char)171,(char)172,(char)173,(char)174,(char)175,(char)176,(char)177,(char)178,(char)179,(char)180,(char)181,(char)182,(char)183,(char)184,(char)185,(char)186,(char)187,(char)188,(char)189,(char)190,(char)191,(char)192,(char)193,(char)194,(char)195,(char)196,(char)197,(char)198,(char)199,(char)200,(char)201,(char)202,(char)203,(char)204,(char)205,(char)206,(char)207,(char)208,(char)209,(char)210,(char)211,(char)212,(char)213,(char)214,(char)215,(char)216,(char)217,(char)218,(char)219,(char)220,(char)221,(char)222,(char)223,(char)224,(char)225,(char)226,(char)227,(char)228,(char)229,(char)230,(char)231,(char)232,(char)233,(char)234,(char)235,(char)236,(char)237,(char)238,(char)239,(char)240,(char)241,(char)242,(char)243,(char)244,(char)245,(char)246,(char)247,(char)248,(char)249,(char)250,(char)251,(char)252,(char)253,(char)254,(char)255};
	private char freq_en [] = {'e',' ','t','a','o','n','i','s','r','h','l','d','c','u','m','f','p','w','g','b','y','v','k','x','j','q','z',(char)33,(char)34,(char)35,(char)36,(char)37,(char)38,(char)39,(char)40,(char)41,(char)42,(char)43,(char)44,(char)45,(char)46,(char)47,(char)48,(char)49,(char)50,(char)51,(char)52,(char)53,(char)54,(char)55,(char)56,(char)57,(char)58,(char)59,(char)60,(char)61,(char)62,(char)63,(char)64,(char)65,(char)66,(char)67,(char)68,(char)69,(char)70,(char)71,(char)72,(char)73,(char)74,(char)75,(char)76,(char)77,(char)78,(char)79,(char)80,(char)81,(char)82,(char)83,(char)84,(char)85,(char)86,(char)87,(char)88,(char)89,(char)90,(char)91,(char)92,(char)93,(char)94,(char)95,(char)96,(char)123,(char)124,(char)125,(char)126,(char)127,(char)128,(char)129,(char)130,(char)131,(char)132,(char)133,(char)134,(char)135,(char)136,(char)137,(char)138,(char)139,(char)140,(char)141,(char)142,(char)143,(char)144,(char)145,(char)146,(char)147,(char)148,(char)149,(char)150,(char)151,(char)152,(char)153,(char)154,(char)155,(char)156,(char)157,(char)158,(char)159,(char)160,(char)161,(char)162,(char)163,(char)164,(char)165,(char)166,(char)167,(char)168,(char)169,(char)170,(char)171,(char)172,(char)173,(char)174,(char)175,(char)176,(char)177,(char)178,(char)179,(char)180,(char)181,(char)182,(char)183,(char)184,(char)185,(char)186,(char)187,(char)188,(char)189,(char)190,(char)191,(char)192,(char)193,(char)194,(char)195,(char)196,(char)197,(char)198,(char)199,(char)200,(char)201,(char)202,(char)203,(char)204,(char)205,(char)206,(char)207,(char)208,(char)209,(char)210,(char)211,(char)212,(char)213,(char)214,(char)215,(char)216,(char)217,(char)218,(char)219,(char)220,(char)221,(char)222,(char)223,(char)224,(char)225,(char)226,(char)227,(char)228,(char)229,(char)230,(char)231,(char)232,(char)233,(char)234,(char)235,(char)236,(char)237,(char)238,(char)239,(char)240,(char)241,(char)242,(char)243,(char)244,(char)245,(char)246,(char)247,(char)248,(char)249,(char)250,(char)251,(char)252,(char)253,(char)254,(char)255};

	public int cleflaplusprobable;
	public int deuxiemecleflaplusprobable;
	public int troisiemecleflaplusprobable;

	public Attaque_cesar_complet(String messagecrypte,String langue){
		char freq []; // Choix de notre reference de fréquence pour les lettres
		if(langue=="Francais" ||langue=="fr" ||langue=="Fr" ||langue=="French" ||langue=="francais") freq = freq_fr;
		else freq = freq_en;

		// Creation d'un tableau contenant le nombre de fois que chaque lettre apparait
		int tab []={0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		for(int i=0;i<messagecrypte.length();i++){ // On parcours le message
			for(int j=0;j<224;j++){ // On parcours les lettres pour trouver la bonne
				if(freq[j]==messagecrypte.charAt(i)) {tab[j]+=1; j=260; /*Pour sortir de la boucle*/}
			}
		}
		//for(int j=0;j<26;j++) System.out.print(tab[j]);

		// Recherche des 3 lettres les + utilisées :
		int elementmax1=0,elementmax2=0,elementmax3=0;
		int max1=0,max2=0,max3=0;
		for(int k=0;k<224;k++) {
			if(tab[k]>max1){elementmax1=k; max1=tab[k];}; // On regarde si ca marche bien pour le moment (il ne reste plus qu'a chercher les 2-3 lettres les + utilisées, en déduire les clefs les + probables et les montrer à l'utilisateur.
		}
		for(int k=0;k<224;k++) {
			if(tab[k]>max2 && k!=elementmax1){elementmax2=k; max2=tab[k];}; // On regarde si ca marche bien pour le moment (il ne reste plus qu'a chercher les 2-3 lettres les + utilisées, en déduire les clefs les + probables et les montrer à l'utilisateur.
		}
		for(int k=0;k<224;k++) {
			if(tab[k]>max3 && k!=elementmax1 && k!=elementmax2){elementmax3=k; max3=tab[k];}; // On regarde si ca marche bien pour le moment (il ne reste plus qu'a chercher les 2-3 lettres les + utilisées, en déduire les clefs les + probables et les montrer à l'utilisateur.
		}
		System.out.println(freq[elementmax1]+" "+freq[elementmax2]+" "+freq[elementmax3]);

		// Il ne reste plus qu'à afficher les clefs les plus probables
		//int cleflaplusprobable=0;
		if(freq[elementmax1]-freq[0]<0) cleflaplusprobable=256+(freq[elementmax1]-freq[0]);
		else cleflaplusprobable=(freq[elementmax1]-freq[0]);

		if(freq[elementmax2]-freq[0]<0) deuxiemecleflaplusprobable=256+(freq[elementmax2]-freq[0]);
		else deuxiemecleflaplusprobable=(freq[elementmax2]-freq[0]);

		if(freq[elementmax3]-freq[0]<0) troisiemecleflaplusprobable=256+(freq[elementmax3]-freq[0]);
		else troisiemecleflaplusprobable=(freq[elementmax3]-freq[0]);

		//System.out.println("Clef la plus probable : "+cleflaplusprobable+"\n"+"2eme clef la plus probable : "+deuxiemecleflaplusprobable+"\n"+"3eme clef la plus probable : "+troisiemecleflaplusprobable+"\n");

	}
}
