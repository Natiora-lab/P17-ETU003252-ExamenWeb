package hello;

public class Depense extends BaseModele{
    
    int id_credit;
    double montant;
    String name;
    double reste;
   
    public Depense(int id,int idcredit,String name,double montant,double reste) {
        super(id);
        this.montant = montant;
        this.id_credit = idcredit;
        this.id_credit = idcredit;
        this.name = name;
        this.reste = reste;
    }
    public int getId_credit() {
        return id_credit;
    }
    public void setId_credit(int id_credit) {
        this.id_credit = id_credit;
    }
    public double getMontant() {
        return montant;
    }
    public void setMontant(double montant) {
        this.montant = montant;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public double getReste() {
        return reste;
    }
    public void setReste(double reste) {
        this.reste = reste;
    }
    
}
