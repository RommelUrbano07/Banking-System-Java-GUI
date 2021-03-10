/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package encryptionqueue;

import java.util.Arrays;
import java.util.Scanner;

/**
 *
 * @author Asus
 */
public class EncryptionQueue {
    static int offset=4;

    public CircularQueue genCircle(int offset) {
        CircularQueue queue = new CircularQueue();
        int var = 'A';
        for (int i = 0; i < 26; i++) {
            int temp = var + offset;
            if (temp <= 90) {
                char letter = (char) temp;
                queue.enqueue(letter);
                var++;
            } else {
                var = 'A';
                temp = var;
                for (int j = i; j < 26; j++) {
                    char letter = (char) temp;
                    queue.enqueue(letter);
                    temp++;
                }
                break;
            }
        }
        return queue;
    }

    public String encrypt(String input) {
        CircularQueue queue=genCircle(offset);
        String output = "";
        Node temp = queue.front;
        for (int i = 0; i < input.length(); i++) {
            System.out.print(input.charAt(i));
            int encrypt = input.charAt(i);
            String value1[] = binary(encrypt, 8).split("");
            String value2[] = binary(temp.data, 8).split("");
            for (int j = 0; j < 8; j++) {
                int val1 = Integer.parseInt(value1[j]);
                int val2 = Integer.parseInt(value2[j]);
                if (val1 != val2) {
                    output += "1";
                } else {
                    output += "0";
                }
            }
            temp = temp.next;
            if (i + 1 != input.length()) {
                output += ",";
            }
        }
        return Arrays.toString(decimal(output)).replaceAll("[\\[ \\]]", "");
    }

    public  String decrypt(String input) {
        CircularQueue queue=genCircle(offset);
        String arr[]=input.split(",");
        String output = "";
        Node temp = queue.front;
        for (int i = 0; i < arr.length; i++) {
            int encrypt = Integer.parseInt(arr[i]);
            String value1[] = binary(encrypt, 8).split("");
            String value2[] = binary(temp.data, 8).split("");
            for (int j = 0; j < 8; j++) {
                int val1 = Integer.parseInt(value1[j]);
                int val2 = Integer.parseInt(value2[j]);
                if (val1 != val2) {
                    output += "1";
                } else {
                    output += "0";
                }
            }
            temp = temp.next;
            if (i + 1 != arr.length) {
                output += ",";
            }
        }
        String var = Arrays.toString(decimal(output)).replaceAll("[\\[\\] ]", "");
        return decode(var);
    }

    public String decode(String output) {
        String arr[] = output.split(",");
        for (int i = 0; i < arr.length; i++) {
            char temp= (char)(Integer.parseInt(arr[i]));
            arr[i] = "" + temp;
        }
        StringBuilder sb = new StringBuilder();
        for (String string : arr) {
            sb.append(string);
        }
        return sb.toString();
    }

    public String[] decimal(String output) {
        String arr[] = output.split(",");
        int temp;
        for (int i = 0; i < arr.length; i++) {
            temp = Integer.parseInt(arr[i], 2);
            arr[i] = "" + temp;
        }
        return arr;
    }

    public String binary(int value, int iterations) {
        if (iterations == 1) {
            return "0";
        } else {
            return binary(value / 2, --iterations) + (value % 2);
        }
    }

    public static void main(String[] args) {
        EncryptionQueue var = new EncryptionQueue();
        String encrypt=var.encrypt("20190926780");
        System.out.println(encrypt);
        String decrypt =var.decrypt("119,118,118,113,121,115,121,116,126,124,127,100");
        System.out.println(decrypt);
    }
}
