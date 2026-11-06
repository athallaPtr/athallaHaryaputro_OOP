package com.Atha.frontend.systems;

import java.util.ArrayList;
import java.util.List;

import com.Atha.frontend.objects.GameObject;
import com.Atha.frontend.objects.Player;
import com.Atha.frontend.objects.bullets.Bullet;
import com.Atha.frontend.objects.enemies.Enemy;
import com.Atha.frontend.objects.items.Item;
import com.Atha.frontend.objects.items.ItemType;

public class CollisionReferee {

    public void resolveCollisions(Player player, List<GameObject> entities, BulletManager bulletManager) {
        if (player == null || entities == null || bulletManager == null) return;

        List<GameObject> newDrops = new ArrayList<>();

        for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
            if (bullet.isDestroyed()) continue;

            for (GameObject entity : entities) {
                if (entity instanceof Enemy enemy && !enemy.isDestroyed()) {
                    if (bullet.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                        boolean defeated = enemy.takeDamage(bullet.getDamage());

                        if (defeated) {
                            player.addScore(enemy.getScoreValue());
                            Item drop = EntityFactory.createItem(
                                enemy.getX(),
                                enemy.getY(),
                                ItemType.POWER
                            );
                            newDrops.add(drop);
                        }

                        bullet.destroy();
                        break;
                    }
                }
            }
        }

        // 2. Mediator checks Enemy Bullets vs Player (Core Hitbox vs Graze Hitbox)
        for (Bullet bullet : bulletManager.getActiveEnemyBullets()) {
            if (bullet.isDestroyed()) continue;

            if (!player.isDestroyed()) {
                // Priority A: Direct Core Hitbox Collision (Damage & Life Loss)
                if (bullet.getCoreHitbox().overlaps(player.getCoreHitbox())) {
                    if (!player.isInvulnerable()) {

                    // TODO 1: Wrap the contents of this block with an `if (!player.isInvulnerable())` check,
                    System.out.println("[CollisionReferee] CORE HIT! Enemy Bullet hit " + player.getName() + " for " + bullet.getDamage() + " DMG!");
                    // TODO 2: Replace `player.takeDamage(bullet.getDamage())` with `player.takeDamage(100, bulletManager, entities)`
                    bullet.destroy(); // Mark bullet for recycling by BulletManager
                    break;
                }
                // Priority B: Graze Hitbox Collision (Passes close to player without hitting core)
                else if (!bullet.hasBeenGrazed() && bullet.getCoreHitbox().overlaps(player.getGrazeHitbox())) {
                    bullet.setGrazed(true);
                    // TODO 4: Replace player.addScore(50) with a call to the player's addGraze() method
                    System.out.println("[CollisionReferee] GRAZE! " + player.getName() + " grazed an enemy bullet (+50 pts)!");
                }
            }
        }

        // 3. Mediator checks Player vs Items and Enemies (Core vs Graze)
        for (GameObject entity : entities) {
            // ... Item checking code from Module 7 remains the same ...
        } else if (entity instanceof Enemy enemy) {
            if (player.getCoreHitbox().overlaps(enemy.getCoreHitbox())) {
                // TODO 5: Wrap the contents of this block with `if (!player.isInvulnerable())`
                System.out.println("[CollisionReferee] CORE COLLISION! Player collided with " + enemy.getName() + "!");
                // TODO 6: Replace `player.takeDamage(10)` with `player.takeDamage(100, bulletManager, entities)`
            }
        }
    }
        entities.addAll(newDrops);
    }
}
