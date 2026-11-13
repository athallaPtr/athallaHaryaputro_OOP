package com.Atha.frontend.objects.patterns.entityStrategy;

import com.Atha.frontend.objects.GameObject;

public interface EntityMovementPattern {
    void move(GameObject entity, float delta);
}
