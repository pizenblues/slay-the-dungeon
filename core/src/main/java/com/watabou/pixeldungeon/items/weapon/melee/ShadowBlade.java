package com.watabou.pixeldungeon.items.weapon.melee;
import com.watabou.pixeldungeon.sprites.ItemSpriteSheet;

public class ShadowBlade extends MeleeWeapon{
    {
        name = "shadow blade";
        // update sprite!
        image = ItemSpriteSheet.SHADOWBLADE;
    }

    public ShadowBlade() {
        super( 1, 1.2f, 1f );
    }

    @Override
    public String desc() {
        return "A sharp little dagger, with a beautifully crafted handle.";
    }
}
