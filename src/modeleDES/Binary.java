package modeleDES;
import java.util.*;

/**
 *	<p>Binary est une classe gerant les transformations entiers-binaire et binaire-entiers</p>
 */

public class Binary {

	/**
	 * <p>
	 * 	Si le parametre est une representation d'un nombre binaire, elle retourne un buffer de byte. Selon le
	 *  principe que un caractere est code sur 8 bits.
	 * </p>
	 * @param param Representation binaire a convertir en byte
	 * @return Valeur decimal du parametre
	 * @throws Exception Erreur lancee lorsque le code ne peut s'executer correctement.
	 */
	public static final ArrayList<Integer> binaryStringToIntTable(String param) throws Exception {
		if(!Binary.isBinaryRepresentation(param)) throw new Exception ("Erreur Lors de la conversion binaire-entier");

		if( (param.length()%8)!=0)
			throw new Exception("La chaine binaire ne peut etre converti en suite d'entiers");

		int i = 0, incr = 8 ;
		ArrayList<Integer> buffer = new ArrayList<Integer>();
		while(i<param.length()){
			int end  = ( (i+incr)<param.length() )? (i+incr):param.length() ;
			String substr = param.substring(i,end);
			int val = binaryStringToInt(substr);
			if(val<DESConstant.MIN_ASCII || val>DESConstant.MAX_ASCII) throw new Exception("Erreur La chaine binaire ne peut etre converti en Suite d'entiers");
			buffer.add(val);
			i+=incr;
		}
		return buffer;
	}

	/**
	 * <p>Retourne la conversion en decimal du parametre si il existe, sinon -1</p>
	 * @param param Representation Binaire
	 * @return La conversion en decimal du parametre.
	 * @throws Exception Erreur lancee si la consversion n'a pu se faire
	 */
	public static final int binaryStringToInt(String param)throws Exception {
		if(!Binary.isBinaryRepresentation(param)) throw new Exception ("Erreur Lors de la conversion binaire-entier");
		int pow = 0;
		int result=0 ;
		for(int i=param.length()-1;i>=0;i--) {
			result+= (param.charAt(i)=='1'? 1:0)* ((int)Math.pow(2, pow)) ;
			pow++;
		}
		return result ;
	}


	/**
	 * <p> Converti le parametre a une chaine binaire.</p>
	 * @param param Parametre dont on veut obtenir une representation binaire.
	 * @return null si la conversion est impossible, sinon la representation binaire du parametre.
	 */
	public static final String intToBinaryString(ArrayList<Integer> param){
		String result = null ;
		if(param!=null && param.size()>0){
			result = "";
			for(int i=0;i<param.size();i++){
				result+=Binary.intToBinaryString(param.get(i));
			}
		}
		return result;
	}

	/**
	 * <p>Retourne une chaine representant la conversion binaire du parametre.</p>
	 * @param param Parametre a convertir.
	 * @return La conversion binaire de param.
	 */
	public static final String intToBinaryString(int param){
		String result = null;
		if(param>=0){
			result="";
			int val = param ;
			while(val>1){
				result= (val%2)+ result;
				val/=2;
			}
			result= val+result ;
			int size = 8-result.length() ;
			for(int i=1;i<=size;i++) result="0"+result;
		}
		return result;
	}

	/**
	 * <p>Dit si le prametre est la representation d'un nombre binaire.</p>
	 * @param param Chaine a verifier.
	 * @return True si param est un nombre binaire et False dans le cas contraire.
	 */
	public static final boolean isBinaryRepresentation(String param){
		if(param!=null && param.length()>0){
			boolean result = true ;
			for(int i=0;i<param.length();i++){
				if(param.charAt(i)!='0' && param.charAt(i)!='1'){
					result = false ;
					break;
				}
			}
			return result ;

		}else return false ;
	}

	/**
	 * <p>Calcule le XOR de a et b. A condition que a et b soit des representations de nombres binaires.</p>
	 * @param a Operande 1
	 * @param b Operande 2
	 * @return Ou( et(a,non(b)), et(non(a),b)) si la taille de a est identique a la taille de b et a et b sont different de null, sinon null.
	 * @throws Exception Erreur lancee,entre autre, lorsque les parametres ne sont pas des chaines Binaire.
	 */
	public static final String xor(String a, String b)throws Exception {
		String result = null ;
		if(!Binary.isBinaryRepresentation(a) || !Binary.isBinaryRepresentation(b)) throw new Exception ("Erreur lors de l'application de Xor.Un parametre n'est pas une chaine binaire");

		result = "";
		String param1 = a ;
		String param2 = b;

		if(param1.length()<param2.length()) for(int i=0;i<(param2.length()-param1.length());i++) param1="0"+param1 ;
		else if(param2.length()<param1.length()) for(int i=0;i<(param1.length()-param2.length());i++) param2="0"+param2 ;

		for(int i=0;i<param1.length();i++)
			result+= Integer.parseInt(""+param1.charAt(i))^Integer.parseInt(""+param2.charAt(i));

		return result ;
	}

}
