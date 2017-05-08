import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.*;

/**
 * Created by brenn on 4/20/2017.
 */
public class client {

    public client(String file) throws IOException{
        
        //Starts incoming request in a new thread
        
        System.out.println("client hit");
        long epoch = System.currentTimeMillis() / 1000;
        String url = "192.168.0.6";
        byte[] byteArray = new byte[1000];

        Socket s = new Socket(url, 8098);

        s.getOutputStream().write(("GET\r\nFilename: file.txt\r\nTime: " + epoch + "\r\n\r\n").getBytes());
        s.getInputStream().read(byteArray);

        String data = new String(byteArray);

        data = data.split("\r\n\r\n")[1];

        FileOutputStream fis = new FileOutputStream(file);
        fis.write(data.getBytes());
        fis.close();

        s.close();


    }

}
