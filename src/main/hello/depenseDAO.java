package hello;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class depenseDAO extends InterfaceDAO {
     
    //fonction save
    public  void save(Connection DB,BaseModele b) throws Exception {
        Depense m=(Depense)b;
        String request = "INSERT INTO Depense (id_credit ,montant) VALUES (? ,  ?)";
        PreparedStatement preparedStatement=null;
        boolean autoCommitOriginal = false;
      
           
        try {
            autoCommitOriginal = DB.getAutoCommit();
            DB.setAutoCommit(false);
            creditDAO function = new creditDAO();
            Credit a = function.findById(m.getId_credit());
            double reste =a.getReste()+m.getMontant();
            if( reste <= a.getMontant() ){
             
              Credit cred=(
                    new Credit(
                        a.getId(),
                        a.getNom(),
                        a.getMontant(),
                        reste
                    )
                );
                function.update(cred);
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, m.getId_credit());    
            preparedStatement.setDouble(2, m.getMontant());  
            // Exécution de la mise à jour
            preparedStatement.executeUpdate();}
            else{
                throw new Exception("Montant credit insuffisant");
            }
             DB.commit();
        } catch (SQLException e) {
             DB.rollback();
            // Relancer l'exception SQL avec un message plus descriptif
            throw new Exception("Erreur lors de l'insertion dans la base de données : " + e.getMessage(), e);
        } catch (Exception e) {
             DB.rollback();
            // Relancer toute autre exception
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        }finally{
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture du PreparedStatement : " + e.getMessage());
                }
            }
        }
    }

    public  void save(BaseModele b) throws Exception {
        Connection connection = null;
        try{
            connection = Connexion.connect("root", "");
            this.save(connection, b);
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message plus descriptif
            throw new Exception("Erreur lors de l'insertion dans la base de données : " + e.getMessage(), e);
        } catch (Exception e) {
            // Relancer toute autre exception
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        } finally{
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
        }
    }
    
    //fonction lister
    public  List<BaseModele> findAll(Connection DB) throws Exception {
        List<BaseModele> Depenses = new ArrayList<>();
        String request = "SELECT Depense.id as id,Depense.id_credit as Id_credit, Credit.name as name,sum(Depense.montant) as montant ,(Credit.montant-sum(Depense.montant)) as reste FROM Depense join Credit on Depense.id_credit=Credit.id group by Credit.id";
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            statement = DB.createStatement();
            resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                Depenses.add(
                new Depense(
                    resultSet.getInt("id"),
                    resultSet.getInt("Id_credit"),
                    resultSet.getString("name"),
                    resultSet.getDouble("Montant"),
                    resultSet.getDouble("reste")
                    )
                );
            }
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message descriptif
            throw new Exception("Erreur lors de la récupération des Depenses : " + e.getMessage(), e);
        } catch (Exception e) {
            // Relancer toute autre exception
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        }finally{
            if(statement != null){
                try {
                    statement.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture du Statement : " + e.getMessage());
                }
            }
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture du PreparteStatement : " + e.getMessage());
                }
            }
        }
    
        return Depenses;
    }
    public  List<BaseModele> findAll() throws Exception {
        List<BaseModele> Depenses = new ArrayList<>();
        Connection connection = null;
        try{
            connection = Connexion.connect("root", "");
           Depenses = findAll(connection);
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message descriptif
            throw new Exception("Erreur lors de la récupération des Depense : " + e.getMessage(), e);
        } catch (Exception e) {
            // Relancer toute autre exception
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
        }
    
        return Depenses;
    }

    //fonction find by id
    public  Depense findById(Connection DB,int DepenseId) throws Exception {
        Depense Depenses = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
           String request = "SELECT Depense.id as id,Depense.id_credit as Id_credit, credit.name as name,Depense.montant as montant FROM Depense join credit on Depense.id_credit=credit.id where depense.id= ?";
           preparedStatement = DB.prepareStatement(request);
           preparedStatement.setInt(1, DepenseId);
           resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                        Depenses=(
                            new Depense(
                                resultSet.getInt("id"),
                                resultSet.getInt("id_credit"),
                                resultSet.getString("name"),
                                resultSet.getDouble("Montant"),
                                0
                            )
                        );
            }
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message descriptif
            throw new Exception("Erreur lors de la récupération du Depense : " + e.getMessage(), e);
        } catch (Exception e) {
            // Relancer toute autre exception
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        }finally{
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture du Statement : " + e.getMessage());
                }
            }
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture du PreparteStatement : " + e.getMessage());
                }
            }
        }
    
        return Depenses;
    }
    public  Depense findById(int DepenseId) throws Exception {
        Depense Depenses = null;
        Connection connection = null;
        try{
            connection = Connexion.connect("root", "");
            Depenses =  findById(connection,DepenseId);
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message descriptif
            throw new Exception("Erreur lors de la récupération du Depense : " + e.getMessage(), e);
        } catch (Exception e) {
            // Relancer toute autre exception
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
        }
    
        return Depenses;
    }

    //fonction find by id
    public  List<Depense> pagination(Connection DB,int id,int counter) throws Exception {
       List<Depense> Depenses = new ArrayList<>();
       PreparedStatement preparedStatement = null;
       ResultSet resultSet = null;
        try {
            String request = "SELECT Depense.id as id,Depense.id_credit as Id_credit, credit.name as name,Depense.montant as montant FROM Depense join credit on Depense.id_credit=credit.id where id <= ? and id >= ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, counter);
            resultSet =  preparedStatement.executeQuery();
               
            while (resultSet.next()) {
                Depenses.add(
                    new Depense(
                        resultSet.getInt("id"),
                        resultSet.getInt("id_credit"),
                        resultSet.getString("name"),
                        resultSet.getDouble("Montant"),
                        0
                    )
                );
            }
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message descriptif
            throw new Exception("Erreur lors de la récupération du Depense : " + e.getMessage(), e);
        } catch (Exception e) {
            // Relancer toute autre exception
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        }finally{
            if(preparedStatement != null){
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture du Statement : " + e.getMessage());
                }
            }
            if(resultSet != null){
                try {
                    resultSet.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture du PreparteStatement : " + e.getMessage());
                }
            }
        }
    
        return Depenses;
    }
    public  List<Depense> pagination(int id,int counter) throws Exception {
        List<Depense> Depenses = new ArrayList<>();
        Connection connection = null;
         try{
            connection = Connexion.connect("root", "");
             Depenses = pagination(connection, id, counter);
         } catch (SQLException e) {
             // Relancer l'exception SQL avec un message descriptif
             throw new Exception("Erreur lors de la récupération du Depense : " + e.getMessage(), e);
         } catch (Exception e) {
             // Relancer toute autre exception
             throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
         }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
        }
     
         return Depenses;
     }

    //fonction delete
    public  void delete(Connection DB,int DepenseId) throws Exception {
        PreparedStatement preparedStatement = null ;
        boolean autoCommitOriginal = false;
        try{
            autoCommitOriginal = DB.getAutoCommit();
            DB.setAutoCommit(false);
            String request = "DELETE FROM Depense WHERE id = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, DepenseId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Aucun Depense trouvé avec l'ID : " + DepenseId);
            }
             DB.commit();
        } catch (SQLException e) {
             DB.rollback();
            throw new Exception("Erreur lors de la suppression du Depense : " + e.getMessage(), e);
        } catch (Exception e) {
             DB.rollback();
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        }finally{
            if(preparedStatement!= null){
                try{
                    preparedStatement.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture du Statement : " + e.getMessage());
                }
            }
        }
    }
    public  void delete(int DepenseId) throws Exception {
        Connection connection  = null;
        try{
            connection = Connexion.connect("root", "");
            delete(connection, DepenseId);
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la suppression du Depense : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
        }
    }
    
    //fonction update
    public  void update(Connection DB,BaseModele b) throws Exception {
        Depense m = (Depense)b;
        String request = "UPDATE Depense SET id_credit = ?,montant = ? WHERE id = ?";
        PreparedStatement preparedStatement  = null;
        boolean autoCommitOriginal = false;
        try{
            autoCommitOriginal = DB.getAutoCommit();
            DB.setAutoCommit(false);
           preparedStatement  = DB.prepareStatement(request);
           preparedStatement.setInt(1, m.getId_credit());
           preparedStatement.setDouble(2, m.getMontant());
           preparedStatement.setInt(3, m.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                    throw new Exception("Aucun Depense trouvé avec l'ID : " + m.getId());
            }
             DB.commit();
        } catch (SQLException e) {
             DB.rollback();
            throw new Exception("Erreur lors de la mise à jour du Depense : " + e.getMessage(), e);
        } catch (Exception e) {
             DB.rollback();
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        }
    }

    public  void update(BaseModele b) throws Exception {
        Connection connection = null; 
        try{
            connection = Connexion.connect("root", "");
            update(connection, b);
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la mise à jour du Depense : " + e.getMessage(), e);
        } catch (Exception e) {
            throw new Exception("Une erreur est survenue : " + e.getMessage(), e);
        }finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
                }
            }
        }
    }
}
