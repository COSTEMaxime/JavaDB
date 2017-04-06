public class BDD {
	
	public static void main(String[] args) {
		
		ORM orm = new ORM("nouvelle bdd");
		Fenetre fenetre = new Fenetre(orm);
	}
}