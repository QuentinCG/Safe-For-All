package modeleAES;

public class AESWord128 {
	
	int byte1,byte2,byte3,byte4;
	
	public AESWord128(int b1, int b2, int b3, int b4){
		this.byte1 = b1;
		this.byte2 = b2;
		this.byte3 = b3;
		this.byte4 = b4;
	}
	
	public AESWord128(AESWord128 w){
		this.byte1 = w.byte1;
		this.byte2 = w.byte2;
		this.byte3 = w.byte3;
		this.byte4 = w.byte4;
	}
	
	public AESWord128 rotWord(){
		AESWord128 out = new AESWord128(this.byte4,this.byte2,this.byte3,this.byte1);
		return out;
	}
	
	public AESWord128 xor(AESWord128 w){
		AESWord128 out = new AESWord128(this.byte1 ^ w.byte1,this.byte2 ^ w.byte2,this.byte3 ^ w.byte3,this.byte4 ^ w.byte4);
		return out;
	}
}
