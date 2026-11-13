
package com.Atha.frontend.systems;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.Atha.frontend.objects.Player;
import com.Atha.frontend.objects.BulletType;
import com.Atha.frontend.objects.bullets.Bullet;
import com.Atha.frontend.objects.enemies.Boss;
import com.Atha.frontend.objects.enemies.Fairy;
import com.Atha.frontend.objects.items.Item;
import com.Atha.frontend.objects.items.ItemType;
import com.Atha.frontend.systems.AssetManager;

public class EntityFactory {

    // Create a Player and assign the 'player_idle' animation from AssetManager
    public static Player createPlayer(float x, float y, String name, int hp, int power, int spellCards) {
        Player player = new Player(x, y, name, hp, power, spellCards);
        Animation<TextureRegion> anim = AssetManager.getInstance().getAnimation("player_idle");
        player.setAnimation(anim);
        return player;
    }

    public static Fairy createFairy(float x, float y, String name, int hp) {
        Fairy fairy = new Fairy(x, y, name, hp);
        Animation<TextureRegion> anim = AssetManager.getInstance().getAnimation("fairy_idle_red");
        fairy.setAnimation(anim);
        return fairy;
    }

    public static Fairy createFairy(float x, float y, String name, int hp, String keyString) {
        Fairy fairy = new Fairy(x, y, name, hp);
        Animation<TextureRegion> idleAnim = AssetManager.getInstance().getAnimation(keyString);
        fairy.setAnimation(idleAnim);
        return fairy;
    }


    public static Boss createBoss(float x, float y, String name, int hp) {
        Boss boss = new Boss(x, y, name, hp);
        Animation<TextureRegion> anim = AssetManager.getInstance().getAnimation("boss_idle");
        boss.setAnimation(anim);
        return boss;
    }

    // Create an Item and assign its sprite from AssetManager based on ItemType
    public static Item createItem(float x, float y, ItemType itemType) {
        Item item = new Item(x, y, itemType);
        String key = switch (itemType) {
            case POWER -> "item_power";
            case POINT -> "item_point";
            case BOMB  -> "item_bomb";
            case LIFE  -> "item_life";
        };
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion(key);
        item.setSprite(sprite);
        return item;
    }

    public static Bullet createPlayerBullet(float x, float y, int damage, String spriteKey) {
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion(spriteKey);
        Bullet bullet = new Bullet(x, y, BulletType.AMULET, damage);
        bullet.setSprite(sprite);
        return bullet;
    }

    public static Bullet createPlayerBullet(float x, float y, int damage) {
        return createPlayerBullet(x, y, damage, "bullet_amulet");
    }

    // [MODIFY] Update the Module 8 createEnemyBullet method with the following changes:
    public static Bullet createEnemyBullet(float x, float y, int damage, String spriteKey) { // Add String spriteKey as a parameter
        String key = (spriteKey != null) ? spriteKey : "bullet_danmaku"; // Add local String variable key
        TextureRegion sprite = AssetManager.getInstance().getTextureRegion(key); // Change "bullet_danmaku" to the key variable
        Bullet bullet = new Bullet(x, y, 0f, BulletType.DANMAKU, damage);
        bullet.setSprite(sprite);
        return bullet;
    }

    // [ADDITION] Add the following 3-parameter overloaded method (delegating to the 4-parameter method above):
    public static Bullet createEnemyBullet(float x, float y, int damage) {
        return createEnemyBullet(x, y, damage, "bullet_danmaku");
    }

}
