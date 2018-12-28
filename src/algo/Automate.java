package algo;

import gui.MenuPrincipal;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Classe (sans constructeur) représentant l'automate de recherche.
 * 
 * @author Romain SEMLER - Rémi SOULIER
 * @version 1.0
 * @date 21/10/2015
 *
 */
public class Automate {

	// Liste des mots trouvés
	private static ArrayList<String> listeMotTrouve = new ArrayList<String>();
	// Châine de caractères contenant un mot trouvé
	private static String mot;
	// Noeud parent d'un autre
	private static Noeud parent;
	// Tableau de char pour stocker les cracatères d'un mot
	private static char[] tab;
	// Nombre de caractères lus
	private static int nbCarac = 0;
	// Nombre de mots trouvés
	private static int nbMotsTrouves = 0;
	
	/**
	 * Fonction statique qui ajoute les mots reçus dans l'automate.
	 * 
	 * @param mot
	 * 		Le mot à ajouter dans l'automate.
	 * @param noeud
	 * 		Le noeud de référence (ou de départ).
	 */
	public static void ajouterMot(String mot, Noeud noeud) {
		
		if (mot.length() != 0) {			
        	Noeud n = noeud.getEnfant(mot.charAt(0));
            if (n == null) {
            	Noeud enfant = new Noeud(mot.charAt(0), noeud);
                if (mot.length() == 1) {
                    enfant.setEstFini(true);
                }
                noeud.ajouterEnfant(enfant);
                ajouterMot(mot.substring(1), enfant);
            } else {
            	ajouterMot(mot.substring(1), n);
            }
        }
    }

	/**
	 * Fonction statique qui affiche l'automate à partir de la profondeur 0.
	 * 
	 * @see Noeud#afficher(int)
	 * @param noeud
	 * 		Le neoud de départ de l'affichage.
	 */
    public static void afficherAutomate(Noeud noeud) {
        noeud.afficher(0);
    }
    
    /**
     * Fonction statique qui ajoute les états d'échec à chaque noeud de l'automate.
     * 
     * @param racine
     * 		Le noeud de référence pour les états d'échec.
     */
    public static void ajouterEtatsEchec(Noeud racine){
        LinkedList<Noeud> etat = new LinkedList<Noeud>();

        racine.setEtatEchec(racine);
        etat.add(racine);

        while(etat.size() != 0){
        	Noeud enTete = (Noeud) etat.poll();

            if(enTete.getListeEnfants() != null){
                for(Noeud enfant : enTete.getListeEnfants()){
                	etat.add(enfant);
                }
            }

            if(enTete != racine){
            	Noeud etatEchec = enTete.getNoeudParent().getEtatEchec();

                while(etatEchec.estFini() && etatEchec != racine){
                	etatEchec = etatEchec.getEtatEchec();
                }

                enTete.setEtatEchec(etatEchec.getEnfant(enTete.getLettre()));
                
                if(enTete.getEtatEchec() == null){
                	enTete.setEtatEchec(racine);
                }
                if(enTete.getEtatEchec() == enTete){
                	enTete.setEtatEchec(racine);
                }
            }
        }
    }
    
    /**
     * Fonction statique qui recherche dans l'automate un mot, cracatère par caractère, à partir d'un noeud.
     * 
     * @param texte
     * 		Le texte où chercher les mots.
     * @param racine
     * 		Le noeud par où commencer.
     * @param numeroLigne
	 * 		Le numéro de la ligne de texte en cours de lecture.
     */
	public static void rechercher(String texte, Noeud racine, int numeroLigne){
		Noeud etatCourant = racine;
		Noeud n, nbis;
        char lettreCourante;
        int i;

        for(i = 0; i < texte.length(); i++){
        	nbCarac++;
            n = etatCourant;
            lettreCourante = texte.charAt(i);

            while(n.getEnfant(lettreCourante) == null && n != racine){
                n = n.getEtatEchec();
            }

            if(n == racine){
                n = n.getEnfant(lettreCourante);

                if(n == null){
                    n = racine;
                }
                
            } else {
                n = n.getEnfant(lettreCourante);
            }
            
            nbis = n;
            while(nbis != racine){
                
            	if(nbis.estFini()){
                	motTrouve(lettreCourante, nbis, racine, numeroLigne, nbCarac);
                } 
                
                nbis = nbis.getEtatEchec();
            }
            
            etatCourant = n;
        } 
        
        // Incrémentation nécessaire pour "\n"
        nbCarac++;
    }
	
	/**
	 * Fonction statique qui ajoute un mot à la liste des mots trouvés.
	 * 
	 * @param lettreCourante
	 * 		Le caractère courant.
	 * @param noeudCourant
	 * 		Le noeud courant (qui est donc forcément un état final).
	 * @param racine
	 * 		Le noeud par où l'on a commencé la recherche.
	 * @param numeroLigne
	 * 		Le numéro de la ligne de texte en cours de lecture.
	 * @param nbCarac
	 * 		Le nombre de caractères lus.
	 */
	public static void motTrouve(char lettreCourante, Noeud noeudCourant, Noeud racine, int numeroLigne, int nbCarac) {
		
		parent = noeudCourant.getNoeudParent();
		String ligne = String.valueOf(numeroLigne);
		
		if (parent != racine) {
			tab = new char[50];
	    	int j = 2;
	    			
	    	tab[0] = lettreCourante;
	    	tab[1] = parent.getLettre();
	    	
	    	

	    	while(parent != racine) {
				parent = parent.getNoeudParent();
				
				if (parent == racine) {
					break;
				}
				
				tab[j] = parent.getLettre();
				++j;
			}
	    	
	    	mot = new String(tab);
	    	mot = mot.substring(0, j);
			mot = new StringBuffer(mot).reverse().toString();
			
			listeMotTrouve.add(mot);
			listeMotTrouve.add(ligne);
			nbMotsTrouves++;
			MenuPrincipal.surligner(mot, nbCarac);
			
		} else {
			mot = String.valueOf(lettreCourante);
			listeMotTrouve.add(mot);
			listeMotTrouve.add(ligne);
			nbMotsTrouves++;
			MenuPrincipal.surligner(mot, nbCarac);
		}		
	}
	
	/**
	 * Fonction statique qui renvoie le nombre de mots trouvés.
	 * 
	 * @return Le nombre de mots trouvés.
	 */
	public static int getNbMotsTrouves() {
		return nbMotsTrouves;
	}
	
	/**
	 * Fonction statique qui réinitialise les données de recherche (caractères, mots et listes).
	 */
	public static void reinitialiser() {
		nbCarac = 0;
		nbMotsTrouves = 0;
		listeMotTrouve.clear();
	}
	
	/**
	 * Fonction statique qui affiche la liste des mots trouvés.
	 */
	public static ArrayList<String> getMotsTrouves() {
		return listeMotTrouve;
	}
}
