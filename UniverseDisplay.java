
   import java.awt.*;
   import javax.swing.JFrame;
   
   public class UniverseDisplay {
   
      public static void main(String[] args) {
         Universe myApplet = new Universe(); // define applet of interest
         JFrame myFrame = new JFrame("My Universe"); // create frame with title

         myApplet.init();	

         myFrame.add(myApplet, BorderLayout.CENTER);
         myFrame.pack(); // set window to appropriate size (for its elements)
         myFrame.setVisible(true); // usual step to make frame visible
			myFrame.setSize(512, 590);
         myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
      }
      
   }
   