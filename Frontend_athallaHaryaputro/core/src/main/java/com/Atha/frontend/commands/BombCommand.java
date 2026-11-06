package com.Atha.frontend.commands;

import com.Atha.frontend.objects.GameObject;
import com.Atha.frontend.objects.Player;
import com.Atha.frontend.systems.BulletManager;

import java.util.List;

public class BombCommand implements Command {
    private List<GameObject> entities;

    public BombCommand() {
    }

    public BombCommand(List<GameObject> entities) {
        this.entities = entities;
    }

    public void setEntities(List<GameObject> entities) {
        this.entities = entities;
    }

    @Override
    public void execute(Player player, BulletManager bulletManager) {
        if (player != null) {
            player.useBomb(bulletManager, entities);
        }
    }
}
