package modeleDES;
import java.nio.*;
import java.util.*;

/**
 * <p>
 * 	Cette classe regroupe la plupart des fonctions qui seront utilisees dans l'implementation de DES.
 * </p>
 */
public class DESUtil {


	/**
	 * <p> Encode le parametre en une chaine binaire utlisable par l'algolrithme de chiffrement de DES.</p>
	 * @param message Message a encoder.
	 * @return Une chaine binaire d'une representation Ascii du message.
	 * @throws Exception Erreur lancee lorsque le code ne peut s'executer correctement.
	 * @see model.DES#encode(String, String)
	 */
	public static String messageToDESBinaryString(String message) throws Exception{
		if(message==null || (message!=null && message.length()==0))
			throw new Exception("Le message a adpater est vide.");

		String param = message ;
		int sizeMessage = param.length()*8 ;
		if( (sizeMessage%DESConstant.SIXTYFOUR_BIT)!=0){
			int count = DESConstant.SIXTYFOUR_BIT - (sizeMessage%DESConstant.SIXTYFOUR_BIT) ;
			if( (count%8)!=0 ) throw new Exception("Erreur Interne :: Ne peut Spliter le message en bloc de 64 bits");
			int countSpace = (count/8);
			for(int i=0;i<countSpace;i++) param+=" ";
		}

		return Binary.intToBinaryString(DESUtil.AsciiEncoding(param));
	}

	/**
	 * <p>
	 * 	 Cree une donnee en permutant les valeurs du premier parametre selon les valeurs de table.
	 *   Condition les valeurs de table doivent aller de 0 a (param.length -1).
	 *   Principe : Pour tous i, resultat[table[i]] = param[i].
	 * </p>
	 * @param param Parametre dont les valeurs seront permuttees.
	 * @param table Tableau definissant le principe de permutation.
	 * @return  Le resultat de la permutation si aucune erreur n'est survenue, sinon null.
	 * @throws IndexOutOfBoundsException Erreur lancee si une des valeurs de la table n'est pas dans [0,param.lentgh-1];
	 */
	public static final String applyPermutationTable(String param,byte[] table) throws IndexOutOfBoundsException{
		if(param!=null && table!=null){
			char[] result = new char[table.length];
			for(int i=0;i<result.length;i++) result[i]=param.charAt(table[i]-1);
			return (new String(result));
		}
		return null ;
	}

	/**
	 * <p> Prend un tableau de byte et considere que chaqu'elt est l'encodage d'un caractere qui doit etre decode.</p>
	 * @param param Tableau de code.
	 * @return Chaine de caractere dont les codes des composantes sont ceux stockes dans param.
	 */
	public static final String AsciiDecoding(ArrayList<Integer> param) throws Exception{
		if(param==null) throw new Exception("La chaine a decoder n'est pas une chaine AScii");
		CharBuffer buffer = CharBuffer.allocate(param.size());
		for(int i=0;i<param.size();i++){
			char val = (char)(param.get(i)).intValue();
			if(val<DESConstant.MIN_ASCII || val>DESConstant.MAX_ASCII) throw new Exception("La chaine a decoder n'est pas une chaine Ascii");
			buffer.put(val);
		}
		buffer.flip();
		return buffer.toString();
	}

	/**
	 * <p>Encode la chaine passee en parametre et la retourne comme un tableau de byte.</p>
	 * @param param Chaine que l'on doit encoder
	 * @return Un tableau ou chaqu'element est l'encodage d'un caractere de la chaine.
	 */
	public static final ArrayList<Integer> AsciiEncoding(String param) throws Exception{
		if(param==null) throw new Exception("La chaine a encoder n'est pas une chaine AScii");
		ArrayList<Integer> result = new ArrayList<Integer>();
		for(int i=0;i<param.length();i++) {
			int val = param.charAt(i);
			if(val<DESConstant.MIN_ASCII || val>DESConstant.MAX_ASCII) throw new Exception("La chaine a encoder n'est pas une chaine Ascii");
			result.add(val);
		}
		return result;
	}

	/**
	 * <p>
	 *  Donne une representation du parametre binaire en chaine de caractere comprehensible.
	 *  L'on utilise messageToDESBinaryString pour retrouver le chiffre du message lisible que
	 *  l'on obtient.
	 * </p>
	 * @param message Message binaire a transformer.
	 * @return Une chaine de caractere comprehensible.
	 * @throws Exception Erreur lancee lorsque le code ne peut s'executer correctement.
	 * @see #messageToDESBinaryString(String)
	 */

	public static String binaryStringToReadableMessage(String message) throws Exception {

		if(!Binary.isBinaryRepresentation(message))
			throw new Exception("Le message a transformer n'est pas une representation binaire");

		int i=0,count=8;
		ArrayList<Integer> buffer = new ArrayList<Integer>();
		while(i<message.length()){
			buffer.add(Binary.binaryStringToInt(message.substring(i, i+count)));
			i+=count;
		}
		return DESUtil.AsciiDecoding(buffer);
	}

	/**
	 * <p>
	 * 	 Construit une cle de 56 bits avec 8 bits de parite a partir d'une chaine de caractere, de longueur
	 * 	 d'au moins 7 caracteres.
	 * </p>
	 * @param param Chaine de caractere a partir de laquelle la cle est constuite.
	 * @return Une representation binaire de 64 bits
	 * @throws Exception Erreur lance lorsqu'il est impossible d'obtenir une cle de 56 bits.
	 */
	public static String buildKey(String param) throws Exception {
		String result = null;
		if(param!=null && param.length()>DESConstant.SIX_BIT){
			result = "";
			String str = Binary.intToBinaryString(DESUtil.AsciiEncoding(param.substring(0,7)));
			if(str.length()!=DESConstant.FIFTYSIX_BIT) throw new Exception("La cle ne peut tenir sur 56 bits");
			int i=0,incr = 7,count;
			while(i<DESConstant.FIFTYSIX_BIT){
				String substr = str.substring(i,i+incr);
				count=0;
				if(substr!=null) for(int j=0;j<substr.length();j++) if(substr.charAt(j)=='0') count++;
				if((count%2)==0) substr+="1"; else substr+="0";
				result+=substr;
				i+=incr;
			}
			if(result.length()!=DESConstant.SIXTYFOUR_BIT) throw new Exception("Erreur Interne :: La cle generee ne tient pas sur 64 bits");
		}
		return result;
	}

	/**
	 * <p>Transforme un tableau de byte en un tampon de byte</p>
	 * @param param Tableau à transformer
	 * @return Un Tampon de byte.
	 */
	public static final ByteBuffer byteTableToByteBuffer(ArrayList<Byte> param){
		if(param!=null && param.size()>0){
			ByteBuffer buffer = ByteBuffer.allocateDirect(param.size());
			for(int i=0; i<buffer.limit();i++) buffer.put(param.get(i));
			buffer.flip();
			return buffer;
		}
		return null;
	}

	/**
	 * <p>Transforme un tableau de byte en un tampon de byte</p>
	 * @param param Tableau à transformer
	 * @return Un Tampon de byte.
	 */
	public static final ByteBuffer byteTableToByteBuffer(byte[] param){
		if(param!=null && param.length>0){
			ByteBuffer buffer = ByteBuffer.allocateDirect(param.length);
			for(int i=0; i<buffer.limit();i++) buffer.put(param[i]);
			buffer.flip();
			return buffer;
		}
		return null;
	}

	/**
	 * <p>Convertit 4 bytes en l'entier correspondant</p>
	 * @param param Tableau de 4 bytes à convertir
	 * @return L'entier correspondant à l'ensemble passe en parametre.
	 */
	public static final int bytesToInt(byte param[]) throws Exception{
		return 	(param[0] & 0xFF) << 24 |
				(param[1] & 0xFF) << 16 |
				(param[2] & 0xFF) << 8 |
				(param[3] & 0xFF);
	}



	/**
	 * <p> Convertit un entier en 4 bytes.</>
	 * @param param L'entier à convertir.
	 * @return Un tableau de 4 bytes representant un entier
	 */
	public static final  byte[] intToBytes(int param){
		byte res[] = new byte[4];
		res[0] = (byte)((param) >>> 24);
		res[1] = (byte)((param) >>> 16);
		res[2] = (byte)((param) >>> 8);
		res[3] = (byte)((param));
		return res;
	}

	/**
	 * <p> Dit si le parametre est une chaine de caractere generee par DES.binaryStringReadableMessage. </p>
	 * @param message Chaine de caractere a tester.
	 * @return true si la chaine est un "Chaine de caractere DES", falsse dans le cas contraire.
	 * @see #binaryStringToReadableMessage(String)
	 */
	public static boolean isDESReadableMessage(String message){
		if(message!=null && message.length()>0){
			boolean isDESMessage = true ;
			for(int i=0;i<message.length();i++){
				 if(message.charAt(i)<DESConstant.MIN_ASCII || message.charAt(i)>DESConstant.MAX_ASCII){
					 isDESMessage = false ;
					 break;
				 }
			}
			return isDESMessage;
		}
		return false;
	}

	/**
	 * <p>Effectue une rotation circulaire ou un decalage vers la gauche de count pas.</p>
	 * @param param Source du decalage.
	 * @param count Nombre de pas.
	 * @return Le premier parametre ayant subi un decalage de count pas vers la gauche.
	 * @throws IndexOutOfBoundsException Erreur lancee lorsque count>=param.length.
	 */
	public static final String leftCircularRotation(String param,int count) throws IndexOutOfBoundsException {
		if(param!=null && count>0){
			String part1 = param.substring(0,count);
			String part2 = param.substring(count);
			return new String(part2+part1);
		}
		return null ;
	}
}
