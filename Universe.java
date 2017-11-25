   import javax.swing.JApplet;
   import java.awt.Graphics;
   import java.awt.Color;
   import java.awt.Graphics2D;
   import java.lang.Math;
   import java.awt.geom.Ellipse2D.Double;
   import java.util.Random;
   import java.util.ArrayList;
   import java.awt.event.ActionEvent;
   import javax.swing.JPanel;
   import javax.swing.JLabel;
   import javax.swing.JButton;
   import java.awt.GridLayout;
   import java.awt.BorderLayout;
   import java.util.Scanner;
   import java.awt.event.ActionListener;
   
   public class Universe extends JApplet{
   
      
      // Applet 
      private final int APPLET_WIDTH = 512, APPLET_HEIGHT = 512;
      int n = 5;
      private Body[] bodies = new Body[n];
      double g = 6.67*Math.pow(10,-11);
      Random r;                              // Random Generator
      Scanner in;
      UniversePanel universe;
      private JPanel buttonRowPanel;
      JButton solar, pause, reset, nodes,dTUp,dTDown,thetaUp,thetaDown;
      
      
      public void init() {
         setSize(APPLET_WIDTH, APPLET_HEIGHT);
         r = new Random();

         // Create universe
         bodies[0] = new Body(new Vector(0,0), new Vector(0,0), 2*Math.pow(10,12));
         bodies[1] = new Body(new Vector(5.79*Math.pow(10,4), 0), new Vector(0,.0479), 3.302*Math.pow(10,5));
         bodies[2] = new Body(new Vector(1.08*Math.pow(10,5), 0), new Vector(0,.035), 4.869*Math.pow(10,6));
         bodies[3] = new Body(new Vector(1.496*Math.pow(10,5), 0), new Vector(0,.02928), 5.974*Math.pow(10,6));
         bodies[4] = new Body(new Vector(2.79*Math.pow(10,5), 0), new Vector(0,.0241), 6.419*Math.pow(10,5));
      
         // Create universe
         universe = new UniversePanel(bodies);

         // Set up buttons
         JPanel buttonRowPanel = new JPanel(); 
         JButton solar = new JButton("Solar System");
         JButton random = new JButton("Galaxy");
			JButton pause = new JButton("Pause");
         JButton nodes = new JButton("Nodes");
         JButton dTUp = new JButton("Increase dT");
         JButton dTDown = new JButton("Decrease dT ");
			JButton thetaUp = new JButton("Increase Theta");
         JButton thetaDown = new JButton("Decrease Theta");
         
         // Add buttons to button panel
         buttonRowPanel.setLayout(new GridLayout(2,4));
         buttonRowPanel.setBackground(Color.BLACK);
         buttonRowPanel.setSize(400,50);
         buttonRowPanel.add(solar);
         buttonRowPanel.add(dTUp);
         buttonRowPanel.add(thetaUp);
         buttonRowPanel.add(pause);
         buttonRowPanel.add(random);
         buttonRowPanel.add(dTDown);
         buttonRowPanel.add(thetaDown);
         buttonRowPanel.add(nodes);
         
         getContentPane().add(universe, BorderLayout.CENTER);
         getContentPane().add(buttonRowPanel, BorderLayout.SOUTH);
         
         // Button Action Listeners
      class SolarListener implements ActionListener { // Generate Solar System
         
		   public void actionPerformed(ActionEvent event){
            // Set parameters
            n = 5;
            bodies = new Body[n];
            
            // Set up planets
            bodies[0] = new Body(new Vector(0,0), new Vector(0,0), 2*Math.pow(10,12));
            bodies[1] = new Body(new Vector(5.79*Math.pow(10,4), 0), new Vector(0,.0479), 3.302*Math.pow(10,5));
            bodies[2] = new Body(new Vector(1.08*Math.pow(10,5), 0), new Vector(0,.035), 4.869*Math.pow(10,6));
            bodies[3] = new Body(new Vector(1.496*Math.pow(10,5), 0), new Vector(0,.02928), 5.974*Math.pow(10,6));
            bodies[4] = new Body(new Vector(2.79*Math.pow(10,5), 0), new Vector(0,.0241), 6.419*Math.pow(10,5));
         
            // Create universe
            universe.changeBodies(bodies);
            repaint();
         }
      }
      
     class RandomListener implements ActionListener { // Generate Random System
         
         public void actionPerformed(ActionEvent event){
            // Set parameters
            n = 1000;
            bodies = new Body[n];
            
            // Set bodies
            bodies[0] = new Body(new Vector(0,0), new Vector(0,0), 2*Math.pow(10,12));
            for(int i=1; i<n; i++){
               Vector position = new Vector(r.nextInt((int)(8*Math.pow(10,5)))-4*Math.pow(10,5),r.nextInt((int)(8*Math.pow(10,5)))-4*Math.pow(10,5));
               Vector velocity = new Vector(position.getY()/position.getMag(),-position.getX()/position.getMag()).sMult(Math.sqrt(g*bodies[0].getM()/position.getMag()));
               bodies[i] = new Body(position, velocity, (int)r.nextInt((int)(2*Math.pow(10,8))));
            }
            // Create universe
            universe.changeBodies(bodies);
            repaint();
         }
      }
      class PauseListener implements ActionListener { // Pause or unpause
         
         public void actionPerformed(ActionEvent event){
            boolean pause = universe.pause();
            
            if(pause)
               universe.printTree();
         }
      }
      class NodeListener implements ActionListener { // Display Nodes
         
         public void actionPerformed(ActionEvent event){
            universe.nodeSwitch();
         }
      }
      class DTUpListener implements ActionListener { // increase dT
         public void actionPerformed(ActionEvent event){
            universe.dTIncrement(0);
         }
      }
      
      class DTDownListener implements ActionListener { // decrease dT
         public void actionPerformed(ActionEvent event){
            universe.dTIncrement(1);
         }
      }
      class ThetaUpListener implements ActionListener { // increase theta
         public void actionPerformed(ActionEvent event){
            universe.thetaIncrement(0);
         }
      }
      class ThetaDownListener implements ActionListener { // decrease theta
         public void actionPerformed(ActionEvent event){
            universe.thetaIncrement(1);
         }
      }
   
         
      // Connect buttons with their actions
      ActionListener SolarListener = new SolarListener();
	   solar.addActionListener(SolarListener);
      ActionListener RandomListener = new RandomListener();
	   random.addActionListener(RandomListener);
      ActionListener PauseListener = new PauseListener();
	   pause.addActionListener(PauseListener);
      ActionListener NodeListener = new NodeListener();
	   nodes.addActionListener(NodeListener);
      ActionListener DTUpListener = new DTUpListener();
	   dTUp.addActionListener(DTUpListener);
      ActionListener DTDownListener = new DTDownListener();
	   dTDown.addActionListener(DTDownListener);
      ActionListener ThetaUpListener = new ThetaUpListener();
	   thetaUp.addActionListener(ThetaUpListener);
      ActionListener ThetaDownListener = new ThetaDownListener();
	   thetaDown.addActionListener(ThetaDownListener);
         
      }
      
      public void paint (Graphics g) {
         getContentPane().repaint();
         setVisible(true);
      }
         
   }