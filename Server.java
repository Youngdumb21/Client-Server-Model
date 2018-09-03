import java.net.*;
import java.io.*;
 
public class Server
{
    //initialize socket and input stream
    private Socket          socket   = null;
    private ServerSocket    server   = null;
    private DataInputStream in       =  null;
 
    // constructor with port
    public Server(int port)
    {
        // starts server and waits for a connection
        try
        {
            server = new ServerSocket(port);
            System.out.println("Server started");
 
            System.out.println("Waiting for a client ...");
 
            socket = server.accept();
            System.out.println("Client accepted");
            
            //RSA
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA", "FlexiCore");
            Cipher cipher = Cipher.getInstance("RSA", "FlexiCore");

            kpg.initialize(1024);
            KeyPair keyPair = kpg.generateKeyPair();
            PrivateKey privKey = keyPair.getPrivate();
            PublicKey pubKey = keyPair.getPublic();

            cipher.init(Cipher.DECRYPT_MODE, privKey);

            // takes input from the client socket
            in = new DataInputStream(
                new BufferedInputStream(socket.getInputStream()));
            
            String cleartextAgainFile = "cleartextAgainSymm.txt";
            CipherInputStream cis = new CipherInputStream(in, cipher);
            fos = new FileOutputStream(cleartextAgainFile);

            while ((i = cis.read(block)) != -1) {
                fos.write(block, 0, i);
            }
            fos.close();

            System.out.println("Closing connection"); 
            // close connection
            socket.close();
            in.close();
        }
        catch(IOException i)
        {
            System.out.println(i);
        }
    }
 
    public static void main(String args[])
    {
        Server server = new Server(5001);
    }
}
