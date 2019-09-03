package userInterface;

import javax.swing.*;
public class gui {
  public static void startUI(){
    JFrame f=new JFrame();//creating instance of JFrame

    JButton b=new JButton("click");//creating instance of JButton
    b.setBounds(0,0,100, 80);//x axis, y axis, width, height

    f.add(b);//adding button in JFrame

    f.setSize(400,200);//400 width and 500 height
    f.setLayout(null);//using no layout managers
    f.setVisible(true);//making the frame visible
  }
}
