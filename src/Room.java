/**
 * Diese Klasse realisiert einen Raum im MUD.
 * Ein Raum hat eine Nummer, einen Namen, eine Beschreibung, eine 
 * Referenz auf die Area, der er angehört und die Koordinaten
 * im Raumgitter dieser Area. Ein Raum kann immer nur genau einer Area angehören. 
 * Zusätzlich verwaltet ein Raum eine Liste von Personen, die sich in ihm befinden.
 *
 */
import java.util.ArrayList;

public class Room extends NamedObject{

	/**
	 * Die Zeile, in der sich dieser Raum im Raumgitter seiner Area befindet.
	 */
	private int row;

	/**
	 * Die Spalte, in der sich dieser Raum im Raumgitter seiner Area befindet.
	 */
	private int column;

	/**
	 * Referenz auf die Area, zu der dieser Raum gehört.
	 */
	private Area area;

	/**
	 * Eine Liste von Referenzen von Personen, die sich in diesem Raum befinden.
	 */
	private ArrayList<Person> persons;

	/**
	 * Maximale Anzahl von Personen, die sich gleichzeitig im Raum aufhalten
	 * dürfen. Standartwert ist hier 100 Personen.
	 */
	private int maxPersons = 100;

	/**
	 * Eine Liste von Referenzen von {@linkplain Item}s, die sich in diesem Raum
	 * befinden.
	 */
	private ArrayList<Item> items;

	/**
	 * Ein Array von Türen. An Index 0 befindet sich eine Tür nach Norden, an
	 * Index 1 nach Osten, an Index 2 nach Süden und an Index 3 nach Westen.
	 * Sollte keine Tür in der entsprechenden Himmelsrichtung existieren, so
	 * findet sich an dem korrespondierenden Index der Wert {@code null}.
	 */
	private Door[] doors;

	/**
	 * Erzeugt einen neuen Raum mit den gegebenen Werten für Nummer, Name und
	 * Beschreibung. Initialisiert zudem die Personen- und Itemliste und legt
	 * die Größe des Türen-Arrays auf 4 Elemente fest.
	 * 
	 * @param i
	 *            die Nummer des neuen Raumes
	 * @param n
	 *            der Name des neuen Raumes
	 * @param desc
	 *            die Beschreibung des neuen Raumes
	 */
	public Room(int i, String n, String desc) {
		super(i, n, desc);
		persons = new ArrayList<Person>();
		items = new ArrayList<Item>();
		doors = new Door[4];
	}

	/**
	 * Erzeugt einen neuen Raum mit den gegebenen Werten für Nummer, Name,
	 * Beschreibung, Koordinaten und die Area, zu der der neue Raum gehört.
	 * Initialisiert zudem die Personen- und Itemliste und legt die Größe des
	 * Türen-Arrays auf 4 Elemente fest.
	 * 
	 * @param i
	 *            die Nummer des neuen Raumes
	 * @param n
	 *            der Name des neuen Raumes
	 * @param desc
	 *            die Beschreibung des neuen Raumes
	 * @param x
	 *            die Spalte im Raumgitter der Area
	 * @param y
	 *            die Zeile im Raumgitter der Area
	 * @param theArea
	 *            die Area, zu der der neue Raum gehört
	 */
	public Room(int i, String n, String desc, int x, int y, Area theArea) {
		super(i, n, desc);
		row = y;
		column = x;
		area = theArea;
		persons = new ArrayList<Person>();
		items = new ArrayList<Item>();
		doors = new Door[4];
	}

	/**
	 * Erzeugt einen neuen Raum mit den gegebenen Werten für Nummer, Name,
	 * Beschreibung, Koordinaten, die Area, zu der der neue Raum gehört und die
	 * Anzahl der maximalen Personen im Raum. Initialisiert zudem die Personen-
	 * und Itemliste und legt die Größe des Türen-Arrays auf 4 Elemente fest.
	 * 
	 * @param i
	 *            die Nummer des neuen Raumes
	 * @param n
	 *            der Name des neuen Raumes
	 * @param desc
	 *            die Beschreibung des neuen Raumes
	 * @param x
	 *            die Spalte im Raumgitter der Area
	 * @param y
	 *            die Zeile im Raumgitter der Area
	 * @param theArea
	 *            die Area, zu der der neue Raum gehört
	 */
	public Room(int i, String n, String desc, int x, int y, Area theArea,
			int maxP) {
		super(i, n, desc);
		row = y;
		column = x;
		area = theArea;
		persons = new ArrayList<Person>();
		items = new ArrayList<Item>();
		maxPersons = maxP;
		doors = new Door[4];
	}

	/**
	 * Gibt eine Zeichenkette bestehend aus dem Namen und der Beschreibung
	 * dieses Raumes zurück. Außerdem wird die Liste von Personen und Items im
	 * Raum mithilfe einer Foreach-Schleife zurückgegeben.
	 * 
	 * @return eine Zeichenkette mit Namen und Beschreibung
	 */
	public String look() {
		StringBuffer result = new StringBuffer();
		result.append(getName() + "\n" + getDesc() + "\n");
		for (Person p : persons) {
			result.append(p.getName() + " is standing here.\n");
		}
		for (Item i : items) {
			result.append(i.getName() + " is here.\n");
		}
		return result.toString();
	}

	/**
	 * Gibt die Zeile dieses Raumes im Raumgitter zurück.
	 * 
	 * @return die Zeile dieses Raumes
	 */
	public int getRow() {
		return row;
	}

	/**
	 * Gibt die Spalte dieses Raumes im Raumgitter zurück.
	 * 
	 * @return die Spalte dieses Raumes
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * Gibt eine Referenz auf die Area zurück, zu der dieser Raum gehört.
	 * 
	 * @return die Area zu der dieser Raum gehört
	 */
	public Area getArea() {
		return area;
	}

	/**
	 * Ermittelt anhand des übergebenen Parameters den entsprechend benachbarten
	 * Raum des Raumgitters. Diese Methode wird von der Methode
	 * {@linkplain Person#walk(int)} benötigt, um die Person in den Zielraum
	 * setzen zu können. 1 steht hier für Norden, 2 für Osten, 3 für Süden und 4
	 * für Westen.
	 * 
	 * Anhand des Richtungsparameters werden die Koordinaten des gesuchten
	 * Raumes ausgerechnet und die Methode {@linkplain Area#getRoomAt(int, int)}
	 * mit diesen berechneten Koordinaten als Parameter aufgerufen. Das Ergebnis
	 * der Methode wird dann zurückgegeben. Falls der Parameter einen anderen
	 * als einen der obigen 4 Werte hat, wird dadurch der aktuelle Raum
	 * zurückgegeben.
	 * 
	 * @param dir
	 *            die Richtung kodiert als ganze Zahl
	 * @return den benachbarten Raum dieses Raums in der durch den Parameter
	 *         bestimmten Richtung oder {@code null} falls in der angegebenen
	 *         Richtung kein Raum existiert
	 */
	public Room getTargetRoom(int dir) {
		int row = getRow();
		int column = getColumn();

		if (dir == 1) {
			row--;
		} else if (dir == 2) {
			column++;
		} else if (dir == 3) {
			row++;
		} else if (dir == 4) {
			column--;
		}

		return area.getRoomAt(row, column);
	}

	/**
	 * Diese Methode prüft anhand der Personen im Raum (size()) und der Anzahl
	 * der maximal zugelassenen Personen, ob eine weitere Person diesen Raum
	 * betreten darf.
	 * 
	 * @return {@code true} falls die maximale Zahl von Personen noch nicht
	 *         erreicht ist, {@code false} sonst
	 */
	public boolean allowToEnter() {
		return persons.size() < maxPersons;
	}

	/**
	 * Diese Methode fügt eine Person in die Liste der Personen im Raum ein.
	 * Wird von {@linkplain Person#transfer(Room)} aufgerufen.
	 * 
	 * @param p
	 *            Person, die hinzugefügt werden soll
	 */
	public void addPerson(Person p) {
		persons.add(p);
	}

	/**
	 * Diese Methode löscht eine Person aus einem Raum. Zum Beispiel, wenn diese
	 * den Raum wechselt. Wird von {@linkplain Person#transfer(Room)}
	 * aufgerufen.
	 * 
	 * @param p
	 *            Person, die entfernt werden soll
	 */
	public void removePerson(Person p) {
		persons.remove(p);
	}

	/**
	 * Diese Methode fügt ein Item in die Liste der Items im Raum ein. Wird von
	 * {@linkplain Item#transfer(Room)} aufgerufen.
	 * 
	 * @param i
	 *            Item, das hinzugefügt werden soll
	 */
	public void addItem(Item i) {
		items.add(i);
	}

	/**
	 * Diese Methode löscht ein Item aus einem Raum. Zum Beispiel, wenn das Item
	 * aufgehoben wird. Wird von {@linkplain Item#transfer(Room)} und
	 * {@linkplain Person#pickUp(Item)} aufgerufen.
	 * 
	 * @param item
	 *            Item, das entfernt werden soll.
	 */
	public void removeItem(Item item) {
		items.remove(item);
	}

	/**
	 * Fügt die übergebene Tür in der gewünschten Himmelsrichtung zu diesem Raum
	 * hinzu. Dabei wird die Tür in das Array von Türen aufgenommen. Der Index
	 * richtet sich hier nach der übergebenen Himmelsrichtung. Sollte es schon
	 * eine Tür in dieser Himmelsrichtung übergeben, wird sie durch die neue Tür
	 * ersetzt.
	 * 
	 * @param d
	 *            Tür, die hinzugefügt werden soll
	 * @param dir
	 *            Himmelsrichtung, in der sich die Tür befindet
	 */
	public void addDoor(Door d, int dir) {
		doors[dir - 1] = d;
	}

	/**
	 * Gibt die Tür in der angegebenen Himmelsrichtung zurück.
	 * 
	 * @param dir
	 *            Himmelsrichtung der gewünschten Tür
	 * @return die Tür in der gewünschten Himmelsrichtung oder {@code null}
	 *         falls die Himmelsrichtung nicht bekannt ist oder es keine Tür in
	 *         dieser Himmelsrichtung gibt
	 */
	public Door getDoor(int dir) {
		if (dir > 0 || dir < 5) {
			return doors[dir - 1];
		}
		return null;
	}

	/**
	 * Prüft, ob eine Tür existiert und ob sie offen ist. Nur dann darf eine
	 * Person den Raum durch Gehen betreten.
	 * 
	 * @param dir
	 *            zu prüfende Himmmelsrichtung
	 * @return {@code true} falls es keine Tür in der übergebenen
	 *         Himmelsrichtung gibt, oder falls dort eine Tür gibt und sie offen
	 *         ist, sonst {@code false}
	 */
	public boolean doorOpened(int dir) {
		return (doors[dir - 1] != null && doors[dir - 1].allowToEnter());
	}
}
