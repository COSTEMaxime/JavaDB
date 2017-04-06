import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Classe g�rant la connexion au serveur mySql et d'ex�cuter des requ�tes
 * La classe permet de lire et d'�crire dans une BDD
 * 
 * @author COSTE Maxime
 */


public class ORM {

	public Connection connexion;
	private Statement statement;

	public ORM(String nomBase) {
		openConnexion(nomBase);
	}

	public void openConnexion(String nomBase) {

		try {
			Class.forName("com.mysql.jdbc.Driver");		// chargement du driver
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}

		try {

			String url = "jdbc:mysql://localhost:3306/" + nomBase + "?useSSL=false";	//adresse du serveur local sur lequel se trouve la BDD
			String login = "root";														//login du compte
			String passwd = "";															//mot de passe du compte

			this.connexion = DriverManager.getConnection(url, login, passwd);	// connexion avec le serveur
			this.statement = connexion.createStatement();						// objet permettant d'ex�cuter des requ�tes

		} catch (SQLException e) {
			System.out.println("SQLException : " + e.getMessage());
			System.out.println("SQLState : " + e.getSQLState());
			System.out.println("VendorError : " + e.getErrorCode());
		}
	}

	public void closeConnexion() {

		if (this.connexion != null)
			try {
				this.connexion.close();		//fermeture de la connexion
			} catch (SQLException e) {
				e.printStackTrace();
			}

		if (this.statement != null)
			try {
				this.statement.close();		//fermeture de l'objet statement
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	

	public int ecrireBase(String requete) {

		int resultat = 0;

		try {
			resultat = statement.executeUpdate(requete);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return resultat;
	}

	public ResultSet lireBase(String requete) {

		try {
			return statement.executeQuery(requete);		//l'objet statement renvoie un ResulSet contenant les donn�es que la requ�te a renvoy�

		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}