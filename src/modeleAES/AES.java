package modeleAES;


public class AES {
	
	
	final int Nb = 4;	//Nombre de colonne (toujours 4 pour AES)
	final int Nk = 4;	//Nombre de mot dans la clé (un mot = 4 octet) (4 pour AES-128)
	final int Nr = 10;	//Nombre de tour de ronde (10 pour AES-128)
	
	int key[];
	int expandKey[];

	AESWord128 rCon[];	
	
	//La boite-S pour l'operation SubBytes
	private int sBox[] = { 
	    0x63, 0x7C, 0x77, 0x7B, 0xF2, 0x6B, 0x6F, 0xC5, 0x30, 0x01, 0x67, 0x2B, 0xFE,
	    0xD7, 0xAB, 0x76, 0xCA, 0x82, 0xC9, 0x7D, 0xFA, 0x59, 0x47, 0xF0, 0xAD, 0xD4,
	    0xA2, 0xAF, 0x9C, 0xA4, 0x72, 0xC0, 0xB7, 0xFD, 0x93, 0x26, 0x36, 0x3F, 0xF7,
	    0xCC, 0x34, 0xA5, 0xE5, 0xF1, 0x71, 0xD8, 0x31, 0x15, 0x04, 0xC7, 0x23, 0xC3,
	    0x18, 0x96, 0x05, 0x9A, 0x07, 0x12, 0x80, 0xE2, 0xEB, 0x27, 0xB2, 0x75, 0x09,
	    0x83, 0x2C, 0x1A, 0x1B, 0x6E, 0x5A, 0xA0, 0x52, 0x3B, 0xD6, 0xB3, 0x29, 0xE3,
	    0x2F, 0x84, 0x53, 0xD1, 0x00, 0xED, 0x20, 0xFC, 0xB1, 0x5B, 0x6A, 0xCB, 0xBE,
	    0x39, 0x4A, 0x4C, 0x58, 0xCF, 0xD0, 0xEF, 0xAA, 0xFB, 0x43, 0x4D, 0x33, 0x85,
	    0x45, 0xF9, 0x02, 0x7F, 0x50, 0x3C, 0x9F, 0xA8, 0x51, 0xA3, 0x40, 0x8F, 0x92,
	    0x9D, 0x38, 0xF5, 0xBC, 0xB6, 0xDA, 0x21, 0x10, 0xFF, 0xF3, 0xD2, 0xCD, 0x0C,
	    0x13, 0xEC, 0x5F, 0x97, 0x44, 0x17, 0xC4, 0xA7, 0x7E, 0x3D, 0x64, 0x5D, 0x19,
	    0x73, 0x60, 0x81, 0x4F, 0xDC, 0x22, 0x2A, 0x90, 0x88, 0x46, 0xEE, 0xB8, 0x14,
	    0xDE, 0x5E, 0x0B, 0xDB, 0xE0, 0x32, 0x3A, 0x0A, 0x49, 0x06, 0x24, 0x5C, 0xC2,
	    0xD3, 0xAC, 0x62, 0x91, 0x95, 0xE4, 0x79, 0xE7, 0xC8, 0x37, 0x6D, 0x8D, 0xD5,
	    0x4E, 0xA9, 0x6C, 0x56, 0xF4, 0xEA, 0x65, 0x7A, 0xAE, 0x08, 0xBA, 0x78, 0x25,
	    0x2E, 0x1C, 0xA6, 0xB4, 0xC6, 0xE8, 0xDD, 0x74, 0x1F, 0x4B, 0xBD, 0x8B, 0x8A,
	    0x70, 0x3E, 0xB5, 0x66, 0x48, 0x03, 0xF6, 0x0E, 0x61, 0x35, 0x57, 0xB9, 0x86,
	    0xC1, 0x1D, 0x9E, 0xE1, 0xF8, 0x98, 0x11, 0x69, 0xD9, 0x8E, 0x94, 0x9B, 0x1E,
	    0x87, 0xE9, 0xCE, 0x55, 0x28, 0xDF, 0x8C, 0xA1, 0x89, 0x0D, 0xBF, 0xE6, 0x42,
	    0x68, 0x41, 0x99, 0x2D, 0x0F, 0xB0, 0x54, 0xBB, 0x16
	};
	
	
	// la boite-S pour l'operation InvSubBytes
	private int invSBox[] = {
	    0x52, 0x09, 0x6A, 0xD5, 0x30, 0x36, 0xA5, 0x38, 0xBF, 0x40, 0xA3, 0x9E, 0x81,
	    0xF3, 0xD7, 0xFB, 0x7C, 0xE3, 0x39, 0x82, 0x9B, 0x2F, 0xFF, 0x87, 0x34, 0x8E,
	    0x43, 0x44, 0xC4, 0xDE, 0xE9, 0xCB, 0x54, 0x7B, 0x94, 0x32, 0xA6, 0xC2, 0x23,
	    0x3D, 0xEE, 0x4C, 0x95, 0x0B, 0x42, 0xFA, 0xC3, 0x4E, 0x08, 0x2E, 0xA1, 0x66,
	    0x28, 0xD9, 0x24, 0xB2, 0x76, 0x5B, 0xA2, 0x49, 0x6D, 0x8B, 0xD1, 0x25, 0x72,
	    0xF8, 0xF6, 0x64, 0x86, 0x68, 0x98, 0x16, 0xD4, 0xA4, 0x5C, 0xCC, 0x5D, 0x65,
	    0xB6, 0x92, 0x6C, 0x70, 0x48, 0x50, 0xFD, 0xED, 0xB9, 0xDA, 0x5E, 0x15, 0x46,
	    0x57, 0xA7, 0x8D, 0x9D, 0x84, 0x90, 0xD8, 0xAB, 0x00, 0x8C, 0xBC, 0xD3, 0x0A,
	    0xF7, 0xE4, 0x58, 0x05, 0xB8, 0xB3, 0x45, 0x06, 0xD0, 0x2C, 0x1E, 0x8F, 0xCA,
	    0x3F, 0x0F, 0x02, 0xC1, 0xAF, 0xBD, 0x03, 0x01, 0x13, 0x8A, 0x6B, 0x3A, 0x91,
	    0x11, 0x41, 0x4F, 0x67, 0xDC, 0xEA, 0x97, 0xF2, 0xCF, 0xCE, 0xF0, 0xB4, 0xE6,
	    0x73, 0x96, 0xAC, 0x74, 0x22, 0xE7, 0xAD, 0x35, 0x85, 0xE2, 0xF9, 0x37, 0xE8,
	    0x1C, 0x75, 0xDF, 0x6E, 0x47, 0xF1, 0x1A, 0x71, 0x1D, 0x29, 0xC5, 0x89, 0x6F,
	    0xB7, 0x62, 0x0E, 0xAA, 0x18, 0xBE, 0x1B, 0xFC, 0x56, 0x3E, 0x4B, 0xC6, 0xD2,
	    0x79, 0x20, 0x9A, 0xDB, 0xC0, 0xFE, 0x78, 0xCD, 0x5A, 0xF4, 0x1F, 0xDD, 0xA8,
	    0x33, 0x88, 0x07, 0xC7, 0x31, 0xB1, 0x12, 0x10, 0x59, 0x27, 0x80, 0xEC, 0x5F,
	    0x60, 0x51, 0x7F, 0xA9, 0x19, 0xB5, 0x4A, 0x0D, 0x2D, 0xE5, 0x7A, 0x9F, 0x93,
	    0xC9, 0x9C, 0xEF, 0xA0, 0xE0, 0x3B, 0x4D, 0xAE, 0x2A, 0xF5, 0xB0, 0xC8, 0xEB,
	    0xBB, 0x3C, 0x83, 0x53, 0x99, 0x61, 0x17, 0x2B, 0x04, 0x7E, 0xBA, 0x77, 0xD6,
	    0x26, 0xE1, 0x69, 0x14, 0x63, 0x55, 0x21, 0x0C, 0x7D
	};

/****************************************************************************************************************
 * Constructeur
 ****************************************************************************************************************/
	
	/**
	 * Constructeur d'objet AES : cle, cle etendu, generation de rCon, sBox, invSBox
	 * @param cle       la cle de chiffrement
	 */
	public AES(String cle){
		key = new int[Nk * 4];
		expandKey = new int[4 * Nk * (Nr +1)];
		
		//Decoupage de la clé format texte en format integer
		for (int i=0;i< (Nk * 4);i++){
			key[i] = Integer.valueOf(cle.substring(i,i+1));
		}
		
		//Generation de rCon
		rCon = new AESWord128[16];
		genRCon();
		//Generation des clés étendus
		expandKey = keyExpansion(key);
		
	}
	
	public AES(byte cle[]){
		key = new int[Nk * 4];
		expandKey = new int[4 * Nk * (Nr +1)];
		
		//Decoupage de la clé format texte en format integer
		for (int i=0;i< (Nk * 4);i++){
			key[i] = cle[i] + 128;
		}
		
		//Generation de rCon
		rCon = new AESWord128[16];
		genRCon();
		//Generation des clés étendus
		expandKey = keyExpansion(key);
	}
	
/****************************************************************************************************************
 * Procédure de chiffrement
 ****************************************************************************************************************/
	
	/**
	 * Permet de chiffrer une suite de bytes
	 */
	public byte[] chiffrerMess(byte mess[]){
		int tmpbloc[][]= new int[4][4];
		int i = 0, j = 0;
		byte size[] = new byte[4];		
		byte res[] = new byte[((mess.length/16)*16)+32];
		
		//J'ai choisi d'ajouter un bloc contenant la taille du message d'origine car 
		//une fois chiffrer le message est de taille multiple a 16 donc il faudra la taille d'origine
		//pour dechiffrer
		size = intToBytes(mess.length);
		tmpbloc[0][0] = size[0] + 128;
		tmpbloc[0][1] = size[1] + 128;
		tmpbloc[0][2] = size[2] + 128;
		tmpbloc[0][3] = size[3] + 128;
		for(i =4;i<16;i++)
			tmpbloc[i/4][i%4] = 0;
		
		tmpbloc = chiffrerBloc(tmpbloc);
		i=0;
		addTab(res,tmpbloc,i);
		
		while (i<mess.length) {
			//Creation du bloc de 16bytes à chiffrer
			if (i+16<=mess.length){
				for (j=0; j<16;j++){
					tmpbloc[j/4][j%4] = mess[i+j] + 128;
				}
			}else{
				j=0;
				for (int k=i; k<mess.length;k++){
					tmpbloc[j/4][j%4] = mess[k] + 128;
					j++;
				}
				for (int k = j; k<16;k++)
					tmpbloc[j/4][j%4] = 0;
			}

			//Chiffrement des données
			tmpbloc = chiffrerBloc(tmpbloc);
			
			//Ajout des données au tableau de resultat
			addTab(res,tmpbloc,i+16);
			
			i+=16;
		}
		
		return res;
	}
	
	/**
	 * Permet de dechiffrer une suite de bytes 
	 */
	public byte[] dechiffrerMess(byte mess[]){
		
		byte tmpsize[] = new byte[4];
		int size = 0, pos = 0;
		int tmpbloc[][] = new int[4][4];
		
		for (int i=0;i<16;i++)
			tmpbloc[i/4][i%4] = mess[i] + 128;
		tmpbloc = dechiffrerBloc(tmpbloc);
		
		tmpsize[0] = (byte)(tmpbloc[0][0] - 128);
		tmpsize[1] = (byte)(tmpbloc[0][1] - 128);
		tmpsize[2] = (byte)(tmpbloc[0][2] - 128);
		tmpsize[3] = (byte)(tmpbloc[0][3] - 128);
		size = bytesToInt(tmpsize);
		
		byte res[] = new byte[size];
		int i = 16;
		while (i<mess.length){
			for (int j=0;j<16;j++)
				tmpbloc[j/4][j%4] = mess[i+j] + 128;
			
			tmpbloc = dechiffrerBloc(tmpbloc);
			addTab(res,tmpbloc,pos);
			pos+=16;
			i += 16;
		}
		
		return res;
	}
	
	/**
	 * Chiffre un bloc 16 bytes rangés dans un tableau [4][4]
	 */
	private int[][] chiffrerBloc(int matrice[][]){
		
		matrice = addRoundKey(matrice, getRoundKey(0));
		
		//Ronde AES
		for (int i=1; i<Nr;i++){
			matrice = subBytes(matrice);
			matrice = shiftRows(matrice);
			matrice = mixColumns(matrice);
			matrice = addRoundKey(matrice, getRoundKey(i));
		}
		matrice = subBytes(matrice);
		matrice = shiftRows(matrice);
		matrice = addRoundKey(matrice, getRoundKey(Nr));
		return matrice;
	}
	
	/**
	 * Dechiffre un bloc 
	 */
	private int[][] dechiffrerBloc(int matrice[][]){
		matrice = addRoundKey(matrice, getRoundKey(Nr));
		
		//Ronde AES
		for (int i=Nr-1; i>0; i--){
			matrice = invShiftRows(matrice);
			matrice = invSubBytes(matrice);
			matrice = addRoundKey(matrice, getRoundKey(i));
			matrice = invMixColumns(matrice);
		}
		matrice = invShiftRows(matrice);
		matrice = invSubBytes(matrice);
		matrice = addRoundKey(matrice, getRoundKey(0));
		return matrice;
	}

/****************************************************************************************************************
 * Construction des clés de rondes
 ****************************************************************************************************************/
	
	/**
	 * Applique un XOR sur chaque element de in par key 
	 * @param in	tableau 4x4 a traiter
	 * @param key	cle de cryptage
	 * @return		retourne un tableau 4x4
	 */
	private int[][] addRoundKey(int in[][], int key[])
	{
		int out[][] = new int[4][4];
		int k =0, l=0;
		
		for (int i=0;i< (4*Nk);i++)
		{
			k = i/4 % 4;
			l = i % 4;
			out[k][l] = in[k][l] ^ key[i]; 
		}
		
		return out;
	}
	
	/**
	 * Procede à la generation des clés de rondes 
	 */
	private int[] keyExpansion(int k[])
	{
		AESWord128 tmp;
		AESWord128 w[] = new AESWord128[Nk * (Nr + 1)];
		
		int t = 0, i = 0;
		int result[] = new int[4 * Nk * (Nr+1)] ;
		
		for (i=0; i< Nk; i++)
		{
			w[i] = new AESWord128(k[t],k[t+1],k[t+2],k[t+3]);
			t += 4;
		}
		
		for(i=Nk; i < (Nb * (Nr +1)); i++ )
		{
			tmp = new AESWord128(w[i-1]);
			
			if ((i % Nk) == 0){
				tmp = new AESWord128((subWord(tmp.rotWord())).xor(rCon[i / Nk]));
			}else if((Nk > 6) && (i % Nk ==4)){
				tmp = new AESWord128(subWord(tmp));
			}
			
			w[i] = new AESWord128(w[i - Nk].xor(tmp));
		}
		
		t = 0;
		i = 0;
		
		while ( i < (Nk * (Nr+1)))
		{
			result[t] = w[i].byte1;
			result[t+1] = w[i].byte2;
			result[t+2] = w[i].byte3;
			result[t+3] = w[i].byte4;
			i++;
			t+=4;
		}
		return result;
	}
	
	/**
	 * Retourne la clé de ronde de la ronde round 
	 * @round
	 */
	private int[] getRoundKey(int round)
	{
		int out[] = new int[4*Nk];
		int t= 0;
		for (int i=(round*4*Nk); i < (round*4*Nk)+(4*Nk); i++){
			out[t] = expandKey[i];
			t++;
		}
		
		return out;
		
	}
	/**
	 * Application de la sBox a un mot AES de clé de ronde
	 * @param w
	 * @return
	 */
	private AESWord128 subWord(AESWord128 w)
	{
		AESWord128 out = new AESWord128(sBox[w.byte1],sBox[w.byte2],sBox[w.byte3],sBox[w.byte4]);
		return out;
	}

/****************************************************************************************************************
 * Construction des differents tableaux utilisés
 ****************************************************************************************************************/
	/**
	 * Generation du tableau rCon utilisé pour créer les clés de rondes
	 */
	private void genRCon()
	{		
		rCon[1] = new AESWord128(1,0,0,0);	//2^0
		
		for (int i=2;i<15;i++){
			rCon[i] = new AESWord128(gf2Mult(2,rCon[i-1].byte1),0,0,0);
		}
	}	
	
/****************************************************************************************************************
 * Procédure de chiffrement
 ****************************************************************************************************************/
	
	/**
	 * Operation SubBytes, transforme les octet de in par la S-Box
	 * @param in	tableau 4x4 representant les donnees sur lesquelles ont dois effectuer une substitution
	 * @return		retourne un tableau 4x4 ou les octets sont substitues
	 */
	private int[][] subBytes(int in[][] )
	{
		int out[][] = new int[4][4];
		
		for (int i = 0; i<4;i++){
			for (int j=0;j<4;j++){
				out[i][j]= sBox[in[i][j]];
			}
		}
		
		return out;
	}
	
	/**
	 * Applique un decalage a gauche circulaire  
	 * @param in	matrice4x4 de donnees a applique le decalage circulaire e gauche
	 * @return		retourne un tableau 4x4 sur lequel un decalage cirulaire e etait effectue
	 */
	private int[][] shiftRows(int in[][])
	{
		int out[][] = new int[4][4];
		
		for (int i=0;i<4;i++){
			out[0][i]=in[0][i];
		}
		
		out[1][0] = in[1][1];
		out[1][1] = in[1][2];
		out[1][2] = in[1][3];
		out[1][3] = in[1][0];
		
		out[2][0] = in[2][2];
		out[2][1] = in[2][3];
		out[2][2] = in[2][0];
		out[2][3] = in[2][1];
		
		out[3][0] = in[3][3];
		out[3][1] = in[3][0];
		out[3][2] = in[3][1];
		out[3][3] = in[3][2];
		
		return out;
		
	}
	
	/**
	 * Traite les colonnes commes des polynomes de degres n et applique un produit matriciel sur chaque element
	 * @param in
	 * @return
	 */
	private int[][] mixColumns(int in[][])
	{
		int out[][] = new int[4][4];
		int[] sp = new int[4];
		
		   for (int c = 0; c < 4; c++) {
		      sp[0] = (gf2Mult(0x02, in[0][c])) ^ (gf2Mult(0x03, in[1][c])) ^ in[2][c] ^ in[3][c];
		      sp[1] = in[0][c] ^ (gf2Mult(0x02, in[1][c])) ^ (gf2Mult(0x03, in[2][c])) ^ in[3][c];
		      sp[2] = in[0][c] ^ in[1][c] ^ (gf2Mult(0x02, in[2][c])) ^ (gf2Mult(0x03, in[3][c]));
		      sp[3] = (gf2Mult(0x03, in[0][c])) ^ in[1][c] ^ in[2][c] ^ (gf2Mult(0x02, in[3][c]));
		      for (int i = 0; i < 4; i++) out[i][c] = sp[i];
		   }
		
		return out;
	}
	
/****************************************************************************************************************
 * Procédure de déchiffrement
 ****************************************************************************************************************/

	/**
	 * Operation inverse de SuBytes
	 * @param in	donnees e deSubytes
	 * @return		retourne un tableau 4x4 ou les donnees sont plus substitues par la S-Box
	 */
	private int[][] invSubBytes(int in[][] )
	{
		int out[][] = new int[4][4];
		
		for (int i = 0; i<4;i++){
			for (int j=0;j<4;j++){
				out[i][j]= invSBox[in[i][j]];
			}
		}
		
		return out;
	}
		
	/**
	 * Inverse de ShiftRow, donc decalage a droite
	 * @param in
	 * @return
	 */
	private int[][] invShiftRows(int in[][]){
		int out[][] = new int[4][4];
		
		for (int i=0;i<4;i++){
			out[0][i]=in[0][i];
		}
		
		out[1][1] = in[1][0];
		out[1][2] = in[1][1];
		out[1][3] = in[1][2];
		out[1][0] = in[1][3];
		
		out[2][2] = in[2][0];
		out[2][3] = in[2][1];
		out[2][0] = in[2][2];
		out[2][1] = in[2][3];
		
		out[3][3] = in[3][0];
		out[3][0] = in[3][1];
		out[3][1] = in[3][2];
		out[3][2] = in[3][3];
		
		return out;
	}	
	
	/**
	 * Inverse de MixColumns
	 * @param in
	 * @return
	 */
	private int[][] invMixColumns(int in[][])
	{
		int out[][] = new int[4][4];
		int[] sp = new int[4];
		
		   for (int c = 0; c < 4; c++) {
		      sp[0] = (gf2Mult(0x0e, in[0][c])) ^ (gf2Mult(0x0b, in[1][c])) ^
		              (gf2Mult(0x0d, in[2][c])) ^ (gf2Mult(0x09, in[3][c]));
		      sp[1] = (gf2Mult(0x09, in[0][c])) ^ (gf2Mult(0x0e, in[1][c])) ^
		              (gf2Mult(0x0b, in[2][c])) ^ (gf2Mult(0x0d, in[3][c]));
		      sp[2] = (gf2Mult(0x0d, in[0][c])) ^ (gf2Mult(0x09, in[1][c])) ^
		              (gf2Mult(0x0e, in[2][c])) ^ (gf2Mult(0x0b, in[3][c]));
		      sp[3] = (gf2Mult(0x0b, in[0][c])) ^ (gf2Mult(0x0d, in[1][c])) ^
		              (gf2Mult(0x09, in[2][c])) ^ (gf2Mult(0x0e, in[3][c]));
		      for (int i = 0; i < 4; i++) out[i][c] = sp[i];
		   }

		
		return out;
	}
	
/****************************************************************************************************************
 * Calcul dans GF(2^8)
 ****************************************************************************************************************/

	/**
	 * Calcul x * 2 soit dans GF(2^8) : (ax^7+bx^6+cx^5+dx^4+ex^3+fx^2+gx+ h) * x
	 */
	private int xtime(int x){
		x = x<<1;
		if (x >>> 8 == 1)
			x = (x ^ 0x1B) & 0xff;
		return x;
	}
	
	/**
	 * Calcul dans GF(2^8) : (ax^7+bx^6+cx^5+dx^4+ex^3+fx^2+gx+ h) * x^y
	 */
	private int x_time(int x, int y){
		if (y !=0){
			for (int i=0; i<y;i++)
				x =  xtime(x);
		}else{
			x =0;
		}
		return x;
	}
	
	/**
	 * Calcul dans GF(2^8) : x * y
	 */
	private int gf2Mult(int x, int y){
		
		int tmpres = 0;
		int res = 0;
		
		if ((y>>>7)==1){			//Si le dernier bit est a 1 alors on multiplie par x^7
			tmpres = x_time(x,7);	
			res = tmpres;
		}
		
		for(int i=1;i<7;i++){			//On test tous les bits du 6eme au 2eme
			if ((y<<24+i)>>>31 == 1){
				tmpres = x_time(x,7-i);
				res = res ^tmpres;
			}
		}
		
		if ((y<<31)>>>31 == 1)	//Si le premier bit est a 1
			res = res ^x;		//On Xor le resultat avec x
		
		return res;
		
	}
	
	
/****************************************************************************************************************
 * Opérations sur les matrices
 ****************************************************************************************************************/
	
	/**
	 * Ajoute au tableau de byte in le tableau add d'int converit en byte à partir de la position pos
	 * @param in
	 * @param add
	 * @param pos
	 */
	private void addTab(byte in[], int add[][], int pos){
		int t=0;
		for (int i=pos; i<pos+16;i++){
			if (i < in.length)
				in[i] = (byte)(add[t/4][t%4]-128);
			t++;
		}
	}
	
/****************************************************************************************************************
 * Opérations générales
 ****************************************************************************************************************/
	
	/**
	 * Convertit un entier en 4 bytes
	 */
	private byte[] intToBytes(int i){
		byte res[] = new byte[4];
		res[0] = (byte)((i) >>> 24);  	
		res[1] = (byte)((i) >>> 16);  	
		res[2] = (byte)((i) >>> 8); 
		res[3] = (byte)((i));
		return res;
	}
	
	/**
	 * Convertit 4 bytes en l'entier correspondant
	 */
	private int bytesToInt(byte b[]){
		return 	(b[0] & 0xFF) << 24 | 
				(b[1] & 0xFF) << 16 | 	
				(b[2] & 0xFF) << 8 |
				(b[3] & 0xFF);
	}

}
