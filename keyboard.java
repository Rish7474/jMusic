import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;

public class keyboard extends JPanel implements KeyListener{
   private ArrayList<String> notes = new ArrayList<String>(Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
   private boolean[] keysDown = new boolean[8];
   private  MidiChannel[] channels;
   private int instrument = 1; 
   private  int volume = 150;
   private int octave = 3;
   private int x = 0;
   public keyboard(){
      addKeyListener(this);
      setFocusable(true);
      setFocusTraversalKeysEnabled(false);
      try{
         Synthesizer synth = MidiSystem.getSynthesizer();
         synth.open();
         channels = synth.getChannels();
      }
      catch (Exception e) {
         throw new RuntimeException(e);
      }
   }
   
   public void paint(Graphics g){
      drawKeyBoard(g);
   }
   
   public void increaseVol(){volume++;}
   public void decreaseVol(){volume--;}
   public void increaseOctave(){octave++;}
   public void decreaseOctave(){octave--;}
   
   public int id(String note){return notes.indexOf(note.substring(0))+12*octave+12;}
   public int id(String note,int x){return notes.indexOf(note.substring(0))+12*(octave-1)+12;}
   
   public void run(){
      while(true)
         {
            repaint();
         }
   }
   
   public void drawKeyBoard(Graphics g){
      g.setColor(Color.white);
      int itr = 0; //iterative number
      for(int c = 5; c<800;c+=100){
         if(keysDown[itr])
            g.setColor(Color.yellow);
         g.fillRect(c,0,95,500);
         g.setColor(Color.white);
         itr++;
      }
   }
   
   @Override
   public void keyPressed(KeyEvent e){
      int keyCode = e.getKeyCode();
      switch (keyCode){
         case KeyEvent.VK_UP: 
            increaseOctave();
            break;
         case KeyEvent.VK_DOWN:
            decreaseOctave();
            break;
         case KeyEvent.VK_MINUS:
            decreaseVol();
            break;
         case KeyEvent.VK_EQUALS:
            increaseVol();
            break;
         case KeyEvent.VK_A:
            channels[instrument].noteOn(id("G",0),volume);
            keysDown[0] = true;
            break;     
         case KeyEvent.VK_S:
            channels[instrument].noteOn(id("A",0),volume);
            keysDown[1] = true;
            break;  
         case KeyEvent.VK_D:
            channels[instrument].noteOn(id("B",0),volume);
            keysDown[2] = true;
            break; 
         case KeyEvent.VK_F:
            channels[instrument].noteOn(id("C"),volume);
            keysDown[3] = true;
            break;    
         case KeyEvent.VK_G:
            channels[instrument].noteOn(id("D"),volume);
            keysDown[4] = true;
            break;  
         case KeyEvent.VK_H:
            channels[instrument].noteOn(id("E"),volume);
            keysDown[5] = true;
            break;
         case KeyEvent.VK_J:
            channels[instrument].noteOn(id("F"),volume);
            keysDown[6] = true;
            break;
         case KeyEvent.VK_K:
            channels[instrument].noteOn(id("G"),volume);
            keysDown[7] = true;
            break;
         case KeyEvent.VK_Q:
            channels[instrument].noteOn(id("G#"),volume);
            break;
         case KeyEvent.VK_W:
            channels[instrument].noteOn(id("A#"),volume);
            break; 
         case KeyEvent.VK_R:
            channels[instrument].noteOn(id("C#"),volume);
            break; 
         case KeyEvent.VK_T:
            channels[instrument].noteOn(id("D#"),volume);
            break;  
         case KeyEvent.VK_U:
            channels[instrument].noteOn(id("F#"),volume);
            break;
         case KeyEvent.VK_1:
            instrument = 1;
            break;
         case KeyEvent.VK_2:
            instrument = 9;
            break;
    }      
   }
   @Override
   public void keyTyped(KeyEvent e){}
   @Override
   public void keyReleased(KeyEvent e){
    int keyCode = e.getKeyCode();
      switch (keyCode){ 
         case KeyEvent.VK_A:
            channels[instrument].noteOn(id("G",0),volume);
            keysDown[0] = false;
            break;     
         case KeyEvent.VK_S:
            channels[instrument].noteOn(id("A",0),volume);
            keysDown[1] = false;
            break;  
         case KeyEvent.VK_D:
            channels[instrument].noteOn(id("B",0),volume);
            keysDown[2] = false;
            break; 
         case KeyEvent.VK_F:
            channels[instrument].noteOn(id("C"),volume);
            keysDown[3] = false;
            break;    
         case KeyEvent.VK_G:
            channels[instrument].noteOn(id("D"),volume);
            keysDown[4] = false;
            break;  
         case KeyEvent.VK_H:
            channels[instrument].noteOn(id("E"),volume);
            keysDown[5] = false;
            break;
         case KeyEvent.VK_J:
            channels[instrument].noteOn(id("F"),volume);
            keysDown[6] = false;
            break;
         case KeyEvent.VK_K:
            channels[instrument].noteOn(id("G"),volume);
            keysDown[7] = false;
            break;
       }

   }
}
