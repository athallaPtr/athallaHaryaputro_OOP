
package com.Atha.frontend.systems;

import com.Atha.frontend.objects.bullets.Bullet;

public class CollisionReferee {

    public void resolveCollisions(Player player, List<GameObject> entities, BulletManager bulletManager) {
        if (player == null || entities == null || bulletManager == null) return;

        List<GameObject> newDrops = new ArrayList<>();

        // Collision logic is divided into 3 stages
        //
        for (Bullet bullet : bulletManager.getActivePlayerBullets()) {
            if (bullet.isDestroyed()) continue;

            for (gameOb)
        }
        // TODO: Code for Question 3
        //
        // TODO: Code for Question 4
        //
        // TODO: Code for Question 5
        //
        // ...

        entities.addAll(newDrops);
    }

}
