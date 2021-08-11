package textcollage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

public class DrawTextPanel extends JPanel  {
			
	private ArrayList<DrawTextItem> theString = new ArrayList<DrawTextItem>();
	 private Color currentTextColor = Color.BLACK; 
	 private Canvas canvas; 
	 private JTextField input; 
	 private SimpleFileChooser fileChooser; 
	 private JMenuBar menuBar; 
	 private MenuHandler menuHandler;
	 private JMenuItem undoMenuItem;
	
	 private JMenuBar getMenuBar() {

		return null;
	} 
}	


 private class Canvas extends JPanel {
	
	private static final long serialVersionUID = 1L;

	private SimpleFileChooser fileChooser;
	Canvas() {
 
	setPreferredSize( new Dimension(800,600) );
	setBackground(Color.LIGHT_GRAY);
	setFont( new Font( "Serif", Font.BOLD, 24 ));
		 }
 
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		((Graphics2D)g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	if (theString != null) {
		for (DrawTextItem thisString: theString) {
			thisString.draw(g);
	}
	   }

	 private class MenuHandler implements ActionListener {

		 private Color currentTextColor;
		public void actionPerformed(ActionEvent evt) {
			 doMenuCommand( evt.getActionCommand());
	}

		private void doMenuCommand(String actionCommand) {
			// TODO Auto-generated method stub

		}
	
public void DrawTextPanel() {
		 fileChooser = new SimpleFileChooser();
		 undoMenuItem = new JMenuItem("Remove Item"); 
		 undoMenuItem.setEnabled(false);
		 menuHandler = new MenuHandler();
		 setLayout(new BorderLayout(3,3)); 
		 setBackground(Color.BLACK); 
		 setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 
		 canvas = new Canvas();
		 add(canvas, BorderLayout.CENTER);
		 JPanel bottom = new JPanel();
		 bottom.add(new JLabel("Text to add: "));
		 input = new JTextField("Hi all, I am student of UoPeople. Nice to meet you.", 40); 
		 bottom.add(input);
		 add(bottom, BorderLayout.SOUTH); 
		 canvas.addMouseListener( new MouseAdapter() {

public void mousePressed(MouseEvent e) {
	doMousePress( e );
}
		                
public void doMousePress( MouseEvent e ) {
		                    String text = input.getText().trim();
		                    if (text.length() == 0) {
		                        input.setText("Hi all, I am student of UoPeople. Nice to meet you.");
		                        text = "Hi all, I am student of UoPeople. Nice to meet you.";
		                    }
		                    DrawTextItem s = new DrawTextItem( text, e.getX(), e.getY() );
		                    s.setTextColor(currentTextColor);
		                    s.setFont( new Font( "Arial", Font.ITALIC + Font.BOLD, 14 ));
		                    s.setMagnification(3);
		                    s.setBorder(true);
		                    s.setRotationAngle(25);
		                    s.setTextTransparency(0.3);
		                    s.setBackground(Color.RED);
		                    s.setBackgroundTransparency(0.7);
		                    theString.add(s);
		                    undoMenuItem.setEnabled(true);
		                    canvas.repaint();
		                }
		                    
		                    
public JMenuBar getMenuBar() {
    if (menuBar == null) {
        menuBar = new JMenuBar();
        String commandKey; 
        if (System.getProperty("mrj.version") == null)
        	commandKey = "control ";
        else
        	commandKey = "meta ";
        
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);
        JMenuItem saveItem = new JMenuItem("Save..."); 
        saveItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "N")); 
        saveItem.addActionListener(menuHandler);
        fileMenu.add(saveItem);
        JMenuItem openItem = new JMenuItem("Open..."); 
        openItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "O")); 
        openItem.addActionListener(menuHandler);
        fileMenu.add(openItem);
        fileMenu.addSeparator();
        JMenuItem saveImageItem = new JMenuItem("Save Image..."); 
        saveImageItem.addActionListener(menuHandler); fileMenu.add(saveImageItem);
		                    
        JMenu editMenu = new JMenu("Edit");
        menuBar.add(editMenu);
        undoMenuItem.addActionListener(menuHandler);        
        undoMenuItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "Z")); 
        editMenu.add(undoMenuItem);
        editMenu.addSeparator();
        JMenuItem clearItem = new JMenuItem("Clear"); 
        clearItem.addActionListener(menuHandler);
        editMenu.add(clearItem);
        
        
        JMenu optionsMenu = new JMenu("Options");
        menuBar.add(optionsMenu);
        JMenuItem colorItem = new JMenuItem("Set Text Color..."); 
        colorItem.setAccelerator(KeyStroke.getKeyStroke(commandKey + "T")); 
        colorItem.addActionListener(menuHandler); optionsMenu.add(colorItem);
        JMenuItem bgColorItem = new JMenuItem("Set Background Color..."); 
        bgColorItem.addActionListener(menuHandler); 
        optionsMenu.add(bgColorItem);
    }
    return menuBar;
}
private void doMenuCommand(String command) {
    if (command.equals("Save...")) {
    	File textFile = fileChooser.getOutputFile(this, "Select Text File Name", "text.txt");
    			           if (textFile == null)
    			               return;
    			           try {
    			               BufferedWriter bw = new BufferedWriter(new FileWriter(textFile));
    			               bw.write(canvas.getHeight() + "~"+ canvas.getWidth()
    			               +"~"+canvas.getBackground().getRGB());
    			               
                  
    			               for (DrawTextItem dti : theString) { bw.write(System.lineSeparator()); 
    			               bw.write(dti.getString()+"~"+dti.getX()+"~"+dti.getY()
    			               +"~"+dti.getTextColor().getRGB()+"~"+dti.getRotationAngle());
    			                              }
    			                              bw.close();
    			                          }
    			               catch (Exception e) { 
    			            	   JOptionPane.showMessageDialog(this, "Sorry, an error occurred while trying to save the image details:\n" + e);
    			               }
    			                          }
    			               }
    			             
					else if (command.equals("Open...")) {         
    			               
						File textFile = fileChooser.getInputFile(this, "Select Text File Name");
			           if (textFile == null)
			return; try {
			Scanner sc = new Scanner(textFile);
			String meta = sc.nextLine(); 
			canvas.setSize(Integer.parseInt(meta.split("~")[1]),
			Integer.parseInt(meta.split("~")[0]));
			canvas.setBackground(new Color(Integer.parseInt(meta.split("~")[2]))); 
    			               
			 theString = new ArrayList<DrawTextItem>();
			    while(sc.hasNextLine()) {
			String element = sc.nextLine();
			DrawTextItem dti = new DrawTextItem(element.split("~")[0]); 
			dti.setX(Integer.parseInt(element.split("~")[1])); 
			dti.setY(Integer.parseInt(element.split("~")[2])); 
			dti.setTextColor(new Color(Integer.parseInt(element.split("~") [3])));	               
			dti.setRotationAngle(Double.parseDouble(element.split("~")[4]));
	        theString.add(dti);
	    }
	  
	    sc.close();
	}
	catch (Exception e) { 
		JOptionPane.showMessageDialog(this, "Sorry, an error occurred while trying to save the imag details:\n" + e);	               
	}
			 canvas.repaint();
		 }
	       else if (command.equals("Clear")) {
	    	   theString = new ArrayList<DrawTextItem>();
	    	   
	    	   undoMenuItem.setEnabled(false);
	           canvas.repaint();
	       }
	       else if (command.equals("Remove Item")) {
	    	   
	       }
	       theString.remove(theString.size()-1);
	       if (theString.size()==0) {
	           undoMenuItem.setEnabled(false);
	   }
	       canvas.repaint();
	   }
else if (command.equals("Set Text Color...")) {
Color c = JColorChooser.showDialog(this, "Select Text Color", currentTextColor);
           if (c != null)
               currentTextColor = c;
       }
			 
else if (command.equals("Set Background Color...")) {
Color c = JColorChooser.showDialog(this, "Select Background Color", canvas.getBackground());
private SimpleFileChooser fileChooser;
         if (c != null) {
        	 Color c;
			canvas.setBackground(c);
         }
             canvas.repaint();
} }
     else if (command.equals("Save Image...")) {
    	 
     }
File imageFile = fileChooser.getOutputFile();

private Component canvas;
if (imageFile == null);{
    return;
    
try {
			 
	 BufferedImage image = new
			 BufferedImage(canvas.getWidth(),canvas.getHeight(),
					 BufferedImage.TYPE_INT_RGB);
					 Graphics g = image.getGraphics();
					 g.setFont(canvas.getFont());
					 ((JComponent) canvas).paintComponent(g);	
					 boolean ok = ImageIO.write(image, "PNG", imageFile);
					 if (ok == false)
						 throw new Exception("PNG format is not supported (this thing shouldn't happen!).");
}
catch (Exception e) {
	JOptionPane.showMessageDialog(canvas, this, "Sorry, an error occurred while trying to save the image:\n" +e, 0);
	
}
 }
 }
 
}
					 
					 