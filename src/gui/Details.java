package gui;

import java.util.ArrayList;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
/**
 * Classe représentant la boîte de dialogue des détails d'une recherche.
 * 
 * @author Romain SEMLER - Rémi SOULIER
 * @version 1.0
 * @date 21/10/2015
 *
 */
public class Details extends JDialog {

	// Liste de copie
	private ArrayList<String> copie = new ArrayList<String>();
	
	// Objets de tableau
	private JTable tableauOccurences;
	private DefaultTableModel tableau;
	
	// Tableaux d'objet
	private static final Object[][] occurences = {};
	private static final Object[] nomsColonnes = {"Mot", "Ligne"};
	
	/**
	 * Constructeur de la classe "Details".
	 * 
	 * @param parent
	 * 		La fenêtre parent.
	 * @param titre
	 * 		Le titre de la boîte de dialogue.
	 * @param modal
	 * 		Modalité de la boîte de dialogue.
	 * @param motsTrouves
	 * 		Liste des mots trouvés dans le texte.
	 */
	public Details(JFrame parent, String titre, boolean modal, ArrayList<String> motsTrouves) {
		super(parent, titre, modal);
	    this.setSize(300, 200);
	    this.setResizable(false);
	    this.copie.clear();
	    this.copie = new ArrayList<String>(motsTrouves);

	    // Suppression du dernier élément de la liste (correspond au nombre d'occurences).
	    copie.remove(copie.size() - 1);
	    
	    tableau = new DefaultTableModel(occurences, nomsColonnes) {
	    	// Cases du tableau non éditables
	    	public boolean isCellEditable(int lignes, int colonnes) {
	    		return false;
	    	}
	    };
	    
	    for (int i = 0; i < copie.size(); i += 2) {
	        tableau.addRow(new Object[]{copie.get(i), copie.get(i + 1)});
	    }
	    
	    tableauOccurences = new JTable(tableau);
	    tableauOccurences.setBounds(37, 143, 397, 183);
	    
	    this.getContentPane().add(new JScrollPane(tableauOccurences));
	}
}
