package tcp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class server {

    static void ser(String path) throws IOException {
        File f = new File(path);
        //Read File Contents into contents array 
        byte[] contents;
        long fileLength = f.length();
        long current = 0;

        ServerSocket ss = new ServerSocket(7);
        Socket socket = ss.accept();

        FileInputStream fis = new FileInputStream(f);
        BufferedInputStream bis = new BufferedInputStream(fis);

        //Get socket's output stream
        OutputStream os = socket.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        String fname = path.substring(path.lastIndexOf('\\'));
        oos.writeObject(fname);
        oos.writeObject(fileLength);

        // long start = System.nanoTime();
        while (current != fileLength) {
            int size = 65535;
            if (fileLength - current >= size) {
                current += size;

            } else {
                size = (int) (fileLength - current);
                current = fileLength;
            }
            System.out.println("Sending ... " + (current * 100) / fileLength + " % ");
            contents = new byte[size];
            bis.read(contents, 0, size);
            os.write(contents);
        }

        os.flush();
        socket.close();
        ss.close();
        System.out.println("File sent succesfully!");

    }

    static void claint(String ip) throws IOException, ClassNotFoundException {

        String fname;
        long l;
        Socket socket = new Socket(ip, 7);
        byte[] contents = new byte[65535];
        InputStream is = socket.getInputStream();

        ObjectInputStream ois = new ObjectInputStream(is);
        fname = (String) ois.readObject();
        l = (long) ois.readObject();
        //Initialize the FileOutputStream to the output file's full path.
        FileOutputStream fos = new FileOutputStream("D:\\phone" + fname);
        BufferedOutputStream bos = new BufferedOutputStream(fos);

        //No of bytes read in one read() call
        int bytesRead = 0;
        long t = 0;
        while ((bytesRead = is.read(contents)) != -1) {
            bos.write(contents, 0, bytesRead);
            t += bytesRead;
            System.out.println("Receiving ...." + ((t * 100) / l) + " % ");
        }

        bos.flush();
        socket.close();
        System.out.println("File saved successfully!");
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        int c;

        String path, ip;
        Scanner cin = new Scanner(System.in);
        Scanner cin1 = new Scanner(System.in);
        while (true) {

            System.out.println("1-Send file");
            System.out.println("2-Recive file");
            System.out.println("3-Exit");
            System.out.print("Enter choies : ");
            c = cin.nextInt();
            if (c == 1) {
                System.out.println("Enter path");
                path = cin1.nextLine();
                ser(path);
            } else if (c == 2) {
                System.out.println("Enter ip server");
                ip = cin1.nextLine();
                claint(ip);
            } else if (c == 3) {
                break;
            }

        }
    }
}
