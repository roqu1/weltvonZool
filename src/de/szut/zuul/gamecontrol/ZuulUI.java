package de.szut.zuul.gamecontrol;

import de.szut.zuul.exceptions.ItemNotFoundException;
import de.szut.zuul.exceptions.ItemTooHeavyException;

public class ZuulUI {

    /**
     * @param args
     * @throws ItemNotFoundException
     * @throws ItemTooHeavyException
     */
    public static void main(String[] args) throws ItemNotFoundException, ItemTooHeavyException {
        Game game = new Game();
        game.play();
    }
}
