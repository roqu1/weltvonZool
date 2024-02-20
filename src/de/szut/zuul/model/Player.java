package de.szut.zuul.model;

import de.szut.zuul.exceptions.ItemNotFoundException;
import de.szut.zuul.exceptions.ItemTooHeavyException;
import de.szut.zuul.model.states.Injured;
import de.szut.zuul.model.states.State;

import java.util.LinkedList;

public class Player {
    private Room currentRoom;
    private double loadCapacity;
    private LinkedList<Item> items = new LinkedList<>();
    private State state;

    public Player() {
        this.loadCapacity = 10.0;
        this.state = Injured.getInstance();
    }

    public Room getRoom() {
        return this.currentRoom;
    }

    public void goTo(Room newRoom) {
        this.currentRoom = newRoom;
    }

    public void takeItem(Item item) throws ItemTooHeavyException {
        if (this.isTakePossible(item)) {
            this.items.add(item);
            showStatus();
        } else {
            throw new ItemTooHeavyException(item.getName() + " is too heavy!");
        }
    }

    public double calculateWeight() {
        double weight = 0.0;
        for (Item item : this.items) {
            weight += item.getWeight();
        }
        return weight;
    }

    public boolean isTakePossible(Item item) {
        return this.calculateWeight() + item.getWeight() <= this.loadCapacity;
    }

    public Item dropItem(String itemName) throws ItemNotFoundException {
        for (Item item : this.items) {
            if (item.getName().equals(itemName)) {
                this.items.remove(item);
                showStatus();
                return item;
            }
        }
        throw new ItemNotFoundException("You don't own this item!");
    }

    public String showStatus() {
        String status = "> Status of the player \n";
        status += "loadCapacity: " + this.loadCapacity + "kg \n";
        status += "taken items: ";
        for (Item item : this.items) {
            status += item.getName() + ", " + item.getWeight() + "kg \n";
        }
        status += "current load: " + this.calculateWeight() + "kg \n";
        status += "State: " + state.getClass().getSimpleName() + "\n";
        return status.toString();
    }
}
