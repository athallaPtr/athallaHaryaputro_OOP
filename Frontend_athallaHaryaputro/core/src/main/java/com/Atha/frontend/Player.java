package com.Atha.frontend;

public class Player {
    public String name;
    public int hp;
    public int power;
    public int spellCards;

    public Player(String name, int hp, int power, int spellCards) {
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
    }

    public void takeDamage(int damage) {
        hp -= damage;

        if (hp < 0){
            hp = 0;
        }

        if (hp > 0){
            System.out.println(name + "took " + damage + " damage ! remaining HP: " + hp);
        } else {
            System.out.println(name + " was defeated !");
        }
    }

    public void shoot(Enemy target) {
        int damage = 10 + power;

        System.out.println(name + "shoots " + target.name + " dealing " + damage + " DMG!");

        targe.takeDamage(damage);
    }

    public boolean isAlive() {
        return hp > 0;
    }
}
