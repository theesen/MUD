
public class Key extends Item{
	/**
	 * Die T�r zu der der Schl�ssel passt.
	 */
	private Door door;
	/**
	 * Konstruktor der Klasse Item. Erzeugt einen Schl�ssel der exakt eine T�r �ffnen kann.
	 * Superklassen: 	Item
	 * 					NamedObject
	 * 
	 * @param i Nummer des Schl�ssels.
	 * @param n Name des Schl�ssels.
	 * @param desc Beschreibung des Schl�ssels.
	 */
	public Key (int i, String n, String desc, Door d){
		super(i,n,desc);
		door = d;
	}
	
	/**
	 * Die Methode soll testen ob der Schl�ssel zur T�r (@param door) passt.
	 */
	public boolean suits(){
		
		
		return false;
	}
	/**
	 * Soll angeben ob der SChl�ssel beim Verwenden zerst�rt wurde.
	 * @return
	 */
	public boolean turn (){
		return true;
	}

}
