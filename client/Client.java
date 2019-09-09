package client;

//work on this last

import javax.swing.*;
import java.time.Duration;

public class Client {
  public static void GUI(){
    JFrame f=new JFrame();//creating instance of JFrame

    JButton b=new JButton("click");//creating instance of JButton
    b.setBounds(0,0,100, 80);//x axis, y axis, width, height

    f.add(b);//adding button in JFrame

    f.setSize(400,200);//x and y
    f.setLayout(null);//using no layout managers
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
