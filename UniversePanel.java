   import javax.swing.JPanel;
	import java.awt.Graphics;
	import java.awt.Graphics2D;
	import java.awt.Color;
	import java.awt.geom.Line2D;
   import java.util.ArrayList;
   import java.sql.Timestamp;
   import java.util.Date;
   
   public class UniversePanel extends JPanel {
      // Universe constants
      double dT = 2500;                               // Time interval
      double g = 6.67*Math.pow(10,-11);               // force constant
      double theta = .5;                              // Accuracy of sim (0 = brute force)
      int  n;                                         // number of bodies in simulation
      int uniR = 2000;                                // Scaling
      int side = 512;                                 // Size of panel
      boolean pause = false, node = false;            // Pause, and whether nodes are displayed
      int counter = 0;                                // counts number of frames painted
      long fps;                                        // frames per second
      Date date1, date2;
      QuadNode tree;                
      Body[] bodies;
      
      
      public UniversePanel(Body[] b) {
         bodies = b;
         n = bodies.length;
      }
      
      public void paintComponent(Graphics g){
         Graphics2D g2 = (Graphics2D) g;
         
         if(counter == 0)
            date1= new java.util.Date();

         g2.setColor(Color.BLACK);
         g2.fillRect(0,0,side, side);

         // Draw each body
         g2.setColor(Color.WHITE);
         
         for(int i=0; i<n; i++) {
            if(i%5==0)
               g2.setColor(Color.YELLOW);
            if(i%5==1)
               g2.setColor(Color.GREEN);
            if(i%5==2)
               g2.setColor(Color.ORANGE);
            if(i%5==3)
               g2.setColor(Color.BLUE);
            if(i%5==4)
               g2.setColor(Color.RED);
            
            Vector vPos = bodies[i].getPos();
            if(i==0)
               g2.fillOval((int)vPos.getX()/uniR + 246,(int)vPos.getY()/uniR + 246,20,20);  
            
            g2.fillOval((int)vPos.getX()/uniR + 254,(int)vPos.getY()/uniR + 254,4,4);  
         }
         
         if(!pause)
            increment();
         
         // if paused, create tree, then draw, else just draw tree
         if(node && pause){
            tree = generateTree();
            tree.drawLines(g,uniR);
         }
         else if(node)
            tree.drawLines(g,uniR);


         if(counter == 99){
            date2= new java.util.Date();
            Timestamp t1 = new Timestamp(date1.getTime());
			   Timestamp t2 = new Timestamp(date2.getTime());
			   long ellapsedTime = t2.getTime() - t1.getTime();
            if(!pause)
               fps = 100000/ellapsedTime;
         }
         g2.setColor(Color.BLACK);
         g2.fillRect(275,0,237,20);
         g2.setColor(Color.WHITE);
         String s = String.format("dT: %6.0f    theta: %3.1f   fps: %6d",dT,theta,fps);
         g2.drawString(s,275,15);
         
         counter = (counter+1)%100;
         repaint();
      }
      
      public void increment() {
         // instantiate array of forces
         Vector[] accelVector = new Vector[n];
         QuadNode tree = generateTree();
         
         // Calculate accelerations on each body from tree
         for(int i=0; i<n; i++)
            accelVector[i] = tree.accelSum(bodies[i]);
         
         // Move each body according to acceleration     
         for(int i=0; i<n; i++)
            bodies[i].accelerate(accelVector[i],dT);
      }
      
      public QuadNode generateTree() { // Create Quad tree of particles
         tree = new QuadNode(side*uniR,theta,g);
         for(int i=0; i<n; i++)
            tree.addParticle(bodies[i]);
         
         tree.getCM();
      return tree;
      }
      
      public void changeBodies(Body[] b) {
         bodies = b;
         n = bodies.length;
         
      }
      
      public boolean pause(){ // pause or unpause simulation
         pause = !pause;
      return pause;
      }
      public void nodeSwitch(){ // pause or unpause simulation
         node = !node;
      }
      public void printTree(){
         System.out.println("Tree Stats!");
         System.out.println("Max Depth:   " + tree.maxDepth());
         System.out.println("Total Nodes: " + tree.totalNodes());
         System.out.println("Avg Depth:   " + tree.depthWeight()/tree.totalNodes());
         System.out.println();
      
      }
      
      public double dTIncrement(int i){
         if(i == 0)
            dT += 500;
         if(i == 1)
            dT -= 500;
         return dT;
      }
      
      
      public double thetaIncrement(int i){
         if(i == 0)
            theta += .5;
         if(i == 1)
            theta -= .5;
         return theta;
      }
      
   }
   

   
   
   
   
   
