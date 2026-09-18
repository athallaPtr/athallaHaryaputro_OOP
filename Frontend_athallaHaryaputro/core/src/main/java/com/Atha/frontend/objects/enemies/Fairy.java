package com.Atha.frontend.objects.enemies;

import com.badlogic.gdx.graphics.Color;
import com.Atha.frontend.objects.Collidable;
import com.Atha.frontend.objects.Player;

public class Fairy extends Enemy {
    public Fairy(String name, int hp) {
        super(150, 380, 24, 24, Color.PINK, name, hp, 500L);
    }

    public Fairy(float x, float y, String name, int hp) {
        super(x, y, 24, 24, Color.PINK, name, hp, 500L);
    }
}

@Override
public void update(float delta) {
    this.x += speed * delta;
    if (this.x > 600 || this.x < 0) {
        this.speed = -this.speed;
    }
}

@Override
public void onCollision(Collidable other) {
    if (other instanceof Player) {
        System.out.println("Player touches fairy");
    }
}
