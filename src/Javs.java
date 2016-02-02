/** Currency follower - java project 
 * Application connects to Polish main bank site and returns information about currency rates
 * using parsing (jsoup)
 * using exceptions
 * Date: 18.06.2015
 * Author: Micha³ Czerwieñ
 */

import java.io.IOException;								/** import libraries */
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
 
public class Javs extends JFrame{						/** Declaration of public class */
	
public Javs() {
	   makeUI();
}
private void makeUI() {									/** Desing of GUI */
	
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setSize(780, 700);

    Button0 = new JButton("Get data!");					/** Button to update data */
    t = new JTextArea(30, 30);
    z = new JTextArea(30, 30);							/** 3 Text areas, non editable */
    q = new JTextArea(5, 5);
    t.setEditable(false);
    z.setEditable(false);
    q.setEditable(false);
    t.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    z.applyComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
    MyPanel = new JPanel();
    display = new JPanel(); 

    
    Font font = new Font("Verdana", Font.BOLD, 12);		/** Font settings */
    t.setFont(font);
    z.setFont(font);
    q.setFont(font);

    display.add(t);
    display.add(z);   
    MyPanel.add(Button0);								/** Placement of elements */
    add(display, BorderLayout.WEST);
    add(MyPanel, BorderLayout.CENTER);
    add(Button0, BorderLayout.NORTH);
    add(q, BorderLayout.SOUTH);
    
    t.addKeyListener(new KeyAdapter() {					/** Panel Listener */
        @Override
        public void keyTyped(KeyEvent e) {
            typeOnt(e);
        }
    });

    Button0.addActionListener(new ActionListener() {	/** Listener of button */
        @Override
        public void actionPerformed(ActionEvent e) {
            Button0pressed(e);
        }
    });
}
void typeOnt(KeyEvent e) {								/** action consumer */
    e.consume();
}
void Button0pressed(ActionEvent e) {					
    Document doc;
    Document doc2;
    
        t.setText(null);								/** resetting fields to null */
        z.setText(null);
        q.setText(null);
        
        try {
        	 
		doc = Jsoup.connect("http://www.nbp.pl/home.aspx?f=/kursy/kursya.html").get();
		doc2 = Jsoup.connect("http://www.nbp.pl/home.aspx?navid=archa&c=/ascx/tabarch.ascx&n=a104z150601").get();
		
		String title = doc.title();						/** Date obtaining */
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		t.setText(t.getText() + title + "\n");
		t.setText(t.getText() + "Data from : " + (dateFormat.format(date))+ "\n");
		z.setText(z.getText() + "Currencies : " + "\n");
		z.setText(z.getText() + "Data from : 2015/06/01" + "\n");
		
		/** Getting elements
		 * Algorithm scans the page for possible tables having set width
		 * For all the columns in that table selects rows
		 * For tds.size() > 2 algorithm gets the Text inside table 
		 */
		
	    for (Element table : doc.select("table[width=361]")) {	
	        for (Element row : table.select("tr")) {
	            Elements tds = row.select("td");
	            if (tds.size() > 2) {
	            	t.setText(t.getText() + tds.get(0).text() + ":" + tds.get(1).text()+ ":" + tds.get(2).text()+ "\n");
	            	
	            }
	        }
	    }
	    for (Element table : doc2.select("table[width=386]")) {
	        for (Element row : table.select("tr")) {
	            Elements tds = row.select("td");
	            if (tds.size() > 2) {
	            	z.setText(z.getText() + tds.get(0).text() + ":" + tds.get(1).text()+ ":" + tds.get(2).text()+ "\n");
	            	
	            }
	        }
	    }
	    
	    /** Comparing
	     * As above the algorithm obtains data from tables
	     * All commas had to be switched to dots for comparison
	     * Text is then switched to float number
	     * Numbers are compared and depending on the output the currency rate dropped or raised.
	     */
	    
	    for (Element table : doc.select("table[width=361]")) {
		for (Element table1 : doc2.select("table[width=386]")) {
		for (Element row1 : table1.select("tr")) {	
		Elements tds1 = row1.select("td");
		for (Element row : table.select("tr")) {	
		Elements tds = row.select("td");
		        if (tds1.size() > 2 && tds.size() > 2 ) {

		        	Strin = tds.get(2).text();
	            	Strin = Strin.toString(); 
	            	Strin = Strin.replaceAll(",",".");
		        	Strin2 = tds1.get(2).text();		        			        	
		        	Strin2 = Strin2.toString();		        	
		        	Strin2 = Strin2.replaceAll(",",".");		        		        	
		            float Value1 = Float.parseFloat(Strin);
		            float Value2 = Float.parseFloat(Strin2);
		            if (Value1 > Value2){
		            q.setText("Value of : " + tds.get(0).text() + " raised !"  );
		            }else{
		            q.setText("Value of : " + tds.get(0).text() + " fallen "  );
		            }
		            
		        }
		       }
		}}}
     
	} catch (IOException a) {    						/** catching exception */
		a.printStackTrace();
	} 
} 
  public static void main(String[] args) {				/** Main function */

    new Javs().setVisible(true);
}
JButton Button0;
JPanel display;
JPanel MyPanel;
JTextArea t;
JTextArea z;
JTextArea q;
public static String Strin;
public static String Strin2;
  }
 

