package main.client;

//work on this last

import javax.swing.*;

class Client {
  static void GUI(){
    JFrame f=new JFrame("Pprocess");//creating instance of JFrame

    JButton b=new JButton("Face me cowards!");//creating instance of JButton
    b.setBounds(100,50,200, 80);//x axis, y axis, width, height

    f.add(b);//adding button in JFrame


    f.setSize(400,200);//x and y
    f.setLayout(null);//using no layout managers
    f.setVisible(true);
    f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
}
