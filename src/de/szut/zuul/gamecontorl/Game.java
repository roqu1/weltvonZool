package de.szut.zuul;

import de.szut.zuul.exceptions.ItemNotFoundException;
import de.szut.zuul.exceptions.ItemTooHeavyException;

/**
 * This class is the main class of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game. Users
 * can walk around some scenery. That's all. It should really be extended
 * to make it more interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all
 * rooms, creates the parser and starts the game. It also evaluates and
 * executes the commands that the parser returns.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game {
    private Parser parser;
    private Player player;

    /**
     * Create the game and initialise its internal map.
     */
    public Game() {
        player = new Player();
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms() {
        Room marketsquare, templePyramid, tavern, sacrificialSite, hut, jungle, secretPassage, cave, beach, wizardRoom,
                basement;

        // create the rooms
        marketsquare = new Room("on the market square");
        templePyramid = new Room("in a temple pyramid");
        tavern = new Room("in the tavern at the market square");
        sacrificialSite = new Room("at a sacrificial site");
        hut = new Room("in a hut");
        jungle = new Room("in the jungle");
        secretPassage = new Room("in a secret passage");
        cave = new Room("in a cave");
        beach = new Room("on the beach");
        wizardRoom = new Room("in the room of the wizard");
        basement = new Room("in the basement");

        // initialise room exits
        marketsquare.setExits(tavern, templePyramid, null, sacrificialSite, null, null); // Marktplatz
        templePyramid.setExits(hut, null, null, marketsquare, wizardRoom, basement); // Tempelpyramide
        tavern.setExits(null, hut, marketsquare, null, null, null); // Taverne
        sacrificialSite.setExits(null, marketsquare, null, null, null, null); // Opferstätte
        hut.setExits(null, jungle, templePyramid, tavern, null, null); // Wohnhütte
        jungle.setExits(null, null, null, hut, null, null); // Dschungel
        secretPassage.setExits(null, null, null, cave, null, null); // Geheimgang
        cave.setExits(null, secretPassage, beach, null, null, null); // Höhle
        beach.setExits(cave, null, null, null, null, null); // Strand
        wizardRoom.setExits(null, null, null, null, null, templePyramid); // Zimmer des Zauberers
        basement.setExits(null, null, null, secretPassage, templePyramid, null); // Keller

        player.goTo(marketsquare);// start game on marketsquare

        // add items to rooms
        marketsquare.putItem("Bogen", "Ein Bogen aus Holz", 0.5);
        cave.putItem("Schatz", "eine kleine Schatztruhe mit Münzen", 7.5);
        wizardRoom.putItem("Pfeile", "Ein Köcher mit diversen Pfeilen", 1.0);
        jungle.putItem("Pflanze", "eine Heilpflanze", 0.5);
        jungle.putItem("Kakao", "ein kleiner Kakaobaum", 5);
        sacrificialSite.putItem("Messer", "ein sehr scharfes, großes Messer", 1);
        hut.putItem("Speer", "ein Speer mit dazugehöriger Schleuder", 5.0);
        tavern.putItem("Nahrung", "ein Teller mit deftigem Fleisch und Maisbrei", 0.5);
        basement.putItem("Schmuck", "ein sehr hübscher Kopfschmuck", 1);
    }

    /**
     * Main play routine. Loops until end of play.
     * 
     * @throws ItemNotFoundException
     * @throws ItemTooHeavyException
     */
    public void play() throws ItemNotFoundException, ItemTooHeavyException {
        printWelcome();

        // Enter the main command loop. Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        printRoomInformation();
    }

    private void printRoomInformation() {
        System.out.println("You are " + player.getRoom().getLongDescription());
        System.out.println();
    }

    /**
     * Given a command, process (that is: execute) the command.
     * 
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     * @throws ItemNotFoundException
     * @throws ItemTooHeavyException
     */
    private boolean processCommand(Command command) throws ItemNotFoundException, ItemTooHeavyException {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        } else if (commandWord.equals("go")) {
            goRoom(command);
        } else if (commandWord.equals("look")) {
            look();
        } else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        } else if (commandWord.equals("take")) {
            takeItem(command);
            look();
        } else if (commandWord.equals("drop")) {
            dropItem(command);
            look();
        }

        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the
     * command words.
     */
    private void printHelp() {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the market square.");
        System.out.println();
        System.out.println("Your command words are:");
        System.out.println(parser.showCommands());
    }

    /**
     * Try to go in one direction. If there is an exit, enter
     * the new room, otherwise print an error message.
     */
    private void goRoom(Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = player.getRoom().getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        } else {
            player.goTo(nextRoom);
            printRoomInformation();
        }
    }

    /**
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * 
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) {
        if (command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        } else {
            return true; // signal that we want to quit
        }
    }

    private String look() { // Vielleicht nochmal überarbeiten
        String description = player.getRoom().getLongDescription();
        System.out.println(description);
        return description;
    }

    private void takeItem(Command command) throws ItemNotFoundException, ItemTooHeavyException {
        if (!command.hasSecondWord()) {
            System.out.println("What you want to take?");
            return;
        }
        String itemName = command.getSecondWord();
        Item item = player.getRoom().getItem(itemName);
        if (item == null) {
            throw new ItemNotFoundException("There is no item like this");
        } else {
            try {
                player.takeItem(item);
                System.out.println("You took the item!");
            } catch (ItemTooHeavyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private void dropItem(Command command) throws ItemNotFoundException {
        if (!command.hasSecondWord()) {
            System.out.println("What you want to drop?");
            return;
        }

        try {
            String itemName = command.getSecondWord();
            Item item = player.dropItem(itemName);
            player.getRoom().putItem(item.getName(), item.getDescription(), item.getWeight());
            System.out.println("You dropped the item!");

        } catch (ItemNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}
