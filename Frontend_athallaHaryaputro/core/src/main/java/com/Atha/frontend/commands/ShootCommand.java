package com.Atha.frontend.commands;

import com.Atha.frontend.objects.Player;
import com.Atha.frontend.systems.BulletManager;

public class ShootCommand implements Command {
    @Override
    public void execute(Player player, BulletManager bulletManager) {
        if (player != null) {
            player.shootBullet(bulletManager);
        }
    }
}
