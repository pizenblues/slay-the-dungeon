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
package com.watabou.pixeldungeon.windows;

import com.watabou.input.Touchscreen.Touch;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Game;
import com.watabou.noosa.TouchArea;
import com.watabou.pixeldungeon.Chrome;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.Window;
import com.watabou.utils.SparseArray;

public class WndStory extends Window {
	private static final int WIDTH = 120;
	private static final int MARGIN = 6;
	private static final float bgR	= 0.90f;
	private static final float bgG	= 0.90f;
	private static final float bgB	= 0.90f;
	public static final int ID_SEWERS		= 0;
	public static final int ID_PRISON		= 1;
	public static final int ID_CAVES		= 2;
	public static final int ID_METROPOLIS	= 3;
	public static final int ID_HALLS		= 4;
	private static final SparseArray<String> CHAPTERS = new SparseArray<String>();
	
	static {
		CHAPTERS.put( ID_SEWERS, 
		"Since Eden left your party, you have wondered around looking for answers on his whereabouts. " +
		"All the clues led you to this dungeons. Nobody was able to give you an answer about " +
		"how deep this place really goes." );
		
		CHAPTERS.put( ID_PRISON,
		// ADD DESCRIPTION
		"Many years ago an underground prison was built here for the most dangerous criminals. At the time it seemed " +
		"like a very clever idea, because this place indeed was very hard to escape. But soon dark miasma started to permeate " +
		"from below, driving prisoners and guards insane. In the end the prison was abandoned, though some convicts " +
		"were left locked up here." );
		
		CHAPTERS.put( ID_CAVES,
		// ADD DESCRIPTION
		"The caves, which stretch down under the abandoned prison, are sparcely populated. They lie too deep to be exploited " +
		"by the City and they are too poor in minerals to interest the dwarves. In the past there was a trade outpost " +
		"somewhere here on the route between these two states, but it has perished since the decline of Dwarven Metropolis. " +
		"Only omnipresent gnolls and subterranean animals dwell here now." );
		
		CHAPTERS.put( ID_METROPOLIS,
		"The royal high mage, who once lived in the halls of this castle, spent years looking for the secret of eternal life."+
				"His insatiable hunger for knowledge led him to his goal, but in exchange his soul was corrupted by dark magic."+
				"He drove the kingdom of Eldya to its demise. Now ruins are all that remain of the once great castle, and the high mage is damned to wander the castle forever.");

		CHAPTERS.put( ID_HALLS,
		"The floor feels soft and moist under your boots, as if you were standing in the flesh of an ancient beast. " +
		"You can feel how the influence of the cursed object is affecting this place. " +
		"The putrid smell makes your eyes water, and you feel the need to run away from this hellhole. " +
		"But turning back is not an option, not when you are so close to your goal. \n\n" +
		"Very few adventurers have ever descended this far..." );
	};
	
	private BitmapTextMultiline tf;
	private float delay;
	
	public WndStory( String text ) {
		super( 0, 0, Chrome.get( Chrome.Type.SCROLL ) );
		
		tf = PixelScene.createMultiline( text, 7 );
		tf.maxWidth = WIDTH - MARGIN * 2;
		tf.measure();
		tf.ra = bgR;
		tf.ga = bgG;
		tf.ba = bgB;
		tf.rm = -bgR;
		tf.gm = -bgG;
		tf.bm = -bgB;
		tf.x = MARGIN;
		add( tf );
		
		add( new TouchArea( chrome ) {
			@Override
			protected void onClick( Touch touch ) {
				hide();
			}
		} );
		
		resize( (int)(tf.width() + MARGIN * 2), (int)Math.min( tf.height(), 180 ) );
	}
	
	@Override
	public void update() {
		super.update();
		
		if (delay > 0 && (delay -= Game.elapsed) <= 0) {
			shadow.visible = chrome.visible = tf.visible = true;
		}
	}
	
	public static void showChapter( int id ) {
		
		if (Dungeon.chapters.contains( id )) {
			return;
		}
		
		String text = CHAPTERS.get( id );
		if (text != null) {
			WndStory wnd = new WndStory( text );
			if ((wnd.delay = 0.6f) > 0) {
				wnd.shadow.visible = wnd.chrome.visible = wnd.tf.visible = false;
			}
			
			Game.scene().add( wnd );
			
			Dungeon.chapters.add( id );
		}
	}
}
