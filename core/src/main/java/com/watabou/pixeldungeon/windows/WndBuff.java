package com.watabou.pixeldungeon.windows;

import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Image;
import com.watabou.noosa.TextureFilm;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.BuffIndicator;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.ui.Window;

public class WndBuff extends Window{
    private static final int WIDTH		= 112;
    private static final int HEIGHT_P	= 160;
    private static final int HEIGHT_L	= 144;
    private static final String TXT_TITLE	= "Buffs";
    private BitmapText txtTitle;
    private SmartTexture icons;
    private TextureFilm film;
    private float pos = 16;
    private static final int GAP = 2;

    public WndBuff() {

        super();

        icons = TextureCache.get( Assets.BUFFS_LARGE );
        film = new TextureFilm( icons, 16, 16 );

        resize( WIDTH, PixelDungeon.landscape() ? HEIGHT_L : HEIGHT_P );

        txtTitle = PixelScene.createText( TXT_TITLE, 9 );
        txtTitle.hardlight( Window.TITLE_COLOR );
        txtTitle.measure();
        txtTitle.x = PixelScene.align( PixelScene.uiCamera, (WIDTH - txtTitle.width()) / 2 );
        add( txtTitle );

        for (Buff buff : Dungeon.hero.buffs()) {
            int index = buff.icon();
            if (index != BuffIndicator.NONE) {
                buffSlot(buff);
            }
        }
    }

    private void buffSlot( Buff buff ) {
        Image icon = new Image( icons );
        icon.frame( film.get( buff.icon() ) );
        icon.y = pos;
        add( icon );

        BitmapText txt = PixelScene.createText( buff.toString(), 8 );
        txt.x = icon.width + GAP;
        txt.y = pos + (int)(icon.height - txt.baseLine()) / 2;
        add( txt );

        pos += GAP + icon.height;
    }
}
