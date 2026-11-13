package com.Atha.frontend.objects.patterns.bulletStrategy;

import com.Atha.frontend.objects.bullets.Bullet;

public interface BulletMovementPattern {
    void move(Bullet bullet, float delta);
}
