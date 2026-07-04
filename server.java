import java.net.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

 class server {
    String file_path;
    JLabel l,l1,l2,lx2;
    JButton b,b1;
    JFrame f;
    JTextField t1,t2;

    void read_file() 
    {   
         f=new JFrame();
         lx2=new JLabel("ChatPRO");
         Color customColor = Color.decode("#136f04");
         f.getContentPane().setBackground(customColor);
      
        l=new JLabel();
        b=new JButton("Open");
        l1=new JLabel("Client says:");
        l2=new JLabel("Type here:");
        b1=new JButton("send");
        t1=new JTextField();
        t2=new JTextField();
        f.setSize(700,700);
        f.setTitle("SERVER");
        f.setVisible(true);
        f.setLayout(null);
        f.add(b);
        f.add(l);
        f.add(l1);
        f.add(l2);
        f.add(t1);
        f.add(t2);
        f.add(b1);
        f.add(lx2);
        
        b.setBounds(328, 250, 90, 27);// x-axis for horizontal positioning
        l.setBounds(230,300 ,600,20);
        l.setForeground(Color.white);
        l.setFont(new Font("Times new roman",Font.BOLD,15));
        lx2.setBounds(150, 95, 600, 90);
        lx2.setForeground(Color.black);
        lx2.setFont(new Font("Monospaced",Font.BOLD,100));

      t1.setBounds(290, 400, 160, 30);
      t2.setBounds(290, 450, 160, 30);
      b1.setBounds(328, 500, 90, 27);// x-axis for horizontal positioning
      l1.setBounds(200, 400, 150, 30);
      l2.setBounds(205, 450, 150, 30);

      l1.setForeground(Color.white);
      l1.setFont(new Font("Times new roman",Font.BOLD,16));
       l2.setForeground(Color.white);
      l2.setFont(new Font("Times new roman",Font.BOLD,16));
      
          try
          { 
    ServerSocket ss=new ServerSocket(1008);
    System.out.println("Server waiting for client!");
    Socket s=ss.accept();
    DataInputStream di=new DataInputStream(s.getInputStream());

     String recieve_file_name=di.readUTF(); // It is only used for receiving the file name
     file_path=di.readUTF();  // it is used to get the file path cam from client
     System.out.println("\nClient says:"+recieve_file_name);// printing the file name
     int choice=JOptionPane.showConfirmDialog(null, "File Arrived !", "Notification", JOptionPane.OK_CANCEL_OPTION);
     
     if (choice==JOptionPane.OK_OPTION) 
     {
        l.setText("RECEIVED FILE: "+recieve_file_name);
     } 
     else 
     {
        l.setText("error while opening the file ");
     }
    /*below code is of file opening */
    b.addMouseListener(new MouseAdapter() {//open button
            public void mouseClicked(MouseEvent e)
            {
                try{
                File f=new File(file_path);
                if (f.exists()) //---> firstly i used(receieve_path.exists()) but exists can oly be used fie objects due to this i make one file one object and passed receive_path in it
                {
                    Desktop.getDesktop().open(f);
                } else 
                {
                    System.out.println("File not found !");
                }
            }
            catch(Exception a)
            {
                System.out.println(a);
            }
            }
            });
            try{
      ServerSocket ss1=new ServerSocket(1080);
      Socket s1=ss1.accept();
      DataInputStream di1=new DataInputStream(s1.getInputStream());

      DataOutputStream dos=new DataOutputStream(s1.getOutputStream());
      b1.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent m)
        {try{
          String str3=t2.getText();
          t2.setText("");
          dos.writeUTF(str3);
          dos.flush();
        }
        catch(Exception e)
        {
          System.out.println(e);
        }
        }
      });
  
  while (true) {
     String str2=di1.readUTF();
      t1.setText(str2);           // for continous communication used while loop
      System.out.println(str2);
  }}
  catch(Exception m)
  {
    System.out.println(m);
  }
      
    }
catch(Exception e)
{
    System.out.println(e);
}
}
public static void main(String arg[])
{    
    server sr = new server();
    sr.read_file();       
}
 }

