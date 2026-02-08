package com.pizenblues.pixeldungeon.windows;

import java.io.IOException;
import com.pizenblues.noosa.Game;
import com.pizenblues.pixeldungeon.PixelDungeon;
import com.pizenblues.pixeldungeon.scenes.BadgesScene;
import com.pizenblues.pixeldungeon.scenes.TitleScene;
import com.pizenblues.pixeldungeon.scenes.AboutScene;
import com.pizenblues.pixeldungeon.scenes.RankingsScene;
import com.pizenblues.pixeldungeon.ui.Icons;
import com.pizenblues.pixeldungeon.ui.SecondaryButton;
import com.pizenblues.pixeldungeon.ui.Window;
public class WndMenu extends Window{
    private static final String TXT_BADGES	= "Badges";
    private static final String TXT_RANKING	= "Rankings";
    private static final String TXT_SETTINGS	= "Settings";
    private static final String TXT_ABOUT	= "About";
    private static final String TXT_EXIT		= "Exit Game";
    private static final int WIDTH		= 120;
    private static final int BTN_HEIGHT	= 22;
    private static final int GAP		= 2;
    private int pos;

    public WndMenu() {
        super();
        SecondaryButton badgesButton;
        SecondaryButton rankingButton;
        SecondaryButton settingsButton;
        SecondaryButton aboutButton;
        SecondaryButton exitButton;

        addSecondaryButton( badgesButton = new SecondaryButton( TXT_BADGES ) {
            @Override
            protected void onClick() {
                hide();
                PixelDungeon.switchNoFade( BadgesScene.class );
            }
        } );
        badgesButton.icon(Icons.BOOK.get());

        addSecondaryButton( rankingButton = new SecondaryButton( TXT_RANKING ) {
            @Override
            protected void onClick() {
                hide();
                PixelDungeon.switchNoFade( RankingsScene.class );
            }
        } );
        rankingButton.icon(Icons.CHALLENGE_ON.get());

        addSecondaryButton( aboutButton = new SecondaryButton( TXT_ABOUT ) {
            @Override
            protected void onClick() {
                hide();
                PixelDungeon.switchNoFade( AboutScene.class );
            }
        } );
        aboutButton.icon(Icons.INFOICON.get());

        addSecondaryButton( settingsButton = new SecondaryButton( TXT_SETTINGS ) {
            @Override
            protected void onClick() {
                parent.add( new WndSettings( false ) );
            }
        } );
        settingsButton.icon(Icons.PREFS.get());

        addSecondaryButton( exitButton = new SecondaryButton( TXT_EXIT ) {
            @Override
            protected void onClick() {
                hide();
                if (Game.scene() instanceof TitleScene) {
                    Game.instance.finish();
                } else {
                    PixelDungeon.switchNoFade( TitleScene.class );
                }
            }
        } );
        exitButton.icon(Icons.CLOSE.get());

        resize( WIDTH, pos );
    }

    private void addSecondaryButton( SecondaryButton btn ) {
        add( btn );
        btn.setRect( 0, pos > 0 ? pos += GAP : 0, WIDTH, BTN_HEIGHT );
        pos += BTN_HEIGHT;
    }
}
