package hello;

public class Credit extends BaseModele{
    
    String nom;
    double montant;
    double reste;

    

    public Credit(int id, String nom,double montant,double reste) {
        super(id);
        this.nom = nom;
        this.montant = montant;
        this.reste = reste;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
    public double getReste() {
        return reste;
    }

    public void setReste(double reste) {
        this.reste = reste;
    }
}
