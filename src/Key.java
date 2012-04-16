
public class Key extends Item{
	/**
	 * Die Tür zu der der Schlüssel passt.
	 */
	private Door door;
	/**
	 * Konstruktor der Klasse Item. Erzeugt einen Schlüssel der exakt eine Tür öffnen kann.
	 * Superklassen: 	Item
	 * 					NamedObject
	 * 
	 * @param i Nummer des Schlüssels.
	 * @param n Name des Schlüssels.
	 * @param desc Beschreibung des Schlüssels.
	 */
	public Key (int i, String n, String desc, Door d){
		super(i,n,desc);
		door = d;
	}
	
	/**
	 * Die Methode soll testen ob der Schlüssel zur Tür (@param door) passt.
	 */
	public boolean suits(){
		
		
		return false;
	}
	/**
	 * Soll angeben ob der SChlüssel beim Verwenden zerstört wurde.
	 * @return
	 */
	public boolean turn (){
		return true;
	}

}
