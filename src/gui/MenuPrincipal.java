package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.text.Highlighter.HighlightPainter;

import algo.AhoCorasick;

@SuppressWarnings("serial")
/**
 * Classe représentant la fenêtre principale du programme.
 * 
 * @author Romain SEMLER - Rémi SOULIER
 * @version 1.0
 * @date 21/10/2015
 *
 */
public class MenuPrincipal extends JFrame implements ActionListener{
	
	// Listes nécessaires aux fonctionalités du programme
	private ArrayList<String> mots = new ArrayList<String>();
	private ArrayList<String> motsTmp = new ArrayList<String>();
	private ArrayList<String> motsTrouves = new ArrayList<String>();
	
	// Tampon de lecture
	private BufferedReader bufferMots;
	
	// Objets de fichier
	private JFileChooser ouvrirFichier;
	private FileNameExtensionFilter format;
	private File fichierMots;
	private File fichierTexte;
	
	// Dimensions d'objets
	private Dimension dimSaisie;
	private Dimension dimBoutonAjout;
	private Dimension dimBoutonRecherche;
	private Dimension dimListeMots;
	private Dimension dimTexte;
	
	// Boutons
	private JButton ajouter;
	private JButton rechercher;
	
	// Barres de défilement
	private JScrollPane scrollListe;
	private JScrollPane scrollTexte;
	
	// Champs de texte
	private JTextField saisieMot;
	private JTextArea listeMots;
	private static JTextArea texte;
	
	// Case à cocher
	private JCheckBox casse;
	
	// Panels utilisés
	private JPanel conteneurWest;
	private JPanel conteneurEst;
	private JPanel conteneurSaisie;
	private JPanel conteneurRecherche;
	private JPanel conteneurPrincipal;
	
	// Label utilisé
	private JLabel nombreDeMotsTrouves;
	
	// Layouts utilisés
	private BorderLayout blEst;
	private GridLayout glWest;
	private BorderLayout blRecherche;
	private BorderLayout blPrincipal;
	private BorderLayout blSaisie;
	
	// Item de menu
	private JMenu recherche;
	
	// Police d'écriture
	private Font police;
	
	// Surlignement
	private static Highlighter motTexte;
	private static HighlightPainter painter;
	private static boolean estSurligne = false;
	
	/**
	 * Constructeur de la classe "MenuPrincipal".
	 */
	public MenuPrincipal() {
		super("Algorithme d'Aho-Corasick");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setContentPane(Conteneur());
        this.setJMenuBar(BarreMenu());
        this.setSize(550, 570);
	}
	
	/**
	 * Fonction mettant en place le panel de la fenêtre.
	 * 
	 * @return Le panel de la fenêtre.
	 */
	JPanel Conteneur() {
    	
		dimSaisie = new Dimension(200, 5);
		dimBoutonAjout = new Dimension(200, 5);
		dimBoutonRecherche = new Dimension(150, 50);
		dimListeMots = new Dimension(140, 170);
		dimTexte = new Dimension(180, 240);
		
		conteneurWest = new JPanel();
		conteneurEst = new JPanel();
    	conteneurSaisie = new JPanel();
    	conteneurRecherche = new JPanel();
    	conteneurPrincipal = new JPanel();
    	
    	nombreDeMotsTrouves = new JLabel();
    	
    	blEst = new BorderLayout();
    	glWest = new GridLayout(3, 1);
    	blRecherche = new BorderLayout();
    	blPrincipal = new BorderLayout();
    	blSaisie = new BorderLayout();
    	
    	ajouter = new JButton("Ajouter");
    	saisieMot = new JTextField();
    	casse = new JCheckBox("Sensible à la casse");
    	listeMots = new JTextArea();
    	texte = new JTextArea();
    	rechercher = new JButton("Lancer la recherche");
    	
    	police = new Font("Trebuchet MS", Font.BOLD, 15);
    	
    	listeMots.setEditable(false);
    	
    	saisieMot.setFont(police);
    	ajouter.setFont(police);
    	rechercher.setFont(police);
    	nombreDeMotsTrouves.setFont(police);
    	
    	texte.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	rechercher.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	nombreDeMotsTrouves.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    	
    	
    	scrollListe = new JScrollPane(listeMots);
    	scrollTexte = new JScrollPane(texte);
    	
    	
    	scrollListe.setPreferredSize(dimListeMots);
    	scrollTexte.setPreferredSize(dimTexte);
    	
    	scrollListe.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	scrollTexte.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	
    	scrollTexte.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));

    	rechercher.setOpaque(true);
    	
    	rechercher.setBackground(Color.ORANGE);
    	
    	casse.setSelected(true);
    	casse.setBackground(Color.LIGHT_GRAY);
    	
    	ajouter.setPreferredSize(dimBoutonAjout);
    	saisieMot.setPreferredSize(dimSaisie);
    	rechercher.setPreferredSize(dimBoutonRecherche);
    	
    	conteneurEst.setLayout(blEst);
    	conteneurEst.setBorder(BorderFactory.createEmptyBorder(20, 0, -2, 70));
    	conteneurEst.setBackground(Color.LIGHT_GRAY);
    	
    	conteneurWest.setLayout(glWest);
    	conteneurWest.setBorder(BorderFactory.createEmptyBorder(20, 70, -2, 0));
    	conteneurWest.setBackground(Color.LIGHT_GRAY);
    	
    	conteneurSaisie.setLayout(blSaisie);
    	conteneurSaisie.setBackground(Color.LIGHT_GRAY);
    	
    	conteneurRecherche.setLayout(blRecherche);
    	conteneurRecherche.setBackground(Color.LIGHT_GRAY);
    	
    	conteneurPrincipal.setLayout(blPrincipal);
    	conteneurPrincipal.setBackground(Color.LIGHT_GRAY);
        	
    	conteneurWest.add(saisieMot, BorderLayout.NORTH); 
    	conteneurWest.add(ajouter, BorderLayout.CENTER);
    	
    	conteneurEst.add(scrollListe, BorderLayout.NORTH);
    	conteneurEst.add(casse, BorderLayout.CENTER);
    	
    	conteneurSaisie.add(conteneurWest, BorderLayout.WEST);
    	conteneurSaisie.add(conteneurEst, BorderLayout.EAST);
    	
    	conteneurRecherche.add(scrollTexte, BorderLayout.NORTH);
    	conteneurRecherche.add(rechercher, BorderLayout.CENTER);
        
        ajouter.addActionListener(this);
        rechercher.addActionListener(this);
        casse.addActionListener(this);
        
        conteneurPrincipal.add(conteneurSaisie, BorderLayout.NORTH);
        conteneurPrincipal.add(nombreDeMotsTrouves, BorderLayout.CENTER);
        conteneurPrincipal.add(conteneurRecherche, BorderLayout.SOUTH);

        
        return conteneurPrincipal;
    }

	/**
	 * Fonction mettant en place la barre des menus.
	 * 
	 * @return La barre des menus.
	 */
	private JMenuBar BarreMenu() {
	     	
     	JMenuBar menu = new JMenuBar();
     	
     	JMenu fichier = new JMenu("Fichier");	
     	menu.add(fichier);
     	
     	recherche = new JMenu("Recherche");	
     	menu.add(recherche);     	
     	recherche.setEnabled(false);
     	
     	JMenuItem details = new JMenuItem("Voir les détails");			
     	recherche.add(details);												
     	details.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                afficherDetails();
            }
        });
     	
     	JMenuItem item = new JMenuItem("Ajouter des mots");			
     	fichier.add(item);												
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ajoutMots();        	
            }
        });
        
     	item = new JMenuItem("Ajouter un texte");			
     	fichier.add(item);												
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                ajoutTexte();            	
            }
        });
        
        fichier.addSeparator();
        
        item = new JMenuItem("Supprimer les mots");			
        fichier.add(item);												
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	supprimerListe();            	
            }
        });
        
        item = new JMenuItem("Supprimer le texte");			
        fichier.add(item);												
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	supprimerTexte();            	
            }
        });
        
        item = new JMenuItem("Tout supprimer");			
        fichier.add(item);												
        item.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            	supprimerTout();            	
            }
        });

     	return menu;
	}
	
	/**
	 * Ecoute d'action lors de l'appui sur le bouton "Ajouter" ou "Rechercher".
	 */
	public void actionPerformed(ActionEvent arg0) {
		    	
		if(arg0.getSource() == ajouter) {
			if(saisieMot.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Il n'y a aucun mot à ajouter");
			} else {
				majListe(saisieMot.getText());
			}
		}
		
		if(arg0.getSource() == rechercher) {
			if (texte.getText().equals("") || listeMots.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "Veuillez ajouter des mots-clés et un texte pour lancer la recherche.");
			} else {
				recherche.setEnabled(true);
				if (estSurligne) {
					texte.getHighlighter().removeAllHighlights();
				}
				
				if (casse.isSelected()) {
					motsTrouves.clear();
					AhoCorasick recherche = new AhoCorasick(texte.getText(), mots);
					motsTrouves = new ArrayList<String>(recherche.trouverMots());
					setNbMotsTrouves(motsTrouves.get(motsTrouves.size() - 1));
					recherche.reinitialiser();
				} else {
					motsTrouves.clear();
					for (int i = 0; i < motsTmp.size(); i++) {
						motsTmp.set(i, motsTmp.get(i).toUpperCase());
					}
					AhoCorasick recherche = new AhoCorasick(texte.getText().toUpperCase(), motsTmp);
					motsTrouves.addAll(recherche.trouverMots());
					setNbMotsTrouves(motsTrouves.get(motsTrouves.size() - 1));
					recherche.reinitialiser();
				}
			}
		}
	}
		 
	/**
	 * Ajoute des mots à la liste de recherche par une ouverture de fichier texte.
	 */
	private void ajoutMots() {
		ouvrirFichier = new JFileChooser();
		format = new FileNameExtensionFilter(".txt", "txt");
		
		ouvrirFichier.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ouvrirFichier.setFileFilter(format);
				
		if(ouvrirFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			fichierMots = ouvrirFichier.getSelectedFile();
			majListe(fichierMots);
		}
	}
		    
	/**
	 * Ajoute un texte par une ouverture de fichier texte.
	 */
	private void ajoutTexte() {
		ouvrirFichier = new JFileChooser();
		format = new FileNameExtensionFilter(".txt", "txt");
		
		ouvrirFichier.setFileSelectionMode(JFileChooser.FILES_ONLY);
		ouvrirFichier.setFileFilter(format);
				
		if(ouvrirFichier.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			fichierTexte = ouvrirFichier.getSelectedFile();
			majTexte(fichierTexte);
		}
	}
	
	/**
	 * Met à jour la liste des mots à chercher lors d'un ajout de mots.
	 * 
	 * @param mot
	 * 		Le mot à ajouter.
	 */
	private void majListe(String mot) {
		saisieMot.setText("");
		listeMots.setText("");
		mots.add(mot);
		motsTmp.add(mot);
		for (int i = 0; i < mots.size(); i++) {
			listeMots.append(mots.get(i) + "\n");
		}
	}
	
	/**
	 * Met à jour la liste des mots à chercher lors d'un ajout de mots par l'ouverture d'un fichier.
	 * 
	 * @param fichierMots
	 * 		Le fichier texte contenant les mots à chercher.
	 */
	private void majListe(File fichierMots) {
		saisieMot.setText("");
		listeMots.setText("");
		String line;
		try {
			bufferMots = new BufferedReader(new InputStreamReader(new FileInputStream(fichierMots)));
			try {
				while ((line = bufferMots.readLine()) != null) {
					mots.add(line);
					motsTmp.add(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < mots.size(); i++) {
			listeMots.append(mots.get(i) + "\n");
		}
	}
	
	/**
	 * Met à jour le champ texte lors d'un nouvel ajout de texte par l'ouverture d'un fichier.
	 * 
	 * @param fichierTexte
	 * 		Le fichier texte contenant le texte.
	 */
	private void majTexte(File fichierTexte) {
		texte.setText("");

		String line;
		try {
			bufferMots = new BufferedReader(new InputStreamReader(new FileInputStream(fichierTexte)));
			try {
				while ((line = bufferMots.readLine()) != null) {
					texte.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Supprime la liste des mots à rechercher.
	 */
	private void supprimerListe() {
		if(listeMots.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Il n'y a aucun mot à supprimer.");
		} else {
			recherche.setEnabled(false);
			nombreDeMotsTrouves.setText("");
			mots.clear();
			motsTmp.clear();
			listeMots.setText("");
		}
	}
	
	/**
	 * Supprime entièrement le texte.
	 */
	private void supprimerTexte() {
		if(texte.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Il n'y a aucun texte à supprimer");
		} else {
			recherche.setEnabled(false);
			nombreDeMotsTrouves.setText("");
			texte.setText("");
		}
	}
	
	/**
	 * Supprime la liste des mots à rechercher ainsi que tout le texte.
	 */
	private void supprimerTout() {
		if(listeMots.getText().equals("") && texte.getText().equals("")) {
			JOptionPane.showMessageDialog(this, "Il n'y a rien à supprimer.");
		} else {
			recherche.setEnabled(false);
			nombreDeMotsTrouves.setText("");
			mots.clear();
			motsTmp.clear();
			listeMots.setText("");
			texte.setText("");
		}
	}
	
	/**
	 * Met à jour le label en indiquant le nombre d'occurences trouvées.
	 * 
	 * @param nb
	 * 		Le nombre d'occurences trouvées.
	 */
	public void setNbMotsTrouves(String nb) {
		
		int nbMots = Integer.parseInt(nb);
		
		if (nbMots == 0) {
			nombreDeMotsTrouves.setText("Aucune occurence n'a été trouvé dans le texte.");
			recherche.setEnabled(false);
		} else if (nbMots == 1) {
			nombreDeMotsTrouves.setText(nbMots + " occurence a été trouvé.");
		} else {
			nombreDeMotsTrouves.setText(nbMots + " occurences ont été trouvés.");

		}
	}
	
	/**
	 * Surligne un mot dans le texte pour indiquer sa présence.
	 * 
	 * @param mot
	 * 		Le mot à surligner.
	 * @param caractere
	 * 		Le dernier caractère du mot à surligner.
	 */
	public static void surligner(String mot, int caractere) {
		motTexte = texte.getHighlighter();
		painter = new DefaultHighlighter.DefaultHighlightPainter(Color.GREEN);
		int p0 = caractere - mot.length();
		int p1 = caractere;
		try {
			motTexte.addHighlight(p0, p1, painter);
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		
		estSurligne = true;
	}
	
	/**
	 * Affiche les détails de la recherche dans une boîte de dialogue.
	 * 
	 * @see Details
	 */
	private void afficherDetails() {
		Details vueDetails = new Details(this, "Détails des occurences", true, motsTrouves);
		vueDetails.setLocationRelativeTo(null);
		vueDetails.setVisible(true);
	}
	
	/**
	 * Fonction principale de l'ensemble du programme.
	 */
	public static void main(String[] args) {
		MenuPrincipal menuPrincipal = new MenuPrincipal();
		menuPrincipal.setLocationRelativeTo(null);
		menuPrincipal.setVisible(true);
	}
}
