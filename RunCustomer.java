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
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *Luvolwethu James, 219126224
 * 7 July 2021
 * @author Miss James
 */
public class RunCustomer extends Stakeholder {
    ObjectInputStream input;
FileWriter fw;
BufferedWriter bw;

private ArrayList<Customer> custArrays= new ArrayList<Customer>();
private ArrayList<Supplier> suppArrays= new ArrayList<Supplier>();
    
public void populateArray(){
        
        
        custArrays.set(0, new Customer("C150", "Luke", "Atmyass", "Bellville", "27*Jan*1981", 1520.50, false));
        custArrays.set(1, new Customer("C130", "Stu", "Padassol", "Sea Point", "18*May*1987", 645.25, true));   
        custArrays.set(2, new Customer("C100", "Mike", "Rohsopht", "Bellville", "24*Jan*1993", 975.10, true));
        custArrays.set(3, new Customer("C300", "Ivana.B", "Withew", "Langa", "16 Jul 1998", 1190.50, false));
        custArrays.set(4, new Customer("C250", "Eileen", "Sideways", "Nyanga", "27 Nov 1999", 190.85, true));
        custArrays.set(5, new Customer ("C260", "Ima", "Stewpidas", "Atlantis", "27 Jan 2001", 1890.70, true));
    }

   public void OpenFile(){
        try{
            input = new ObjectInputStream( new FileInputStream( "stakeholder.ser" ) ); 
            System.out.println("File is created");               
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
               String cust ="Customer";
               String supp = "Supplier";
               String name = line.getClass().getSimpleName();
               if (name.equals(cust)){
                   custArrays.add((Customer)line);
               } else if (name.equals(supp)){
                   suppArrays.add((Supplier)line);
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
        public int getAge(String dob){
        String[] interstice= dob.split("-");
        LocalDate birth = LocalDate.of(Integer.parseInt(interstice[0]), Integer.parseInt(interstice[1]), Integer.parseInt(interstice[2]));
        LocalDate current = LocalDate.now();
        Period difference = Period.between(birth, current);
        int age = difference.getYears();
        return age;
     }
        
       public void sortingCustomer(){
        String[] sortOrder = new String[custArrays.size()];
        ArrayList<Customer> sortCus= new ArrayList<Customer>();
        int cCount = custArrays.size();
        for (int i = 0; i < cCount; i++) {
            sortOrder[i] = custArrays.get(i).getStHolderId();
        }
        Arrays.sort(sortOrder);
        
        for (int i = 0; i < cCount; i++) {
            for (int j = 0; j < cCount; j++) {
                if (sortOrder[i].equals(custArrays.get(j).getStHolderId())){
                    sortCus.add(custArrays.get(j));
                }
            }
        }
        custArrays.clear();
        custArrays= sortCus;
     
    }
       
       public String dobF(String dob){
       DateTimeFormatter formatDob= DateTimeFormatter.ofPattern("dd MMM YYYY");
       LocalDate birth = LocalDate.parse(dob);
       String date = birth.format(formatDob);
       return date;
       }
       
       public void writingToTxt(){
        try{
            fw = new FileWriter("customersOutFile.txt");
            bw = new BufferedWriter(fw);
            bw.write(String.format("%s\n","===============================Customers================================"));
            bw.write(String.format("%-15s %-15s %-15s %-15s %-15s \n", "ID","Name","Surname","Date of Birth","Age"));
            bw.write(String.format("%s\n","========================================================================"));
            for (int i = 0; i < custArrays.size(); i++) {
                bw.write(String.format("%-15s %-15s %-15s %-15s %-15s \n", custArrays.get(i).getStHolderId(), custArrays.get(i).getFirstName(), custArrays.get(i).getSurName(), dobF(custArrays.get(i).getDateOfBirth()),getAge(custArrays.get(i).getDateOfBirth())));
            }
            bw.write(String.format("%s\n"," "));
            bw.write(String.format("%s",affordsrent()));
        }
        catch(IOException filenotfound )
        {
            System.out.println(filenotfound);
            System.exit(1);
        }
        try{
            bw.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe);
            System.exit(1);
        }
       }
        
      public String  affordsrent (){
        int cCount = custArrays.size();
        int rentted = 0;
        int notrentted = 0;
        for (int i = 0; i < cCount; i++) {
            if (custArrays.get(i).getCanRent()){
                rentted++;
            }else {
                notrentted++;
            }
        }
        String output = String.format ("Number of customers who afford rent: %s\nNumber of customers who cannot afford rent: %s\n",rentted,notrentted);
        return output;
    }
      
      public static void main(String args[])  {
        RunCustomer obj = new RunCustomer();
        obj.OpenFile();
        obj.reading();
        obj.closingFile();
        obj.sortingCustomer();
        obj.writingToTxt();
      }
        }