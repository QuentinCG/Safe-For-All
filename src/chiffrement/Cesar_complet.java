package chiffrement;

/**
 * Cryptage type César complet (les 224 éléments dans la table ASCII commencant par l'element n°32)
 */
public class Cesar_complet {

	private static final int TAILLE_ALPHABET = 224; /* 224 éléments dans la table ASCII commencant par l'element n°32 (car avant : ce ne sont pas des caractères) */
	private String texte;
	
	/**
	 * Element à chiffrer/déchiffrer
	 */
	public Cesar_complet(String texte) {
		this.texte = texte;
	}
	
	/**
	 * Chiffrement César complet
	 */
	public String chiffrer(int decalage) {
		StringBuilder sb = new StringBuilder(texte.length());
		for (char c : texte.toCharArray()) {
			if (c >= ' ' && c <= 255) {
				sb.append(decaleVar(c, decalage, ' '));
			}else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Déchiffrement César complet
	 */
	public String dechiffrer(int decalage) {
		return chiffrer(-decalage);
	}

	/**
	 * Décalage de "decalage" d'un caractère "caractere"
	 */
	private static char decaleVar(char caractere, int decalage, char caractereBase) {
		int base = caractereBase;
		if (decalage < 0) {
			base += TAILLE_ALPHABET - 1;
		}
		return (char) (((caractere) - base + decalage) % TAILLE_ALPHABET + base);
	}
	/**
	public static void main(String[] args) {
		


		String messageacacher = "Bonjour, je m'appelle Quentin, J'ai 22 ans !";
		int key = 10;
		System.out.println("Cryptage de Cesar_complet :\nClef : "+key+"\nTexte à chiffrer : "+messageacacher);		

		Cesar_complet Cesar1 = new Cesar_complet(messageacacher);
		String messagechiffe=Cesar1.chiffrer(key);
		System.out.println("Message chiffré :    "+messagechiffe);
			

		Cesar_complet Cesar2 = new Cesar_complet(messagechiffe);
		String messagedechiffe=Cesar2.dechiffrer(key);
		System.out.println("Message déchiffré :  "+messagedechiffe+"\n");
	}*/
	
	
}
