//C:\jdk-1.8\bin\mp
import java.net.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class client extends Application { 
  String file_path;
  static JFrame fr1;   // global declarations
  JButton b,b1,b2,b3;
  JLabel l,lsp;
  JTextField t1,t2;
  static JLabel l1,l2;

public void start(Stage primaryStage)// primstage means primaryStage
{  
  
  SwingUtilities.invokeLater(() -> { // used when we use both javafx and swing components
  fr1=new JFrame("CLIENT");
  b=new JButton("Browse files");
  b1=new JButton("Send");
  b3=new JButton("Chat");
  l=new JLabel();
    lsp=new JLabel();
  l1=new JLabel(" ------- WANT TO CHAT WITH SERVER ? -------");
  
  l.setForeground(Color.white);
  l.setFont(new Font("Times new roman",Font.BOLD,15));

  l1.setForeground(Color.BLACK);
  l1.setFont(new Font("Monospaced",Font.BOLD,22));

   lsp.setForeground(Color.white);
  lsp.setFont(new Font("Times new roman",Font.BOLD,15));

  fr1.setSize(700,700);
  fr1.setVisible(true);
  fr1.setLayout(null);
  fr1.add(b);
  fr1.add(b1);
  fr1.add(l);
  fr1.add(b3);
  fr1.add(l1);
  fr1.add(lsp);
  b.setBounds(290, 100, 120, 27);// x-axis for horizontal positioning
  b1.setBounds(305, 150, 90, 27);// y-axis for vertical positioning
  l.setBounds(200,200 ,700,20);
  lsp.setBounds(305,250 ,700,20);//file sent
  l1.setBounds(60, 347, 700, 30);
  b1.setEnabled(false);
  b3.setEnabled(false);
  b3.setBounds(305, 400, 90, 27);
  JLabel background = new JLabel(new ImageIcon("green2.png"));
  background.setBounds(0, 0, 685, 655);
  fr1.add(background);

    b.addActionListener(new ActionListener() {//Browse files button
      public void actionPerformed(ActionEvent e)
      {    Platform.runLater(() -> {
         // used when we use both javafx and swing components
        // Update JavaFX UI components based on the result
        FileChooser fc=new FileChooser();// this function for choosing your file
         fc.setTitle("My files");
  
         File selectedFile=fc.showOpenDialog(primaryStage);
         if(selectedFile!=null)
         { 
           file_path=selectedFile.getAbsolutePath();
           String file_name=selectedFile.getName();
           l.setText("CHOOSEN FILE: "+file_name);
           System.out.println("Selected File: " +file_path);// prints file on terminal
               
               b1.setEnabled(true);
               b3.setEnabled(true);
               b1.addActionListener(new ActionListener() {//Send button
               public void actionPerformed(ActionEvent e)
               {
               lsp.setText("FIle Sent !");
               send_data();
               }
               });     
         }
         else{
           System.out.println("No file selected !");
         }
         b3.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent m)
          {
              temp();
          }
         });
        });//end of platform runlater
      }
    });
    });   
}

void send_data()
{
  try{
   System.out.println("into the socket");
   Socket s=new Socket("localhost",1008);
   DataOutputStream dos=new DataOutputStream(s.getOutputStream());
   
File file=new File(file_path);
dos.writeUTF(file.getName()); //sending the file name to server
dos.writeUTF(file.getAbsolutePath());
dos.flush();
// flushing read data to server
System.out.println("File sent successfully !"+file.getName());//printing confirmation msg of file name

}
catch(Exception e)
{
System.out.println(e);
}
}

void temp()
{  
  JFrame fr=new JFrame("ChatPRO");
  JTextField tx1=new JTextField();
      JTextField tx2=new JTextField();
      JLabel lx=new JLabel("Server Says:");
      JLabel lx1=new JLabel("Type here:");
      JLabel lx2=new JLabel("ChatPRO");
      JButton bx2=new JButton("send");
      fr.setBackground(Color.DARK_GRAY);
      fr.setVisible(true);
      fr.setSize(700,700);
      fr.setLayout(null);
      fr.add(lx);
      fr.add(lx1);
      fr.add(lx2);
      fr.add(tx1);
      fr.add(tx2);
      fr.add(bx2);
      tx1.setBounds(290, 300, 160, 30);
      tx2.setBounds(290, 350, 160, 30);
      bx2.setBounds(328, 400, 90, 27);// x-axis for horizontal positioning
      lx2.setBounds(150, 95, 600, 90);
      lx2.setForeground(Color.black);
          lx2.setFont(new Font("Monospaced",Font.BOLD,100));
      lx.setBounds(200, 300, 150, 30);
      lx.setForeground(Color.white);
          lx.setFont(new Font("Times new roman",Font.BOLD,16));
      lx1.setBounds(205, 350, 150, 30);
      lx1.setForeground(Color.white);
          lx1.setFont(new Font("Times new roman",Font.BOLD,16));
        JLabel background = new JLabel(new ImageIcon("green2.png"));
background.setBounds(0, 0, 685, 655);
fr.add(background);
      try{
  Socket s=new Socket("localhost",1080);
  DataOutputStream di=new DataOutputStream(s.getOutputStream());
  DataInputStream di2=new DataInputStream(s.getInputStream());
  
  bx2.addActionListener(new ActionListener() 
   {
      public void actionPerformed(ActionEvent m)
      {   try{
          String x=tx2.getText();
          tx2.setText("");
          di.writeUTF(x);
          di.flush();
          // di.close();
          //  s.close();// always remember use this socket close with previous close componet don't keep him alone 
      }
      catch(Exception e)
      {
          System.out.println(e);
      }
      }
  });

  
  Thread serverCommunicationThread = new Thread(() -> {
    try {
        while (true) {
            String str4 = di2.readUTF();
            SwingUtilities.invokeLater(() -> tx1.setText(str4));
        }
    } catch (IOException e) {
        System.out.println("Server connection closed.");
    }
});

serverCommunicationThread.start();
}
catch(Exception e)
{
System.out.println(e);
}
}
public static void main(String args[])
{ 
  client c=new client();
    launch(args);
}
}