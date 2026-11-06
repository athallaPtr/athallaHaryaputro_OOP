package com.Atha.frontend.commands;

import com.Atha.frontend.objects.Player;
import com.Atha.frontend.systems.BulletManager;

public interface Command {
    void execute(Player player, BulletManager bulletManager);
}
