package com.watabou.pixeldungeon.windows;

import com.watabou.noosa.Game;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.scenes.InterlevelScene;
import com.watabou.pixeldungeon.ui.Icons;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.pixeldungeon.ui.Window;

import java.io.IOException;

public class WindGameOver extends Window {
    private static final String TXT_START		= "Start New Game";
    private static final int WIDTH		= 120;

    public WindGameOver(){
        super();
        RedButton btnRestart = new RedButton( TXT_START ){
            @Override
            protected void onClick() {
                Dungeon.hero = null;
                PixelDungeon.challenges( Dungeon.challenges );
                InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
                InterlevelScene.noStory = true;
                Game.switchScene( InterlevelScene.class );
            }
        };

        btnRestart.icon( Icons.get( Dungeon.hero.heroClass ) );
        add(btnRestart);
    }
}
