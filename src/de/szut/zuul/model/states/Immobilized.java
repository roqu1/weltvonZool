package de.szut.zuul.model.states;

public class Immobilized implements State {

  private static Immobilized instance = null;

  private Immobilized() {
  }

  public static Immobilized getInstance() {
    if (instance == null) {
      instance = new Immobilized();
    }
    return instance;
  }

  @Override
  public State heal() {
    return Injured.getInstance();
  }

  @Override
  public State harm() {
    return this;
  }

  @Override
  public State harmHeavy() {
    return this;
  }
}
