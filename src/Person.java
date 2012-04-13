/**
 * Diese Klasse realisiert eine Person im MUD.
 * Eine Person hat eine Nummer, einen Namen, eine Beschreibung und
 * eine Referenz auf den Raum, in dem sich die Person befindet. 
 * Darüberhinaus hat eine Person einen Beutel, d.h. eine Liste
 * von Gegenständen, die sich in ihrem Besitz befinden können.
 * Initial befindet sich eine Person nicht in einem Raum.
 */
import java.util.ArrayList;

public class Person extends NamedObject{

	/**
	 * Referenz auf den Raum, in dem sich diese Person gerade befindet. Hat den
	 * Wert {@code null}, falls die Person sich in keinem Raum befindet.
	 */
	private Room room;

	/**
	 * Liste von Gegenständen, die die Person bei sich trägt.
	 */
	private ArrayList<Item> bag;

	/**
	 * Erzeugt eine neue Person mit den gegebenen Werten.
	 * 
	 * @param i
	 *            die Nummer der neuen Person
	 * @param n
	 *            der Name der neuen Person
	 * @param desc
	 *            die Beschreibung der neuen Person
	 */
	public Person(int i, String n, String desc) {
		super(i, n, desc);
		bag = new ArrayList<Item>();
	}

	/**
	 * Setzt diese Person in den als Parameter übergebenen Raum. Es wird hier
	 * nicht geprüft, ob der Parameter {@code null} ist. Der Verwender dieser
	 * Methode muss sicherstellen, dass ein gültiger Parameter übergeben wird.
	 * Nach erfolgtem Transfer in den neuen Raum wird dieser Person der
	 * Rückgabewert der Methode {@linkplain Room#look()} gesendet. Man kann den
	 * neuen Raum nur betreten, wenn dieser nicht voll ist
	 * {@linkplain Room#allowToEnter()}. Ausserdem muss die aktuelle Person aus
	 * der Liste ihres vorherigen Raums entfernt werden
	 * {@linkplain Room#removePerson(Person)} und genauso in die Liste des neuen
	 * Raums aufgenommen werden {@linkplain Room#addPerson(Person)}.
	 * 
	 * @param r
	 *            der Raum, in den diese Person gesetzt werden soll
	 */
	public void transfer(Room r) {
		if (r.allowToEnter()) {
			if (room != null) {
				room.removePerson(this);
			}
			room = r;
			room.addPerson(this);
			send(room.look());
		} else {
			send("You can not enter! The room is too crowded!");
		}
	}

	/**
	 * Gibt den Raum zurück, in dem sich diese Person befindet.
	 * 
	 * @return der Raum dieser Person
	 */

	public Room getRoom() {
		return room;
	}

	/**
	 * Gibt eine Zeichenkette mit Informationen über diese Person zurück. Die
	 * Informationen beinhalten den Namen, die Nummer und die Beschreibung
	 * dieser Person. Falls sich diese Person in einem Raum befindet, wird
	 * dessen Name und Nummer ebenfalls an die Zeichenkette angehängt. Falls die
	 * Person sich nicht in einem Raum befindet, wird "in room: none" angehängt.
	 * Es wird ebenfalls zeilenweise angehängt, welche Gegenstände sich im
	 * Besitz der Person befinden.
	 * 
	 * @return eine Zeichenkette mit detaillierten Informationen über diese
	 *         Person
	 */
	public String stat() {
		StringBuffer result = new StringBuffer();
		result.append(getName() + " [" + getId() + "]\n" + getDesc() + "\n");
		result.append("in room:");
		if (room == null) {
			result.append("none\n");
		} else {
			result.append(room.getName() + " [" + room.getId() + "]\n");
		}
		result.append("Bag with " + bag.size() + " item/s:\n");
		if (bag.size() == 0) {
			result.append("empty\n");
		}
		for (Item item : bag) {
			result.append(item.getName() + "\n");
		}
		return result.toString();
	}

	/**
	 * Gibt eine Zeichenkette mit Informationen über diese Person zurück. Die
	 * Informationen beinhalten den Namen, die Nummer und die Beschreibung
	 * dieser Person.
	 * 
	 * @return eine Zeichenkette mit verkürzten Informationen über diese Person
	 */
	public String statSmall() {
		String result = getName() + " [" + getId() + "]\n" + getDesc() + "\n";
		return result;
	}

	/**
	 * Realisiert das Gehen dieser Person in die durch den Parameter kodierte
	 * Richtung. 1 steht hier für Norden, 2 für Osten, 3 für Süden und 4 für
	 * Westen. Wenn diese Person sich nicht in einem Raum befindet, dann wird
	 * eine entsprechende Fehlermeldung an sie gesendet (es sollte eigentlich
	 * nicht passieren, dass sich eine Person außerhalb eines Raumes befindet).
	 * Sonst wird der Zielraum durch Aufruf der Methode
	 * {@linkplain Room#getTargetRoom(int)} ermittelt. Falls es keinen Raum in
	 * der angegebenen Richtung gibt, dann liefert dieser Aufruf {@code null}
	 * zurück und auch in diesem Fall wird eine Fehlermeldung an die Person
	 * gesendet. Existiert der Raum, wird durch die Methode
	 * {@linkplain Room#doorOpened(int)} geprüft, ob eine Tür existiert und ob
	 * sie offen ist. Wenn ja darf man endlich den Raum wechseln. Ansonsten wird
	 * wieder eine Fehlermeldung ausgegeben.
	 * 
	 * @param dir
	 *            die Richtung, in die diese Person gehen möchte
	 */
	public void walk(int dir) {
		if (dir < 1 || dir > 4) {
			send("That direction is unknown!");
			return;
		}
		if (room != null) {
			Room targetRoom = room.getTargetRoom(dir);
			if (targetRoom != null) {
				if (room.doorOpened(dir)) {
					transfer(targetRoom);
				} else {
					send("The door is closed.");
				}
			} else {
				send("You cannot go in that direction.");
			}
		} else {
			// Sicherheitsnetz, sollte eigentlich nicht passieren
			send("Since you are nowhere right now, you cannot go anywhere from here!");
		}
	}

	/**
	 * Gibt die übergebene Zeichenkette auf der Konsole aus.
	 * 
	 * @param message
	 *            die auszugebende Zeichenkette
	 */
	public void send(String message) {
		System.out.println(message);
	}

	/**
	 * Realsiert das Aufnehmen von Items aus dem Raum, in dem sich die aktuelle
	 * Person befindet. Dabei wird überprüft, ob der Beutel bereits voll ist.
	 * Dies ist er, wenn er fünf Items beinhaltet. Ist er voll, erscheint eine
	 * Fehlermeldung. Andernfalls wird das Item dem Inventar unserer Person
	 * hinzugefügt und aus der Liste der Items des Rooms entfernt. Falls sich
	 * der Gegenstand nicht im Raum befindet, wird eine entsprechende
	 * Fehlermeldung gesendet.
	 * 
	 * @param i
	 *            Das Item, das in die Tasche gepackt werden soll
	 */
	public void pickUp(Item item) {
		if (item.getRoom() != room) {
			send("You do not see that here!");
			return;
		}
		if (!addItem(item)) {
			send("Bag full!");
			return;
		}
		room.removeItem(item);
	}

	/**
	 * Realisiert das Wegwerfen von Items aus dem Beutel dieser Person. Wenn das
	 * angegebene Item in der Liste existiert, wird es also aus der Liste
	 * entfernt. Befindet sich die Person in einem Raum wird das Item wieder in
	 * den Raum gelegt. Wenn der Gegenstand nicht existiert, wird eine entsprechende
	 * Fehlermeldung gesendet. Die Referenz auf den Besitzer des Gegenstands wird gelöscht. 
	 * 
	 * @param i
	 *            Das Item, das aus der Tasche geworfen werden soll
	 */
	public void drop(Item item) {
		if (bag.indexOf(item) >= 0) {
			item.transfer(room);
		} else {
			send("You do not have that item!");
		}
	}

	/**
	 * Entfernt den gegebenen Gegenstand aus dem Beutel dieser Person.
	 * 
	 * @param item
	 *            der Gegenstand, der entfernt werden soll
	 */
	public void removeItem(Item item) {
		bag.remove(item);
	}

	/**
	 * Fügt den gegebenen Gegenstand in den Beutel dieser Person ein. Falls die
	 * maximale Anzahl von Gegenständen überschritten sein sollte, wird der
	 * Gegenstand nicht eingefügt.
	 * 
	 * @param item
	 *            der Gegenstand, der in den Beutel gepackt werden soll
	 * @return {@code true} falls der Gegenstand in den Beutel gelegt wurde,
	 *         {@code false} sonst
	 */
	public boolean addItem(Item item) {
		if (bag.size() >= 5) {
			return false;
		}
		bag.add(item);
		return true;
	}

}
