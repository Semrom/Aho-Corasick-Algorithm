package algo;

import java.util.ArrayList;

/**
 * Classe représentant la préparation d'une recherche Aho-Corasick.
 * 
 * @author Romain SEMLER - Rémi SOULIER
 * @version 1.0
 * @date 21/10/2015
 *
 */
public class AhoCorasick {

	// Liste des mots à chercher
	private ArrayList<String> motAChercher;
	// Liste des mots trouvés
	private ArrayList<String> motsTrouves;
	// Le texte où chercher (stocké sous une chaîne de caractères)
	private String texte;
	// Noeud correspondant à la racine de l'arbre
	private Noeud racine;
	
	/**
	 * Constructeur de la classe "AhoCorasick".
	 * 
	 * @param texte
	 * 		Le texte où chercher les mots.
	 * @param mots
	 * 		La liste de mots à chercher
	 */
	public AhoCorasick(String texte, ArrayList<String> mots) {
		this.texte = texte;
		this.motAChercher = mots;
		ajouterMots();
	}
	
	/**
	 * Fonction qui crée la racine de l'automate et qui envoie les mots à chercher dans l'automate.
	 */
	public void ajouterMots(){
		
		racine = new Noeud('/');
		
		for (int i = 0; i < motAChercher.size(); i++) {
			Automate.ajouterMot(motAChercher.get(i), racine);
		}
	}
	
	/**
	 * Fonction qui commence la recherche des mots.
	 * 
	 * @return Les mots trouvés ainsi que le nombre d'occurences.
	 */
	public ArrayList<String> trouverMots() {

		String[] lines = texte.split("\\n");			
		int compteurLigne = 0;		
		
		Automate.ajouterEtatsEchec(racine);

		for (int i = 0; i < lines.length; i++) {
			compteurLigne++;
			Automate.rechercher(lines[i], racine, compteurLigne);
		}
		
		motsTrouves = Automate.getMotsTrouves();
		motsTrouves.add(String.valueOf(Automate.getNbMotsTrouves()));
		
		return motsTrouves;
	}
	
	/**
	 * Fonction qui demande la réinitialisation des données de recherche.
	 * 
	 * @see Automate#reinitialiser()
	 */
	public void reinitialiser() {
		Automate.reinitialiser();
	}
}
