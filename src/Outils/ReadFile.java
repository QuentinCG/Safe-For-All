package Outils;
import java.io.*;

public class ReadFile {


	public String chaine;
	/**
	 *  Ouvrir un fichier contenant un String
	 * 	ReadFile monfichier =new ReadFile("monfichier.txt"); // Creation de l'instance qui recupère ce qu'il y a dans le fichier
	 *  System.out.println(monfichier.chaine); // Ce qui se trouve dans le fichier se trouve dans "monfichier.chaine"
	 */
	public ReadFile(String fichier){
		try{
			chaine="";
			InputStream ips=new FileInputStream(fichier);
			InputStreamReader ipsr=new InputStreamReader(ips);
			BufferedReader br=new BufferedReader(ipsr);
			String ligne;
			while ((ligne=br.readLine())!=null){
				chaine+=ligne+"\n";
			}
			br.close();
			System.out.println("Lecture du fichier "+fichier+" reussi !");
		}
		catch (Exception e){
			System.out.println(e.toString());
		}
	}



}

