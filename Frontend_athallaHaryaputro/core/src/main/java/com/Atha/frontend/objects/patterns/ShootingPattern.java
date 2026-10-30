
package com.Atha.frontend.objects.patterns;

import com.Atha.frontend.systems.BulletManager;

public interface ShootingPattern {
    void execute(float originX, float originY, BulletManager bulletManager, boolean isPlayer);
}
