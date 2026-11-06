package com.Atha.frontend.commands;

import com.Atha.frontend.objects.Player;
import com.Atha.frontend.systems.BulletManager;

public class FocusCommand implements Command {
    private final boolean focused;

    public FocusCommand(boolean focused) {
        this.focused = focused;
    }

    @Override
    public void execute(Player player, BulletManager bulletManager) {
        if (player != null) {
            player.setFocused(focused);
        }
    }
}
