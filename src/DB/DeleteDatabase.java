package DB;

 import java.sql.Connection;  

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author tanzeem
 */
public class DeleteDatabase {
      
      
      //  data.clear();
      //  data.removeAll(data);
       public static void deleteRecord(int id,String tableName,String id_name){
        Connection c ; 
           
           try{  
         c = DBConnection.connect(); 
        System.out.println("reached delete"+tableName+" "+id);        
         String query = "Delete from "+tableName+" where "+id_name+"='"+id+"';";
         c.createStatement().execute(query);
         c.close();
         
           
       }catch(Exception e){  
        System.out.println("Error on Deleting Data");        
      }  
       }
}
