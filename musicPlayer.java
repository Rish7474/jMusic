import java.awt.Color; 
import javax.swing.JFrame;
import javax.swing.JPanel; 
public class musicPlayer{
   public static void main(String args[]){
      JFrame frame = new JFrame();
      frame.setBounds(150,50,800,500);
      frame.setTitle("Sound Keyboard");
      frame.setBackground(Color.blue);
      frame.setResizable(true);
      keyboard k = new keyboard();
      frame.add(k);
      frame.setVisible(true);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      k.run();
   }
}
