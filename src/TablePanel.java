import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

/**
 * Classe créant un panel contenant une vue d'une table sql
 * 
 * @author COSTE Maxime
 * @param model la vue que l'on souhaite afficher
 */

@SuppressWarnings("serial")
public class TablePanel extends JPanel {

	private JTable table;

	public TablePanel(TableModel model) {

		table = new JTable(model);							//création de la JTable qui sera affichée
		setLayout(new BorderLayout());						//création et ajout d'un nouveau layout
		add(new JScrollPane(table), BorderLayout.CENTER);	//on centre notre table et on lui ajoute une barre de scrolling si jamais
	}
}