import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import modeleAES.AES;
import modeleDES.Binary;
import modeleDES.DESUtil;
import Outils.ReadFile;
import Outils.SaveFile;
import chiffrement.Attaque_cesar_classique;
import chiffrement.Attaque_cesar_complet;
import chiffrement.Attaque_vigenere;
import chiffrement.Cesar_classique;
import chiffrement.Cesar_complet;
import chiffrement.Creationclef;
import chiffrement.DES;
import chiffrement.Vigenere;
import chiffrement.Vigenere_complet;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Fenetre extends JFrame implements ActionListener, MouseListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
/* Variables statiques pour les images */
public static final String NOUVEAU_IMG = "nouveau.png";
public static final String OUVRIR_IMG = "ouvrir.png";
public static final String OUVRIR_FICHIER_IMG = "ouvrir_fichier.png";
public static final String OUVRIR_CLEF_IMG = "ouvrir_clef.png";
public static final String OUVRIR_FICHIER_CRYPTE_IMG = "ouvrir_fichier_crypte.png";
public static final String ENREGISTRER_IMG = "enregistrer.png";
public static final String ENREGISTRER_CLEF_IMG = "enregistrer_clef.png";
public static final String ENREGISTRER_FICHIER_IMG = "enregistrer_fichier.png";
public static final String ENREGISTRER_FICHIER_CRYPTE_IMG = "enregistrer_fichier_crypte.png";
public static final String ENREGISTRERSOUS_IMG = "enregistrersous.png";
public static final String IMPRIMER_IMG = "imprimer.png";
public static final String EXPORTER_PDF_IMG = "pdf.png";
public static final String QUITTER_IMG = "quitter.png";
public static final String ANNULER_IMG = "annuler.png";
public static final String RETABLIR_IMG = "retablir.png";
public static final String COPIER_IMG = "copier.png";
public static final String COUPER_IMG = "couper.png";
public static final String COLLER_IMG = "coller.png";
public static final String AIDE_IMG = "aide.png";
public static final String APROPOS_IMG = "apropos.png";
public static final String CRYPTAGE_IMG = "cryptage.png";
public static final String LOGO_IMG = "logo.png";

/* Variables statiques pour le menu */
public static final String NOUVEAU = "Nouveau projet";
public static final String OUVRIR_CLEF = "Ouvrir une clef";
public static final String OUVRIR_FICHIER = "Ouvrir un fichier non cypté";
public static final String OUVRIR_FICHIER_CRYPTE = "Ouvrir un fichier crypté";
public static final String ENREGISTRERSOUS_CLEF = "Enregistrer la clef";
public static final String ENREGISTRERSOUS_FICHIER = "Enregistrer le fichier non crypté";
public static final String ENREGISTRERSOUS_FICHIER_CRYPTE = "Enregistrer le fichier crypté";
public static final String IMPRIMER = "Imprimer";
public static final String EXPORTER_PDF = "Exporter le projet au format PDF";
public static final String QUITTER = "Quitter";
public static final String ANNULER = "Annuler";
public static final String RETABLIR = "Retablir";
public static final String COPIER = "Copier";
public static final String COUPER = "Couper";
public static final String COLLER = "Coller";
public static final String AIDE = "Aide";
public static final String APROPOS = "A propos";
public static final String BOUTTON_AES = "AES 128 bits";
public static final String BOUTTON_DES = "DES 56 bits";
public static final String BOUTTON_VIGENERE = "Vigenère";
public static final String BOUTTON_VIGENERE_AMELIORE = "Vigenère amélioré";
public static final String BOUTTON_CESAR_AMELIORE = "César amélioré";
public static final String BOUTTON_CESAR_CLASSIQUE = "César classique";
public static final String BOUTTON_RSA = "RSA";
public static final String BOUTTON_LANCER_CRYPTAGE = "Lancer le cryptage";
public static final String BOUTTON_LANCER_DECRYPTAGE = "Lancer le décryptage";
public static final String CHERCHER_CLEF = "Chercher la clef de cryptage";
public static final String BOUTTON_LANCER_CREATION_CLEF = "Creer une clef aléatoire";
public String choixcryptage=BOUTTON_CESAR_AMELIORE; /* Le type de cryptage que l'utilisateur a séléctionné (par défaut : AES) */
public String Element_clique = "null";
/* Elements que l'on utilise pour le cryptage */
public JEditorPane texte_message_decrypte = new JEditorPane();
public JEditorPane texte_message_crypte = new JEditorPane();
public JLabel supertitre = new JLabel("Cryptage de type "+choixcryptage);
public JLabel supertitre2 = new JLabel("<html>Conditions d'utilisation :<br/> - La clef de cryptage doit être composée de 7 caractères ASCII<br/> - Le message n'a pas de restriction particulière</html>");
public JTextField texte_clef = new JTextField("Clef de cryptage");
public JButton chercherlaclef = new JButton(CHERCHER_CLEF);

Fenetre(String titre){
		
	/* --------------------------------------------------- Definition de notre fenêtre de base ------------------------------- */
		super(titre);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); /*permet de quitter l'application en même temps que la fenêtre*/
		/* Fenêtre compatible avec des écrans de tailles superieurs ou égaux à 800x600 : */
		this.setLocation(0,0); /* Où se trouve la fenêtre par défaut */
		this.setSize(1024,768); /* Taille de la fenêtre par défaut */
	    this.setLayout(new BorderLayout());	
	   this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource(LOGO_IMG)));  // Icone
	/* --------------------------------------------------- Fin de la definition de notre fenêtre de base --------------------- */
	/* --------------------------------------------------- LE MENU ------------------------------------------------------------*/
		/* Notre menu : */
			JMenuBar m= new JMenuBar(); /* Création du menu */
			
		/* Barre de menu "Fichier" */
			JMenu fichiers=new JMenu("Fichier"); 
	
			JMenuItem nouveau = new JMenuItem(NOUVEAU, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(NOUVEAU_IMG)))) ; /* Les élements de Fichier */
			nouveau.setAccelerator(KeyStroke.getKeyStroke('N',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
			nouveau.addActionListener(this);	
			JMenuItem ouvrirclef = new JMenuItem(OUVRIR_CLEF, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(OUVRIR_CLEF_IMG)))) ;
			ouvrirclef.addActionListener(this);				
			JMenuItem ouvrirfichier = new JMenuItem(OUVRIR_FICHIER, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(OUVRIR_FICHIER_IMG)))) ;
			ouvrirfichier.addActionListener(this);	
			JMenuItem ouvrirfichiercrypte = new JMenuItem(OUVRIR_FICHIER_CRYPTE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(OUVRIR_FICHIER_CRYPTE_IMG)))) ;
			ouvrirfichiercrypte.addActionListener(this);	
			JMenuItem enregistrersousclef = new JMenuItem(ENREGISTRERSOUS_CLEF, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(ENREGISTRER_CLEF_IMG)))) ;
			enregistrersousclef.addActionListener(this);
			JMenuItem enregistrersousfichier = new JMenuItem(ENREGISTRERSOUS_FICHIER, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(ENREGISTRER_FICHIER_IMG)))) ;
			enregistrersousfichier.addActionListener(this);
			JMenuItem enregistrersousfichiercrypte = new JMenuItem(ENREGISTRERSOUS_FICHIER_CRYPTE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(ENREGISTRER_FICHIER_CRYPTE_IMG)))) ;
			enregistrersousfichiercrypte.addActionListener(this);

			/*JMenuItem imprimer = new JMenuItem(IMPRIMER, new ImageIcon(IMPRIMER_IMG)) ;
			imprimer.setAccelerator(KeyStroke.getKeyStroke('P',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
			imprimer.addActionListener(this);	*/
			
			JMenuItem exporterpdf = new JMenuItem(EXPORTER_PDF, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(EXPORTER_PDF_IMG)))) ;
			exporterpdf.addActionListener(this);	
			
			JMenuItem quitter = new JMenuItem(QUITTER, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(QUITTER_IMG)))) ;
			quitter.setAccelerator(KeyStroke.getKeyStroke('Q',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
			quitter.addActionListener(this);
			
			fichiers.add(nouveau); /* Ajouter les éléments séparés au menu fichiers */
			fichiers.add(ouvrirclef);
			fichiers.add(ouvrirfichier);
			fichiers.add(ouvrirfichiercrypte);
			fichiers.add(enregistrersousclef);
			fichiers.add(enregistrersousfichier);
			fichiers.add(enregistrersousfichiercrypte);
			fichiers.addSeparator(); 
			//fichiers.add(imprimer);
			fichiers.add(exporterpdf);
			fichiers.addSeparator(); 
			fichiers.add(quitter);
								
			m.add(fichiers); /* Ajouter fichiers à la barre de menu */
	
		/* Barre de menu "Edition" */
	
			JMenu edition=new JMenu("Edition"); 
			
			/*JMenuItem annuler = new JMenuItem(ANNULER, new ImageIcon(ANNULER_IMG)) ;
			annuler.setAccelerator(KeyStroke.getKeyStroke('Z',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
			annuler.addActionListener(this);	
			JMenuItem retablir = new JMenuItem(RETABLIR, new ImageIcon(RETABLIR_IMG)) ;
			retablir.setAccelerator(KeyStroke.getKeyStroke('Y',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
			retablir.addActionListener(this);	*/
			JMenuItem couper = new JMenuItem(COUPER, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(COUPER_IMG)))) ;
			couper.setAccelerator(KeyStroke.getKeyStroke('X',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
			couper.addActionListener(this);	
			JMenuItem copier = new JMenuItem(COPIER, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(COPIER_IMG)))) ;
			copier.setAccelerator(KeyStroke.getKeyStroke('C',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
			copier.addActionListener(this);	
			JMenuItem coller = new JMenuItem(COLLER, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(COLLER_IMG)))) ;
			coller.setAccelerator(KeyStroke.getKeyStroke('V',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
			coller.addActionListener(this);	
			
			/*edition.add(annuler); // Ajouter les éléments séparés au menu edition	
			edition.add(retablir);	
			edition.addSeparator(); */
			edition.add(couper);	
			edition.add(copier);	
			edition.add(coller);	
			
			m.add(edition); // Ajouter édition à la barre de menu 
		
		
			
		/* Barre de menu "Cryptages symétriques puis asymétriques" */
			
			JMenu cryptages=new JMenu("Type de cryptage"); 
			
			JMenuItem boutton_aes = new JMenuItem(BOUTTON_AES, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(CRYPTAGE_IMG)))) ; /* Les élements de Cryptages */
			boutton_aes.addActionListener(this);	
			JMenuItem boutton_des = new JMenuItem(BOUTTON_DES, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(CRYPTAGE_IMG)))) ;
			boutton_des.addActionListener(this);
			JMenuItem boutton_vigenere = new JMenuItem(BOUTTON_VIGENERE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(CRYPTAGE_IMG)))) ;
			boutton_vigenere.addActionListener(this);	
			JMenuItem boutton_vigenere_ameliore = new JMenuItem(BOUTTON_VIGENERE_AMELIORE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(CRYPTAGE_IMG)))) ;
			boutton_vigenere_ameliore.addActionListener(this);	
			JMenuItem boutton_cesar_classique = new JMenuItem(BOUTTON_CESAR_CLASSIQUE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(CRYPTAGE_IMG)))) ;
			boutton_cesar_classique.addActionListener(this);
			JMenuItem boutton_cesar_ameliore = new JMenuItem(BOUTTON_CESAR_AMELIORE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(CRYPTAGE_IMG)))) ;
			boutton_cesar_ameliore.addActionListener(this);
			JMenuItem boutton_rsa = new JMenuItem(BOUTTON_RSA, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(CRYPTAGE_IMG)))) ;
			boutton_rsa.addActionListener(this);	
			
			//cryptages.add(boutton_aes); /* Ajouter les éléments séparés au menu cryptages */	
			cryptages.add(boutton_des);	
			cryptages.add(boutton_vigenere);	
			cryptages.add(boutton_vigenere_ameliore);	
			cryptages.add(boutton_cesar_classique);	
			cryptages.add(boutton_cesar_ameliore);	
			//cryptages.addSeparator(); 			
			//cryptages.add(boutton_rsa);	
			
			m.add(cryptages); /* Ajouter édition à la barre de menu */
		
			
		/* Barre de menu "A Propos" */
			
			JMenu menuapropos=new JMenu(APROPOS); 
			
			JMenuItem aide = new JMenuItem(AIDE, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(AIDE_IMG)))) ; /* Les élements de A Propos */
			aide.setAccelerator(KeyStroke.getKeyStroke('H',Toolkit.getDefaultToolkit().getMenuShortcutKeyMask(),false));
			aide.addActionListener(this);	
			JMenuItem apropos = new JMenuItem(APROPOS, new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(APROPOS_IMG)))) ;
			apropos.addActionListener(this);				
			
			menuapropos.add(aide); /* Ajouter les éléments séparés au menu A Propos */	
			menuapropos.addSeparator(); 			
			menuapropos.add(apropos);	
			
			m.add(menuapropos); /* Ajouter A Propos à la barre de menu */

			
		this.setJMenuBar(m); /* Afficher le menu complet */
		
		/* --------------------------------------------------- FIN DU MENU ---------------------------------------------------*/
		/* --------------------------------------------------- DEBUT DU MENU SECONDAIRE --------------------------------------*/
		
		
		JToolBar toolBar = new JToolBar();
		
		JButton nouveau2 = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(NOUVEAU_IMG))));
		nouveau2.setBorder(BorderFactory.createEmptyBorder());	/* Boutton plus beau */
		nouveau2.setActionCommand(NOUVEAU);
		nouveau2.setToolTipText(NOUVEAU);
		nouveau2.addActionListener(this);
	
		JButton ouvrirclef2 = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(OUVRIR_CLEF_IMG))));
		ouvrirclef2.setBorder( BorderFactory.createEmptyBorder() );
		ouvrirclef2.setActionCommand(OUVRIR_CLEF);	
		ouvrirclef2.setToolTipText(OUVRIR_CLEF);
		ouvrirclef2.addActionListener(this);
		
		JButton ouvrirfichier2 = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(OUVRIR_FICHIER_IMG))));
		ouvrirfichier2.setBorder( BorderFactory.createEmptyBorder() );
		ouvrirfichier2.setActionCommand(OUVRIR_FICHIER);
		ouvrirfichier2.setToolTipText(OUVRIR_FICHIER);
		ouvrirfichier2.addActionListener(this);
		
		JButton ouvrirfichiercrypte2 = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(OUVRIR_FICHIER_CRYPTE_IMG))));
		ouvrirfichiercrypte2.setBorder( BorderFactory.createEmptyBorder() );
		ouvrirfichiercrypte2.setActionCommand(OUVRIR_FICHIER_CRYPTE);
		ouvrirfichiercrypte2.setToolTipText(OUVRIR_FICHIER_CRYPTE);
		ouvrirfichiercrypte2.addActionListener(this);
		
		JButton enregistrersousclef2 = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(ENREGISTRER_CLEF_IMG))));
		enregistrersousclef2.setBorder( BorderFactory.createEmptyBorder() );
		enregistrersousclef2.setActionCommand(ENREGISTRERSOUS_CLEF);	
		enregistrersousclef2.setToolTipText(ENREGISTRERSOUS_CLEF);
		enregistrersousclef2.addActionListener(this);
		
		JButton enregistrersousfichier2 = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(ENREGISTRER_FICHIER_IMG))));
		enregistrersousfichier2.setBorder( BorderFactory.createEmptyBorder());
		enregistrersousfichier2.setActionCommand(ENREGISTRERSOUS_FICHIER);	
		enregistrersousfichier2.setToolTipText(ENREGISTRERSOUS_FICHIER);
		enregistrersousfichier2.addActionListener(this);
		
		JButton enregistrersousfichiercrypte2 = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(ENREGISTRER_FICHIER_CRYPTE_IMG))));
		enregistrersousfichiercrypte2.setBorder( BorderFactory.createEmptyBorder());
		enregistrersousfichiercrypte2.setActionCommand(ENREGISTRERSOUS_FICHIER_CRYPTE);	
		enregistrersousfichiercrypte2.setToolTipText(ENREGISTRERSOUS_FICHIER_CRYPTE);
		enregistrersousfichiercrypte2.addActionListener(this);
		
		JButton exporterpdf2 = new JButton(new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(EXPORTER_PDF_IMG))));
		exporterpdf2.setBorder( BorderFactory.createEmptyBorder());
		exporterpdf2.setActionCommand(EXPORTER_PDF);	
		exporterpdf2.setToolTipText(EXPORTER_PDF);
		exporterpdf2.addActionListener(this);

		/* LA COMBO BOX N'A PAS DE LISTENER .... */
		/*String[] macombobox = {"A FAIRE ....", "Option 2", "Option 3", "Option 4"};
		JComboBox combo = new JComboBox(macombobox);
	    //combo.addItemListener(this);
	    combo.setMaximumSize(new Dimension(200, 20));
	    
	    JLabel bouttonseparateur= new JLabel("Type de cryptage utilisé : ");
	    bouttonseparateur.setBorder(BorderFactory.createEmptyBorder());
		*/
		toolBar.add(nouveau2);
	    toolBar.addSeparator(new Dimension(10,20));
		toolBar.add(ouvrirclef2);
	    toolBar.addSeparator(new Dimension(5,20));	
		toolBar.add(ouvrirfichier2);
	    toolBar.addSeparator(new Dimension(5,20));	
		toolBar.add(ouvrirfichiercrypte2);
		toolBar.addSeparator(new Dimension(10,20));	
		toolBar.add(enregistrersousclef2);
	    toolBar.addSeparator(new Dimension(5,20));	
		toolBar.add(enregistrersousfichier2);
	    toolBar.addSeparator(new Dimension(5,20));
		toolBar.add(enregistrersousfichiercrypte2);
	    toolBar.addSeparator(new Dimension(5,20));
		toolBar.add(exporterpdf2);
	    toolBar.addSeparator(new Dimension(15,20));
	    /*toolBar.add(bouttonseparateur);
	    toolBar.add(combo);*/
	
	    this.getContentPane().add(toolBar, BorderLayout.NORTH);		
	    
		/* --------------------------------------------------- FIN DU MENU SECONDAIRE --------------------------------------*/
	    
		/* --------------------------------------------------- Debut de notre fenetre de cryptage --------------------------*/


	
	    
	    JPanel container = new JPanel();
	
	    /* Titre indiquant le type de cryptage */
		    JPanel panelmarge = new JPanel();    
		    panelmarge.setPreferredSize(new Dimension(this.getWidth()+600,30)); 
		    //JLabel supertitre = new JLabel("Cryptage de type "+choixcryptage);
		    supertitre.setFont(new Font("Arial",Font.BOLD,17));    
		    panelmarge.add(supertitre);
		    container.add(panelmarge);	

	    /* Paragraphe expliquant les modalités du cryptage en question */
		    JPanel panelexplication = new JPanel();    
		    panelexplication.setPreferredSize(new Dimension(this.getWidth()+600,100)); 
		    //JLabel supertitre2 = new JLabel("La clef de cryptage doit ....");
		    supertitre2.setPreferredSize(new Dimension(700,80));
		    supertitre2.setFont(new Font("Arial",Font.BOLD,12));    
		    panelexplication.add(supertitre2);
		    container.add(panelexplication);

	    /* Titre de notre fenetre cryptage */
	        JPanel paneltitre = new JPanel();    
		    paneltitre.setPreferredSize(new Dimension(this.getWidth()+600,55));
		    JLabel supertitre3 = new JLabel("Vous pouvez selectionner le type de cryptage que vous souhaitez dans le barre de menu.");
		    supertitre3.setFont(new Font("Arial",Font.ITALIC,11));	    
		    paneltitre.add(supertitre3);
		    container.add(paneltitre);	
    
	    /* Bloc panelclef */
		    JPanel panelclef = new JPanel();    
		    panelclef.setPreferredSize(new Dimension(this.getWidth()+600,35));
		   // JTextField texte_clef = new JTextField("Clef de cryptage");
		    texte_clef.setPreferredSize(new Dimension(525,25));
		    texte_clef.setToolTipText("Clé à utiliser pour l'opération de chiffrement/déchiffrement");
		    texte_clef.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e)
		        {
		        	Element_clique="texte_clef";
		        } 
		    });
		    JButton creationduneclef = new JButton(BOUTTON_LANCER_CREATION_CLEF);
		    panelclef.add(new JLabel("Votre clef de cryptage :      "));
		    panelclef.add(texte_clef);
		    panelclef.add(creationduneclef);
		    creationduneclef.addActionListener(this);	

		    container.add(panelclef);	

	    
	    /* Bloc paneltxtdecrypte */
		    JPanel paneltxtdecrypte = new JPanel(); 
		    paneltxtdecrypte.setPreferredSize(new Dimension(this.getWidth()+700,150));
		    //JEditorPane texte_message_decrypte = new JEditorPane();
		    texte_message_decrypte.setPreferredSize(new Dimension(700,130));
		    texte_message_decrypte.setText("Message décrypté");
		    texte_message_decrypte.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e)
		        {
		        	Element_clique="texte_message_decrypte";
		        } 
		    });
		   // texte_message_decrypte.addMouseListener(this);
		    paneltxtdecrypte.add(new JLabel("Votre message décrypté : "));
		    
		    JScrollPane scrollpane = new JScrollPane(texte_message_decrypte);
		    paneltxtdecrypte.add(scrollpane);
		    
		   // paneltxtdecrypte.add(texte_message_decrypte);	    
		    container.add(paneltxtdecrypte);	
    
	    /* Bloc paneltxtcrypte */
		    JPanel paneltxtcrypte = new JPanel(); 
		    paneltxtcrypte.setPreferredSize(new Dimension(this.getWidth()+700,185));
		    //JEditorPane texte_message_crypte = new JEditorPane();
		    texte_message_crypte.setPreferredSize(new Dimension(700,150));
		    texte_message_crypte.setText("Message crypté");
		    texte_message_crypte.addMouseListener(new java.awt.event.MouseAdapter() {
		        public void mouseClicked(MouseEvent e)
		        {
		        	Element_clique="texte_message_crypte";
		        } 
		    });
		    paneltxtcrypte.add(new JLabel("Votre message crypté :      "));
		   // paneltxtcrypte.add(texte_message_crypte);	
		    
		    JScrollPane scrollpane2 = new JScrollPane(texte_message_crypte);
		    paneltxtcrypte.add(scrollpane2);	
		    container.add(paneltxtcrypte);	

	    /* Bloc panelboutons */
		    JPanel panelboutons = new JPanel();    
		    panelboutons.setPreferredSize(new Dimension(this.getWidth()+600,50));
		    JButton lancercryptage = new JButton(BOUTTON_LANCER_CRYPTAGE);
		    lancercryptage.addActionListener(this);	
		    JButton lancerdecryptage = new JButton(BOUTTON_LANCER_DECRYPTAGE);
		    lancerdecryptage.addActionListener(this);	
		    //JButton chercherlaclef = new JButton(CHERCHER_CLEF);
		    chercherlaclef.addActionListener(this);	
	
		    panelboutons.add(new JLabel("                                                 "));
		    panelboutons.add(lancercryptage);
	
		    panelboutons.add(new JLabel("       "));
		    panelboutons.add(lancerdecryptage);	  
		    panelboutons.add(new JLabel("       "));
		    panelboutons.add(chercherlaclef);
		    container.add(panelboutons);	

	    //.add(Box.createVerticalStrut(_space));



	   	this.getContentPane().add(container, BorderLayout.CENTER);
 
	    
		/* --------------------------------------------------- Fin de notre fenetre de cryptage --------------------------*/
	    
	    
		this.setVisible(true); /* Autoriser l'affichage de la fenêtre (à mettre à la fin de notre fonction */
	}
	
	
	/* --------------------------------------------------- FONCTION GERANT LA PAGE DE CRYPTAGE --------------------------------------*/
	
	// FONCTION PAS TERMINE !!!! A FINIR !!!!!!!!!!!!!
	
		public void gererPageCryptage(){
			if(choixcryptage==BOUTTON_RSA){
				// RSA est un cryptage asymétrique (--> 2 clefs, 1 String crypté, 1 String décrypté)
				System.out.println("Ouverture d'une page pour le cryptage asymétrique.\nEn effet, le choix de cryptage est de type "+choixcryptage);
			}
			else 
			{
				// Tout les autres cryptages sont de type cryptage symétrique (--> 1 clef, 1 String crypté, 1 String décrypté)
				System.out.println("Ouverture d'une page pour le cryptage symétrique.\nEn effet, le choix de cryptage est de type "+choixcryptage);
		
			}
		}
	
	/* --------------------------------------------------- FIN DE LA FONCTION GERANT LA PAGE DE CRYPTAGE --------------------------------------*/
	
		
		
	/* --------------------------------------------------- ACTIONS LORSQUE L'ON CLIQUE SUR UN BOUTTON --------------------------------------*/
	
// Les méthodes des actions
		
	public void actionPerformed(ActionEvent e) {
		String cmd = e.getActionCommand();
		System.out.println("Vous avez appuyé sur " +cmd);

		
		if(cmd.equals(QUITTER)) {
			System.exit(0);
		}
		
		else if(cmd.equals(COPIER)){
			if (texte_message_decrypte.getSelectedText()!=null)	texte_message_decrypte.copy();
			else if(texte_message_crypte.getSelectedText()!=null) texte_message_crypte.copy();
			else if(texte_clef.getSelectedText()!=null) texte_clef.copy();
			else JOptionPane.showMessageDialog(null,"Selectionnez ce que vous voulez copier" , AIDE, JOptionPane.INFORMATION_MESSAGE);      
		}
		else if(cmd.equals(COUPER)){
			if (texte_message_decrypte.getSelectedText()!=null)	texte_message_decrypte.cut();
			else if(texte_message_crypte.getSelectedText()!=null) texte_message_crypte.cut();
			else if(texte_clef.getSelectedText()!=null) texte_clef.cut();
			else JOptionPane.showMessageDialog(null,"Selectionnez ce que vous voulez couper" , AIDE, JOptionPane.INFORMATION_MESSAGE);      
		}
		else if(cmd.equals(COLLER)){
			
			if (Element_clique=="texte_message_decrypte") texte_message_decrypte.paste();
			else if (Element_clique=="texte_message_crypte") texte_message_crypte.paste();
			else if (Element_clique=="texte_clef") texte_clef.paste();
			else JOptionPane.showMessageDialog(null,"Selectionnez le champ de texte pour coller ce que vous souhaitez." , AIDE, JOptionPane.INFORMATION_MESSAGE);   
		}
		
		else if(cmd.equals(EXPORTER_PDF)){
			
		// L'utilisateur choisi l'endoit où il enregistre le projet
			JFileChooser dialogue3 = new JFileChooser(new File("."));
			String chemin_fichier_enregistrer;
			if(dialogue3.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
			    chemin_fichier_enregistrer = dialogue3.getSelectedFile().getPath();
			    System.out.println(chemin_fichier_enregistrer);
			    
	    // Création du PDF
			    
			      Document document=new Document();
			      try {
					PdfWriter.getInstance(document,new FileOutputStream(chemin_fichier_enregistrer+".pdf"));
				      document.open();  

				      URL url1 = getClass().getResource("logopdf.png");
				      Image img = Image.getInstance(url1);
				      document.add(img);
				      document.add(new Paragraph("\n\n"));
				      document.add(new Paragraph("Le cryptage utilisé est de type "+choixcryptage+"\n\n"));
				      document.add(new Paragraph("La clef utilisée est :\n")); 			      
				      document.add(new Paragraph(texte_clef.getText()));
				      document.add(new Paragraph("\nLe message en clair est :\n")); 			      
				      document.add(new Paragraph(texte_message_decrypte.getText()));
				      document.add(new Paragraph("\nLe message crypté est :\n")); 			      
				      document.add(new Paragraph(texte_message_crypte.getText()));
				      document.add(new Paragraph("\n\n\nL'application \"Safe for all v1.0\" créée par Quentin Comte-Gaz se trouve à l'adresse :"));
				      com.itextpdf.text.Font blueFont = new com.itextpdf.text.Font();
				      blueFont.setColor(0,0,0xFF);
				      document.add(new Chunk("http://quentincg.free.fr/safeforall/", blueFont).setAnchor("http://quentincg.free.fr/safeforall/"));
				     
				      document.close(); 
				      JOptionPane.showMessageDialog(null,"Votre fichier PDF a bien été exporté à l'adresse "+chemin_fichier_enregistrer+".pdf", "Fichier PDF exporté", JOptionPane.INFORMATION_MESSAGE);  
				} catch (DocumentException | IOException e1) {
						JOptionPane.showMessageDialog(null,"Votre fichier PDF n'a pas pu etre créé pour une raison inconnue.", "Fichier PDF non exporté", JOptionPane.INFORMATION_MESSAGE);  

				}			    
			    
			}
			else {
				//JOptionPane.showMessageDialog(null,"Vous n'avez pas selectionné l'adresse du fichier, il se trouvera donc à l'endoit où se trouve l'application.", "Fichier PDF non exporté", JOptionPane.INFORMATION_MESSAGE);
			}
		
		      
			// Ouverture du PDF
		      /*if (Desktop.isDesktopSupported())
		      {
		          Desktop desktop = Desktop.getDesktop();
		          if (desktop.isSupported(Desktop.Action.OPEN))
		          {
		              try
		              {
		                  desktop.open(new File(chemin_fichier_enregistrer+".pdf"));
		              }
		              catch (Exception e1)
		              {
		                  e1.printStackTrace();
		              }
		          }
		          else
		              System.err.println("OPEN not supported");
		      }
		      else
		          System.err.println("Desktop not supported");*/
		      
		}
		

		
		else if(cmd.equals(APROPOS) || cmd.equals(AIDE)){
			ImageIcon img = new ImageIcon(Toolkit.getDefaultToolkit().getImage(getClass().getResource(LOGO_IMG)));
			if(cmd.equals(APROPOS)) JOptionPane.showMessageDialog(null,"<html>Logiciel : Safe for all<br />Version : 1.0<br />© Tous droits réservés 2012-2013<br /><br />Auteur : Quentin Comte-Gaz<br />Contact : Quentincg@free.fr<br />Site de l'éditeur : http://quentincg.free.fr/</html>" , APROPOS, JOptionPane.INFORMATION_MESSAGE, img);      
			else JOptionPane.showMessageDialog(null,"Aide complète en ligne à l'adresse :\nhttp://quentincg.free.fr/safeforall/" , AIDE, JOptionPane.INFORMATION_MESSAGE, img);      
	
		}
		else if(cmd.equals(NOUVEAU)){
			texte_message_decrypte.setText(""); 
			texte_message_crypte.setText(""); 
			texte_clef.setText(""); 
			
			
		}
		else if(cmd.equals(OUVRIR_CLEF)){
			JFileChooser dialogue1 = new JFileChooser(new File("."));
			if(dialogue1.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			    String chemin_clef_ouvrir = dialogue1.getSelectedFile().getPath();
			    System.out.println(chemin_clef_ouvrir);
				ReadFile maclef = new ReadFile(chemin_clef_ouvrir);
				texte_clef.setText(maclef.chaine);
			}
			else System.out.println("Vous n'avez pas selectionné de fichier !");			
		}
		
		else if(cmd.equals(OUVRIR_FICHIER)){
			JFileChooser dialogue2 = new JFileChooser(new File("."));
			if(dialogue2.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			    String chemin_fichier_ouvrir = dialogue2.getSelectedFile().getPath();
			    System.out.println(chemin_fichier_ouvrir);
				ReadFile momfichier = new ReadFile(chemin_fichier_ouvrir);
				texte_message_decrypte.setText(momfichier.chaine);   
			
			}
			else System.out.println("VVous n'avez pas selectionné de fichier !");			
		}
		
		else if(cmd.equals(OUVRIR_FICHIER_CRYPTE)){
			JFileChooser dialogue2 = new JFileChooser(new File("."));
			if(dialogue2.showOpenDialog(null)==JFileChooser.APPROVE_OPTION){
			    String chemin_fichier_ouvrir = dialogue2.getSelectedFile().getPath();
			    System.out.println(chemin_fichier_ouvrir);
				ReadFile momfichier = new ReadFile(chemin_fichier_ouvrir);
				texte_message_crypte.setText(momfichier.chaine);   
			
			}
			else System.out.println("VVous n'avez pas selectionné de fichier !");			
		}
		
		else if(cmd.equals(ENREGISTRERSOUS_FICHIER)){
			JFileChooser dialogue3 = new JFileChooser(new File("."));
			if(dialogue3.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
			    String chemin_fichier_enregistrer = dialogue3.getSelectedFile().getPath();
			    System.out.println(chemin_fichier_enregistrer);
				new SaveFile(chemin_fichier_enregistrer+".txt",texte_message_decrypte.getText());			    
			}
			else System.out.println("Vous n'avez pas selectionné l'adresse du fichier !");			
		}
		
		else if(cmd.equals(ENREGISTRERSOUS_FICHIER_CRYPTE)){
			JFileChooser dialogue3 = new JFileChooser(new File("."));
			if(dialogue3.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
			    String chemin_fichier_enregistrer = dialogue3.getSelectedFile().getPath();
			    System.out.println(chemin_fichier_enregistrer);
				new SaveFile(chemin_fichier_enregistrer+".txt",texte_message_crypte.getText());			    
			}
			else System.out.println("Vous n'avez pas selectionné l'adresse du fichier !");			
		}
		
		else if(cmd.equals(ENREGISTRERSOUS_CLEF)){
			JFileChooser dialogue4 = new JFileChooser(new File("."));
			if(dialogue4.showSaveDialog(null)==JFileChooser.APPROVE_OPTION){
			    String chemin_clef_enregistrer2 = dialogue4.getSelectedFile().getPath();
			    System.out.println(chemin_clef_enregistrer2);
				new SaveFile(chemin_clef_enregistrer2+".txt",texte_clef.getText());			    
				}
			else System.out.println("Vous n'avez pas selectionné l'adresse du fichier !");	
		}
		
		else if(cmd.equals(BOUTTON_AES) || cmd.equals(BOUTTON_DES) || cmd.equals(BOUTTON_VIGENERE) || cmd.equals(BOUTTON_VIGENERE_AMELIORE) || cmd.equals(BOUTTON_CESAR_AMELIORE) || cmd.equals(BOUTTON_CESAR_CLASSIQUE) || cmd.equals(BOUTTON_RSA)){
			choixcryptage=cmd;	// On choisit le type de cryptage en fonction du bouton appuyé	
			supertitre.setText("Cryptage de type "+choixcryptage);
			System.out.println("Notre nouveau cryptage est : "+choixcryptage);
			
			if(cmd.equals(BOUTTON_AES)) supertitre2.setText("<html>Conditions d'utilisation :<br/> - La clef de cryptage doit être constituée de 16 caractères ASCII<br/> - Le message n'a pas de restriction particulière</html>");
			if(cmd.equals(BOUTTON_DES)) supertitre2.setText("<html>Conditions d'utilisation :<br/> - La clef de cryptage doit être constituée de 7 caractères ASCII<br/> - Le message n'a pas de restriction particulière</html>");
			if(cmd.equals(BOUTTON_VIGENERE)) supertitre2.setText("<html>Conditions d'utilisation :<br/> - La clef de cryptage doit être constituée uniquement de lettres<br/> - Seul les lettres du message seront cryptées, les autres caractères (0->9,&é\"...) seront donc effacés, de plus, les lettres en minuscules seront transformées en majuscules</html>");
			if(cmd.equals(BOUTTON_VIGENERE_AMELIORE)) supertitre2.setText("<html>Conditions d'utilisation :<br/> - La clef de cryptage doit être constituée uniquement de lettres<br/> - Seul les lettres du message seront cryptées, les autres caractères (0->9,&é\"...) ne seront pas cryptés mais seront toujours présents</html>");
			if(cmd.equals(BOUTTON_CESAR_AMELIORE)) supertitre2.setText("<html>Conditions d'utilisation :<br/> - La clef de cryptage doit être un entier compris entre 0 et 224<br/> - Le message n'a pas de restriction particulière</html>");
			if(cmd.equals(BOUTTON_CESAR_CLASSIQUE)) supertitre2.setText("<html>Conditions d'utilisation :<br/> - La clef de cryptage doit être un entier compris entre 0 et 26<br/> - Seul les lettres du message seront cryptées, les autres caractères ne seront cependant pas effacés</html>");
			if(cmd.equals(BOUTTON_RSA)) supertitre2.setText("<html>Conditions d'utilisation :<br/> - La clef privée et publique vous sont fournit par defaut<br/> - Le message n'a pas de restriction particulière</html>");
			
			if(cmd.equals(BOUTTON_RSA) || cmd.equals(BOUTTON_AES) ||  cmd.equals(BOUTTON_DES))
				chercherlaclef.setVisible(false);	
			else chercherlaclef.setVisible(true);
			gererPageCryptage();
		}
		

		if(cmd.equals(BOUTTON_LANCER_CREATION_CLEF)){
	
			if(choixcryptage==BOUTTON_AES) {
				Creationclef clef = new Creationclef(BOUTTON_AES);
				texte_clef.setText(clef.clefAES());
			}
			
			if(choixcryptage==BOUTTON_DES) {
				Creationclef clef = new Creationclef(BOUTTON_DES);
				texte_clef.setText(clef.clefDES());
			}	
			
			if(choixcryptage==BOUTTON_CESAR_CLASSIQUE) {
				Creationclef clef = new Creationclef(BOUTTON_CESAR_CLASSIQUE);
				texte_clef.setText(clef.clefCESARCLASSIQUE());
			}	
			
			if(choixcryptage==BOUTTON_CESAR_AMELIORE) {
				Creationclef clef = new Creationclef(BOUTTON_CESAR_AMELIORE);
				texte_clef.setText(clef.clefCESARAMELIORE());
			}			
			
			if(choixcryptage==BOUTTON_VIGENERE) {
				Creationclef clef = new Creationclef(BOUTTON_VIGENERE);
				texte_clef.setText(clef.clefVIGENERE());
			}	
			
			if(choixcryptage==BOUTTON_VIGENERE_AMELIORE) {
				Creationclef clef = new Creationclef(BOUTTON_VIGENERE_AMELIORE);
				texte_clef.setText(clef.clefVIGENEREAMELIORE());
			}	
			
			
		}
		
		
		
		
		
		
		if(cmd.equals(BOUTTON_LANCER_CRYPTAGE)){
			System.out.println("Debut du cryptage "+choixcryptage);
			String messageacrypter = texte_message_decrypte.getText();
			String maclef = texte_clef.getText();
			System.out.println("Debut du cryptage type "+choixcryptage+" en utilisant la clef \""+maclef+"\" et le message à crypter \""+messageacrypter+"\"");
			
			if(choixcryptage==BOUTTON_AES)
			{
				if(maclef.length()==16){
					AES aes;
					byte b[]= null;				
					aes = new AES(maclef.getBytes());		//Construction de l'objet AES
					b = messageacrypter.getBytes();		//Chaine a chiffrer sous forme de byte
					b = aes.chiffrerMess(b);			//Chiffrement du message
					String messagecrypte5 = new String(b);
					System.out.println("Fin du cryptage.\nLe message crypté est :\n"+messagecrypte5);	
					texte_message_crypte.setText(messagecrypte5);
				}
				else {
					JOptionPane.showMessageDialog(null,"Le cryptage est de type AES 128 bits.\nVotre clef de cryptage doit donc contenir 16 caractères ASCII." , AIDE, JOptionPane.ERROR_MESSAGE);      

				}
			} else if(choixcryptage==BOUTTON_CESAR_AMELIORE){
				try{
					int maclefcesar = Integer.parseInt(maclef);
					Cesar_complet cesar3 = new Cesar_complet(messageacrypter);
					String messagechiffe3=cesar3.chiffrer(maclefcesar);
					texte_message_crypte.setText(messagechiffe3);
				}
				catch(NumberFormatException f){
					 JOptionPane.showMessageDialog(null,"Le cryptage est de type César amélioré.\nVotre clef de cryptage doit donc contenir uniquement un entier compris entre 0 et 224." , AIDE, JOptionPane.ERROR_MESSAGE);      
	
				}
				

		}else if(choixcryptage==BOUTTON_CESAR_CLASSIQUE){
			try{
				int maclefcesar = Integer.parseInt(maclef);
				Cesar_classique cesar4 = new Cesar_classique(messageacrypter);
				String messagechiffe4=cesar4.chiffrer(maclefcesar);
				texte_message_crypte.setText(messagechiffe4);
			}
			catch(NumberFormatException f){
				 JOptionPane.showMessageDialog(null,"Le cryptage est de type César classique.\nVotre clef de cryptage doit donc contenir uniquement un entier compris entre 0 et 26." , AIDE, JOptionPane.ERROR_MESSAGE);   
			}
		}else if(choixcryptage==BOUTTON_DES){
			if(maclef.length()==7){
				try {
					texte_message_crypte.setText(DESUtil.binaryStringToReadableMessage(DES.encode(DESUtil.messageToDESBinaryString(messageacrypter), maclef)));
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null,"Impossible de crypter.\nErreur inconnu." , AIDE, JOptionPane.ERROR_MESSAGE);   

				}				
			}
			else{
				 JOptionPane.showMessageDialog(null,"Le cryptage est de type DES 56 bits.\nVotre clef de cryptage doit donc contenir 7 caractères ASCII." , AIDE, JOptionPane.ERROR_MESSAGE);   
			}
		}else if(choixcryptage==BOUTTON_VIGENERE){
			texte_message_crypte.setText(new Vigenere(messageacrypter).chiffrer(maclef));
		}else if(choixcryptage==BOUTTON_VIGENERE_AMELIORE){
			texte_message_crypte.setText(new Vigenere_complet(messageacrypter).chiffrer(maclef));
		}	
			
			
		}
			
		if(cmd.equals(CHERCHER_CLEF)){
			if(choixcryptage==BOUTTON_CESAR_CLASSIQUE){
				Attaque_cesar_classique attaqueclef= new Attaque_cesar_classique(texte_message_crypte.getText(),"Francais");
				Cesar_classique cesar = new Cesar_classique(texte_message_crypte.getText());
				if((texte_message_crypte.getText()).length()>25) JOptionPane.showMessageDialog(null,"Clef la plus probable : "+attaqueclef.cleflaplusprobable+"\nTexte déchiffré associé : "+cesar.dechiffrer(attaqueclef.cleflaplusprobable).substring(0,25)+"\n\n2ème clef la plus probable : "+attaqueclef.deuxiemecleflaplusprobable+"\nTexte déchiffré associé : "+cesar.dechiffrer(attaqueclef.deuxiemecleflaplusprobable).substring(0,25)+"\n\n3ème clef la plus probable : "+attaqueclef.troisiemecleflaplusprobable+"\nTexte déchiffré associé : "+cesar.dechiffrer(attaqueclef.troisiemecleflaplusprobable).substring(0,25)+"\nForce brute :\nClef = 0 : "+cesar.dechiffrer(0).substring(0,25)+"\nClef = 1 : "+cesar.dechiffrer(1).substring(0,25)+"\nClef = 2 : "+cesar.dechiffrer(2).substring(0,25)+"\nClef = 3 : "+cesar.dechiffrer(3).substring(0,25)+"\nClef = 4 : "+cesar.dechiffrer(4).substring(0,25)+"\nClef = 5 : "+cesar.dechiffrer(5).substring(0,25)+"\nClef = 6 : "+cesar.dechiffrer(6).substring(0,25)+"\nClef = 7 : "+cesar.dechiffrer(7).substring(0,25)+"\nClef = 8 : "+cesar.dechiffrer(8).substring(0,25)+"\nClef = 9 : "+cesar.dechiffrer(9).substring(0,25)+"\nClef = 10 : "+cesar.dechiffrer(10).substring(0,25)+"\nClef = 11 : "+cesar.dechiffrer(11).substring(0,25)+"\nClef = 12 : "+cesar.dechiffrer(12).substring(0,25)+"\nClef = 13 : "+cesar.dechiffrer(13).substring(0,25)+"\nClef = 14 : "+cesar.dechiffrer(14).substring(0,25)+"\nClef = 15 : "+cesar.dechiffrer(15).substring(0,25)+"\nClef = 16 : "+cesar.dechiffrer(16).substring(0,25)+"\nClef = 17 : "+cesar.dechiffrer(17).substring(0,25)+"\nClef = 18 : "+cesar.dechiffrer(18).substring(0,25)+"\nClef = 19 : "+cesar.dechiffrer(19).substring(0,25)+"\nClef = 20 : "+cesar.dechiffrer(20).substring(0,25)+"\nClef = 21 : "+cesar.dechiffrer(21).substring(0,25)+"\nClef = 22 : "+cesar.dechiffrer(22).substring(0,25)+"\nClef = 23 : "+cesar.dechiffrer(23).substring(0,25)+"\nClef = 24 : "+cesar.dechiffrer(24).substring(0,25)+"\nClef = 25 : "+cesar.dechiffrer(25).substring(0,25)+"", "Recherche de la clef de cryptage", JOptionPane.INFORMATION_MESSAGE);   
				else JOptionPane.showMessageDialog(null,"Votre message est bien trop court pour une analyse fréquentielle.\n\n"+"Brute force :"+"\nClef = 0 : "+cesar.dechiffrer(0).substring(0,texte_message_crypte.getText().length())+"\nClef = 1 : "+cesar.dechiffrer(1).substring(0,texte_message_crypte.getText().length())+"\nClef = 2 : "+cesar.dechiffrer(2).substring(0,texte_message_crypte.getText().length())+"\nClef = 3 : "+cesar.dechiffrer(3).substring(0,texte_message_crypte.getText().length())+"\nClef = 4 : "+cesar.dechiffrer(4).substring(0,texte_message_crypte.getText().length())+"\nClef = 5 : "+cesar.dechiffrer(5).substring(0,texte_message_crypte.getText().length())+"\nClef = 6 : "+cesar.dechiffrer(6).substring(0,texte_message_crypte.getText().length())+"\nClef = 7 : "+cesar.dechiffrer(7).substring(0,texte_message_crypte.getText().length())+"\nClef = 8 : "+cesar.dechiffrer(8).substring(0,texte_message_crypte.getText().length())+"\nClef = 9 : "+cesar.dechiffrer(9).substring(0,texte_message_crypte.getText().length())+"\nClef = 10 : "+cesar.dechiffrer(10).substring(0,texte_message_crypte.getText().length())+"\nClef = 11 : "+cesar.dechiffrer(11).substring(0,texte_message_crypte.getText().length())+"\nClef = 12 : "+cesar.dechiffrer(12).substring(0,texte_message_crypte.getText().length())+"\nClef = 13 : "+cesar.dechiffrer(13).substring(0,texte_message_crypte.getText().length())+"\nClef = 14 : "+cesar.dechiffrer(14).substring(0,texte_message_crypte.getText().length())+"\nClef = 15 : "+cesar.dechiffrer(15).substring(0,texte_message_crypte.getText().length())+"\nClef = 16 : "+cesar.dechiffrer(16).substring(0,texte_message_crypte.getText().length())+"\nClef = 17 : "+cesar.dechiffrer(17).substring(0,texte_message_crypte.getText().length())+"\nClef = 18 : "+cesar.dechiffrer(18).substring(0,texte_message_crypte.getText().length())+"\nClef = 19 : "+cesar.dechiffrer(19).substring(0,texte_message_crypte.getText().length())+"\nClef = 20 : "+cesar.dechiffrer(20).substring(0,texte_message_crypte.getText().length())+"\nClef = 21 : "+cesar.dechiffrer(21).substring(0,texte_message_crypte.getText().length())+"\nClef = 22 : "+cesar.dechiffrer(22).substring(0,texte_message_crypte.getText().length())+"\nClef = 23 : "+cesar.dechiffrer(23).substring(0,texte_message_crypte.getText().length())+"\nClef = 24 : "+cesar.dechiffrer(24).substring(0,texte_message_crypte.getText().length())+"\nClef = 25 : "+cesar.dechiffrer(25).substring(0,texte_message_crypte.getText().length()), "Recherche de la clef de cryptage", JOptionPane.INFORMATION_MESSAGE);
			}
			
			else if(choixcryptage==BOUTTON_CESAR_AMELIORE){
				Attaque_cesar_complet attaqueclef= new Attaque_cesar_complet(texte_message_crypte.getText(),"Francais");
				Cesar_complet cesar = new Cesar_complet(texte_message_crypte.getText());
				
				StringBuilder sb = new StringBuilder();
				int j=0;
				for(int i=0;i<255;i++){
					sb.append(i+":"+cesar.dechiffrer(i).substring(0,9)+" ");
					if(j>8){
						sb.append("\n");
						j=0;
					}else j++;
				}
				if((texte_message_crypte.getText()).length()>25) JOptionPane.showMessageDialog(null,"Clef la plus probable : "+attaqueclef.cleflaplusprobable+"\nTexte déchiffré associé : "+cesar.dechiffrer(attaqueclef.cleflaplusprobable).substring(0,25)+"\n\n2ème clef la plus probable : "+attaqueclef.deuxiemecleflaplusprobable+"\nTexte déchiffré associé : : "+cesar.dechiffrer(attaqueclef.deuxiemecleflaplusprobable).substring(0,25)+"\n\n3ème clef la plus probable : "+(attaqueclef.cleflaplusprobable-32)+"\nTexte déchiffré associé : "+cesar.dechiffrer(attaqueclef.cleflaplusprobable-32).substring(0,25)+"\n\n4ème clef la plus probable : "+(attaqueclef.deuxiemecleflaplusprobable-32)+"\nTexte déchiffré associé : "+cesar.dechiffrer(attaqueclef.deuxiemecleflaplusprobable-32).substring(0,25)+"\n\nForce brute :\n"+sb, "Recherche de la clef de cryptage", JOptionPane.INFORMATION_MESSAGE);   
				else JOptionPane.showMessageDialog(null,"Votre message est bien trop court pour une analyse fréquentielle.\n\nForce brute :\n"+sb, "Recherche de la clef de cryptage", JOptionPane.INFORMATION_MESSAGE);				
			}
			
			else if(choixcryptage==BOUTTON_VIGENERE){
				Attaque_vigenere attaqueclef= new Attaque_vigenere(texte_message_crypte.getText());

				if((texte_message_crypte.getText()).length()>100) JOptionPane.showMessageDialog(null,"Taille de la clef : "+attaqueclef.tailleclef+"\nLa fonction de cryptanalyse n'est pas terminé et ne permet donc pas de retrouver le message complet","Recherche de la clef de cryptage", JOptionPane.INFORMATION_MESSAGE);   
				else JOptionPane.showMessageDialog(null,"Votre message crypté est bien trop court pour une analyse Kasiski (il faut au minimum 100 caractères)", "Recherche de la clef de cryptage", JOptionPane.INFORMATION_MESSAGE);				
			}		
			
			else if(choixcryptage==BOUTTON_VIGENERE_AMELIORE){
				 JOptionPane.showMessageDialog(null,"Cete fonction n'a pas encore été codé","Recherche de la clef de cryptage",JOptionPane.INFORMATION_MESSAGE);	
			}
			
			else if(choixcryptage==BOUTTON_DES){
				 JOptionPane.showMessageDialog(null,"Cete fonction n'a pas encore été codé","Recherche de la clef de cryptage",JOptionPane.INFORMATION_MESSAGE);	
			}
			
			else if(choixcryptage==BOUTTON_AES){
				 JOptionPane.showMessageDialog(null,"Cete fonction n'a pas encore été codé","Recherche de la clef de cryptage",JOptionPane.INFORMATION_MESSAGE);	
			}
			
			
		}
		
		if(cmd.equals(BOUTTON_LANCER_DECRYPTAGE)){
			System.out.println("Debut du décryptage "+choixcryptage);
			String messageadecrypter = texte_message_crypte.getText();
			String maclef2 = texte_clef.getText();
			System.out.println("Debut du décryptage type "+choixcryptage+" en utilisant la clef \""+maclef2+"\" et le message crypté \""+messageadecrypter+"\"");
							
			if(choixcryptage==BOUTTON_AES)
			{
				if(maclef2.length()>=16){
					AES aes2;
					byte b2[]= null;
					aes2 = new AES(maclef2.getBytes());		//Construction de l'objet AES
					b2 = messageadecrypter.getBytes();
					String messagedechiffe5;
					messagedechiffe5 = new String(aes2.dechiffrerMess(b2));//Déchiffrement du message chiffré
					texte_message_decrypte.setText(messagedechiffe5);
					System.out.println("Message déchiffré :\n"+messagedechiffe5);				
				}
				else {
					 JOptionPane.showMessageDialog(null,"Le cryptage est de type AES 128 bits.\nVotre clef de cryptage doit donc contenir au minimum 16 caractères." , AIDE, JOptionPane.ERROR_MESSAGE);      
				}
			}else if(choixcryptage==BOUTTON_CESAR_AMELIORE){
				try{
					int maclefcesar2 = Integer.parseInt(maclef2);
					Cesar_complet cesar4 = new Cesar_complet(messageadecrypter);
					String messageadecrypter7=cesar4.dechiffrer(maclefcesar2);
					System.out.println("Message déchiffré :  "+messageadecrypter7+"\n");
					texte_message_decrypte.setText(messageadecrypter7);
				}
				catch(NumberFormatException f){
					JOptionPane.showMessageDialog(null,"Le cryptage est de type César amélioré.\nVotre clef de cryptage doit donc contenir uniquement un entier compris entre 0 et 224." , AIDE, JOptionPane.ERROR_MESSAGE);      
				}
			}else if(choixcryptage==BOUTTON_CESAR_CLASSIQUE){
				try{
					int maclefcesar9 = Integer.parseInt(maclef2);
					Cesar_classique cesar9 = new Cesar_classique(messageadecrypter);
					String messageadecrypter9=cesar9.dechiffrer(maclefcesar9);
					System.out.println("Message déchiffré :  "+messageadecrypter9+"\n");
					texte_message_decrypte.setText(messageadecrypter9);
				}
				catch(NumberFormatException f){
					JOptionPane.showMessageDialog(null,"Le cryptage est de type César classique.\nVotre clef de cryptage doit donc contenir uniquement un entier compris entre 0 et 26." , AIDE, JOptionPane.ERROR_MESSAGE);      
				}
				
				
				
				
				
			}else if(choixcryptage==BOUTTON_DES){
				if(maclef2.length()==7){
					try {
						texte_message_decrypte.setText(DESUtil.AsciiDecoding(Binary.binaryStringToIntTable(DES.decode(DESUtil.messageToDESBinaryString(messageadecrypter), maclef2))));
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						JOptionPane.showMessageDialog(null,"Impossible de décrypter.\nErreur inconnu\nAvez vous bien mis le bon message crypté ?." , AIDE, JOptionPane.ERROR_MESSAGE);   

					}				
				}
				else{
					JOptionPane.showMessageDialog(null,"Le cryptage est de type DES 56 bits.\nVotre clef de cryptage doit donc contenir 7 caractères ASCII." , AIDE, JOptionPane.ERROR_MESSAGE);   
				}
			}else if(choixcryptage==BOUTTON_VIGENERE){
				
				texte_message_decrypte.setText((new Vigenere(messageadecrypter)).dechiffrer(maclef2));
			}else if(choixcryptage==BOUTTON_VIGENERE_AMELIORE){
				
				texte_message_decrypte.setText((new Vigenere_complet(messageadecrypter)).dechiffrer(maclef2));
			}
				

			
		}
	
		
	}			

			

//Les méthodes de la souris :

	public void mouseMoved(MouseEvent e){
		//System.out.println("j'ai bougé\n");
	}

	public void mouseClicked(MouseEvent e){
		/*texte_message_decrypte texte_message_crypte texte_clef*/
		
		System.out.println("j'ai appuyé\n");
	}
	
	public void mouseReleased(MouseEvent e){
		//System.out.println("j'ai relaché\n");
	}
	
	public void mouseEntered(MouseEvent e) {
		//System.out.println("Je suis allé sur un élément\n");
	}

	public void mouseExited(MouseEvent e) {
		//System.out.println("Je suis parti d'un élément\n");
	}

	public void mouseDragged(MouseEvent e) {
		//System.out.println("J'ai appuyé et bougé\n");
	}	
	public void mousePressed(MouseEvent e){	
		//System.out.println("J'ai appuyé et je le suis encore \n");		
	}
	
	}  
