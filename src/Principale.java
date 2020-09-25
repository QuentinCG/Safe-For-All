

public class Principale {

	/**
	 * @param args
	 * @throws Exception
	 */

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub




		new Fenetre("Safe for all v1.0 (Programme de cryptologie en Java cr�� par Quentin Comte-Gaz)");



		/* Sauvegarde d'un fichier */
		/*
		String monadresse= "D:\\Java\\Projet_Cryptologie\\monfichier.txt";
		String moncontenu= "Kikou (ligne1)\nLigne 2\nLigne 3 : azertyuiopqsdfghjklmwxcvbn1234567890\n\n";
		SaveFile sauvegarde = new SaveFile(monadresse,moncontenu);
		*/

		/* Vigen�re : */
		/*
		String key = "CLEFdeceTYPE";
        String messageacacher = "Mon super site : Http://quentincg.free.fr/ !";
    	System.out.println("Cryptage Vigen�re : \nClef : "+key+"\nTexte � chiffrer : "+messageacacher);
    	Vigenere achiffrer = new Vigenere(messageacacher);
        String enc = achiffrer.chiffrer(key);
        System.out.println("Message chiffr� : "+enc);

        Vigenere adechiffrer = new Vigenere(enc);
        System.out.println("Message d�chiffr� : "+adechiffrer.dechiffrer(key)+"\n");
        */

		/* Vigen�re am�lior� : */
		/*
		String key = "CLEFDECETYPE";
        String messageacacher = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789&�\"'(-�_��Mon super site : Http://quentincg.free.fr/ !";
    	System.out.println("Cryptage Vigen�re : \nClef :             "+key+key+key+key+key+key+key+key+"\nTexte � chiffrer : "+messageacacher);
    	Vigenere_complet achiffrer = new Vigenere_complet(messageacacher);
        String enc = achiffrer.chiffrer(key);
        System.out.println("Message chiffr� :  "+enc);

        Vigenere_complet adechiffrer = new Vigenere_complet(enc);
        System.out.println("Message d�chiffr� :"+adechiffrer.dechiffrer(key)+"\n");

		 */




        /* C�sar de base (uniquement les lettres minuscules et majuscules) : */

        /*String messageacacher2 = "Mon super site : Http://quentincg.free.fr/ !";
		int key2 = 10;
		System.out.println("Cryptage de C�sar classique :\nClef : "+key2+"\nTexte � chiffrer : "+messageacacher2);

		Cesar_classique cesar1 = new Cesar_classique(messageacacher2);
		String messagechiffe2=cesar1.chiffrer(key2);
		System.out.println("Message chiffr� :    "+messagechiffe2);

		Cesar_classique cesar2 = new Cesar_classique(messagechiffe2);
		String messagedechiffe2=cesar2.dechiffrer(key2);
		System.out.println("Message d�chiffr� :  "+messagedechiffe2+"\n");*/


		/* Cesar am�lior� (Tout les caract�res de la table ASCII sans compter les 32 premiers) : */

		/*String messageacacher3 = "Mon super site : Http://quentincg.free.fr/ !";
		int key3 = 10;
		System.out.println("Cryptage de Cesar am�lior� :\nClef : "+key3+"\nTexte � chiffrer : "+messageacacher3);

		Cesar_complet cesar3 = new Cesar_complet(messageacacher3);
		String messagechiffe3=cesar3.chiffrer(key3);
		System.out.println("Message chiffr� :    "+messagechiffe3);

		Cesar_complet cesar4 = new Cesar_complet(messagechiffe3);
		String messagedechiffe3=cesar4.dechiffrer(key3);
		System.out.println("Message d�chiffr� :  "+messagedechiffe3+"\n");*/


		/* DES 56 bits */

		/*String messageacacher4="Mon super site : Http://quentincg.free.fr/ !";
		String key4 = "Aa�4-9wq";
		System.out.println("Cryptage DES 54 bits :\nClef : "+key4+"\nTexte � chiffrer :   "+messageacacher4);

		String messagechiffe4 = DESUtil.binaryStringToReadableMessage(DES.encode(DESUtil.messageToDESBinaryString(messageacacher4), key4));
		System.out.println("Message chiffr� :    "+messagechiffe4);

		String messagedechiffe4 = (DESUtil.AsciiDecoding(Binary.binaryStringToIntTable(DES.decode(DESUtil.messageToDESBinaryString(messagechiffe4), key4))));
		System.out.println("Message d�chiffr� :  "+messagedechiffe4+"\n");	*/

		/* AES 128 bits */
		/*
		AES aes;
		byte b[]= null;
		String messageacacher5="Message d�crypt�dfgdf dfgdfg";
		String key5 = "Clef de cryptage"; //Il faut une cl� de 16 octets dans la version 128bits (16*8) de AES
		System.out.println("Cryptage AES 128 bits :\nClef : "+key5+"\nTexte � chiffrer :   "+messageacacher5);

		aes = new AES(key5.getBytes());		//Construction de l'objet AES
		b = messageacacher5.getBytes();		//Chaine a chiffrer sous forme de byte
		b = aes.chiffrerMess(b);			//Chiffrement du message
		String messagechiffe5 = new String(b);
		System.out.println("Message chiffr� :    "+messagechiffe5);

		String messagedechiffe5 = new String(aes.dechiffrerMess(b));	//D�chiffrement du message chiffr�
		System.out.println("Message d�chiffr� :  "+messagedechiffe5+"\n");*/

		/* RSA */
			/* On choisit ici la taille de la cl� (ici : 2048 bits) */
		/*RSA rsa = new RSA(2048);
		String messageacacher6 = "Mon super site : Http://quentincg.free.fr/ !";
		System.out.println("Cryptage RSA xxxx bits (ici, 2048 bits) :\nTexte � chiffrer :   "+messageacacher6);

		byte c[] = rsa.chiffrer(messageacacher6.getBytes());
		System.out.println("Clef publique : "+rsa.getPublicKey());
		System.out.println("Clef priv�e : "+rsa.getPrivateKey());
		String messagechiffe6=new String(c);
		System.out.println("Message chiffr� :    "+messagechiffe6);

		c = rsa.dechiffrer(c);
		String messagedechiffe6 = new String(c);
		System.out.println("Message d�chiffr� :  "+messagedechiffe6+"\n");*/

	}

}
