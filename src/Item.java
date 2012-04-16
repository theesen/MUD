/**
 * Diese Klasse realisiert einen Gegenstand im MUD. Ein Gegenstand hat eine
 * Nummer, einen Namen, eine Beschreibung und Referenzen auf einen Raum und auf
 * eine Person. Ein Gegenstand kann sich in einem Raum oder im Besitz einer
 * Person befinden, aber nicht beides gleichzeitig. Initial existiert ein
 * Gegenstand aber einfach so, d.h. er befindet sich weder in einem Raum noch im
 * Besitz einer Person.
 * 
 */
public class Item extends NamedObject{

	/**
	 * Referenz auf den Raum, in dem sich der Gegenstand befindet. Ist
	 * {@code null}, falls sich der Gegenstand im Besitz einer Person befindet.
	 */
	private Room room;

	/**
	 * Referenz auf die Person, in dessen Besitz sich er Gegenstand befindet.
	 * Ist {@code null}, falls sich der Gegenstand in einem Raum befindet.
	 */
	private Person person;

	/**
	 * Erzeugt einen neuen Gegenstand mit den gegegebenen Werten.
	 * 
	 * @param i
	 *            die Nummer des neuen Gegenstands
	 * @param n
	 *            der Name des neuen Gegenstands
	 * @param desc
	 *            die Beschreibung des neuen Gegenstands
	 */
	public Item(int i, String n, String desc) {
		super(i, n, desc);
	}

	/**
	 * Gibt den Raum zurück, in dem sich dieser Gegenstand befindet.
	 * 
	 * @return der Raum, in dem sich dieser Gegenstand befindet
	 */
	public Room getRoom() {
		return room;
	}

	/**
	 * Transferiert diesen Gegenstand in den als Parameter gegebenen Raum. Das
	 * Attribut {@linkplain Item#room} wird entsprechend auf den neuen Raum
	 * gesetzt. Sollte dieser Gegenstand sich im Beutel einer Person befinden,
	 * dann wird er aus diesem entfernt und die Referenz auf diese Person wird
	 * gelöscht, d.h. auf {@code null} gesetzt. Befand sich der Gegenstand
	 * bereits in einem Raum, so wird er aus seinem alten Raum entfernt.
	 * Ausserdem wird er in die Itemliste des neuen Raums hinzugefügt, falls der
	 * übergebene Raum nicht {@code null} ist.
	 * 
	 * @param r
	 *            Referenz auf den Raum, in den dieser Gegenstand gesetzt werden
	 *            soll
	 */
	public void transfer(Room r) {
		if (room != null) {
			room.removeItem(this);
		}
		if (person != null) {
			person.removeItem(this);
		}
		person = null;
		room = r;
		if (room != null) {
			room.addItem(this);
		}
	}

	/**
	 * Versetzt diesen Gegenstand in den Besitz der als Parameter gegebenen
	 * Person. Es wird zunächst geprüft, ob dieser Parameter möglicherweise
	 * {@code null} ist. Wenn das so ist, bedeutet das, dass der Gegenstand
	 * keinen Besitzer mehr hat. Also wird die Referenz auf die Person gelöscht
	 * und die Methode beendet.
	 * 
	 * Es kann aus verschiedenen Gründen passieren, dass die Person den
	 * Gegenstand nicht aufnehmen kann. In diesem Fall passiert nichts weiter.
	 * Das Attribut {@link Item#person} wird entsprechend gesetzt und die
	 * Referenz auf den Raum wird gelöscht, d.h. auf {@code null} gesetzt.
	 * Möglicherweise befand sich dieser Gegenstand im Besitz einer anderen
	 * Person, daher wird er ggbfls. aus dem Beutel dieser Person gelöscht.
	 * 
	 * @param p
	 *            Referenz auf die Person, in deren Besitz dieser Gegenstand
	 *            übergehen soll
	 */
	public void setOwner(Person p) {
		if (p == null) {
			person = null;
			return;
		}
		if (p.addItem(this)) {
			if (person != null) {
				person.removeItem(this);
			}
			person = p;
			room = null;
		}
	}

	/**
	 * Gibt eine Zeichenkette mit Informationen über diesen Gegenstand zurück.
	 * Diese Informationen enthalten den Namen, die Nummer und die Beschreibung
	 * dieses Gegenstandes. Sollte eine Referenz auf einen Raum existieren, dann
	 * wird der Name und die Nummer dieses Raumes angehängt. Sollte eine
	 * Referenz auf eine Person existieren, dann wird der Name und die Nummer
	 * der Person angehängt.
	 * 
	 * @return eine Zeichenkette mit Informationen über diesen Gegenstand
	 */
	public String stat() {
		String result = getName() + " [" + getId() + "]\n" + getDesc() + "\n";
		if (room != null) {
			result += "in room: " + room.getName() + " [" + room.getId() + "]";
		}
		if (person != null) {
			result += "owned by: " + person.getName() + " [" + person.getId()
					+ "]";
		}
		return result;

	}

	/**
	 * Gibt eine Zeichenkette mit Informationen über diesen Gegenstand zurück.
	 * Diese Informationen enthalten den Namen, die Nummer und die Beschreibung
	 * dieses Gegenstandes.
	 * 
	 * @return eine Zeichenkette mit kurzen Informationen über diesen Gegenstand
	 */
	public String statSmall() {
		String result = getName() + " [" + getId() + "]\n" + getDesc() + "\n";
		return result;
	}


}