import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
            handleEvents();
            startReading();
            // startWriting();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
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
                if(e.getKeyCode()==10){
                    String contentToSend=messageInput.getText();
                    messagTextArea.append("Me : "+contentToSend+"\n");
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
        this.setTitle("Server Messager[End]");
        this.setSize(600, 700);
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
        messagTextArea.setEditable(false);

        this.setLayout(new BorderLayout());
        JScrollPane jScrollPane=new JScrollPane(messagTextArea);
        this.add(heading,BorderLayout.NORTH);
        this.add(jScrollPane,BorderLayout.CENTER);
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
                         JOptionPane.showMessageDialog(this, "Client Terminated the Chat");
                         messageInput.enable(false);
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
                        messageInput.enable(false);
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