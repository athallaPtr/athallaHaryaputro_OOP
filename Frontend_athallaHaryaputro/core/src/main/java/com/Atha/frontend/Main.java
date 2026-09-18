package com.Atha.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture image;

    powerItem = new Item(200, 450, 16, 16, 80f, ItemType.POWER, 500L);
    pointItem = new Item(320, 480, 12, 12, 120f, ItemType.POINT, 1000L);



    @Override
    public void create() {
        batch = new SpriteBatch();
        image = new Texture("libgdx.png");
    }

    @Override
    public void render() {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        batch.draw(image, 140, 210);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        image.dispose();
    }
}
// AABB Collision detection between every unique entity pair
for (int i = 0; i < entities.size(); i++) {
    for (int j = i + 1; j < entities.size(); j++) {
GameObject a = entities.get(i);
GameObject b = entities.get(j);

// TODO: Check whether getCoreHitbox() of a and b overlap (use the .overlaps() method of Rectangle)
// TODO: Call a.onCollision(b) and b.onCollision(a)
    }
        }
