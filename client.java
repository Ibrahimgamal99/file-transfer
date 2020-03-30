package network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class client {

    public static void main(String[] args) {
        Scanner input=new Scanner(System.in);
        String str=input.nextLine();
        try {
           Socket so = new Socket("localhost", 5555);
            DataOutputStream out = new DataOutputStream(so.getOutputStream());
            out.writeUTF(str);
            out.flush();
            out.close();
            so.close();

        } catch (IOException ex) {
            System.out.println(ex);
        }

    }
}
