/**
 * Diese Klasse realisiert einen Bereich bzw. eine Region von Räumen innerhalb
 * des MUDs. Die Räume einer Area werden als zweidimensionales Gitter mit einer
 * festen Breite und einer ebenfalls festen Höhen verwaltet. Alle Räume der Area
 * werden im Konstruktor automatisch erzeugt und mit generierten Namen versehen.
 * 
 */
public class Area extends NamedObject{
	/**
	 * Die Breite des Raumgitters.
	 */
	private int width = 4;

	/**
	 * Die Höhe des Raumgitters.
	 */
	private int height = 4;
	/**
	 * Das eigentliche Raumgitter. Speichert Referenzen auf Räume in Form eines
	 * zweidimensionalen Arrays.
	 */
	private Room[][] rooms;

	/**
	 * Erzeugt eine neue Area mit den übergebenen Parameterwerten. Es wird ein
	 * zweidimensionales Array der Größe {@linkplain Area#width} mal
	 * {@linkplain Area#height} erzeugt und mit neu erzeugten Räumen gefüllt.
	 * Dazu werden zwei for-Schleifen geschachtelt. Im Rumpf der inneren
	 * Schleife wird für jeden Raum eine eindeutige Nummer erzeugt und einer
	 * lokalen Variable zugewiesen. Diese Nummer wird für den Namen des neuen
	 * Raumes verwendet. Die Beschreibung jedes Raumes enthält einen kurzen Text
	 * und die Koordinaten im Raumgitter. Nach der Initialisierung der Räume
	 * werden alle Räume mit Türen verbunden, außer sie befinden sich direkt am
	 * Rand.
	 * 
	 * @param i
	 *            die Nummer dieser Area
	 * @param n
	 *            der Name dieser Area
	 * @param desc
	 *            die Beschreibung dieser Area
	 */
	public Area(int i, String n, String desc) {
		super(i, n, desc);
		rooms = new Room[width][height];

		/*
		 * Erzeugung der Räume in Form eines Gitters der Größe width x height.
		 * Das Raumgitter wird dabei spaltenweise erzeugt.
		 */
		for (int x = 0; x < rooms.length; x++) {
			for (int y = 0; y < rooms[x].length; y++) {
				int roomId = x * rooms[x].length + y + 1;
				String roomName = "Room number " + roomId;
				String roomDesc = "This room is at position (" + (x + 1) + ", "
						+ (y + 1) + ") in the " + getName() + " area.";

				rooms[x][y] = new Room(roomId, roomName, roomDesc, x, y, this);
			}
		}

		/*
		 * Erzeugung der Türen Dies geschieht in mehreren Durchgängen, um den
		 * Vorgang überschaubar zu halten.
		 */

		/*
		 * Schritt 1: Es werden spaltenweise alle Türen in Richtungen Süden
		 * erzeugt. In jedem Schritt wird dann auch gleich die Tür in Richtung
		 * Norden erzeugt. Die letzte Zeile wird jeweils ausgelassen, da ihre
		 * Räume keine Ausgänge in Richtung Süden haben.
		 */
		for (int x = 0; x < rooms.length; x++) {
			for (int y = 0; y < rooms[x].length - 1; y++) {
				createDoor(rooms[x][y], 3, 0);
			}
		}

		/*
		 * Schritt 2: Es werden zeilenweise alle Türen in Richtung Osten
		 * erzeugt. In jedem Schritt wird dann auch gleich die Tür in Richtung
		 * Westen erzeugt. Die letzte Spalte wird jeweils ausgelassen, da ihre
		 * Räume keine Ausgänge in Richtung Osten haben.
		 */
		for (int y = 0; y < rooms[0].length; y++) {
			for (int x = 0; x < rooms.length - 1; x++) {
				createDoor(rooms[x][y], 2, 0);
			}
		}
	}

	/**
	 * Erzeugt im übergebenenen Raum eine neue Tür in der gegebenen
	 * Himmelsrichtung. Diese Tür hat den gegebenen Zustand. Da eine Tür immer
	 * zwei Räume verbindet, wird die neue Tür dem Zielraum ebenfalls
	 * hinzugefügt, allerdings in der entgegengesetzten Himmelsrichtung. Diese
	 * Methode funktioniert nur korrekt, wenn sie die Himmelsrichtungen 3 (für
	 * Süden) oder 2 (für Osten) übergeben bekommt. Da es sich hierbei um eine
	 * private Methode dieser Klasse handelt, wird hier nicht geprüft, ob die
	 * übergebene Himmelsrichtung korrekt ist.
	 * 
	 * @param room
	 *            der Raum, dem die neue Tür hinzugefügt werden soll
	 * @param dir
	 *            die Himmelsrichtung, in der die Tür sich im Raum befinden soll
	 *            (muss 2 oder 3 sein)
	 * @param mode
	 *            der Zustand der Tür (0=offen, 1=geschlossen, 2=verschlossen)
	 * 
	 */
	private void createDoor(Room room, int dir, int mode) {
		int inverseDir = 1;
		if (dir == 2) {
			inverseDir = 4;
		}
		Room targetRoom = room.getTargetRoom(dir);
		Door door = new Door(room, targetRoom, mode);
		room.addDoor(door, dir);
		targetRoom.addDoor(door, inverseDir);
	}

	/**
	 * Gibt die Breite des Raumgitters zurück.
	 * 
	 * @return die Breite des Raumgitters
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Gibt die Höhe des Raumgitters zurück.
	 * 
	 * @return die Höhe des Raumgitters
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Gibt die Referenz auf den Raum zurück, der sich im Raumgitter an den als
	 * Parametern gegebenen Koordinaten befindet. Falls die übergebenen
	 * Koordinaten außerhalb des Raumgitters liegen, wird {@code null}
	 * zurückgegeben.
	 * 
	 * @param row
	 *            die gewünschte Zeile im Raumgitter
	 * @param column
	 *            die gewünschte Spalte im Raumgitter
	 * @return Referenz auf den Raum, der sich an den übergebenen Koordinaten
	 *         befindet oder {@code null}, falls die Koordinaten außerhalb des
	 *         gültigen Bereichs liegen.
	 */
	public Room getRoomAt(int row, int column) {
		Room result;

		if (column >= 0 && column < rooms.length) {
			if (row >= 0 && row < rooms[column].length) {

				result = rooms[column][row];
			} else {
				result = null;
			}
		} else {
			result = null;
		}

		return result;
	}

	/**
	 * Gibt eine Zeichenkette mit Informationen über diese Area zurück. Diese
	 * Informationen beinhalten neben dem Namen, der Nummer und der Beschreibung
	 * der Area auch eine zeilenweise Beschreibung der einzelnen Räume dieser
	 * Area. Dazu wird in zwei geschachtelten for-Schleifen über das
	 * zweidimensionale Raumarray iteriert und für jeden Raum die Methode
	 * {@linkplain Room#look()} aufgerufen und deren Ergebnis jeweils an den
	 * Rückgabewert gehängt.
	 * 
	 * @return eine Zeichenkette mit Informationen über den Inhalt dieser Area
	 */
	public String stat() {
		String result = "Area: " + getName() + " [" + getId() + "]\n" + getDesc()
				+ "\n";
		for (int column = 0; column < rooms.length; column++) {
			for (int row = 0; row < rooms[column].length; row++) {
				result += rooms[column][row].look() + "\n";
			}
		}
		return result;
	}

}
