public class BDD {
	
	public static void main(String[] args) {
		
		ORM orm = new ORM("nouvelle bdd");
		@SuppressWarnings("unused")
		Fenetre fenetre = new Fenetre(orm);
	}
}