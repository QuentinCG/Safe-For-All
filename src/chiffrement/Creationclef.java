package chiffrement;

import java.util.Random;


public class Creationclef {

	private String type_de_cryptage;
	public static final String BOUTTON_AES = "AES 128 bits";
	public static final String BOUTTON_DES = "DES 56 bits";
	public static final String BOUTTON_VIGENERE = "Vigenère";
	public static final String BOUTTON_CESAR_AMELIORE = "César amélioré";
	public static final String BOUTTON_CESAR_CLASSIQUE = "César classique";
	public static final String BOUTTON_RSA = "RSA";
	public static final String BOUTTON_VIGENERE_AMELIORE = "Vigenère amélioré";


	public Creationclef(String cryptage){
		type_de_cryptage = cryptage;
	}


	public String clefAES(){
		if(this.type_de_cryptage==BOUTTON_AES){
			Random r = new Random();
			return (char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256);

		}
		else return "Probleme de création de la clef";
	}

	public String clefDES(){
		if(this.type_de_cryptage==BOUTTON_DES){
			Random r = new Random();
			return (char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256)+""+(char)r.nextInt(256);
		}
		else return "Probleme de création de la clef";
	}

	public String clefCESARAMELIORE(){
		if(this.type_de_cryptage==BOUTTON_CESAR_AMELIORE){
			Random r = new Random();
			int nombre = r.nextInt(224);
			int centaine;
			int dizaine;
			int unite;
			if (nombre>=200) centaine=2;
			else if(nombre>=100) centaine=1;
			else centaine=0;

			if (nombre-100*centaine>=90) dizaine=9;
			else if(nombre-100*centaine>=80) dizaine=8;
			else if(nombre-100*centaine>=70) dizaine=7;
			else if(nombre-100*centaine>=60) dizaine=6;
			else if(nombre-100*centaine>=50) dizaine=5;
			else if(nombre-100*centaine>=40) dizaine=4;
			else if(nombre-100*centaine>=30) dizaine=3;
			else if(nombre-100*centaine>=20) dizaine=2;
			else if(nombre-100*centaine>=10) dizaine=1;
			else dizaine=0;

			if (nombre-100*centaine-10*dizaine>=9) unite=9;
			else if(nombre-100*centaine-10*dizaine>=8) unite=8;
			else if(nombre-100*centaine-10*dizaine>=7) unite=7;
			else if(nombre-100*centaine-10*dizaine>=6) unite=6;
			else if(nombre-100*centaine-10*dizaine>=5) unite=5;
			else if(nombre-100*centaine-10*dizaine>=4) unite=4;
			else if(nombre-100*centaine-10*dizaine>=3) unite=3;
			else if(nombre-100*centaine-10*dizaine>=2) unite=2;
			else if(nombre-100*centaine-10*dizaine>=1) unite=1;
			else unite=0;

			if(centaine!=0) return (char)(48+centaine)+""+(char)(48+dizaine)+""+(char)(48+unite);
			else if(centaine==0 && dizaine!=0) return (char)(48+dizaine)+""+(char)(48+unite);
			else if(centaine==0 && dizaine==0) return Character.toString((char)(48+unite));
			else return "Probleme de création de la clef";
		}
		else return "Probleme de création de la clef";
	}

	public String clefVIGENERE(){
		if(this.type_de_cryptage==BOUTTON_VIGENERE){
			Random r = new Random();
			return (char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26));
		}
		else return "Probleme de création de la clef";
	}

	public String clefVIGENEREAMELIORE(){
		if(this.type_de_cryptage==BOUTTON_VIGENERE_AMELIORE){
			Random r = new Random();
			return (char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26))+""+(char)('A'+r.nextInt(26));
		}
		else return "Probleme de création de la clef";
	}

	public String clefCESARCLASSIQUE(){
		if(this.type_de_cryptage==BOUTTON_CESAR_CLASSIQUE){
			Random r = new Random();
			int nombre = r.nextInt(26);
			int dizaine;
			int unite;
			if (nombre>=20) dizaine=2;
			else if(nombre>=10) dizaine=1;
			else dizaine=0;

			if (nombre-10*dizaine>=9) unite=9;
			else if(nombre-10*dizaine>=8) unite=8;
			else if(nombre-10*dizaine>=7) unite=7;
			else if(nombre-10*dizaine>=6) unite=6;
			else if(nombre-10*dizaine>=5) unite=5;
			else if(nombre-10*dizaine>=4) unite=4;
			else if(nombre-10*dizaine>=3) unite=3;
			else if(nombre-10*dizaine>=2) unite=2;
			else if(nombre-10*dizaine>=1) unite=1;
			else unite=0;

			if(dizaine!=0) return (char)(48+dizaine)+""+(char)(48+unite);
			else return Character.toString((char)(48+unite));
		}
		else return "Probleme de création de la clef";
	}


}
