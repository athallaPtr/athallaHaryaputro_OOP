package com.Atha.frontend;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

import com.Atha.frontend.objects.GameObject;
import com.Atha.frontend.objects.Player;
import com.Atha.frontend.objects.enemies.Boss;
import com.Atha.frontend.objects.enemies.Fairy;
import com.Atha.frontend.objects.items.Item;
import com.Atha.frontend.objects.items.ItemType;

import com.Atha.frontend.systems.AssetManager;
import com.Atha.frontend.systems.BulletManager;
import com.Atha.frontend.systems.CollisionReferee;
import com.Atha.frontend.systems.EntityFactory;

import com.Atha.frontend.commands.Command;
import com.Atha.frontend.commands.InputHandler;
import com.Atha.frontend.ui.GameHUD;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private BulletManager bulletManager;
    private CollisionReferee collisionReferee;
    private Player player;
    private List<Fairy> fairy;
    private Boss boss;
    private Item powerItem;
    private Item pointItem;
    private List<GameObject> entities;

    private InputHandler inputHandler;
    private GameHUD gameHUD;

    private float enemyShootTimer = 0f;

    @Override
    public void create() {
        batch = new SpriteBatch();

        AssetManager.getInstance().init();

        entities = new ArrayList<>();
        fairy = new ArrayList<>();

        player = EntityFactory.createPlayer(
            200, 50, "Reimu Hakurei", 100, 15, 3
        );

        fairy.add(
            EntityFactory.createFairy(150, 380, "Red Fairy", 20)
        );

        fairy.add(
            EntityFactory.createFairy(
                250, 380, "Blue Fairy", 20, "fairy_idle_blue"
            )
        );

        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);

        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.add(player);
        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);

        bulletManager = new BulletManager();
        collisionReferee = new CollisionReferee();


        inputHandler = new InputHandler();
        gameHUD = new GameHUD();

        player.registerObserver(gameHUD);

        inputHandler.setEntities(entities);
    }

    public <T extends GameObject> void updateAndClean(
        List<T> list,
        float delta,
        float screenWidth,
        float screenHeight
    ) {
        Iterator<T> iterator = list.iterator();

        while (iterator.hasNext()) {
            T entity = iterator.next();
            entity.update(delta);

            if (
                entity.isDestroyed() ||
                    entity.isOffScreen(screenWidth, screenHeight)
            ) {
                iterator.remove();
            }
        }
    }

    private void updateEnemyScheduler(float delta) {
        enemyShootTimer += delta;

        if (enemyShootTimer >= 1.5f) {
            boss.shootBullet(bulletManager);
            enemyShootTimer = 0f;
        }
    }

    private void restartGame() {
        System.out.println("[Main] Restarting game...");

        entities.clear();

        bulletManager.clearEnemyBullets();
        bulletManager.clearPlayerBullets();

        entities.add(player);
        player.resetGame();

        fairy.clear();

        fairy.add(
            EntityFactory.createFairy(150, 380, "Red Fairy", 20)
        );

        fairy.add(
            EntityFactory.createFairy(
                250, 380, "Blue Fairy", 20, "fairy_idle_blue"
            )
        );

        boss = EntityFactory.createBoss(250, 400, "Rumia", 150);

        powerItem = EntityFactory.createItem(180, 450, ItemType.POWER);
        pointItem = EntityFactory.createItem(220, 480, ItemType.POINT);

        entities.addAll(fairy);
        entities.add(boss);
        entities.add(powerItem);
        entities.add(pointItem);

        inputHandler.setEntities(entities);

        enemyShootTimer = 0f;
    }

    @Override
    public void render() {
        float delta = Gdx.graphics.getDeltaTime();

        // 0. Restart Game when Game Over
        if (
            Gdx.input != null &&
                Gdx.input.isKeyJustPressed(Input.Keys.R) &&
                player.isGameOver()
        ) {
            restartGame();
        }

        // 1. Process Player Input through Command Pattern
        List<Command> commands = inputHandler.handleInput(delta);

        for (Command command : commands) {
            command.execute(player, bulletManager);
        }

        // 2. Boss periodic shooting scheduler
        updateEnemyScheduler(delta);

        // 3. Update entities within Playfield bounds
        updateAndClean(entities, delta, 416, 560);

        // 4. Update and recycle bullets within Playfield bounds
        bulletManager.update(delta, 416, 560);

        // 5. Evaluate collisions through CollisionReferee
        collisionReferee.resolveCollisions(
            player,
            entities,
            bulletManager
        );

        // 6. Clear the screen
        ScreenUtils.clear(0.1f, 0.1f, 0.15f, 1f);

        // 7. Render entities, bullets, and HUD text
        batch.begin();

        for (GameObject entity : entities) {
            if (!entity.isDestroyed()) {
                entity.render(batch);
            }
        }

        bulletManager.render(batch);

        gameHUD.render(batch);

        batch.end();

        // 8. Render UI borders and Focus Mode indicator
        gameHUD.renderBackground();

        player.renderFocusIndicator(gameHUD.getShapeRenderer());
    }

    @Override
    public void dispose() {
        if (batch != null) {
            batch.dispose();
        }

        if (gameHUD != null) {
            gameHUD.dispose();
        }

        AssetManager.getInstance().dispose();
    }
}
