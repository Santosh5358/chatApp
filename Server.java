import java.net.*;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.Font;
import java.awt.BorderLayout;
import java.io.*;
class Server extends JFrame {
    
    ServerSocket server;
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    private JLabel heading=new JLabel("Server Areas");
    private JTextArea messagTextArea=new JTextArea();
    private JTextField messageInput=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20);
    public Server()
    {
        try {
            server=new ServerSocket(7777);
            System.out.println("sever start");
            System.out.println("wating");
            socket=server.accept();

            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out =new PrintWriter(socket.getOutputStream());
            createGUI();
            startReading();
            startWriting();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
    private void createGUI(){
        this.setTitle("Server Messager[End]");
        this.setSize(600 , 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        heading.setFont(font);
        messagTextArea.setFont(font);
        messageInput.setFont(font);

        this.setLayout(new BorderLayout());
        this.add(heading,BorderLayout.NORTH);
        this.add(messagTextArea,BorderLayout.CENTER);
        this.add(messageInput,BorderLayout.SOUTH);
        this.setVisible(true);

    }
    public void startReading(){
        Runnable r1=()->{
            System.out.println("Reader starte..");

            try {
                while(!socket.isClosed()){
                    String msg= br.readLine();
                    if(msg.equals("exit")){
                         System.out.println("client termined");
                         socket.close();
                         break;
                    }
                    System.out.println("client: "+msg);
                }
            } catch (Exception e) {
                // TODO: handle exception
                // e.printStackTrace();
                System.out.println("Connection is closed");
            }

        };
        new Thread(r1).start();
    }
    public void startWriting(){
        Runnable r2=()->{
            System.out.println("Writing Started...");
            try {
                while(!socket.isClosed()){
                    
                    BufferedReader br1=new BufferedReader(new InputStreamReader(System.in));
                    String content=br1.readLine();
                    out.println(content);
                    out.flush();
                    if(content.equals("exit")){
                        socket.close();
                        break;
                    }
                }
                System.out.println("Connection is closed"); 
            } catch (Exception e) {
                // TODO: handle exception
                // e.printStackTrace();
                System.out.println("Connection is closed");
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) {
        System.out.println("this is a program");
        new Server();
    }
}