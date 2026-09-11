package com.Atha.frontend;

import com.badlogic.gdx.graphics.Color;

public class Enemy extends GameObject {
    protected String name;
    protected int hp;
    protected int maxHp;
    protected long scoreValue;

    public Enemy(String name, int hp) {
        super(200, 380, 24, 24, 0, Color.PINK);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = 100L;
    }

    public Enemy(float x, float y, float width, float height, Color color, String name, int hp, long scoreValue) {
        super(x, y, width, height, 0, color);
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.scoreValue = scoreValue;
    }

    public boolean takeDamage(int damage) {
        if (this.hp <= 0) {
            return false;
        }

        this.hp -= damage;
        if (this.hp <= 0) {
            this.hp = 0;
            System.out.println(name + " took " + damage + " damage! HP: " + this.hp + "/" + this.maxHp);
            System.out.println(name + " was defeated!");
            return true;
        }

        System.out.println(name + " took " + damage + " damage! HP: " + this.hp + "/" + this.maxHp);
        return false;
    }

    public void attack(Player player, int damage) {
        System.out.println(name + " unleashes bullet barrage on " + player.getName() + "!");
        player.takeDamage(damage);
    }

    public boolean isAlive() {
        return this.hp > 0;
    }

    public String getName() {return name;}
    public void setName(String name) { this.name = name: }

public int getHp()  -
