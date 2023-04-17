import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
public class Client extends JFrame {
    Socket socket;
    BufferedReader br;
    PrintWriter out;

    private JLabel heading=new JLabel("Client Areas");
    private JTextArea messagTextArea=new JTextArea();
    private JTextField messageInput=new JTextField();
    private Font font=new Font("Roboto",Font.PLAIN,20);

    public Client(){
        try {

            // System.out.println("sending request to server");
            // socket=new Socket("127.0.0.1",7777);
            // System.out.println("COnnection done");

            // br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // out =new PrintWriter(socket.getOutputStream());
            createGUI();
            handleEvents();
            // startReading();
            // startWriting();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            // System.out.println("Connection is closed");
        }
        
    }
    private void handleEvents() {
        messageInput.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                // TODO Auto-generated method stub
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                // TODO Auto-generated method stub
                String contentToSend=messageInput.getText();
                if(e.getKeyCode()==10){
                    out.println(contentToSend);
                    out.flush();
                    messageInput.setText("");
                    messageInput.requestFocus();
                    // System.out.println("you have entre entre key");
                }
            }
            
        });
    }
    private void createGUI(){
        this.setTitle("Client Messager[End]");
        this.setSize(600, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        heading.setFont(font);
        messagTextArea.setFont(font);
        messageInput.setFont(font);

        heading.setIcon(new ImageIcon("message.png"));
        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        messageInput.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


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
                         System.out.println("Server termined");
                         socket.close();
                         break;
                    }
                    messagTextArea.append("Server: "+msg+"\n");    
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
        System.out.println("this is client ");
        new Client();
    }
}