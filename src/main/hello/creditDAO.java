package hello;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class creditDAO extends InterfaceDAO {
     
    //fonction save
    public  void save(Connection DB,BaseModele b) throws Exception {
        Credit m=(Credit)b;
        String request = "INSERT INTO Credit (name, montant) VALUES (? ,  ?)";
        PreparedStatement preparedStatement=null;
        boolean autoCommitOriginal = false;
        try {
            autoCommitOriginal = DB.getAutoCommit();
            DB.setAutoCommit(false);
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setString(1, m.getNom());    
            preparedStatement.setDouble(2, m.getMontant());    
            // Exécution de la mise à jour
            preparedStatement.executeUpdate();
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
            connection =Connexion.connect("root", "");
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
        List<BaseModele> Credits = new ArrayList<>();
        String request = "SELECT * FROM Credit";
        Statement statement = null;
        ResultSet resultSet = null;
        try{
            statement = DB.createStatement();
            resultSet = statement.executeQuery(request);
            while (resultSet.next()) {
                Credits.add(
                new Credit(
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getDouble("Montant"),
                    resultSet.getDouble("reste")
                    )
                );
            }
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message descriptif
            throw new Exception("Erreur lors de la récupération des credits : " + e.getMessage(), e);
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
    
        return Credits;
    }
    public  List<BaseModele> findAll() throws Exception {
        List<BaseModele> Credits = new ArrayList<>();
        Connection connection = null;
        try{
            connection = Connexion.connect("root", "");
           Credits = findAll(connection);
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message descriptif
            throw new Exception("Erreur lors de la récupération des credit : " + e.getMessage(), e);
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
    
        return Credits;
    }

    //fonction find by id
    public  Credit findById(Connection DB,int CreditId) throws Exception {
        Credit Credits = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try{
           String request = "SELECT * FROM Credit where id= ?";
           preparedStatement = DB.prepareStatement(request);
           preparedStatement.setInt(1, CreditId);
           resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                        Credits=(
                            new Credit(
                                resultSet.getInt("id"),
                                resultSet.getString("name"),
                                resultSet.getDouble("Montant"),
                                resultSet.getDouble("reste")
                            )
                        );
            }
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message descriptif
            throw new Exception("Erreur lors de la récupération du credit : " + e.getMessage(), e);
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
    
        return Credits;
    }
    public  Credit findById(int CreditId) throws Exception {
        Credit Credits = null;
        Connection connection = null;
        try{
            connection = Connexion.connect("root", "");
            Credits =  findById(connection,CreditId);
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message descriptif
            throw new Exception("Erreur lors de la récupération du credit : " + e.getMessage(), e);
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
    
        return Credits;
    }

    //fonction find by id
    public  List<Credit> pagination(Connection DB,int id,int counter) throws Exception {
       List<Credit> Credits = new ArrayList<>();
       PreparedStatement preparedStatement = null;
       ResultSet resultSet = null;
        try {
            String request = "SELECT * FROM Credit where id <= ? and id >= ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, counter);
            resultSet =  preparedStatement.executeQuery();
               
            while (resultSet.next()) {
                Credits.add(
                    new Credit(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("Montant"),
                        resultSet.getDouble("reste")
                    )
                );
            }
        } catch (SQLException e) {
            // Relancer l'exception SQL avec un message descriptif
            throw new Exception("Erreur lors de la récupération du credit : " + e.getMessage(), e);
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
    
        return Credits;
    }
    public  List<Credit> pagination(int id,int counter) throws Exception {
        List<Credit> Credits = new ArrayList<>();
        Connection connection = null;
         try{
            connection = Connexion.connect("root", "");
             Credits = pagination(connection, id, counter);
         } catch (SQLException e) {
             // Relancer l'exception SQL avec un message descriptif
             throw new Exception("Erreur lors de la récupération du credit : " + e.getMessage(), e);
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
     
         return Credits;
     }

    //fonction delete
    public  void delete(Connection DB,int CreditId) throws Exception {
        PreparedStatement preparedStatement = null ;
        boolean autoCommitOriginal = false;
        try{
            autoCommitOriginal = DB.getAutoCommit();
            DB.setAutoCommit(false);
            String request = "DELETE FROM Credit WHERE id = ?";
            preparedStatement = DB.prepareStatement(request);
            preparedStatement.setInt(1, CreditId);
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                throw new Exception("Aucun credit trouvé avec l'ID : " + CreditId);
            }
             DB.commit();
        } catch (SQLException e) {
             DB.rollback();
            throw new Exception("Erreur lors de la suppression du credit : " + e.getMessage(), e);
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
    public  void delete(int CreditId) throws Exception {
        Connection connection  = null;
        try{
            connection = Connexion.connect("root", "");
            delete(connection, CreditId);
        } catch (SQLException e) {
            throw new Exception("Erreur lors de la suppression du credit : " + e.getMessage(), e);
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
        Credit m = (Credit)b;
        String request = "UPDATE Credit SET name = ?,montant = ? ,reste = ? WHERE id = ?";
        PreparedStatement preparedStatement  = null;
        boolean autoCommitOriginal = false;
        try{
            autoCommitOriginal = DB.getAutoCommit();
            DB.setAutoCommit(false);
           preparedStatement  = DB.prepareStatement(request);
           preparedStatement.setString(1, m.getNom());
           preparedStatement.setDouble(2, m.getMontant());
           preparedStatement.setDouble(3, m.getReste());
           preparedStatement.setInt(4, m.getId());
            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected == 0) {
                    throw new Exception("Aucun credit trouvé avec l'ID : " + m.getId());
            }
            DB.commit();
        } catch (SQLException e) {
            DB.rollback();
            throw new Exception("Erreur lors de la mise à jour du credit : " + e.getMessage(), e);
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
            throw new Exception("Erreur lors de la mise à jour du credit : " + e.getMessage(), e);
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
