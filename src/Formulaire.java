import java.text.NumberFormat;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Formulaire {
	
	private JLabel label1;
	private JLabel label2;
	private JLabel label3;
	private JLabel label4;
	public JTextField tf1;
	public JFormattedTextField tf2;
	public JFormattedTextField tf3;
	public JFormattedTextField tf4;
	public JPanel panel;
	
	
	
	public Formulaire(String numero) {
		
		NumberFormat format = NumberFormat.getIntegerInstance();
		format.setGroupingUsed(false);
		
		panel = new JPanel();
		
		label1 = new JLabel(numero + " ingrédient :");
		tf1 = new JTextField(30);
		panel.add(label1);
		panel.add(tf1);
		
		label2 = new JLabel("Quantité du " + numero.toLowerCase() + " ingrédient :");
		tf2 = new JFormattedTextField(format);
		tf2.setColumns(2);
		panel.add(label2);
		panel.add(tf2);
		
		label3 = new JLabel("Fraicheur minimum :");
		tf3 = new JFormattedTextField(format);
		tf3.setColumns(3);
		panel.add(label3);
		panel.add(tf3);
		
		label4 = new JLabel("Fraicheur maximum :");
		tf4 = new JFormattedTextField(format);
		tf4.setColumns(3);
		panel.add(label4);
		panel.add(tf4);		
	}
}