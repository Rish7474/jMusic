import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.io.*;

public class keyboard extends JPanel implements KeyListener{
   private ArrayList<String> notes = new ArrayList<String>(Arrays.asList("C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B"));
   private ArrayList<String> lineList = new ArrayList<String>();
   private String file;
   private boolean[] keysDown = new boolean[13];
   private  MidiChannel[] channels;
   private int instrument = 1; 
   private  int volume = 150;
   private int octave = 3;
   private int autoOctave = 3;
   private long timeInBetween = 300;
   
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
   
   /*This method creates a background for the JFrame and calls two other methods
     to create the keyboard keys*/
   public void paint(Graphics g){
      g.setColor(Color.blue);
      g.fillRect(0,0,1000,1000);
      drawWhiteKeys(g);
      drawBlackKeys(g);
   }
   
   /*This method calls the createNoteList() to add each line of file(.txt) to lineList, then
      goes through each line from the file, and in each line it goes through each char to see what was typed
      and act accordingly.*/
   public void playText(String file){
      this.file = file;
      createNoteList();
      boolean timeCond;
      long start, end;
      for(String line: lineList)
         for(int c = 0; c< line.length();c++){
            if(Character.isDigit(line.charAt(c)))
               autoOctave = Integer.parseInt(line.substring(c,c+1));
            else{
               if(line.substring(c,c+1).equals(" "))
                  timeInBetween+=100;
               else{
                  timeCond = true;
                  start = end = System.currentTimeMillis();
                     while(timeCond){
                        if(end-start>timeInBetween){
                           if(line.substring(1,2).equals("#")){
                              channels[instrument].noteOn(id(line.substring(c,c+2)),volume);
                              timeCond = false;
                              timeInBetween = 300;
                              c++;
                           }
                           else{
                              channels[instrument].noteOn(id(line.substring(c,c+1)),volume);
                              timeCond = false;
                              timeInBetween = 300;
                          }
                        }
                     end = System.currentTimeMillis();
                     }
               }
            }
         repaint();
        }
   }
   
   /*This method takes a .txt file and creates a String ArrayList. Each element in
      the ArrayList is one line of the .txt file.*/
   public void createNoteList(){
      try{
         BufferedReader br = new BufferedReader(new FileReader(file));
         String line;
         while((line = br.readLine()) != null)
            lineList.add(line);
         }         
      catch(Exception e) {System.out.print("Error reading file");}
   }
   
   public void increaseVol(){volume++;}
   public void decreaseVol(){volume--;}
   public void increaseOctave(){octave++;}
   public void decreaseOctave(){octave--;}
   
   /*Both id methods return an unique integer value for each note for the MidiChannel array, channels,
     to use to play sound. int id(String note, int x) was created for specifcally for notes A, G, and B,
     which come before C because the are one octave less than the other notes.*/
   public int id(String note){return notes.indexOf(note)+12*octave+12;}
   public int id(String note,int x){return notes.indexOf(note)+12*(octave-1)+12;}
   
   public void run(){
      playText("MusicText.txt");
      while(true)
         repaint();
   }
   
   /*This method goes through a loop to draw 8 white keys. If a key is pressed, its color will
      change to yellow.*/
   public void drawWhiteKeys(Graphics g){
      g.setColor(Color.white);
      int itr = 0; //key number
      for(int c = 5; c<800;c+=100){
         if(keysDown[itr])
            g.setColor(Color.yellow);
         g.fillRect(c,0,95,500);
         g.setColor(Color.white);
         itr++;
      }
   }

   /*This method goes through a loop to draw 5 red keys. If a key is pressed, its color will
      change to red.*/
   public void drawBlackKeys(Graphics g){
      g.setColor(Color.black);
      int itr = 8;
      for(int c = 75; c<700;c+=100){     
         if(keysDown[itr])
         {
            g.setColor(Color.red);
         }
         else
            g.setColor(Color.black);
       g.fillRect(c,0,50,200);
       g.setColor(Color.black);
       if(itr==9 || itr==11)
         c+=100;
       itr++;
      }
   }
   
   /*This method is called anytime a computer key is pressed. When called it is able to
      get the specfic keycode for the key that was pressed. The keycode is then placed into a
      switch to perform the proper action. Intially a chain of if and else statmenrs were used instead
      of the switch, but changed because a switch offers O(1) time complexity.*/
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
         case KeyEvent.VK_W:
            channels[instrument].noteOn(id("G#"),volume);
            keysDown[8] = true;
            break;
         case KeyEvent.VK_E:
            channels[instrument].noteOn(id("A#"),volume);
            keysDown[9] = true;
            break; 
         case KeyEvent.VK_T:
            channels[instrument].noteOn(id("C#"),volume);
            keysDown[10] = true;
            break; 
         case KeyEvent.VK_Y:
            channels[instrument].noteOn(id("D#"),volume);
            keysDown[11] = true;
            break;  
         case KeyEvent.VK_I:
            channels[instrument].noteOn(id("F#"),volume);
            keysDown[12] = true;
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
            keysDown[0] = false;
            break;     
         case KeyEvent.VK_S:
            keysDown[1] = false;
            break;  
         case KeyEvent.VK_D:
            keysDown[2] = false;
            break; 
         case KeyEvent.VK_F:
            keysDown[3] = false;
            break;    
         case KeyEvent.VK_G:
            keysDown[4] = false;
            break;  
         case KeyEvent.VK_H:
            keysDown[5] = false;
            break;
         case KeyEvent.VK_J:
            keysDown[6] = false;
            break;
         case KeyEvent.VK_K:
            keysDown[7] = false;
            break;
         case KeyEvent.VK_W:
            keysDown[8] = false;
            break;
         case KeyEvent.VK_E:
            keysDown[9] = false;
            break; 
         case KeyEvent.VK_T:
            keysDown[10] = false;
            break; 
         case KeyEvent.VK_Y:
            keysDown[11] = false;
            break;  
         case KeyEvent.VK_I:
            keysDown[12] = false;
            break;
       }
   }
}
