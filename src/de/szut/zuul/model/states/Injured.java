package de.szut.zuul.model.states;

public class Injured implements State {

  private static Injured instance = null;

  private Injured() {
  }

  public static Injured getInstance() {
    if (instance == null) {
      instance = new Injured();
    }
    return instance;
  }

  @Override
  public State heal() {
    return Healthy.getInstance();
  }

  @Override
  public State harm() {
    return Immobilized.getInstance();
  }

  @Override
  public State harmHeavy() {
    return Immobilized.getInstance();
  }

}
