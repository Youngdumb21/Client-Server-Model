import java.net.*;
import java.io.*;
 
public class Client
{
    // initialize socket and input output streams
    private Socket socket            = null;
    private DataInputStream  input   = null;
    private DataOutputStream out     = null;
 
    // constructor to put ip address and port
    public Client(String address, int port) throws Exception
    {
        // establish a connection
        try
        {

            Security.addProvider(new FlexiCoreProvider());

            Cipher cipher = Cipher.getInstance("AES128_CBC", "FlexiCore");
            KeyGenerator keyGen = KeyGenerator.getInstance("AES", "FlexiCore");
            SecretKey secKey = keyGen.generateKey();

            // Encrypt

            cipher.init(Cipher.ENCRYPT_MODE, secKey);

            String cleartextFile = "cleartext.txt";

            socket = new Socket(address, port);
            System.out.println("Connected");
            out    = new DataOutputStream(socket.getOutputStream());
 
            // takes input from file
            FileInputStream fis = new FileInputStream(cleartextFile);
            
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            CipherOutputStream cos = new CipherOutputStream(out, cipher);

            // sends output to the socket
            byte[] block = new byte[8];
            int i;
            while ((i = fis.read(block)) != -1) {
                cos.write(block, 0, i);
            }
            cos.close();
        }
        catch(UnknownHostException u)
        {
            System.out.println(u);
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
 
        // close the connection
        try
        {
            input.close();
            out.close();
            socket.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public static void main(String args[]) throws Exception
    {
        Client client = new Client("172.31.131.207", 5001);
    }
}
