
/**
 * Die Klasse NamedObject ist die Superklasse für
 * 	Room, Person, Area, Item
 * 	@param 
 * @author Thees
 *
 */
public class NamedObject {
	/**
	 * Nummer des Objects
	 */
	private int id;
	/**
	 * Name des Objects
	 */
	private String name;
	/**
	 * Beschreibung des Objects
	 */
	private String description;
	
	/**
	 * Konstruktor für Superklasse.
	 * @param i Nummer des Objects
	 * @param n Name des Objects
	 * @param desc Beschreibung des Objects
	 */
	public NamedObject(int i, String n, String desc){
		id = i;
		name = n;
		description = desc;
	}
	/**
	 * Gibt die ID des Objects wieder.
	 * @return
	 */
	public int getId (){
		return id;
	}
	/**
	 * Gibt den Namen des Objects wieder.
	 * @return
	 */
	public String getName(){
		return name;
	}
	/**
	 * Gibt die Beschreibung des Objects wieder.
	 * @return
	 */
	public String getDesc(){
		return description;
	}

}
