package algo;

import java.util.ArrayList;

/**
 * Classe représentant un noeud de l'arbre.
 * 
 * @author Romain SEMLER - Rémi SOULIER
 * @version 1.0
 * @date 21/10/2015
 *
 */
public class Noeud {

    // Le noeud parent
    private Noeud noeudParent;
    // L'état d'echec du noeud
    private Noeud etatEchec;
    // La lettre du noeud
    private char lettre;
    // La liste des noeuds enfants
    private ArrayList<Noeud> enfants;
    // Indique si le mot est fini ou pas
    private boolean estFini;
    
    /**
     * Constructeur de la classe "Noeud".
     * 
     * @param lettre
     * 		Une des lettres du mot à chercher avec un paramètre.
     */
    public Noeud(char lettre){
        this.lettre = lettre;
        this.enfants = null;
        this.estFini = false;
        this.noeudParent = null;
        this.etatEchec = null;
    }
    
    /**
     * Constructeur de la classe "Noeud" avec deux paramètres
     * 
     * @param lettre
     * 		Une des lettres du mot à chercher.
     * @param parent
     * 		Le noeud parent auquel sera associé le noeud contenant la lettre.
     */
    public Noeud(char lettre, Noeud parent) {
        this.lettre = lettre;
        this.enfants = null;
        this.estFini = false;
        this.noeudParent = parent;
        this.etatEchec = null;
    }
    
    /**
     * Fonction qui renvoie la lettre d'un noeud.
     * 
     * @return La lettre contenue dans le noeud courant.
     */
    public char getLettre() {
        return lettre;
    }
    
    /**
     * Fonction qui renvoie la liste des noeuds enfants d'un noeud.
     * 
     * @return La liste des noeuds enfants du noeud courant.
     */
    public ArrayList<Noeud> getListeEnfants() {
        return enfants;
    }
        
    /**
     * Fonction qui renvoie le noeud parent d'un noeud
     * 
     * @return Le noeud parent du noeud courant.
     */
    public Noeud getNoeudParent() {
        return noeudParent;
    }
    
    /**
     * Fonction qui renvoie l'état d'échec d'un noeud.
     * 
     * @return L'état d'échec du noeud courant.
     */
    public Noeud getEtatEchec(){
        return etatEchec;
    }
    
    /**
     * Fonction qui modifie l'état d'échec d'un noeud.
     * 
     * @param n
     * 		Le noeud correspondant au nouvel état d'échec.
     */
    public void setEtatEchec(Noeud n){
        this.etatEchec = n;
    }
    
    /**
     * Fonction qui indique si le noeud est un état final ou non.
     * 
     * @return Vrai si le noeud courant est un état final, faux sinon.
     */
    public boolean estFini() {
        return estFini;
    }

    /**
     * Fonction qui modifie le statut d'état final d'un noeud.
     * 
     * @param estFini
     * 		Le nouveau statut d'état du noeud courant.
     */
    public void setEstFini(boolean estFini) {
        this.estFini = estFini;
    }

    /**
     * Fonction qui ajoute un noeud enfant à un noeud.
     * 
     * @param enfant
     * 		Le noeud enfant ajouté au noeud courant.
     */
    public void ajouterEnfant(Noeud enfant) {
        if(enfants == null){
        	enfants = new ArrayList<Noeud>();
            
        } 
        enfants.add(enfant);
    }

    /**
     * Fonction qui renvoie le noeud enfant, d'un noeud, en fonction du caractère indiqué.
     * 
     * @param c
     * 		La lettre associée au noeud enfant à récupérer.
     * @return Le noeud enfant (ou null si non trouvé).
     */
    public Noeud getEnfant(char c){
        if(enfants != null){
            for(Noeud enfant : enfants){
                if(enfant.lettre == c){
                    return enfant;
                }
            }
        }
        return null;
    } 
    
    /**
     * Fonction qui affiche (à la console) l'automate complet avec les mots ajoutés et leurs états d'échec.
     * 
     * @param niveau
     * 		Le niveau de profondeur dans l'automate (0 à la racine puis 0 + n).
     */
    public void afficher(int niveau){
        for(int i = 0; i < niveau; i++){
            System.out.print("  ");
        }
        String echec = "";
        if(etatEchec != null){
        	echec += etatEchec.getLettre();
        }
        System.out.println(lettre + " ECHEC : " + echec + " Etat Final : " + estFini);
        if(enfants != null){        
            for(Noeud n : enfants){
                n.afficher(niveau + 1);
                System.out.println("");
            }
        }
    }
}
