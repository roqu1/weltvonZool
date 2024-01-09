package de.szut.zuul;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  The exits are labelled north, 
 * east, south, west.  For each direction, the room stores a reference
 * to the neighboring room, or null if there is no exit in that direction.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */
public class Room 
{
    public String description;
    public Room northExit;
    public Room southExit;
    public Room eastExit;
    public Room westExit;
    public Room upExit;
    public Room downExit;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
    }


    /**
     * Define the exits of this room.  Every direction either leads
     * to another room or is null (no exit there).
     * @param north The north exit.
     * @param east The east east.
     * @param south The south exit.
     * @param west The west exit.
     * @param up The up exit.
     * @param down The down exit.
     */
    public void setExits(Room north, Room east, Room south, Room west, Room up, Room down)
    {
        if(north != null) {
            northExit = north;
        }
        if(east != null) {
            eastExit = east;
        }
        if(south != null) {
            southExit = south;
        }
        if(west != null) {
            westExit = west;
        }
        if(up != null) {
            northExit = up;
        }
        if(down != null) {
            southExit = down;
        }
        if(up != null) {
            eastExit = up;
        }
        if(down != null) {
            westExit = down;
        }
    }

    /**
     * @return The description of the room.
     */
    public String getDescription()
    {
        return description;
    }

    public String exitsToString() {
        StringBuilder exits = new StringBuilder("");
        if(this.northExit!= null)
            exits.append("north");
        if(this.northExit!= null)
            exits.append("south");
        if(this.northExit!= null)
            exits.append("east");
        if(this.northExit!= null)
            exits.append("west");
        if(this.northExit!= null)
            exits.append("up");
        if(this.northExit!= null)
            exits.append("down");
        return exits.toString();
    }

    public Room getExit(String direction) {
        if(direction.equals("north")){
            return this.northExit;
        }
        if(direction.equals("south")){
            return this.southExit;
        }
        if(direction.equals("east")){
            return this.eastExit;
        }
        if(direction.equals("west")){
            return this.westExit;
        }
        if(direction.equals("up")){
            return this.upExit;
        }
        if(direction.equals("down")){
            return this.downExit;
        }
        return null;
    }
}
