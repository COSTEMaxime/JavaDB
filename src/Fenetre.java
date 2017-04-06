import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;


/**
 * Classe g�rant la fen�tre pricnipale
 * Elle contient l'initialisation de la fen�tre principale ainsi que de ses composants
 * Elle g�re l'appel aux fonctions lorsque l'on interragi avec un bouton et
 * des �ventuelles fen�tres qui seront ouvertes lors de l'ex�cution du programme
 * ELle contient la fonction Afficher qui permet d'afficher la vue d'une requ�te SQL
 * 
 * @author COSTE Maxime
 */


public class Fenetre extends JFrame {

	private JSplitPane splitPane;		//objet qui va contenir les deux panels pr�sent sur la fen�tre
	private LeftPanel leftPanel;		//panel de gauche (boutons des requ�tes)
	private JPanel rightPanel;			//panel de droite (affichage des requ�tes)
	private Dimension leftDimension;	//dimensions minimales de la partie gauche de la fen�tre
	private Dimension rightDimension;	//dimensions minimales de la partie droite de la fen�tre
	private Dimension frameDimension;	//dimensions minimales de la fen�tre

	public Fenetre(ORM orm) {

		String path = "C:\\Users\\maxim\\workspace\\BDD\\res\\";	//path pour l'icone de la fen�tre
		
		setTitle("Projet Sql");							  //titre de la fen�tre
		setIconImage(new ImageIcon(path+"icon.png").getImage());	  //icone de la fen�tre
		setSize(1000, 500);								  //taille initiale de la fen�tre
		setLocationRelativeTo(null);					  //la fen�tre est centr�e sur l'�cran
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //action � effectuer lorsque l'on ferme la fen�tre : on arr�te le prgramme
		
		addWindowListener(new WindowAdapter() {			  //autres actions � effectuer lorsque l'on quitte la fen�tre
			public void windowClosing(WindowEvent e) {
				orm.closeConnexion();					  //on ferme la connexion avec le serveur
			}
		});

		frameDimension = new Dimension(600, 400);		//dimensions minimales de la fen�tre
		setMinimumSize(this.frameDimension);			//application des dimensions

		splitPane = new JSplitPane();					//cr�ation de l'objet qui va g�rer les deux panels
		leftPanel = new LeftPanel();					//cr�ation du panel de gauche
		rightPanel = new TablePanel(null);				//cr�ation du panel de droite
		
		leftDimension = new Dimension(300, 400);		//dimensions minimales du panel de gauche
		rightDimension = new Dimension(300, 400);		//dimensions minimales du panel de droite

		getContentPane().setLayout(new GridLayout());	//cr�ation d'un objet qui va g�rer l'agencement des boutons
		getContentPane().add(splitPane);				//ajout de l'objet qui va gerer les deux panels dans la fen�tre

		splitPane.setDividerLocation(getWidth() / 3);	//position de base de la s�paration entre les deux panels
		splitPane.setLeftComponent(leftPanel);			//ajout des deux panels dans la fen�tre
		splitPane.setRightComponent(rightPanel);
		splitPane.setResizeWeight(.5);					//quand on resize la fen�tre, elle se resize de la m�me mani�re des deux cot�s

		leftPanel.setMinimumSize(leftDimension);		//application des dimensions
		rightPanel.setMinimumSize(rightDimension);		//application des dimensions
		
		setVisible(true);								//affichage de la fen�tre
		
		
		//Bouton d'appel du script qui va cr�er la nouvelle base
		leftPanel.bouton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String requete = "{call Cr�erBDD()}";			//cha�ne de caract�re qui contient le nom de la requ�te
				java.sql.CallableStatement callableStatement;	//Objet qui va ex�cuter la proc�dure stock�e
				ORM orm_ = new ORM("bdd");						//Objet quiva g�rer la connexion avecle serveur
				
				try {
					callableStatement = orm_.connexion.prepareCall(requete);	//pr�paration de la requ�te
					callableStatement.executeUpdate();							//ex�cution de la requ�te
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				finally	{
					orm_.closeConnexion();	//fermeture de la connexion avec le serveur
				}
				
			}
		});
		
		
		//Bouton d'appel du script qui va g�n�rer les donn�es dans la nouvelle base
		leftPanel.bouton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String requete = "{call RemplirBDD()}";
				java.sql.CallableStatement callableStatement;
				
				try {
					callableStatement = orm.connexion.prepareCall(requete);
					callableStatement.executeUpdate();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		//Bouton qui affiche les potions fabriqu�es � partir d'un certain ingr�dient
		leftPanel.bouton3.addActionListener(new ActionListener() {			//action � effectuer lorsque l'on appuie sur le bouton 3
			public void actionPerformed(ActionEvent arg0) {
				
				JFrame frame = new JFrame();								//cr�ation d'une nouvelle fen�tre
				frame.setLocationRelativeTo(null);							//la fen�tre sera affich�e au centre de l'�cran
				frame.setAlwaysOnTop(true);
				frame.setTitle("Recherche des potions faites � "
							 + "partir d'un infr�dient");					//titre
				frame.setSize(600, 80);										//taille de la fen�tre
				frame.setResizable(false);									//la taille de la fen�tre ne peut pas �tre modifi�e par l'utilisateur
				
				
				JPanel panel = new JPanel();								//cr�tion d'un panel qui va stocker nos objets
				JTextField tf = new JTextField(30);							//cr�ation d'un textFiel avec une taille de 30
				JLabel label = new JLabel("Ingr�dient � rechercher :");		//cr�ation d'un label
				panel.add(label);											//ajout des objets au panel
				panel.add(tf);
				frame.setContentPane(panel);								//ajout du panel � la fen�tre
				
				frame.setVisible(true);										//on affiche la fen�tre
				
				tf.addActionListener(new ActionListener(){					//action � effectuer

	                public void actionPerformed(ActionEvent e){
	                	frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));	//fermeture de la fen�tre de recherche
	                	afficherSQL(orm, "SELECT Potion FROM pr�parationpotion "						//requ�te qui affiche toutes les potions fabriqu�es � partir d'un certain ingr�dient
	                				+ "WHERE Ingr�dient LIKE ('" + tf.getText() + "');");
	                }});
			}
		});
			
		
		//Bouton qui permet d'afficher les couples de potions/onguents
		leftPanel.bouton4.addActionListener(new ActionListener() {		//action � effectuer lorsque l'on appuie sur le bouton 4
			public void actionPerformed(ActionEvent e) {
				
				afficherSQL(orm, "SELECT Potion, Onguent FROM Potion "		//affichage de la requ�te qui permet de s�lectionner les potions/onguents ayant la m�me recette
							+ "INNER JOIN Onguent ON RIGHT (potion.Potion, LENGTH(potion.Potion) - LOCATE(' ', potion.Potion)) "
							+ "LIKE RIGHT (onguent.Onguent, LENGTH(onguent.Onguent) - LOCATE(' ', onguent.Onguent));");
			}
		});
		
		
		//Bouton qui permet d'afficher la liste des potions faites � partir d'un certain diluant
		leftPanel.bouton5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				JFrame frame = new JFrame();
				frame.setLocationRelativeTo(null);
				frame.setAlwaysOnTop(true);
				frame.setTitle("Recherche des potions faites � partir d'un diluant");
				frame.setSize(600, 100);
				frame.setResizable(false);

				JPanel panel = new JPanel();
				JTextField tf = new JTextField(30);
				JLabel label = new JLabel("Diluant � rechercher :");
				panel.add(label);
				panel.add(tf);
				frame.setContentPane(panel);

				frame.setVisible(true);

				tf.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
	                	afficherSQL(orm, "SELECT Potion FROM pr�parationpotion "
	                				+ "WHERE Diluant LIKE ('" + tf.getText() + "')"
	                				+ "GROUP BY Potion;");
	                }});
			}
		});	
		
		
		//Bouton qui classe les potions par temp�rature
		leftPanel.bouton6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				afficherSQL(orm, "SELECT Potion, Temp�rature FROM pr�parationpotion "
							+ "ORDER BY Temp�rature;");
			}
		});
		
		
		//Bouton qui affiche les couples onguents/potions
		leftPanel.bouton7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				afficherSQL(orm, "SELECT pr�parationpotion.potion ,SUM(PrixIngr�dient) AS PrixTotalIngr�dients,"
							+ "SUM(diluant.PrixDiluant)/COUNT(diluant.PrixDiluant) AS PrixDiluant, SUM(PrixPotion)/COUNT(PrixPotion) AS PrixPotion, "
							+ "SUM(PrixPotion)/COUNT(PrixPotion)-SUM(PrixIngr�dient)-SUM(diluant.PrixDiluant)/COUNT(diluant.PrixDiluant) AS Marge FROM ingr�dient "
							+ "INNER JOIN pr�parationpotion ON pr�parationpotion.ingr�dient LIKE ingr�dient.ingr�dient "
							+ "INNER JOIN potion ON pr�parationpotion.potion LIKE potion.Potion "
							+ "INNER JOIN diluant ON pr�parationpotion.Diluant LIKE diluant.Diluant "
							+ "WHERE pr�parationpotion.potion LIKE potion.Potion "
							+ "GROUP BY pr�parationpotion.Potion;");
			}
		});
		
		//Bouton qui affiche le nombre moyen d'ingr�dients par potion
		leftPanel.bouton8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				afficherSQL(orm, "SELECT COUNT(Ingr�dient)/COUNT( DISTINCT Potion)  AS `Nombre moyen d'ingr�dient par potion` "
							+ "FROM pr�parationpotion;");
			}
		});
		
		
		leftPanel.bouton9.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String procedure = "{call StockIngredient()}";
				PreparedStatement ps;
				try {
					ps = orm.connexion.prepareCall(procedure);
					afficherProcedure(orm, ps);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		leftPanel.bouton10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame frame = new JFrame();
				frame.setLocationRelativeTo(null);
				frame.setAlwaysOnTop(true);
				frame.setTitle("Recherche des commandes d'un client");
				frame.setSize(600, 80);
				frame.setResizable(false);
				
				NumberFormat format = NumberFormat.getIntegerInstance();
				format.setGroupingUsed(false);
				
				JPanel panel = new JPanel();
				JFormattedTextField tf = new JFormattedTextField();
				tf.setColumns(4);
				JLabel label = new JLabel("Client � rechercher :");
				panel.add(label);
				panel.add(tf);
				frame.setContentPane(panel);

				frame.setVisible(true);

				tf.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						String procedure = "{call CommandeClient(?)}";
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						PreparedStatement ps;
						try {
							ps = orm.connexion.prepareCall(procedure);
							ps.setInt(1, Integer.parseInt(tf.getText()));
							afficherProcedure(orm, ps);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
	                }});
			}
		});
		
		//Bouton pour l'ajout de recettes
		leftPanel.bouton11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame frame = new JFrame();
				frame.setLocationRelativeTo(null);
				frame.setAlwaysOnTop(true);
				frame.setTitle("Ajout d'une recette");
				frame.setSize(1100, 500);
				frame.setResizable(false);

				NumberFormat format = NumberFormat.getIntegerInstance();
				format.setGroupingUsed(false);
				
				JPanel panel = new JPanel();
				
				Formulaire ingredient1 = new Formulaire("Premier");
				Formulaire ingredient2 = new Formulaire("Deuxi�me");
				Formulaire ingredient3 = new Formulaire("Troisi�me");
				Formulaire ingredient4 = new Formulaire("Quatri�me");
				Formulaire ingredient5 = new Formulaire("Cinqui�me");
				panel.add(ingredient1.panel);
				
				JLabel label = new JLabel("Nom de la recette :");
				JTextField tf = new JTextField(30);
				panel.add(label);
				panel.add(tf);
				
				JLabel label2 = new JLabel("Diluant :");
				JTextField tf2 = new JTextField(30);
				panel.add(label2);
				panel.add(tf2);
				
				JLabel label3 = new JLabel("Temp�rature :");
				JFormattedTextField tf3 = new JFormattedTextField();
				tf3.setColumns(3);
				panel.add(label3);
				panel.add(tf3);
				
				String[] tab = { "1", "2", "3", "4", "5" };

				JLabel labelMenu = new JLabel("Choix du nombre d'ingr�dients");
				JComboBox<?> menu = new JComboBox<Object>(tab);
				panel.add(labelMenu);
				panel.add(menu);	
				
				JButton bouton = new JButton("Valider");
				panel.add(bouton);
				
				frame.setContentPane(panel);
				
				frame.setVisible(true);


				menu.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						int temp = menu.getSelectedIndex();
						panel.removeAll();
									
						switch (temp) {
						
						case 4:
							panel.add(ingredient5.panel);
						case 3:
							panel.add(ingredient4.panel);
						case 2:
							panel.add(ingredient3.panel);
						case 1:
							panel.add(ingredient2.panel);	
						}
						
						panel.add(ingredient1.panel);
						
						panel.add(label);
						panel.add(tf);
						panel.add(label2);
						panel.add(tf2);
						panel.add(label3);
						panel.add(tf3);
						
						panel.add(labelMenu);
						panel.add(menu);
						panel.add(bouton);
						
						panel.validate();
						panel.repaint();	
					}
				});
				
				bouton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						java.sql.CallableStatement callableStatement;
						
						int temp = menu.getSelectedIndex();
						String requete = "";
						
						switch (temp) {
						
						case 0:
							requete = "{call AjoutRecette1(?,?,?,?,?,?,?)}";
							break;
						case 1:
							requete = "{call AjoutRecette2(?,?,?,?,?,?,?,?,?,?,?)}";
							break;
						case 2:
							requete = "{call AjoutRecette3(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
							break;
						case 3:
							requete = "{call AjoutRecette4(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
							break;
						case 4:
							requete = "{call AjoutRecette5(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
							break;
						}
						
						
						try {
							callableStatement = orm.connexion.prepareCall(requete);

							switch (temp) {

							case 4:
								callableStatement.setString(18, ingredient5.tf1.getText());
								callableStatement.setInt(19, Integer.parseInt(ingredient5.tf2.getText()));
								callableStatement.setInt(20, Integer.parseInt(ingredient5.tf3.getText()));
								callableStatement.setInt(21, Integer.parseInt(ingredient5.tf4.getText()));
								
							case 3:
								callableStatement.setString(14, ingredient4.tf1.getText());
								callableStatement.setInt(15, Integer.parseInt(ingredient4.tf2.getText()));
								callableStatement.setInt(16, Integer.parseInt(ingredient4.tf3.getText()));
								callableStatement.setInt(17, Integer.parseInt(ingredient4.tf4.getText()));
								
							case 2:
								callableStatement.setString(10, ingredient3.tf1.getText());
								callableStatement.setInt(11, Integer.parseInt(ingredient3.tf2.getText()));
								callableStatement.setInt(12, Integer.parseInt(ingredient3.tf3.getText()));
								callableStatement.setInt(13, Integer.parseInt(ingredient3.tf4.getText()));

							case 1:
								callableStatement.setString(6, ingredient2.tf1.getText());
								callableStatement.setInt(7, Integer.parseInt(ingredient2.tf2.getText()));
								callableStatement.setInt(8, Integer.parseInt(ingredient2.tf3.getText()));
								callableStatement.setInt(9, Integer.parseInt(ingredient2.tf4.getText()));

							case 0:
								callableStatement.setString(1, tf.getText());
								callableStatement.setString(2, ingredient1.tf1.getText());
								callableStatement.setInt(3, Integer.parseInt(ingredient1.tf2.getText()));
								callableStatement.setInt(4, Integer.parseInt(ingredient1.tf3.getText()));
								callableStatement.setInt(5, Integer.parseInt(ingredient1.tf4.getText()));
								callableStatement.setString(6, tf2.getText());
								callableStatement.setInt(7, Integer.parseInt(tf3.getText()));
							}
							
							switch (temp) {
							
							case 0:
								callableStatement.setString(6, tf2.getText());
								callableStatement.setInt(7, Integer.parseInt(tf3.getText()));
								break;
							case 1:
								callableStatement.setString(10, tf2.getText());
								callableStatement.setInt(11, Integer.parseInt(tf3.getText()));
								break;
							case 2:
								callableStatement.setString(14, tf2.getText());
								callableStatement.setInt(15, Integer.parseInt(tf3.getText()));
								break;
							case 3:
								callableStatement.setString(18, tf2.getText());
								callableStatement.setInt(19, Integer.parseInt(tf3.getText()));
								break;
							case 4:
								callableStatement.setString(22, tf2.getText());
								callableStatement.setInt(23, Integer.parseInt(tf3.getText()));
								break;
							}
							
							callableStatement.executeUpdate();

						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));	//fermeture de la fen�tre de recherche

					}
				});
			}
		});
		
		
		leftPanel.bouton12.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFrame frame = new JFrame();
				frame.setLocationRelativeTo(null);
				frame.setAlwaysOnTop(true);
				frame.setTitle("Validation d'une recette");
				frame.setSize(600, 80);
				frame.setResizable(false);
				
				NumberFormat format = NumberFormat.getIntegerInstance();
				format.setGroupingUsed(false);
				
				JPanel panel = new JPanel();
				JTextField tf = new JTextField(30);
				JLabel label = new JLabel("Recette � valider :");
				panel.add(label);
				panel.add(tf);
				frame.setContentPane(panel);

				frame.setVisible(true);

				tf.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						String procedure = "{call ValiderRecette(?)}";
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						PreparedStatement ps;
						try {
							ps = orm.connexion.prepareCall(procedure);
							ps.setString(1, tf.getText());
							ps.executeQuery();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
	                }});
			}
		});
		
		
		leftPanel.bouton13.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame();
				frame.setLocationRelativeTo(null);
				frame.setAlwaysOnTop(true);
				frame.setTitle("Suppression d'une recette");
				frame.setSize(600, 80);
				frame.setResizable(false);
				
				NumberFormat format = NumberFormat.getIntegerInstance();
				format.setGroupingUsed(false);
				
				JPanel panel = new JPanel();
				JTextField tf = new JTextField(30);
				JLabel label = new JLabel("Recette � supprimer :");
				panel.add(label);
				panel.add(tf);
				frame.setContentPane(panel);

				frame.setVisible(true);

				tf.addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						
						String procedure = "{call SupprimerRecette(?)}";
						frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
						PreparedStatement ps;
						try {
							ps = orm.connexion.prepareCall(procedure);
							ps.setString(1, tf.getText());
							ps.executeQuery();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
	                }});
			}
		});
		
		
	}
	
	//fonction qui permet d'afficher une requ�te sql
	protected void afficherSQL(ORM orm, String sql) {
		
		int temp = splitPane.getDividerLocation();					//on r�cup�re l'endroit o� �tait la s�paration entre les deux panels
		rightPanel.removeAll();										//suppression des objets que poouvait contenir le panel droit
		ResultSet data = orm.lireBase(sql);							//stockage des donn�es que la requ�te a renvoy�
		ResultSetTableModel model = new ResultSetTableModel(data);	//cr�ation d'un objet ResultSetModel qui va nous permettre d'afficher les donn�es � l'�cran
		rightPanel.add(new TablePanel(model));						//cr�ation de l'objet qui va afficher le r�sultat de la requ�te
		splitPane.setRightComponent(rightPanel);					//ajout de l'objet sur la fen�tre
		splitPane.setDividerLocation(temp);							//la s�paration est remise � sa position pr�c�dente
	}
	
	
	protected void afficherProcedure(ORM orm, PreparedStatement ps) {
		
		try {
			int temp = splitPane.getDividerLocation();
			rightPanel.removeAll();
			ResultSet data = ps.executeQuery();
			ResultSetTableModel model = new ResultSetTableModel(data);
			rightPanel.add(new TablePanel(model));
			splitPane.setRightComponent(rightPanel);
			splitPane.setDividerLocation(temp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}