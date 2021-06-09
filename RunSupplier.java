/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.adpassign3;

import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;

/**Luvolwethu James, 219126224
 * 7 July 2021
 * @author Miss James
 */
public class RunSupplier {
 ObjectInputStream input;
FileWriter filew;
BufferedWriter bufferedw;

private ArrayList<Supplier> suppArrays= new ArrayList<Supplier>();
private ArrayList<Customer> custArrays= new ArrayList<Customer>();
    
public void populateArray(){
        suppArrays.set(0, new Supplier ("S270", "Grand Theft Auto", "Toyota", "Mid-size sedan"));
        suppArrays.set(1, new Supplier ("S400", "Prime Motors", "Lexus", "Luxury sedan"));
        suppArrays.set(2, new Supplier ("S300", "We got Cars", "Toyota", "10-seater minibus"));
        suppArrays.set(3, new Supplier ("S350", "Auto Delight", "BMW", "Luxury SUV"));
        suppArrays.set(4, new Supplier ("S290", "MotorMania", "Hyundai", "compact budget"));
         
    }

   public void OpenFile(){
        try{
            input = new ObjectInputStream( new FileInputStream( "stakeholder.ser" ) ); 
            System.out.println("File created");               
        }
        catch (IOException ioe){
            System.out.println("error opening " + ioe);
            System.exit(1);
        }
    }
           public void reading(){
        try{
           while(true){
               Object line = input.readObject();
               String suppl ="Supplier";
               String custo = "Customer";
               String name = line.getClass().getSimpleName();
               if (name.equals(suppl)){
                   suppArrays.add((Supplier)line);
               } else if (name.equals(custo)){
                   custArrays.add((Customer)line);
               } else {
                   System.out.println("Error");
               }
           
        }
        }
           catch (EOFException eofe) {
            System.out.println("Reached end");
        }
        catch (ClassNotFoundException | IOException ioe) {
            System.out.println("Reading error: "+ ioe);
        }
        
     
        }
       public void closingFile(){
        try{
            input.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("Error closing ser file: " + ioe);
            System.exit(1);
        }
    }
       
       public void writingToTxt(){
        try{
            filew = new FileWriter("supplierOutFile.txt");
            bufferedw = new BufferedWriter(filew);
            bufferedw.write(String.format("%s\n","===============================Suppliers================================"));
            bufferedw.write(String.format("%-15s %-15s %-15s %-15s \n", "ID","Name","ProductType","ProductDescription"));
            bufferedw.write(String.format("%s\n","========================================================================"));
            for (int i = 0; i < suppArrays.size(); i++) {
                bufferedw.write(String.format("%-15s %-15s %-15s %-15s  \n", suppArrays.get(i).getStHolderId(), suppArrays.get(i).getName(), suppArrays.get(i).getProductType(),suppArrays.get(i).getProductDescription()));
            }
            bufferedw.write(String.format("%s\n"," "));
      
        }
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        try{
            bufferedw.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe);
            System.exit(1);
        }
       }
       
       public void sortingSupplier(){
        String[] sortSp = new String[suppArrays.size()];
        ArrayList<Supplier> sortSup= new ArrayList<Supplier>();
        int aCount = suppArrays.size();
        for (int i = 0; i < aCount; i++) {
            sortSp[i] = suppArrays.get(i).getName();
        }
        Arrays.sort(sortSp);
        
        for (int i = 0; i < aCount; i++) {
            for (int j = 0; j < aCount; j++) {
                if (sortSp[i].equals(suppArrays.get(j).getName())){
                    sortSup.add(suppArrays.get(j));
                }
            }
        }
        suppArrays.clear();
        suppArrays= sortSup;
     
    }
       public static void main(String args[])  {    
        RunSupplier obj = new RunSupplier();
        obj.OpenFile();
        obj.reading();
        obj.closingFile();
        obj.writingToTxt();
}
}
   
 

