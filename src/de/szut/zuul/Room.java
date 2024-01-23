package de.szut.zuul;

import java.util.HashMap;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application.
 * "World of Zuul" is a very simple, text based adventure game.
 *
 * A "Room" represents one location in the scenery of the game. It is
 * connected to other rooms via exits. The exits are labelled north,
 * east, south, west. For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room {
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<String, Item> items;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * 
     * @param description The room's description.
     */
    public Room(String description) {
        this.description = description;
        this.exits = new HashMap<String, Room>();
        items = new HashMap<>();
    }

    public void setExit(String direction, Room neighbor) {
        this.exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return this.exits.get(direction);
    }

    /**
     * Define the exits of this room. Every direction either leads
     * to another room or is null (no exit there).
     * 
     * @param north The north exit.
     * @param east  The east east.
     * @param south The south exit.
     * @param west  The west exit.
     * @param up    The up exit.
     * @param down  The down exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room up, Room down) {
        if (north != null) {
            setExit("north", north);
        }
        if (east != null) {
            setExit("east", east);
        }
        if (south != null) {
            setExit("south", south);
        }
        if (west != null) {
            setExit("west", west);
        }
        if (up != null) {
            setExit("up", up);
        }
        if (down != null) {
            setExit("down", down);
        }

    }

    /**
     * @return The description of the room.
     */
    public String getDescription() {
        return description;
    }

    public String getItemsDescription() {
        StringBuilder itemsDescription = new StringBuilder();

        for (Item item : this.items.values()) {
            itemsDescription
                    .append("- " + item.getName() + ", " + item.getDescription() + ", " + item.getWeight() + "kg\n");
        }

        return itemsDescription.toString();
    }

    public String getLongDescription() {
        return this.description + ".\n" + "Items: " + getItemsDescription() + "Exits: " + exitsToString();
    }

    public String exitsToString() {
        StringBuilder exits = new StringBuilder("");
        if (getExit("north") != null)
            exits.append("north ");
        if (getExit("east") != null)
            exits.append("east ");
        if (getExit("south") != null)
            exits.append("south ");
        if (getExit("west") != null)
            exits.append("west ");
        if (getExit("up") != null)
            exits.append("up ");
        if (getExit("down") != null)
            exits.append("down ");
        return exits.toString();
    }

    public Room getExits(String direction) {
        return this.exits.get(direction);
    }

    public void putItem(String name, String description, double weight) {
        Item newItem = new Item(name, description, weight);
        this.items.put(name, newItem);
    }

    public Item getItem(String itemName) {
        return this.items.get(itemName);
    }

    public Item removeItem(String itemName) {
        return this.items.remove(itemName);
    }
}
