/**
 * Diese Klasse realisiert eine Tür im MUD. Eine Tür hat zwei Räume, die sie
 * verbindet, und einen Zustand: 0=offen, 1=geschlossen, 2=verschlossen
 */
public class Door {

	/**
	 * Der erste Raum, der die Tür beinhaltet
	 */
	private Room room1;

	/**
	 * Der zweite Raum, der die Tür beinhaltet
	 */
	private Room room2;

	/**
	 * Der aktuelle Zustand dieser Tür: 0=offen, 1=geschlossen, 2=verschlossen
	 */
	private int mode;

	/**
	 * Der Konstruktor initalisiert alle oben angegeben Attribute. Er soll
	 * später von der Klasse Area aufgerufen werden, um die Räume zu verbinden.
	 * 
	 * @param room1
	 *            erster Raum
	 * @param room2
	 *            zweiter Raum
	 * @param mode
	 *            initialer Modus der Tür
	 */
	Door(Room room1, Room room2, int mode) {
		this.room1 = room1;
		this.room2 = room2;
		this.mode = mode;
	}

	/**
	 * Prüft ob die aktuelle Tür offen ist. Wird später von der Methode
	 * {@linkplain Person#walk(int)} genutzt.
	 * 
	 * @return {@code true} falls die Tür offen ist, sonst {@code false}
	 */
	public boolean allowToEnter() {
		return mode == 0;
	}

	/**
	 * Öffnet geschlossene Türen (mode=1->mode=0) und schließt offene Türen
	 * (mode=0->mode=1). Falls eine Tür verschlossen ist, bleibt dieser Zustand
	 * unverändert. Diese Methode arbeitet auch noch korrekt, falls die Zahl der
	 * Zustände später erweitert wird.
	 */
	public void use() {
		if (mode >= 0 && mode < 2) {
			mode = (mode + 1) % 2;
		}
	}

	public String stat() {
		String result = "Door between room " + room1.getId() + " and room "
				+ room2.getId() + ". It is ";
		switch (mode) {
		case 0:
			result += "open.";
			break;
		case 1:
			result += "closed.";
			break;
		case 2:
			result += "locked.";
			break;
		default:
			result += "UNDEFINED!";
			break;
		}
		return result;
	}
}
