import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.*;

/**
 * Created by brenn on 4/20/2017.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        
        //Start Epoch time and establish the IP for the Cache Server
        
        long epoch = System.currentTimeMillis();
        //String url = "192.168.0.6";
        String url = "192.168.0.12";
        byte[] byteArray = new byte[1000];
        long T2 = 0;
        long T3 = 0;
        Socket s = new Socket(url, 8080);

        //Write to the output stream with the GET request and filename
        
        s.getOutputStream().write(("GET\r\nFilename: file7.txt\r\nTime: " + epoch + "\r\n\r\n").getBytes());
        s.getInputStream().read(byteArray);

        String data = new String(byteArray);
        String [] strw = data.split(("\n"));

        //Parse out the other Epoch times
        
        for(int i = 0; i < strw.length; i++){
            String [] split = strw[i].split(" ");
            if(split[0].equals("Time2:")){
                T2 = Long.parseLong(split[1].trim());
            }
            if(split[0].equals("Time3:")){
                T3 = Long.parseLong(split[1].trim());
            }
        }
        
        //Retrieve and write the file to the correct folder
        
        strw[0] = data.split("\r\n\r\n")[0];
        System.out.println(strw[0]);
        data = data.split("\r\n\r\n")[1];
        final String file = "C:\\CSClient\\file1.txt";
        FileOutputStream fis = new FileOutputStream(file);
        fis.write(data.getBytes());
        long T4 = System.currentTimeMillis();

        //Getting final Epoch time and closing socket

        s.close();
        long T1 = epoch;

        //Printing out Epoch time differences and closing stream
        
        System.out.println("Request time: " + (T2-T1));
        System.out.println("Cache time: " + (T3-T2));
        System.out.println("Transfer time: " + (T4-T3));
        System.out.println("Total time: " + (T4-T1));
        long filesize = (new File(file).length());
        System.out.println("Throughput: " +(filesize)/(T4-T3));
        fis.close();
    }

}
