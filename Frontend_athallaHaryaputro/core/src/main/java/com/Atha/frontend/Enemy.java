package com.Atha.frontend;

public class Enemy {
    public String name;
    public int hp;
    public int maxHp;

    public Enemy(String name, int hp) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
    }

    public void takeDamage(int damage) {
        // 1. Reduce hp by the damage value.
        hp -= damage;

        if (hp < 0) {
            hp = 0;
        }

        System.out.println(name + "took " + damage + " damage ! remaining HP: " + hp + "/" + maxHp);

        if (hp == 0) {
            System.out.println(name + " was defeated ");
        }
    }

    public void attack (Player player, int damage){
        System.out.println(name + "unleashes bullet barrage on  " + player.name + "!");

        player.takeDamage(damage);
    }
    public boolean isAlive() {
        return hp > 0;
    }
}
