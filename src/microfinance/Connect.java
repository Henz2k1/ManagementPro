/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package microfinance;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Henz
 */
public class Connect {
    public static String getConnexion() throws ClassNotFoundException{
        String chemin = "C:\\connexion\\chemin.txt";
        String text="";
        
        
        try{
        text = new String (Files.readAllBytes(Paths.get(chemin)));
        }catch (IOException e){
            e.printStackTrace();
            
        }
         Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        return text;
    } 
    
}
