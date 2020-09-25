package chiffrement;

import java.util.ArrayList;
import java.util.List;

public class Attaque_vigenere {

	public int tailleclef;
	public String cleflaplusprobable;
	/*public String deuxiemecleflaplusprobable;
	public String troisiemecleflaplusprobable;*/

	public Attaque_vigenere(String messagecrypte){

		// On suppose que la clef a une longueur >2 (encore heureux ...)
		//String messagecrypte="UMIJHSLQGRDLSPXLITUNBLRMOOQRHXZXPDPMPSLZKLXRNIWIMFZJCDAGEQTIWTGLMSCOPDCKEELLEOVLWCHBSTFDKPONONSUKXVLMHDCXETXLDLITUNLEKKCLIHSAYDUKCATEURIJXGAVSJDTOBDLITLOFIVGZIVBTTEOUVPRIEESULBAYVHUATCBDLITVAMXUBRIVRTZPMAKTNDRECITAYNWIRTLDCOYICOUOHVWCHBBXOZEYNPPATTDHRFCMWIGUDVDSOITCEEEEYYLWBBMQYRAFOIQUDNVYYMASHQFVSOYEPUIRGYTQMMFAQNRJDBUIVOTWDXLUKSBMTCJCDEMBLZRKMMOAZTECKCCOALENSSLKHYTIXBZEABLIJOUDVUNLGIIKMTCCYFRLASNYIBQZIWIGYOQEXBLERUVVGAYMHAQCKCMZURTEMHZEGAMBRQLVXPQICEQTSEGMGUSPGEBNWPNTLACTVGQYWAPUYDBBLTSASRIDUJMNOXELSNXEAUXFQEZLBYTVIQFXNOYTIREASDUYHKSXXLBASGELESRIELRLWIQXDOCVPSPOULSELRICHTEDYDPQLSQUZZVSTICGSAYMUETDITUMHVVCMZOIEAKAVPZENDDYOFMNAFEAYNRVNEQUIEEZMKIVJDKLQNMPSTQUHPWTSNXFDYADNWPNTSEBSLKGLTOHAFBRCNARRALMETELWPXEQKRDPNTOURPZXSRCBIDLXPOCAVAGDGVVFUBZTBWEBNTABLECIJVFUBGHUWWJXBUEDERSLKGMDCJPCKVKYGERDDWITRMKVPOFXMSEILESSZITGMCGHGDMXECONSATBRKKYAEJQWADODRATERHZMMHZDPZOSAOYTDESKEIFCMVWBBZBCOCIENNZVIMCOVGXZZLUOAROJESGYTAOVGTYPDNXBUIPAFITAYWCBBQENJXDSONSZGLGKIZQTMFNNPCOMAGDGYTAOVDGAXOCOYFINDDVZLOOMFAQAKZEPTILSZPCTGYVHIAFCLYXMELAEXVECMXFXFNYWDPNTLEBSVNPDWMTGIMNZPNDANSPVVFUBDAGDPRXBUEUXSIETGNLXAMDYDBTSPARKEKMCCTGHMGKWMCENTGQEEWQJIGEAFBBONOURIQPVNPVWBCQLWROXAISLDGYTRKCWCQYNVYCDPASFVFGBYMHBMCMQOLUDEVZRKWCFIHGAFZNOYNEMIDETXZLCWIZZCCBDPRUDDRKLPUBGRDLSPXLNTMATZRBQYLSHFTXNPZNTSAMWGHSMASGBWEBVZINLETVGKRYVRJRCKLKDUNERDXITGNMTDDEEWONHAQUDVRMPYVHGQOKWCDONTRNYVMQCYITXBEDXPNSORSKRKCYVQDDWOVKEOU";

		// On recherche des similitudes de longueurs >=3

		int [] diviseurspossibles = new int[messagecrypte.length()]; // La case qui aura l'entier le plus grand sera potentiellement la longueur de la clef
		for(int i=0;i<messagecrypte.length();i++) diviseurspossibles[i]=0; // On initialise notre tableau

		// Recherche de similitude
		for(int i=1;i<messagecrypte.length()-2;i++){ // On parcours toutes les lettres
			for(int j=i+3;j<messagecrypte.length()-2;j++){ // Pour ces lettres, on parcours toutes les lettres qui sont après
				if((messagecrypte.charAt(i)==messagecrypte.charAt(j)) && (messagecrypte.charAt(i+1)==messagecrypte.charAt(j+1)) && (messagecrypte.charAt(i+2)==messagecrypte.charAt(j+2) && messagecrypte.charAt(i-1)!=messagecrypte.charAt(j-1))){
					//On regarde si il y a similitude entre les caractères messagecrypte[i-->i+2] et messagecrypte[j-->j+2]
					//System.out.println("Il y a similitude entre "+i+" et "+j+"\nLes diviseurs de j-i sont : "+factorsOf(j-i));

					List<Integer> maliste= factorsOf(j-i);
				    for(int k=2;k<maliste.size();k++){ //On recupère les diviseurs et on remplit la table diviseurspossibles
				    	diviseurspossibles[maliste.get(k)]+=1;
				    }
				    diviseurspossibles[j-i]+=1;
				}
			}
		}
		//for(int i=0;i<messagecrypte.length();i++) if(diviseurspossibles[i]>15) System.out.println(i+": "+diviseurspossibles[i]); // Afficher les longueurs de clefs les plus probables (à un module près ...)



		// Recherche de la longueur de la clef
		int longueurcleflaplusprobable=0;
		int taillemax=0;
		int vraiclef=0;

		for(int i=0;i<diviseurspossibles.length;i++){
			 if(taillemax<diviseurspossibles[i]){
				 longueurcleflaplusprobable=i;
				 taillemax=diviseurspossibles[i];
			 }

			vraiclef=longueurcleflaplusprobable;
			 for(int w=2;w<15;w++){
				 if(0.8*diviseurspossibles[longueurcleflaplusprobable]<diviseurspossibles[longueurcleflaplusprobable*w]) vraiclef=longueurcleflaplusprobable*w;
			 }
		}
		 System.out.println("La clef a une longueur de "+vraiclef);
		 tailleclef=vraiclef;

		 // Connaissant la taille de la clef, on peut découper le texte en autant de sous-textes, chacun d'entre eux étant obtenu par un même chiffre de César,
		 // A FAIRE / http://pageperso.lif.univ-mrs.fr/~michel.vancaneghem/deug/documents/tp4.pdf
		/* StringBuilder messagecryptecesar = new StringBuilder();
		 char temp;
		 for(int j=0;j<messagecrypte.length()/tailleclef;j++){
			 for(int i=0;i<tailleclef;i++){

				 temp = (char)((char)((int)(messagecrypte.charAt(i+tailleclef*j)-'A')%26-i)+'A');
				 	messagecryptecesar.append(temp);
			 }
		}*/
		//System.out.println(messagecryptecesar);

	}




	static List<Integer> factorsOf (int val) {

	      List<Integer> factors  = new ArrayList<Integer>();
	      for(int i=1; i <= val/2; i++)
	      {
	          if(val % i == 0)
	          {
	              factors.add(i);
	          }
	      }

	      return factors;

	  }

}
