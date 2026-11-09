package com.Atha.frontend.objects;

import com.Atha.frontend.observers.GameObserver;
import com.Atha.frontend.objects.bullets.Bullet;
import com.Atha.frontend.objects.enemies.Enemy;
import com.Atha.frontend.objects.items.Item;
import com.Atha.frontend.objects.items.ItemType;
import com.Atha.frontend.objects.patterns.ShootingPattern;
import com.Atha.frontend.systems.AssetManager;
import com.Atha.frontend.systems.BulletManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Player extends GameObject {

    private String name;
    private int hp;
    private int lives = 3;
    private int power;
    private int spellCards;
    private long score;
    private int grazeCount = 0;
    private boolean focused = false;
    private float shootTimer = 0.1f;
    private float shootCooldown = 0.1f;
    private float invulnerableTimer = 0f;
    private int currentDir = 0;
    private ShootingPattern shootingPattern;
    private List<GameObserver> observers = new ArrayList<>();

    public Player(String name, int hp, int power, int spellCards) {
        super(280, 40, 32, 48, 200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    public Player(float x, float y, String name, int hp, int power, int spellCards) {
        super(x, y, 32, 48, 200f, Color.RED);
        this.name = name;
        this.hp = hp;
        this.power = power;
        this.spellCards = spellCards;
        this.score = 0;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        shootTimer += delta;

        if (invulnerableTimer > 0f) {
            invulnerableTimer -= delta;

            if (invulnerableTimer < 0f) {
                invulnerableTimer = 0f;
            }
        }
    }

    public void updateAnimationState(float dx) {
        AssetManager assets = AssetManager.getInstance();

        if (dx < 0) {
            if (currentDir != -1) {
                currentDir = -1;
                Animation<TextureRegion> animation =
                    assets.getAnimation("player_left");

                if (animation != null) {
                    setAnimation(animation);
                }
            }
        } else if (dx > 0) {
            if (currentDir != 1) {
                currentDir = 1;
                Animation<TextureRegion> animation =
                    assets.getAnimation("player_right");

                if (animation != null) {
                    setAnimation(animation);
                }
            }
        } else if (currentDir != 0) {
            currentDir = 0;
            Animation<TextureRegion> animation =
                assets.getAnimation("player_idle");

            if (animation != null) {
                setAnimation(animation);
            }
        }
    }

    public void move(float dx, float dy) {
        x += dx * speed;
        y += dy * speed;
        clampToPlayfield();
    }

    private void clampToPlayfield() {
        float minX = 32;
        float maxX = 416 - width;
        float minY = 16;
        float maxY = 560 - height;

        x = Math.max(minX, Math.min(x, maxX));
        y = Math.max(minY, Math.min(y, maxY));
    }

    public void moveUp(float delta) {
        move(0, delta);
    }

    public void moveDown(float delta) {
        move(0, -delta);
    }

    public void moveLeft(float delta) {
        move(-delta, 0);
    }

    public void moveRight(float delta) {
        move(delta, 0);
    }

    public void registerObserver(GameObserver observer) {
        if (observer != null && !observers.contains(observer)) {
            observers.add(observer);

            observer.onScoreChanged(score);
            observer.onHpChanged(hp);
            observer.onLivesChanged(lives);
            observer.onSpellCardsChanged(spellCards);
            observer.onPowerChanged(power);
            observer.onGrazeChanged(grazeCount);
        }
    }

    public void removeObserver(GameObserver observer) {
        observers.remove(observer);
    }

    private void notifyScoreChanged() {
        for (GameObserver observer : observers) {
            observer.onScoreChanged(score);
        }
    }

    private void notifyHpChanged() {
        for (GameObserver observer : observers) {
            observer.onHpChanged(hp);
        }
    }

    private void notifyLivesChanged() {
        for (GameObserver observer : observers) {
            observer.onLivesChanged(lives);
        }
    }

    private void notifySpellCardsChanged() {
        for (GameObserver observer : observers) {
            observer.onSpellCardsChanged(spellCards);
        }
    }

    private void notifyPowerChanged() {
        for (GameObserver observer : observers) {
            observer.onPowerChanged(power);
        }
    }

    private void notifyGrazeChanged() {
        for (GameObserver observer : observers) {
            observer.onGrazeChanged(grazeCount);
        }
    }

    @Override
    public Rectangle getCoreHitbox() {
        return new Rectangle(
            x + width / 2f - 4f,
            y + height / 2f - 4f,
            8f,
            8f
        );
    }

    @Override
    public Rectangle getGrazeHitbox() {
        return new Rectangle(x, y, width, height);
    }

    @Override
    public void render(SpriteBatch batch) {
        if (batch == null || !active || isDestroyed()) {
            return;
        }

        if (invulnerableTimer > 0f) {
            boolean visible = ((int) (invulnerableTimer * 16)) % 2 == 0;

            if (!visible) {
                return;
            }
        }

        if (focused) {
            batch.setColor(1f, 1f, 1f, 0.5f);
        }

        super.render(batch);

        if (focused) {
            batch.setColor(1f, 1f, 1f, 1f);
        }
    }

    public void renderFocusIndicator(ShapeRenderer shapeRenderer) {
        if (focused && shapeRenderer != null && active) {
            Rectangle core = getCoreHitbox();

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(Color.RED);

            shapeRenderer.circle(
                core.x + core.width / 2f,
                core.y + core.height / 2f,
                4f
            );

            shapeRenderer.setColor(Color.WHITE);

            shapeRenderer.circle(
                core.x + core.width / 2f,
                core.y + core.height / 2f,
                2f
            );

            shapeRenderer.end();
        }
    }

    @Override
    public void onCollision(Collidable other) {
        if (other instanceof Item item) {
            collectItem(item);
        }
    }

    public void shoot(Enemy target) {
        int damage = 10 + getPower();

        System.out.println(
            getName() + " shoots " + target.getName() +
                " dealing " + damage + " DMG!"
        );

        if (target.takeDamage(damage)) {
            addScore(target.getScoreValue());
        }
    }

    public void shootBullet(BulletManager bulletManager) {
        if (bulletManager == null || shootTimer < shootCooldown) {
            return;
        }

        if (shootingPattern != null) {
            shootingPattern.execute(
                x + width / 2f - 8f,
                y + height,
                bulletManager,
                true
            );
        } else {
            bulletManager.spawnPlayerBullet(
                x + width / 2f - 8f,
                y + height,
                0f,
                400f,
                10 + power
            );
        }

        shootTimer = 0f;
    }

    public void useBomb(BulletManager bulletManager, List<GameObject> entities) {
        if (spellCards > 0) {
            spellCards--;
            notifySpellCardsChanged();

            invulnerableTimer = 5f;

            if (bulletManager != null) {
                bulletManager.clearEnemyBullets(entities);
            }

            System.out.println(
                name + " unleashes SPELL CARD (Fantasy Seal)! " +
                    "All enemy bullets cleared!"
            );
        } else {
            System.out.println("No Spell Cards (Bombs) remaining!");
        }
    }

    public void useBomb(BulletManager bulletManager) {
        useBomb(bulletManager, null);
    }

    public void addGraze() {
        grazeCount++;
        addScore(50);
        notifyGrazeChanged();
    }

    public void takeDamage(int damage) {
        takeDamage(damage, null, null);
    }

    public void takeDamage(
        int damage,
        BulletManager bulletManager,
        List<GameObject> entities
    ) {
        if (isInvulnerable() || isDestroyed()) {
            if (isInvulnerable()) {
                System.out.println(
                    name + " is INVULNERABLE! (0 damage taken)"
                );
            }
            return;
        }

        hp -= damage;

        if (hp < 0) {
            hp = 0;
        }

        notifyHpChanged();

        System.out.println(
            name + " took " + damage +
                " damage! Remaining HP: " + hp
        );

        if (hp == 0) {
            System.out.println(name + " was defeated (Pichuun~)! ");
            loseLife(bulletManager, entities);
        }
    }

    public void loseLife(
        BulletManager bulletManager,
        List<GameObject> entities
    ) {
        lives--;
        notifyLivesChanged();

        if (bulletManager != null) {
            bulletManager.clearEnemyBullets();
        }

        if (lives > 0) {
            if (bulletManager != null) {
                System.out.println(
                    "[Player] " + name +
                        " lost a life! Remaining Lives: " + lives +
                        ". Respawning at (200, 50)..."
                );
            }

            respawn();
        } else {
            if (bulletManager != null) {
                System.out.println(
                    "[Player] GAME OVER! " + name +
                        " has run out of lives!"
                );
            }

            destroy();
        }
    }

    public void respawn() {
        active = true;
        x = 200;
        y = 50;
        hp = 100;
        invulnerableTimer = 3f;
        power = Math.max(10, power - 16);
        spellCards = 3;

        notifyHpChanged();
        notifyPowerChanged();
        notifySpellCardsChanged();
    }

    public void resetGame() {
        lives = 3;
        hp = 100;
        power = 15;
        spellCards = 3;
        score = 0;
        grazeCount = 0;
        active = true;
        x = 200;
        y = 50;
        invulnerableTimer = 3f;

        notifyLivesChanged();
        notifyHpChanged();
        notifyPowerChanged();
        notifySpellCardsChanged();
        notifyScoreChanged();
        notifyGrazeChanged();
    }

    public void collectItem(Item item) {
        if (item.isDestroyed()) {
            return;
        }

        ItemType type = item.getItemTypeEnum();

        if (type != null) {
            switch (type) {
                case POWER -> {
                    power += type.getPowerBonus();
                    notifyPowerChanged();
                    addScore(item.getScoreValue());

                    System.out.println(
                        name + " collected POWER item! Power increased to " +
                            power
                    );
                }

                case POINT -> {
                    addScore(item.getScoreValue());
                    System.out.println(name + " collected POINT item!");
                }

                case BOMB -> {
                    spellCards++;
                    notifySpellCardsChanged();
                    addScore(item.getScoreValue());

                    System.out.println(
                        name + " collected BOMB item! SpellCards: " +
                            spellCards
                    );
                }

                case LIFE -> {
                    lives++;
                    hp = 100;
                    notifyLivesChanged();
                    notifyHpChanged();
                    addScore(item.getScoreValue());

                    System.out.println(
                        name + " collected LIFE item! Remaining Lives: " +
                            lives
                    );
                }
            }
        } else {
            addScore(item.getScoreValue());
            System.out.println(
                name + " collected " + item.getItemType() + "!"
            );
        }

        item.destroy();
    }

    public void addScore(long points) {
        if (points > 0) {
            score += points;

            System.out.println(
                name + " gained " + points +
                    " pts! Total Score: " + score
            );

            notifyScoreChanged();
        }
    }

    public boolean isAlive() {
        return hp > 0 && lives > 0 && !isDestroyed();
    }

    public boolean isGameOver() {
        return lives <= 0;
    }

    public boolean isInvulnerable() {
        return invulnerableTimer > 0f;
    }

    public float getInvulnerableTimer() {
        return invulnerableTimer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(0, hp);
        notifyHpChanged();
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
        notifyLivesChanged();
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = power;
        notifyPowerChanged();
    }

    public int getSpellCards() {
        return spellCards;
    }

    public void setSpellCards(int spellCards) {
        this.spellCards = spellCards;
        notifySpellCardsChanged();
    }

    public long getScore() {
        return score;
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }

    public int getGrazeCount() {
        return grazeCount;
    }

    public ShootingPattern getShootingPattern() {
        return shootingPattern;
    }

    public void setShootingPattern(ShootingPattern shootingPattern) {
        this.shootingPattern = shootingPattern;
    }
}
