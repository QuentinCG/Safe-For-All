package chiffrement;

/**
 * Cryptage type Vigenère
 */
public class Vigenere {

	public String texte;

	/**
	 * Element à chiffrer/déchiffrer
	 */
	public Vigenere(String texte) {
		this.texte = texte;
	}

	/**
	 * Chiffrement vigenère
	 */
    public String chiffrer(String key) {
		key=key.toUpperCase();
        String res = "";
        String texte = this.texte.toUpperCase();

        for (int i = 0, j = 0; i < texte.length(); i++) {
            char c = texte.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            res += (char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }

	/**
	 * Déchiffrement vigenère
	 */
    public String dechiffrer(String key) {
		key=key.toUpperCase();
        String res = "";
        String texte = this.texte.toUpperCase();
        for (int i = 0, j = 0; i < texte.length(); i++) {
            char c = texte.charAt(i);
            if (c < 'A' || c > 'Z') continue;
            res += (char)((c - key.charAt(j) + 26) % 26 + 'A');
            j = ++j % key.length();
        }
        return res;
    }


   /** public static void main(String[] args) {
        String key = "MACLEF";
        String messageacacher = "Beware the Jabberwock, my son! The jaws that bite, the claws that catch!";
    	System.out.println("Cryptage Vigenère : \nClef : "+key+"\nTexte à chiffrer : "+messageacacher);

    	Vigenere achiffrer = new Vigenere(messageacacher);
        String enc = achiffrer.chiffrer(key);
        System.out.println("Message chiffré : "+enc);
        Vigenere adechiffrer = new Vigenere(enc);
        System.out.println("Message déchiffré : "+adechiffrer.dechiffrer(key)+"\n");
    }*/

}