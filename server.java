package network;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class server {

    public static void main(String[] args) {
        try {
            ServerSocket s = new ServerSocket(5555);
            Socket soc=s.accept();
            DataInputStream in=new DataInputStream(soc.getInputStream());
            String str=(String)in.readUTF();
            System.out.println(str);
            s.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}
