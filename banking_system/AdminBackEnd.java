/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package banking_system;

import DataStructure.LinkedList;
import DataStructure.Node;
import GUIForms.Admin;
import GUIForms.CheckTransactionRecord;
import GUIForms.EditCustomerAccount;
import GUIForms.EditStaffAccount;
import GUIForms.RegisterEmployee;
import GUIForms.StaffSection;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;
import encryptionqueue.EncryptionQueue;

/**
 *
 * @author Asus
 */
public class AdminBackEnd {

    static String databaseAdmin = "C:\\Users\\Nikki\\Desktop\\DataStructures\\src\\banking_system\\databaseAdmin.txt";
    static String databaseCustomer = "C:\\Users\\Nikki\\Desktop\\DataStructures\\src\\banking_system\\databaseCustomer.txt";
    static String databaseTransactions = "C:\\Users\\Nikki\\Desktop\\DataStructures\\src\\banking_system\\TotalTransactions.txt";
    static Random rand = new Random();
    public static LinkedList admin = new LinkedList();
    static EncryptionQueue encrypt = new EncryptionQueue();

    public static boolean AdminMenu() throws IOException {
        admin = readAdminDatabase();
        if (admin.getSize() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public static void EnrollMaster(String name, String user, String pass) throws IOException {
        if (passwordChecker(pass)) {
            BufferedWriter var = new BufferedWriter(new FileWriter(databaseAdmin));
            var.append(encrypt.encrypt(user) + "/" + encrypt.encrypt(pass) + "/" + encrypt.encrypt(name) + "/" + encrypt.encrypt(randomAdminID()) + "/" + encrypt.encrypt(LocalDate.now() + "") + "/" + encrypt.encrypt(LocalTime.now() + ""));
            var.newLine();
            var.close();
        } else {
            JOptionPane.showMessageDialog(null, "Password must be at least 7 characters");
        }
    }

    public static void ChangeManagerAccount(String managername, String username, String password) throws FileNotFoundException, IOException {
        BufferedReader read = new BufferedReader(new FileReader(databaseAdmin));
        ArrayList<String[]> updateDB = new ArrayList<>();
        String line;
        int filter = 0;
        while ((line = read.readLine()) != null) {
            String arr[] = line.split("/");
            if (filter == 0) {
                arr[0] = encrypt.encrypt(username);
                arr[1] = encrypt.encrypt(password);
                arr[2] = encrypt.encrypt(managername);
                arr[3] = encrypt.encrypt(LocalDate.now() + "");
                arr[4] = encrypt.encrypt(LocalTime.now() + "");
                filter++;
            }
            updateDB.add(arr);
        }
        updateAdmin(updateDB);
    }

    public static void EditAdminAccount(String accNum, String name, String user, String pass) throws FileNotFoundException, IOException {
        try (BufferedReader read = new BufferedReader(new FileReader(databaseAdmin))) {
            ArrayList<String[]> updateDB = new ArrayList<>();
            String line;
            while ((line = read.readLine()) != null) {
                String arr[] = line.split("/");
                if (accNum.equals(encrypt.decrypt(arr[3]))&& passwordChecker(pass)) {
                    arr[0] = (encrypt.encrypt(user)); //3
                    arr[1] = (encrypt.encrypt(pass)); //0
                    arr[2] = (encrypt.encrypt(name)); //1
                    arr[3] = (encrypt.encrypt(accNum)); //2
                    arr[4] = (encrypt.encrypt((LocalDate.now() + ""))); //2
                    arr[5] = (encrypt.encrypt(LocalTime.now() + "")); //2
                    JOptionPane.showMessageDialog(null, "Account Edited");
                }
                updateDB.add(arr);
            }
            updateAdmin(updateDB);
        }
    }

    public static boolean StaffLogin(String user, String pass) {
        StringBuilder build = new StringBuilder();
        if (admin.getSize() != 0) {
            Node temp = admin.head;
            while (temp != null) {
                String arr[] = temp.getData();
                System.out.println(Arrays.toString(arr));
                if (encrypt.decrypt(arr[0]).equals(user) && encrypt.decrypt(arr[1]).equals(pass)) {
                    build.append("Logged In!\n");
                    build.append("Hello ").append(encrypt.decrypt(arr[2])).append("\n");
                    build.append("ID Number: ").append(encrypt.decrypt(arr[3])).append("\n");
                    StaffSection.user = ("Hello " + encrypt.decrypt(arr[2]));
                    JOptionPane.showMessageDialog(null, build.toString());
                    return true;
                }
                temp = temp.getNext();
            }
            JOptionPane.showMessageDialog(null, "Log in Error, Try Again");
        }
        return false;
    }

    public static boolean ManagerLogin(String user, String pass) {
        StringBuilder build = new StringBuilder();
        if (admin.getSize() != 0) {
            Node temp = admin.head;
            while (temp != null) {
                String arr[] = temp.getData();
                System.out.println(Arrays.toString(arr));
                if (encrypt.decrypt(arr[0]).equals(user) && encrypt.decrypt(arr[1]).equals(pass)) {
                    build.append("Logged In!\n");
                    build.append("Hello ").append(encrypt.decrypt(arr[2])).append("\n");
                    build.append("ID Number: ").append(encrypt.decrypt(arr[3])).append("\n");
                    JOptionPane.showMessageDialog(null, build.toString());
                    return true;
                }
                break;
            }
            JOptionPane.showMessageDialog(null, "Log in Error, Try Again");
        }
        return false;
    }

    public static void updateAdmin(ArrayList<String[]> var) throws IOException {
        try (BufferedWriter write = new BufferedWriter(new FileWriter(databaseAdmin))) {
            for (int i = 0; i < var.size(); i++) {
                for (int j = 0; j < var.get(i).length; j++) {
                    if (j + 1 == var.get(i).length) {
                        write.append(var.get(i)[j]);
                    } else {
                        write.append(var.get(i)[j] + "/");
                    }
                }

                write.append("\n");
            }
            write.close();
        }
    }

    public static void registerAdmin(String StaffName, String user, String pass) throws IOException {
        StringBuilder build = new StringBuilder();
        if (verify(user, pass)) {
            String random=randomAdminID();
            String arr[] = {user, pass, StaffName, random, LocalDate.now() + "", LocalTime.now() + ""};
            Node data = new Node(arr);
            admin.insertAtEnd(data);
            try (BufferedWriter write = new BufferedWriter(new FileWriter(databaseAdmin, true))) {
                build.append("Username: ").append(user).append("\n").append("Password: ").append(pass).append("\n").append("Name: ").append(StaffName).append("\n").append("ID: ").append(random).append("\n").append("Date of Registry: ").append(LocalDate.now()).append("\n").append("Time of Registry: ").append(LocalTime.now()).append("\n");
                JOptionPane.showMessageDialog(null, build.toString());
                write.append(encrypt.encrypt(user) + "/" + encrypt.encrypt(pass) + "/" + encrypt.encrypt(StaffName) + "/" + encrypt.encrypt(random) + "/" + encrypt.encrypt(LocalDate.now() + "") + "/" + encrypt.encrypt(LocalTime.now() + ""));
                write.newLine();
                write.close();
            }
            JOptionPane.showMessageDialog(null, "Registry Success!");
        }
    }

    public static String randomAdminID() throws IOException {
        int random = rand.nextInt(9999);
        Node temp = admin.head;
        while (temp != null) {
            String data[] = temp.getData();
            if (data[3].equals(random + "")) {
                random = rand.nextInt(9999);
            }
            temp = temp.getNext();
        }

        return (LocalDate.now() + "" + random + "").replaceAll("-", "");
    }

    public static LinkedList readAdminDatabase() throws FileNotFoundException, IOException {
        LinkedList database = new LinkedList();
        try (BufferedReader var = new BufferedReader(new FileReader(databaseAdmin))) {
            String line;
            Node data;
            while ((line = var.readLine()) != null) {
                String arr[] = line.split("/");
                data = new Node(arr);
                database.insertAtEnd(data);
            }
        }
        return database;
    }

    public static boolean verify(String user, String pass) {
        boolean condition = true;
        if (passwordChecker(pass) == false) {
            System.out.println("Password is does not contain 7 characters");
            condition = false;
            return condition;
        }
        if (admin.getSize() != 0) {
            Node temp = admin.head;
            String arr[] = temp.getData();
            while (temp != null) {
                if (encrypt.decrypt(arr[0]).equals(user) && encrypt.decrypt(arr[1]).equals(pass)) {
                    System.out.println("Account Already Exists");
                    condition = false;
                    break;
                }
                temp = temp.getNext();
            }
        }
        return condition;
    }

    public static boolean passwordChecker(String password) {
        boolean condition = true;
        if (password.length() < 7) {
            condition = false;
            return condition;
        } else {
            return condition;
        }
    }

    public static void EnrollCustomerAccount(String name, String birthday, String age, String address, String contactNumber, String initialDeposit) throws IOException {
        EncryptionQueue encryptData = new EncryptionQueue();
        StringBuilder build = new StringBuilder();

        try (BufferedWriter var = new BufferedWriter(new FileWriter(databaseCustomer, true))) {
            //1234567 is the initial pin
            String random = randomCustomerID();
            build.append("Account Number: ").append(random).append("\n").append("Pin: ").append("123456").append("\n").append("Customer Name: ").append(name).append("\n").append("Age: ").append(age).append("\n").append("Birthday: ").append(birthday).append("\n").append("Address: ").append(address).append("\n").append("Contact Number: ").append(contactNumber).append("\n").append("Balance: ").append(initialDeposit);
            var.write(encryptData.encrypt(random) + "/" + encryptData.encrypt("123456") + "/" + encryptData.encrypt(name) + "/" + encryptData.encrypt(age) + "/" + encryptData.encrypt(birthday) + "/" + encryptData.encrypt(address) + "/" + encryptData.encrypt(contactNumber) + "/" + encryptData.encrypt(initialDeposit));
            var.newLine();
            JOptionPane.showMessageDialog(null, build.toString());
        }

    }

    public static void GenerateFields(String account) throws FileNotFoundException, IOException {
        EditCustomerAccount bank = new EditCustomerAccount();
        try (BufferedReader read = new BufferedReader(new FileReader(databaseCustomer))) {
            String line;
            while ((line = read.readLine()) != null) {
                String arr[] = line.split("/");
                if (account.equals(encrypt.decrypt(arr[0]))) {
                    bank.jTextField1.setText(encrypt.decrypt(arr[2]));
                    bank.jTextField2.setText(encrypt.decrypt(arr[4]));
                    bank.jTextField3.setText(encrypt.decrypt(arr[3]));
                    bank.jTextField4.setText(encrypt.decrypt(arr[5]));
                    bank.jTextField5.setText(encrypt.decrypt(arr[6]));
                    bank.jTextField6.setText(encrypt.decrypt(arr[0]));
                    bank.setVisible(true);
                    JOptionPane.showMessageDialog(null, "Account Present");
                    return;
                }
            }
            JOptionPane.showMessageDialog(null, "No Account Found");
            bank.setVisible(true);
        }
    }

    public static void GenerateStaff(String account) throws FileNotFoundException, IOException {
        EditStaffAccount bank = new EditStaffAccount();
        try (BufferedReader read = new BufferedReader(new FileReader(databaseAdmin))) {
            String line;
            int filter=0;
            while ((line = read.readLine()) != null) {
                String arr[] = line.split("/");
                if (account.equals(encrypt.decrypt(arr[3])) && filter>0) {
                    bank.jTextField6.setText(encrypt.decrypt(arr[3])); 
                    bank.jTextField1.setText(encrypt.decrypt(arr[2])); 
                    bank.jTextField2.setText(encrypt.decrypt(arr[0])); 
                    bank.jTextField3.setText(encrypt.decrypt(arr[1]));
                    bank.setVisible(true);
                    JOptionPane.showMessageDialog(null, "Account Present");
                    return;
                }
                filter++;
            }
            JOptionPane.showMessageDialog(null, "No Account Found");
            bank.setVisible(true);
        }
    }

    public static String randomCustomerID() throws IOException {
        ArrayList<String> array = readCustomerDatabase();
        int random = rand.nextInt(9999);
        String accNum = LocalDate.now() + "" + random;
        if (random < 1000) {
            random += 1000;
        }
        for (int i = 1; i < array.size(); i += 7) {
            if (array.get(i).equals(accNum)) {
                random = rand.nextInt(9999);
                accNum = LocalDate.now() + "" + random;
            }
        }

        return accNum.replaceAll("[-]", "");
    }

    public static void EditCustomerAccount(String accNum, String name, String age, String address, String Birthdate, String Contact) throws FileNotFoundException, IOException {
        try (BufferedReader read = new BufferedReader(new FileReader(databaseCustomer))) {
            ArrayList<String[]> updateDB = new ArrayList<>();
            String line;
            while ((line = read.readLine()) != null) {
                String arr[] = line.split("/");
                if (accNum.equals(encrypt.decrypt(arr[0]))) {
                    arr[2] = (encrypt.encrypt(name));
                    arr[4] = (encrypt.encrypt(age));
                    arr[3] = (encrypt.encrypt(address));
                    arr[5] = (encrypt.encrypt(Birthdate));
                    arr[6] = (encrypt.encrypt(Contact));
                    JOptionPane.showMessageDialog(null, "Account Edited");
                }
                updateDB.add(arr);
            }
            updateCustomer(updateDB);

        }
    }

    public static void updateCustomer(ArrayList<String[]> var) throws IOException {
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

    public static void CheckTransactions(String input) throws IOException {
        BufferedReader transactions = new BufferedReader(new FileReader(databaseTransactions));
        CheckTransactionRecord form = new CheckTransactionRecord();
        String line;
        StringBuilder build = new StringBuilder();
        while ((line = transactions.readLine()) != null) {
            String arr[] = line.split("/");
            if (arr[0].equals(encrypt.encrypt(input))) {
                form.jTextField1.setText(encrypt.decrypt(arr[0]));
                build.append("Transaction: \n");
                for (String string : arr) {
                    build.append(encrypt.decrypt(string)).append(" ");
                }
                build.append("\n");
            }
        }
        form.jTextArea1.setText(build.toString());
        form.setVisible(true);
    }

    public static ArrayList readCustomerDatabase() throws FileNotFoundException, IOException {
        ArrayList database = new ArrayList();
        BufferedReader var = new BufferedReader(new FileReader(databaseAdmin));
        String line = null;
        while ((line = var.readLine()) != null) {
            String arr[] = line.split("/");
            for (String string : arr) {
                database.add(string);
            }
        }
        var.close();
        return database;
    }

    public static void main(String[] args) throws IOException {
        new Admin().main();
    }

}
