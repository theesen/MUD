/**
 * Eine Klasse zum Testen der neu implementierten Methoden.
 */
public class Dungeon {

	private Area forest;

	private Person gandalf;

	private Person frodo;

	private Person sauron;

	private Item key;

	private Item shield;

	private Item helmet;

	private Item sword;

	private Item beer;

	private Item cookie;

	/**
	 * Diese Methode dient zum Aufruf der verschiedenen Tests.
	 * In blueJ kann sie direkt auf der Klasse aufgerufen werden!
	 * Die einzelnen Tests sind auskommentiert und sollten nur
	 * einzeln ausgeführt werden.
	 */
	public static void main(String[] args) {
		Dungeon d = new Dungeon();
		// d.testRooms();
		// d.testBonus();
		// d.testItems();
		// d.testBag();
		// d.testDoors();
	}

	/**
	 * Erzeugt ein Dungeon zum Testen. Es gibt eine Standard-Area, drei Personen
	 * und ein paar Gegenstände.
	 */
	public Dungeon() {
		forest = new Area(1, "Forest", "The Forest near Midgaard.");
		gandalf = new Person(1, "Gandalf", "A wizard.");
		frodo = new Person(2, "Frodo", "This creature has hairy feet!");
		sauron = new Person(
				3,
				"Sauron",
				"Not only ugly but also smelly. Not too friendly looking either. His shirt says: 'Keep your distance'.");
		key = new Item(1, "A small silver key",
				"This key is rather small but it looks as if it has been polished reecently.");
		shield = new Item(2, "A small shield",
				"This wooden shield is rather small. In a serious fight it wouldn't last long.");
		helmet = new Item(3, "A helmet",
				"A dented and small helmet, made from bronze.");
		sword = new Item(
				4,
				"A child's sword",
				"This small sword is made of rubber. Better not pick a fight with only this weapon equipped!");
		beer = new Item(5, "A bottle of beer",
				"It is a green glas bottle filled with a liquid. The label reads: Beck's beer.");
		cookie = new Item(6, "A cookie",
				"A tasty looking chocolate cookie with nuts and raisins.");
	}

	/**
	 * Tested den Beutel. Wie können Gegenstände den Besitzer wechseln? Erstmal
	 * über die Methode setOwner(). Hier wird also zunächst getestet, ob
	 * setOwner richtig funktioniert Danach werden die Methoden pickUp() und
	 * drop() getestet.
	 */
	public void testBag() {
		// cookie should have no owner and no room:
		System.out.println(cookie.stat());
		cookie.setOwner(sauron);
		// now cookie should be owned by sauron
		System.out.println(cookie.stat());
		hr();
		// and be in saurons bag:
		System.out.println(sauron.stat());
		hr();
		// give him also a shield:
		shield.setOwner(sauron);
		// now cookie should be owned by sauron
		System.out.println(shield.stat());
		hr();
		// and be in saurons bag:
		System.out.println(sauron.stat());
		hr();
		// now try to give 3 more items to sauron:
		helmet.setOwner(sauron);
		sword.setOwner(sauron);
		beer.setOwner(sauron);
		// and check his bag:
		System.out.println(sauron.stat());
		hr();
		// now try to give him the key:
		key.setOwner(sauron);
		// and check his bag (should still be 5 items in there)
		System.out.println(sauron.stat());
		hr();
		// stat gandalf (should have an empty bag):
		System.out.println(gandalf.stat());
		hr();
		// now give the cookie to gandalf:
		cookie.setOwner(gandalf);
		// stat both dudes:
		System.out.println(gandalf.stat());
		System.out.println(sauron.stat());
		hr();
		// check the cookie:
		System.out.println(cookie.stat());
		hr();
		// now give sauron the key
		key.setOwner(sauron);
		// and check:
		System.out.println(sauron.stat());
		hr();
		/* ************************
		 * Test pickUp and drop()************************
		 */
		// transfer sauron to start room
		Room startRoom = forest.getRoomAt(0, 0);
		sauron.transfer(startRoom);
		// now drop key, helmet, sword
		sauron.drop(key);
		sauron.drop(helmet);
		sauron.drop(sword);
		// look at the mess in the room
		System.out.println(startRoom.look());
		hr();
		// and check sauron's bag
		System.out.println(sauron.stat());
		hr();
		// try to drop the helmet again (should not work)
		sauron.drop(helmet);
		// now pickup the cookie (should not work, in possession of different
		// person in different room)
		sauron.pickUp(cookie);
		// now drop everything
		sauron.drop(shield);
		sauron.drop(beer);
		// now bag should be empty
		System.out.println(sauron.stat());
		hr();
		// and room filled with 5 items
		System.out.println(startRoom.look());
		hr();
		// Gandalf is currently not in any room, so let him drop the item
		gandalf.drop(cookie);
		// and check him and the cookie:
		System.out.println(gandalf.stat());
		System.out.println(cookie.stat());
		hr();
		// give the item back and transfer gandalf to the start room
		cookie.setOwner(gandalf);
		gandalf.transfer(startRoom);
		// now let sauron try to pick up gandalf's cookie (should not work!)
		sauron.pickUp(cookie);
		hr();
		// now let gandalf try to pick up all the items in the room
		gandalf.pickUp(shield);
		gandalf.pickUp(key);
		gandalf.pickUp(sword);
		gandalf.pickUp(beer);
		gandalf.pickUp(helmet); // should not work: bag full!
		// so check:
		System.out.println(gandalf.stat());
		hr();
		// so drop the cookie
		gandalf.drop(cookie);
		// let sauron pick it up
		sauron.pickUp(cookie);
		// and let gandalf get the helmet
		gandalf.pickUp(helmet);
		// room should be empty now:
		System.out.println(startRoom.look());
		hr();
		// and gandalf should have full bag but no cookie:
		System.out.println(gandalf.stat());
		hr();
		// and sauron should have a cookie:
		System.out.println(sauron.stat());
		// voila
	}

	/**
	 * Diese Methode testet die Erweiterung der Räume mit Listen von Personen.
	 * Dazu prüft sie, ob die Methode transfer() die Personen aus den Räumen
	 * entfernt und sie in die Zielräume einfügt. Außerdem wird geprüft, ob die
	 * Raumreferenz pro Person richtig gesetzt wird.
	 */
	public void testRooms() {
		Room startRoom = forest.getRoomAt(0, 0);
		// look in the room, should be empty
		System.out.println(startRoom.look());
		hr();
		// gandalf should be listed in the room
		gandalf.transfer(startRoom);
		hr();
		// gandalf and frodo should be listed in the room
		frodo.transfer(startRoom);
		hr();
		// get room to the east
		Room room5 = forest.getRoomAt(0, 1);
		// transfer frodo there, frodo should be listed in that room
		frodo.transfer(room5);
		hr();
		// check for the start room, only gandalf should be there
		System.out.println(startRoom.look());
		hr();
		// person list in rooms seem to be working...now check for person stat:
		// gandalf should be in room [1]
		System.out.println(gandalf.stat());
		// frodo should be in room [5]
		System.out.println(frodo.stat());
		// voila!!!
		hr();
	}

	/**
	 * Hier wird die Bonusaufgabe 1.1.1 getestet. Es wird ein isolierter Raum
	 * erzeugt, der keiner Area zugehört und daher unsinnige Koordinaten hat. Er
	 * erlaubt maximal zwei Personen. Es wird dann versucht, drei Personen
	 * mittels transfer() in den Raum zu bekommen.
	 */
	public void testBonus() {
		/*
		 * Create an isolated room, that is not part of an area, has silly
		 * coordinates and allows for a maximum of 2 persons
		 */
		Room theVoid = new Room(10000, "The Void",
				"You are floating in nothingness.", -1, -1, null, 2);
		// now put three persons in this room
		gandalf.transfer(theVoid);
		frodo.transfer(theVoid);
		// this should not work
		sauron.transfer(theVoid);
		hr();
		// check whether sauron is still in no room:
		System.out.println(sauron.stat());
		// voila
	}

	/**
	 * Diese Methode testet die Erweiterung der Räume mit Listen von
	 * Gegenständen. Dazu prüft sie, ob die Methode transfer() die Gegenstände
	 * aus den Räumen entfernt und sie in die Zielräume einfügt. Außerdem wird
	 * geprüft, ob die Raumreferenz pro Gegenstand richtig gesetzt wird.
	 */
	public void testItems() {
		// pick two random rooms
		Room room10 = forest.getRoomAt(1, 2); // room 10
		Room room7 = forest.getRoomAt(2, 1); // room 7

		// nothing should be there:
		System.out.println(room10.look());
		hr();
		// put a shield there:
		shield.transfer(room10);
		// check whether it is there:
		System.out.println(room10.look());
		// and whether the shield has the correct room reference:
		System.out.println(shield.stat());
		hr();
		// put a key there:
		key.transfer(room10);
		// check whether it is there:
		System.out.println(room10.look());
		// transfer the shield to the room number 7:
		shield.transfer(room7);
		// check whether its there:
		System.out.println(room7.look());
		// check whether its gone from room 10:
		System.out.println(room10.look());
		hr();
		// check the room references of both items:
		System.out.println(shield.stat()); // should reference room 7
		System.out.println(key.stat()); // should still reference room 10
		// voila!
	}

	/**
	 * Zunächst wird geprüft, ob die Türen alle richtig eingesetzt wurden. In
	 * einem ersten Schritt wird überprüft, ob die Räume in der ersten Spalte
	 * Türen in Richtung Westen haben, dann die Räume der letzten Spalte in
	 * Richtung Osten, der ersten Zeile in Richtung Norden und der letzten Zeile
	 * in Richtung Süden.
	 * 
	 * Dann werden die Räume der Reihe nach durchlaufen und geprüft, ob es
	 * jeweils nur eine Verbindungstür gibt.
	 * 
	 * Danach werden Türen geöffnet und geschlossen und die walk-Methoden
	 * ausprobiert.
	 */
	public void testDoors() {
		for (int y = 0; y < 4; y++) {
			Room r = forest.getRoomAt(y, 0);
			if (r.getDoor(4) != null) {
				System.out.println("Room " + r.look()
						+ " has a door to the west!");
			}
			r = forest.getRoomAt(y, 3);
			if (r.getDoor(2) != null) {
				System.out.println("Room " + r.look()
						+ " has a door to the east!");
			}
		}
		for (int x = 0; x < 4; x++) {
			Room r = forest.getRoomAt(0, x);
			if (r.getDoor(1) != null) {
				System.out.println("Room " + r.look()
						+ " has a door to the north!");
			}
			r = forest.getRoomAt(3, x);
			if (r.getDoor(3) != null) {
				System.out.println("Room " + r.look()
						+ " has a door to the south!");
			}
		}

		// check whether there is exactly one door for two adjacent rooms
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				Room room = forest.getRoomAt(y, x);
				for (int dir = 1; dir < 5; dir++) {
					Door door = room.getDoor(dir);
					Room targetRoom = room.getTargetRoom(dir);
					if (targetRoom != null) {
						if (door == null) {
							System.out.println("Door missing in room "
									+ room.look() + " in direction " + dir);
						} else {
							if (door != targetRoom.getDoor(inverseDir(dir))) {
								System.out
										.println("Not the same door in rooms "
												+ room.look() + " "
												+ targetRoom.look()
												+ " in direction " + dir);
							}
						}
					} else {
						if (door != null) {
							System.out
									.println("There is a door where should be none in room "
											+ room.look()
											+ " in direction "
											+ dir);
						}
					}
				}
			}
		}
		Room startRoom = forest.getRoomAt(0, 0);
		gandalf.transfer(startRoom);
		frodo.transfer(startRoom);
		// let gandalf walk east
		gandalf.walk(2);
		// now close the door to the west of gandalf's room
		Door door = gandalf.getRoom().getDoor(4);
		door.use();
		// let gandalf go west (should not work!)
		gandalf.walk(4);
		// let frodo try to go east (should not work!)
		frodo.walk(2);
		// now open the door to the east of frodo's room
		Door door2 = frodo.getRoom().getDoor(2);
		door2.use();
		frodo.walk(2);
		// let gandalf walk back
		gandalf.walk(4);
		// voila
	}

	/**
	 * Gibt ein paar Striche auf der Konsole gefolgt von einer Zeilenschaltung
	 * aus. Wird verwendet, um Ausgaben voneinander zu trennen und so
	 * übersichtlicher zu gestalten.
	 */
	private void hr() {
		System.out.println("---------------");
	}

	/**
	 * Gibt die umgekehrte Himmelsrichtung zu der gegebenen Himmelsrichtung
	 * zurück.
	 * 
	 * @param dir
	 *            die Richtung, zu der die entgegengesetzte Richtung gesucht
	 *            wird
	 * @return die der übergebenen Richtung entgegengesetzte Richtung oder
	 *         {@code -1} falls keine gültige Richtung übergeben wurde
	 */
	private int inverseDir(int dir) {
		switch (dir) {
		case 1:
			return 3;
		case 2:
			return 4;
		case 3:
			return 1;
		case 4:
			return 2;
		}
		return -1;
	}

}
