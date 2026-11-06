package com.Atha.frontend.systems;

import com.Atha.frontend.objects.BulletType;
import com.Atha.frontend.objects.GameObject;
import com.Atha.frontend.objects.bullets.Bullet;
import com.Atha.frontend.objects.items.ItemType;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BulletManager {
    private Queue<Bullet> playerBulletPool;
    private Queue<Bullet> enemyBulletPool;

    private List<Bullet> activePlayerBullets;
    private List<Bullet> activeEnemyBullets;

    public BulletManager() {
        playerBulletPool = new LinkedList<>();
        enemyBulletPool = new LinkedList<>();
        activePlayerBullets = new ArrayList<>();
        activeEnemyBullets = new ArrayList<>();
    }

    public Bullet spawnPlayerBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;

        if (!playerBulletPool.isEmpty()) {
            bullet = playerBulletPool.poll();
        } else {
            bullet = EntityFactory.createPlayerBullet(x, y, damage);
        }

        bullet.init(x, y, vx, vy, BulletType.AMULET, damage, true);
        activePlayerBullets.add(bullet);
        return bullet;
    }

    public Bullet spawnEnemyBullet(float x, float y, float vx, float vy, int damage) {
        Bullet bullet;

        if (!enemyBulletPool.isEmpty()) {
            bullet = enemyBulletPool.poll();
        } else {
            bullet = EntityFactory.createEnemyBullet(x, y, damage);
        }

        bullet.init(x, y, vx, vy, BulletType.DANMAKU, damage, false);
        activeEnemyBullets.add(bullet);
        return bullet;
    }

    public void update(float delta, float screenWidth, float screenHeight) {
        Iterator<Bullet> playerIterator = activePlayerBullets.iterator();

        while (playerIterator.hasNext()) {
            Bullet bullet = playerIterator.next();
            bullet.update(delta);

            if (bullet.isDestroyed() || bullet.isOffScreen(screenWidth, screenHeight)) {
                bullet.destroy();
                playerIterator.remove();
                playerBulletPool.offer(bullet);
            }
        }

        Iterator<Bullet> enemyIterator = activeEnemyBullets.iterator();

        while (enemyIterator.hasNext()) {
            Bullet bullet = enemyIterator.next();
            bullet.update(delta);

            if (bullet.isDestroyed() || bullet.isOffScreen(screenWidth, screenHeight)) {
                bullet.destroy();
                enemyIterator.remove();
                enemyBulletPool.offer(bullet);
            }
        }
    }

    public void clearEnemyBullets(List<GameObject> entities) {
        Iterator<Bullet> iterator = activeEnemyBullets.iterator();

        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();

            if (entities != null) {
                entities.add(EntityFactory.createItem(bullet.getX(), bullet.getY(), ItemType.POINT));
            }

            bullet.destroy();
            iterator.remove();
            enemyBulletPool.offer(bullet);
        }
    }

    public void clearEnemyBullets() {
        clearEnemyBullets(null);
    }

    public void clearPlayerBullets() {
        Iterator<Bullet> iterator = activePlayerBullets.iterator();

        while (iterator.hasNext()) {
            Bullet bullet = iterator.next();
            bullet.destroy();
            iterator.remove();
            playerBulletPool.offer(bullet);
        }
    }

    public void render(SpriteBatch batch) {
        for (Bullet bullet : activePlayerBullets) {
            if (!bullet.isDestroyed()) {
                bullet.render(batch);
            }
        }

        for (Bullet bullet : activeEnemyBullets) {
            if (!bullet.isDestroyed()) {
                bullet.render(batch);
            }
        }
    }

    public List<Bullet> getActivePlayerBullets() {
        return activePlayerBullets;
    }

    public List<Bullet> getActiveEnemyBullets() {
        return activeEnemyBullets;
    }

    public int getPlayerPoolSize() {
        return playerBulletPool.size();
    }

    public int getEnemyPoolSize() {
        return enemyBulletPool.size();
    }
}
