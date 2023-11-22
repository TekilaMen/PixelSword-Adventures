package com.mygdx.game;

public class Npc extends Character {

  private final String name;

  public Npc(
    String name,
    int hp,
    int attack,
    int defense,
    Objects stuff,
    String area
  ) {
    super(hp, attack, defense, stuff, area);
    this.name = name;
  }

  public String getName() {
    return name;
  }
}
