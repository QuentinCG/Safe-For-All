package chiffrement;

/**
 * Cryptage type Vigen�re am�lior� (g�re les majuscules et les minuscules mais ne crypte pas les autres caract�res).
 */
public class Vigenere_complet {
	
	public String texte;
	
	/**
	 * Element � chiffrer/d�chiffrer
	 */
	public Vigenere_complet(String texte) {
		this.texte = texte;
	}
    
	/**
	 * Chiffrement vigen�re
	 */
    public String chiffrer(String key) {
		key=key.toUpperCase();
        String res = "";
        //String texte = this.texte.toUpperCase();
        
        for (int i = 0, j = 0; i < texte.length(); i++) {
            char c = texte.charAt(i);
            if(c >= 'A' && c <= 'Z')	res += (char)((c + key.charAt(j) - 2 * 'A') % 26 + 'A');// Si l'�lement est une majuscule, on crypte
            else if(c >= 'a' && c <= 'z')	res += (char)((c + key.charAt(j)+'a'-'A'/*Pour passer en minuscule*/ - 2*'a') % 26 + 'a');// Si l'�lement est une minuscule, on crypte
            else res +=c; // Si l'�lement n'est pas une lettre, on ne crypte pas
            j = ++j % key.length();
        }
        return res;
    }
    
	/**
	 * D�chiffrement vigen�re
	 */
    public String dechiffrer(String key) {
		key=key.toUpperCase();
        String res = "";
        //String texte = this.texte.toUpperCase();
        for (int i = 0, j = 0; i < texte.length(); i++) {
            char c = texte.charAt(i);
            if (c >= 'A' && c <= 'Z') res += (char)((c - key.charAt(j) + 26/*le 26 est l� car java ne fait pas le modulo avec des nbrs n�gatifs*/) % 26 + 'A');
            else if(c >= 'a' && c <= 'z')	res += (char)((c - key.charAt(j)-'a'+'A' + 26) % 26 + 'a');
            else res +=c;
            j = ++j % key.length();
        }
        return res;
    }
    
    
   /** public static void main(String[] args) {
        String key = "MACLEF";
        String messageacacher = "Beware the Jabberwock, my son! The jaws that bite, the claws that catch!";
    	System.out.println("Cryptage Vigen�re : \nClef : "+key+"\nTexte � chiffrer : "+messageacacher);
       
    	Vigenere achiffrer = new Vigenere(messageacacher);
        String enc = achiffrer.chiffrer(key);
        System.out.println("Message chiffr� : "+enc);
        Vigenere adechiffrer = new Vigenere(enc);
        System.out.println("Message d�chiffr� : "+adechiffrer.dechiffrer(key)+"\n");
    }*/
    
}