// Source code is decompiled from a .class file using FernFlower decompiler.
package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion {
   public Connexion() {
   }

   public static Connection connect(String var0, String var1) throws Exception{
      Connection var2 = null;

      try {
         Class.forName("com.mysql.cj.jdbc.Driver");
         String var3 = "jdbc:mysql://172.80.237.54:3306/db_s2_ETU003252?useSSL=false&serverTimezone=UTC";
         var2 = DriverManager.getConnection(var3, "ETU003252", "BJ3iQKvW");
      } catch (ClassNotFoundException var4) {
         System.out.println("Driver MySQL non trouvé : " + var4.getMessage());
         var4.printStackTrace();
         throw var4;
      } catch (SQLException var5) {
         System.out.println("Erreur SQL : " + var5.getMessage());
         var5.printStackTrace();
         throw var5;
      }

      return var2;
   }

}
