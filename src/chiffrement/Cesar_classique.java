package chiffrement;

/**
 * Cryptage type César (Lettres minuscules et majuscules)
 */
public class Cesar_classique {

	private static final int TAILLE_ALPHABET = 26;
	private String texte;

	/**
	 * Element à chiffrer/déchiffrer
	 */
	public Cesar_classique(String texte) {
		this.texte = texte;
	}
	
	/**
	 * Chiffrement César
	 */
	public String chiffrer(int decalage) {
		StringBuilder sb = new StringBuilder(texte.length());
		for (char c : texte.toCharArray()) {
			if (c >= 'a' && c <= 'z') {
				sb.append(decaleVar(c, decalage, 'a'));
			} else if (c >= 'A' && c <= 'Z') {
				sb.append(decaleVar(c, decalage, 'A'));
			}else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	/**
	 * Déchiffrement César
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
	
	public static void main(String[] args) {
		


		/**String messageacacher = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
		int key = 10;
		System.out.println("Cryptage de Cesar_classique :\nClef : "+key+"\nTexte à chiffrer : "+messageacacher);		

		Cesar_classique Cesar_classique1 = new Cesar_classique(messageacacher);
		String messagechiffe=Cesar_classique1.chiffrer(key);
		System.out.println("Message chiffré :    "+messagechiffe);
			

		Cesar_classique Cesar_classique2 = new Cesar_classique(messagechiffe);
		String messagedechiffe=Cesar_classique2.dechiffrer(key);
		System.out.println("Message déchiffré :  "+messagedechiffe+"\n");*/
	}
	
	
}
