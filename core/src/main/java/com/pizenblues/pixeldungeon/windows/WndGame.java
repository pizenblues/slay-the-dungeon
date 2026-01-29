/*
 * Pixel Dungeon
 * Copyright (C) 2012-2015 Oleg Dolya
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package com.pizenblues.pixeldungeon.windows;

import java.io.IOException;

import com.pizenblues.noosa.Game;
import com.pizenblues.pixeldungeon.Dungeon;
import com.pizenblues.pixeldungeon.PixelDungeon;
import com.pizenblues.pixeldungeon.scenes.GameScene;
import com.pizenblues.pixeldungeon.scenes.InterlevelScene;
import com.pizenblues.pixeldungeon.scenes.RankingsScene;
import com.pizenblues.pixeldungeon.scenes.TitleScene;
import com.pizenblues.pixeldungeon.ui.Icons;
import com.pizenblues.pixeldungeon.ui.RedButton;
import com.pizenblues.pixeldungeon.ui.SecondaryButton;
import com.pizenblues.pixeldungeon.ui.Window;

public class WndGame extends Window {
	private static final String TXT_SETTINGS	= "Settings";
	private static final String TXT_CHALLEGES	= "Challenges";
	private static final String TXT_RANKINGS	= "Rankings";
	private static final String TXT_START		= "Start New Game";
	private static final String TXT_MENU		= "Main Menu";
	private static final String TXT_EXIT		= "Exit Game";
	private static final String TXT_RETURN		= "Return to Game";
	private static final String TXT_JOURNAL		= "Journal";
	private static final String TXT_CATALOG		= "Catalog";

	private static final int WIDTH		= 120;
	private static final int BTN_HEIGHT	= 20;
	private static final int GAP		= 2;
	
	private int pos;
	
	public WndGame() {
		
		super();
		SecondaryButton settingsButton;
		SecondaryButton catalogButton;
		SecondaryButton journalButton;
		SecondaryButton rankingButton;
		RedButton resumeButton;

		addSecondaryButton( settingsButton = new SecondaryButton( TXT_SETTINGS ) {
			@Override
			protected void onClick() {
				hide();
				GameScene.show( new WndSettings( true ) );
			}
		} );
		settingsButton.icon(Icons.PREFS.get());

		addSecondaryButton( catalogButton = new SecondaryButton( TXT_CATALOG ) {
			@Override
			protected void onClick() {
				hide();
				GameScene.show( new WndCatalogus() );
			}
		} );
		catalogButton.icon(Icons.BACKPACK.get());

		addSecondaryButton( journalButton = new SecondaryButton( TXT_JOURNAL ) {
			@Override
			protected void onClick() {
				hide();
				GameScene.show( new WndJournal() );
			}
		} );
		journalButton.icon(Icons.HELP.get());

		addButtons(
			new SecondaryButton( TXT_MENU ) {
				@Override
				protected void onClick() {
					try {
						Dungeon.saveAll();
					} catch (IOException e) {
						// Do nothing
					}
					Game.switchScene( TitleScene.class );
				}
			}, new SecondaryButton( TXT_EXIT ) {
				@Override
				protected void onClick() {
					Game.instance.finish();
				}
			}
		);

		if (!Dungeon.hero.isAlive()) {
			addSecondaryButton( new SecondaryButton( TXT_RANKINGS ) {
				@Override
				protected void onClick() {
					InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
					Game.switchScene( RankingsScene.class );
				}
			} );

			RedButton btnStart;
			addButton( btnStart = new RedButton( TXT_START ) {
				@Override
				protected void onClick() {
					Dungeon.hero = null;
					PixelDungeon.challenges( Dungeon.challenges );
					InterlevelScene.mode = InterlevelScene.Mode.DESCEND;
					InterlevelScene.noStory = true;
					Game.switchScene( InterlevelScene.class );
				}
			} );
			btnStart.icon( Icons.get( Dungeon.hero.heroClass ) );
		}
		
		addButton( resumeButton = new RedButton( TXT_RETURN ) {
			@Override
			protected void onClick() {
				hide();
			}
		} );
		resumeButton.icon(Icons.EXIT.get());
		
		resize( WIDTH, pos );
	}
	
	private void addButton( RedButton btn ) {
		add( btn );
		btn.setRect( 0, pos > 0 ? pos += GAP : 0, WIDTH, BTN_HEIGHT );
		pos += BTN_HEIGHT;
	}

	private void addSecondaryButton( SecondaryButton btn ) {
		add( btn );
		btn.setRect( 0, pos > 0 ? pos += GAP : 0, WIDTH, BTN_HEIGHT );
		pos += BTN_HEIGHT;
	}
	
	private void addButtons( SecondaryButton btn1, SecondaryButton btn2 ) {
		add( btn1 );
		btn1.setRect( 0, pos > 0 ? pos += GAP : 0, (WIDTH - GAP) / 2, BTN_HEIGHT );
		add( btn2 );
		btn2.setRect( btn1.right() + GAP, btn1.top(), WIDTH - btn1.right() - GAP, BTN_HEIGHT );
		pos += BTN_HEIGHT;
	}
}
