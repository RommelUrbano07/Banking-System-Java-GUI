/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banking_system;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Scanner;
import DataStructure.Stack;
import CustomerGUI.Client;
import CustomerGUI.ClientTransaction;
import static banking_system.AdminBackEnd.databaseTransactions;
import encryptionqueue.EncryptionQueue;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import encryptionqueue.EncryptionQueue;
/**
 *
 * @author Asus
 */
public class CustomerBackEnd {

    static String databaseCustomer = "C:\\Users\\Nikki\\Desktop\\DataStructures\\src\\banking_system\\databaseCustomer.txt";
    static Scanner sc = new Scanner(System.in);
    static EncryptionQueue var = new EncryptionQueue();
    public static boolean CustomerMenu(String user, String pass) throws IOException {
        String[] loginDetails = login(user, pass);
        if (loginDetails != null) {
            return true;
        }
        return false;
    }

    public static boolean Withdraw(String user, double input) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(databaseCustomer));
        BufferedWriter write = new BufferedWriter(new FileWriter(databaseTransactions, true));
        ArrayList<String[]> database = new ArrayList<>();
        String line;
        boolean condition=false;
        while ((line = read.readLine()) != null) {
            String arr[] = line.split("/");
            if (arr[0].equals(var.encrypt(user))) {
                try {
                    if (input <= Double.parseDouble(var.decrypt(arr[arr.length - 1]))) {
                        if (input % 100 == 0 && input >= 500) {
                            arr[arr.length - 1] = var.encrypt(Double.parseDouble(var.decrypt(arr[arr.length - 1])) - input + "");
                            System.out.println(arr[arr.length - 1]);
                            write.append(var.encrypt(user) + "/" + var.encrypt("Withdraw") + "/" + var.encrypt(input+"") + "/"+ var.encrypt(LocalDate.now()+"")+"/"+var.encrypt(LocalTime.now()+""));
                            write.newLine();
                            write.close();
                            condition=true;
                        } else {
                            JOptionPane.showMessageDialog(null, "Cannot Withdraw Amount, some denominations not available, allowable withdraws is at least 500 Pesos");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Insufficient Account Balance, Cannot Withdraw.");
                    }
                } catch (Exception e) {
                    System.out.println(Arrays.toString(e.getStackTrace()));
                }
            }
            database.add(arr);
            System.out.println(Arrays.toString(arr));
        }
        update(database);
        return condition;
    }

    public static void DisplayAccount(String user) throws FileNotFoundException, IOException {
        BufferedReader read = new BufferedReader(new FileReader(databaseCustomer));
        String line;
        while ((line = read.readLine()) != null) {
            String arr[] = line.split("/");
            if (arr[0].equals(user)) {
                System.out.println("Account Number: " + arr[0]);
                System.out.println("Name: " + arr[2]);
                System.out.println("Age: " + arr[3]);
                System.out.println("Birth Date: " + arr[4]);
                System.out.println("Address: " + arr[5]);
                System.out.println("Contact Number: " + arr[6]);
                System.out.println("Balance: " + arr[7]);
            }
        }
    }

    public static boolean ChangePassword(String user, String input) throws IOException {
        BufferedReader read = new BufferedReader(new FileReader(databaseCustomer));
        BufferedWriter write = new BufferedWriter(new FileWriter(databaseTransactions, true));
        ArrayList<String[]> database = new ArrayList<>();
        String line;
        boolean condition=false;
        while ((line = read.readLine()) != null) {
            String arr[] = line.split("/");
            if (arr[0].equals(var.encrypt(user))) {
                System.out.println("Please enter your NEW PIN, Current Password: " + arr[1]);
                if (Pattern.matches("([0-9]{6})", input)) {
                    arr[1] = var.encrypt(input);
                    write.append(var.encrypt(user) + "/" + var.encrypt("Change Password") + "/" + var.encrypt(input)+ "/"+ var.encrypt(LocalDate.now()+"")+"/"+var.encrypt(LocalTime.now()+""));
                    condition=true;
                } else {
                    JOptionPane.showMessageDialog(null, "Password must be 6 digits with no characters/special characters");
                }
            }
            System.out.println(Arrays.toString(arr));
            database.add(arr);
        }
        update(database);
        write.newLine();
        write.close();
        return condition;
    }

    public static void update(ArrayList<String[]> var) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(databaseCustomer))) {
            for (int i = 0; i < var.size(); i++) {
                for (int j = 0; j < var.get(i).length; j++) {
                    if (j + 1 == var.get(i).length) {
                        writer.append(var.get(i)[j]);
                    } else {
                        writer.append(var.get(i)[j] + "/");
                    }
                }
                writer.append("\n");
            }
            writer.close();
        }
    }

    public static String CheckTransactions(String accNum) throws IOException {
        BufferedReader transactions = new BufferedReader(new FileReader(databaseTransactions));
        StringBuilder history = new StringBuilder();
        Stack print = new Stack();
        String line;
        while ((line = transactions.readLine()) != null) {
            String arr[] = line.split("/");
            print.push(arr);
        }

        while (!print.isEmpty()) {
            String arr[] = print.top.getData();
            if (accNum.equals(var.decrypt(arr[0]))) {
                for (int i = 0; i < arr.length; i++) {
                    if(i+1==arr.length){
                        history.append(var.decrypt(arr[i]));
                    }else{
                        history.append(var.decrypt(arr[i])).append(",");
                    }
                }
                history.append("\n");
                print.pop();
            } else {
                print.pop();
            }
        }
        return history.toString();
    }

    public static Stack readCustomerDB() throws IOException {
        Stack database = new Stack();
        BufferedReader read = new BufferedReader(new FileReader(databaseCustomer));
        String line;
        while ((line = read.readLine()) != null) {
            String arr[] = line.split("/");
            database.push(arr);
        }
        return database;
    }

    public static String[] login(String user, String password) throws IOException {
        EncryptionQueue var= new EncryptionQueue();
        Stack database = readCustomerDB();
        while (!database.isEmpty()) {
            String arr[] = database.top.getData();
            if (user.equals(var.decrypt(arr[0])) && password.equals(var.decrypt(arr[1]))) {
                ClientTransaction.name = "Hello: " + var.decrypt(arr[2]);
                ClientTransaction.arr = arr;
                database.pop();
                return arr;
            } else if (!database.isEmpty()) {
                database.pop();
            } else {
                return null;
            }
        }
        JOptionPane.showMessageDialog(null, "No Accounts found, try again.");
        return null;
    }

    public static void main(String[] args) throws IOException {
        Client.main();
    }
}
