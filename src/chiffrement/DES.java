package chiffrement;

import java.util.ArrayList;
import modeleDES.Binary;
import modeleDES.DESConstant;
import modeleDES.DESUtil;

/**
 * DES 56 bits
 */
public class DES {

	/**
	 * La clé est sur 56 bits, soit 7 caractères ASCII.
	 */
	public static final String encode(String message,String key) throws Exception{

		if(message==null || message.length()==0) throw new Exception("Le message a chiffrer est vide.");
		if(!Binary.isBinaryRepresentation(message))throw new Exception("Le message a chiffrer n'est pas sur sa forme binaire.");
		if(key==null) throw new Exception("La cle du chiffrement est vide.");
		if(key.length()<7) throw new Exception("La cle du chiffrement doit etre de longueur superieur ou egale à 7.");

		String result = "" ;
		ArrayList<String> subMessages = DES.split(message); /* Découpe le message en sous messages de 64 bits */
		ArrayList<String> keys = DES.genereateKeySet(DESUtil.buildKey(key));

		for(int numMessage=0;numMessage<subMessages.size();numMessage++){
			String subMessage = subMessages.get(numMessage);
			String aux;
			int i= 0,splitPosition=DESConstant.THIRTYTWO_BIT;
			aux = DESUtil.applyPermutationTable(subMessage, DESConstant.IP);
			String Li,Ri ;
			Li = aux.substring(0, splitPosition);
			Ri = aux.substring(splitPosition);

			for(i=1;i<=16;i++){
				String Riplus1 = Binary.xor(Li, DES.f(Ri, keys.get(i-1)));//Riplus1 equivaut a R(i+1)
				Li = Ri ; // Li devient Li+1 avec pour valeur Ri
				Ri = Riplus1;
			}

			String chiffre = DESUtil.applyPermutationTable(Ri+Li, DESConstant.INVERSE_IP);
			result+=chiffre ;
		}
		return result;
	}

	/**
	 * <p>Dechiffre le message code avec DES.encode(String,string) et la cle key</p>
	 * @param message Message a dechiffrer sous forme binaire.
	 * @param key Cle utilise pour obtenir le chiffre message. Cle de taille au moins superieur a 7 caracteres
	 * @return Le message dechiffre, si les parametres en entree sont corrects sinon null.
	 * @throws Exception Erreur lancee lorsque le code ne peut s'executer correctement.
	 * @see #encode(String, String)
	 */


	public static final String decode(String message,String key) throws Exception{

		if(message==null || message.length()==0) throw new Exception("Le message a dechiffrer est vide.");
		if(!Binary.isBinaryRepresentation(message))throw new Exception("Le message a dechiffrer n'est pas sur sa forme binaire.");
		if(key==null) throw new Exception("La cle du dechiffrement est vide.");
		if(key.length()<7) throw new Exception("La cle du dechiffrement doit etre de longueur superieur ou egale a 7.");

		String result = "" ;
		ArrayList<String> subMessages = DES.split(message);
		ArrayList<String> keys = DES.genereateKeySet(DESUtil.buildKey(key));

		for(int numMessage=0;numMessage<subMessages.size();numMessage++){
			String subMessage = subMessages.get(numMessage);
			String aux;
			int i= 16,splitPosition=DESConstant.THIRTYTWO_BIT;
			aux = DESUtil.applyPermutationTable(subMessage, DESConstant.IP);
			String Li,Ri ;
			Ri = aux.substring(0, splitPosition);
			Li = aux.substring(splitPosition);

			for(i=15;i>=0;i--){
				String Rimoins1 = Li;
				Li =  Binary.xor(Ri,DES.f(Rimoins1, keys.get(i)));//Li devient Li-1
				Ri = Rimoins1; // Ri devient Rimoins1
			}

			String chiffre = DESUtil.applyPermutationTable(Li+Ri, DESConstant.INVERSE_IP);
			result+=chiffre ;
		}
		return result;
	}

	/**
	 * <p>
	 * 	Genere un ensemble de 16 sous cles de 48 bits chacun, a partir de la cle key.	 *
	 * </p>
	 * @param key Cle tenant sur 64 bits avec 8 bits de parite. C'est la representation binaire de la cle qui est passe.
	 * @return Un ensemble de 16 sous-cles de 48 bits chacun sous leur forme binaire, si les parametres en entree sont corrects sinon null.
	 * @throws Exception Erreur lancee lorsque le code ne peut s'executer correctement.
	 */
	public static final ArrayList<String> genereateKeySet(String key) throws Exception {

		if(!Binary.isBinaryRepresentation(key) || key.length()!=DESConstant.SIXTYFOUR_BIT)
			throw new Exception("La cle passee en parametre n'est pas une representtation binaire\n " +
								"ou ne tient pas sur 64 bits");

		ArrayList<String> list = new ArrayList<String> ();
		int splitPosition = 28 ; // de 0 a 27 --> ci et de 28 a 55 di
		int i=0;
		String Ci,Di,aux;

		aux = DESUtil.applyPermutationTable(key, DESConstant.PC_1);
		Ci = aux.substring(0, splitPosition);
		Di = aux.substring(splitPosition, aux.length());

		for(i=1;i<=16;i++){
			int pas = (i==1 || i==2 || i==9 || i==16)? 1:2;
			Ci = DESUtil.leftCircularRotation(Ci, pas);
			Di = DESUtil.leftCircularRotation(Di, pas);
			String Ki = DESUtil.applyPermutationTable(Ci+Di, DESConstant.PC_2);
			list.add(Ki);
		}

		if(list.size()!=16) throw new Exception("Erreur Interne:: La cardinalite de l'ensemble des cles partielles est different de 16");
		return list;
	}

	/**
	 * <p>
	 *   Fonction principal pour le chiffrement et le dechiffrement. Les deux parametre doivent etre sous leur forme
	 *   binaire.
	 * </p>
	 * @param param Operande de 32bits sous forme binaire. Exemple: 01100001011000100110001101100100 est la representation ASCII en binaire de "abcd" tenant sur 32bits ou de longueur 32.
	 * @param key Cle partielle de 48 bits.
	 * @return Une valeur de 32 bits sous sa forme binaire, si les parametres en entree sont corrects sinon null.
	 * @throws Exception Erreur lancee lorsque le code ne peut s'executer correctement.
	 */
	public static final String f(String param,String key) throws Exception{

		if(!Binary.isBinaryRepresentation(param)) throw new Exception("Le premier parametre de f n'est pas sur sa forme binaire");
		if(param.length()!=DESConstant.THIRTYTWO_BIT) throw new Exception("Le premier parametre de f ne tient pas sur 32 bits");

		if(!Binary.isBinaryRepresentation(key)) throw new Exception("La cle partielle utilisee dans f n'est pas sur sa forme binaire");
		if(key.length()!=DESConstant.FORTYEIGHT_BIT) throw new Exception("La cle partielle utilisee dans f ne tient pas sur 48 bits");

		String result = "" ;
		String paramExpansion = DESUtil.applyPermutationTable(param, DESConstant.EXPANSION);
		String aux = Binary.xor(paramExpansion, key);
		int j=0, incr = DESConstant.SIX_BIT;
		for(int i=1;i<=8;i++){
			String Bi = aux.substring(j, j+incr); //Bloc i
			String Ci = DES.sboxes(i, Bi); //Chiffre du bloc i
			result+=Ci;
			j+=incr;
		}
		result = DESUtil.applyPermutationTable(result, DESConstant.P);
		if(result.length()!=DESConstant.THIRTYTWO_BIT)
			throw new Exception("Erreur Interne :: Le resultat de l'application de f ne tient pas sur 32 bits.");
		return result ;
	}

	/**
	 * <p>Determine la valeur du s-boxe de numero numSBoxe associe au parametre param.</p>
	 * @param numSBoxe Numero du S-Boxe compris entre 1 et 8
	 * @param param Chaine binaire de 6 bits du genre "001011"
	 * @return Une chaine binaire tenat sur 4 bits, si les parametres sont correctes dans le cas contraire null.
	 * @throws Exception Erreur lancee lorsque le code ne peut s'executer correctement.
	 */
	public static final String sboxes(int numSBoxe,String param) throws Exception{

		if(numSBoxe<1 || numSBoxe>8) throw new Exception("Le numero de la S-Boxe est incorrect");
		if(!Binary.isBinaryRepresentation(param)|| param.length()!=DESConstant.SIX_BIT )
			throw new Exception(param+" n'est pas une representation binaire tenant sur 6 bits");

		int row = 2*Integer.parseInt(""+param.charAt(0))+Integer.parseInt(""+param.charAt(5));
		int column = Binary.binaryStringToInt(param.substring(1,5)) ;
		byte value = -1 ;

		switch(numSBoxe){
			case 1 :  value = DESConstant.SBOXE_1[row][column]; break;
			case 2 :  value = DESConstant.SBOXE_2[row][column]; break;
			case 3 :  value = DESConstant.SBOXE_3[row][column]; break;
			case 4 :  value = DESConstant.SBOXE_4[row][column]; break;
			case 5 :  value = DESConstant.SBOXE_5[row][column]; break;
			case 6 :  value = DESConstant.SBOXE_6[row][column]; break;
			case 7 :  value = DESConstant.SBOXE_7[row][column]; break;
			case 8 :  value = DESConstant.SBOXE_8[row][column]; break;
		}
		if(value==-1) throw new Exception("Erreur lors du calcul de S_Boxe("+numSBoxe+","+param+")");

		String result = Binary.intToBinaryString(value) ;
		String substr = result.substring(0,4), val = result.substring(4);

		if(substr.compareTo("0000")!=0 || val==null || val.length()!=4)
			throw new Exception("Erreur Interne lors du calcul de S_Boxe"+numSBoxe+"("+param+").\n" +
					  "Avec ligne = "+row+" et colonne = "+column);
		return val;
	}




	/**
	 * <p>
	 *  Decouper le message initial en n sous-message pouvant tenir, chacun sur 64 bits.
	 *  En considereant le message code en Ascii binaire tel que sa taille soit un multiple de 64.
	 * </p>
	 * @param message Message a decouper sous forme binaire.
	 * @return Ensemble de sous-chaine, chacun sous sa forme binaire et tenant sur 64 bits.
	 * @throws Exception Erreur lancee lorsque le code ne peut s'executer correctement.
	 */
	public static final ArrayList<String> split(String message) throws Exception{
		if(!Binary.isBinaryRepresentation(message))
			throw new Exception("Le message a spliter n'est pas une chaine de caractere binaire");
		if((message.length()%64)!=0)
			throw new Exception("Le message a spliter n'a pas pour taille un multiple de 64");

		ArrayList<String> list = new ArrayList<String>();
		int i=0, incr = DESConstant.SIXTYFOUR_BIT;
		while(i<message.length()){
			String substr = message.substring(i, i+incr);
			list.add(substr);
			i+=incr;
		}
		return list ;
	}

	/*
	public static void main(String[] arg) throws Exception{

		String messageacacher4="Coucou, c'est quentin ! un gars de 22 ans !";
		String key4 = "Aaù4-9wq";
		System.out.println("Cryptage DES :\nClef : "+key4+"\nTexte à chiffrer :   "+messageacacher4);


		String binary = DESUtil.messageToDESBinaryString(messageacacher4);
		String crypted = DES.encode(binary, key4);
		String valueCrypted = DESUtil.binaryStringToReadableMessage(crypted);
		System.out.println("Message chiffré :    "+valueCrypted);

		String binaireValue = DESUtil.messageToDESBinaryString(valueCrypted);
		String discrypted = DES.decode(binaireValue, key4);
		System.out.println("Message déchiffré :  "+(DESUtil.AsciiDecoding(Binary.binaryStringToIntTable(discrypted)))+"\n");
	}*/
}
