package chiffrement;

/**
 * Cryptage type Vigenère amélioré (gère les majuscules et les minuscules mais ne crypte pas les autres caractères).
 */
public class Vigenere_complet {

	public String texte;

	/**
	 * Element à chiffrer/déchiffrer
	 */
	public Vigenere_complet(String texte) {
		this.texte = texte;
	}

	/**
	 * Chiffrement vigenère
	 */
    public String chiffrer(String key) {
		key=key.toUpperCase();
        String res = "";
        //String texte = this.texte.toUpperCase();

        for (int i = 0, j = 0; i < texte.length(); i++) {
            char c = texte.charAt(i);
            if(c >= 'A' && c <= 'Z')	res += (char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A');// Si l'élement est une majuscule, on crypte
            else if(c >= 'a' && c <= 'z')	res += (char)((c + key.charAt(j)+'a'-'A'/*Pour passer en minuscule*/ - 2*'a') % 26 + 'a');// Si l'élement est une minuscule, on crypte
            else res +=c; // Si l'élement n'est pas une lettre, on ne crypte pas
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
        //String texte = this.texte.toUpperCase();
        for (int i = 0, j = 0; i < texte.length(); i++) {
            char c = texte.charAt(i);
            if (c >= 'A' && c <= 'Z') res += (char)((c - key.charAt(j) + 26/*le 26 est là car java ne fait pas le modulo avec des nbrs négatifs*/) % 26 + 'A');
            else if(c >= 'a' && c <= 'z')	res += (char)((c - key.charAt(j)-'a'+'A' + 26) % 26 + 'a');
            else res +=c;
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