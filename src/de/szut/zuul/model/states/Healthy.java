package de.szut.zuul.model.states;

public class Healthy implements State {
    private static Healthy instance = null;

    private Healthy() {

    }

    public static Healthy getInstance() {
        if (instance == null) {
            instance = new Healthy();
        }
        return instance;
    }

    @Override
    public State heal() {
        return this;
    }

    @Override
    public State harm() {
        return Injured.getInstance();
    }

    @Override
    public State harmHeavy() {
        return Immobilized.getInstance();
    }

}
