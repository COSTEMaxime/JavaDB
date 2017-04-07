import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Classe contenant la partie gauche de l'interface
 * Déclaration et initialisation de tous les objets de type "JButton"
 * 
 * @author COSTE Maxime
 */

@SuppressWarnings("serial")
public class LeftPanel extends JPanel implements ActionListener{
	
	public JButton bouton1;
	public JButton bouton2;
	public JButton bouton3;
	public JButton bouton4;
	public JButton bouton5;
	public JButton bouton6;
	public JButton bouton7;
	public JButton bouton8;
	public JButton bouton9;
	public JButton bouton10;
	public JButton bouton11;
	public JButton bouton12;
	public JButton bouton13;
	
	public LeftPanel() {
		
		FlowLayout fl = new FlowLayout();	//layout qui va gérer l'agencement des boutons
		fl.setHgap(10);						//espace minimal horizontal entre deux boutons
		fl.setVgap(10);						//espace minimal vertical entre deux boutons
		
		this.setLayout(fl);					//ajout du layout au leftPanel
		
		this.bouton1 = new JButton();				  //initialisation du bouton
		this.bouton1.setText("Script de création");   //texte se trouvat sur le bouton
		bouton1.addActionListener(this);			  //ajout d'une fonction permettant de gérer les actions effectuées sur le bouton
		this.add(bouton1);							  //ajout du bouton au leftPanel
		
		this.bouton2 = new JButton();
		this.bouton2.setText("Script de remplissage");
		bouton2.addActionListener(this);
		this.add(bouton2);
		
		this.bouton3 = new JButton();
		this.bouton3.setText("Recherche par ingrédient");
		bouton3.addActionListener(this);
		this.add(bouton3);
		
		this.bouton5 = new JButton();
		this.bouton5.setText("Recherche par diluant");
		bouton5.addActionListener(this);
		this.add(bouton5);
		
		this.bouton4 = new JButton();
		this.bouton4.setText("Couples potions/ongents");
		bouton4.addActionListener(this);
		this.add(bouton4);
		
		this.bouton6 = new JButton();
		this.bouton6.setText("Potions classées par température");
		bouton6.addActionListener(this);
		this.add(bouton6);
		
		this.bouton7 = new JButton();
		this.bouton7.setText("Marge commerciale");
		bouton7.addActionListener(this);
		this.add(bouton7);
		
		this.bouton8 = new JButton();
		this.bouton8.setText("Nombre moyen d'ingrédient");
		bouton8.addActionListener(this);
		this.add(bouton8);
		
		this.bouton9 = new JButton();
		this.bouton9.setText("Stock des ingrédients");
		bouton9.addActionListener(this);
		this.add(bouton9);
		
		this.bouton10 = new JButton();
		this.bouton10.setText("Consultation des commandes d'un client");
		bouton10.addActionListener(this);
		this.add(bouton10);
		
		this.bouton11 = new JButton();
		this.bouton11.setText("Ajouter une recette");
		bouton11.addActionListener(this);
		this.add(bouton11);
		
		this.bouton12 = new JButton();
		this.bouton12.setText("Valider une recette");
		bouton12.addActionListener(this);
		this.add(bouton12);
		
		this.bouton13 = new JButton();
		this.bouton13.setText("Supprimer une recette");
		bouton13.addActionListener(this);
		this.add(bouton13);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}
}