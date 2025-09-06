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

import java.util.Locale;

import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Image;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.Statistics;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.Icons;
import com.watabou.pixeldungeon.ui.SecondaryButton;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.pixeldungeon.ui.Window;

public class WndHero extends Window {
	private static final String TXT_EXP		= "Experience";
	private static final String TXT_STR		= "Strength";
	private static final String TXT_HEALTH	= "Health";
	private static final String TXT_GOLD	= "Gold Collected";
	private static final String TXT_DEPTH	= "Maximum Depth";
	private static final int WIDTH = 112;
	private static final int HEIGHT	= 112;
	private static final String TXT_TITLE		= "Level %d %s";
	private static final String TXT_CATALOGUS	= "Catalogus";
	private static final String TXT_JOURNAL		= "Journal";
	Image strengthIcon = Icons.STREGTHMINI.get();
	Image healthIcon = Icons.HEALTHMINI.get();
	Image expIcon = Icons.EXPMINI.get();
	Image goldIcon = Icons.GOLDMINI.get();
	Image depthIcon = Icons.DEPTHMINI.get();
	private static final int GAP = 5;
	private float pos;

	public WndHero() {
		super();
		resize( WIDTH, HEIGHT );

		Hero hero = Dungeon.hero;

		BitmapText title = PixelScene.createText(
				Utils.format( TXT_TITLE, hero.lvl, hero.className() ).toUpperCase( Locale.ENGLISH ), 9 );
		title.hardlight( TITLE_COLOR );
		title.measure();
		add( title );

		SecondaryButton btnCatalogus = new SecondaryButton( TXT_CATALOGUS ) {
			@Override
			protected void onClick() {
				hide();
				GameScene.show( new WndCatalogus() );
			}
		};
		btnCatalogus.setRect( 0, title.y + title.height() + 4, btnCatalogus.reqWidth() + 2, btnCatalogus.reqHeight() + 2 );
		add( btnCatalogus );

		SecondaryButton btnJournal = new SecondaryButton( TXT_JOURNAL ) {
			@Override
			protected void onClick() {
				hide();
				GameScene.show( new WndJournal() );
			}
		};
		btnJournal.setRect(
				btnCatalogus.right() + 1, btnCatalogus.top(),
				btnJournal.reqWidth() + 2, btnJournal.reqHeight() + 2 );
		add( btnJournal );

		pos = btnCatalogus.bottom() + GAP;

		statSlot( TXT_STR, hero.STR(), strengthIcon);
		statSlot( TXT_HEALTH, hero.HP + "/" + hero.HT, healthIcon);
		statSlot( TXT_EXP, hero.exp + "/" + hero.maxExp(), expIcon);

		statSlot( TXT_GOLD, Statistics.goldCollected, goldIcon);
		statSlot( TXT_DEPTH, Statistics.deepestFloor, depthIcon);

		pos += GAP;

	}

	private void statSlot( String label, String value, Image icon) {

		add(icon);
		icon.y = pos;
		icon.x = 0;

		BitmapText txt = PixelScene.createText( label, 8 );
		txt.y = pos;
		txt.x = 12;
		add( txt );

		txt = PixelScene.createText( value, 8 );
		txt.measure();
		txt.x = PixelScene.align( WIDTH * 0.75f );
		txt.y = pos;
		add( txt );

		pos += GAP + txt.baseLine();
	}

	private void statSlot( String label, int value, Image icon) {
		statSlot( label, Integer.toString( value ), icon);
	}

	public float height() {
		return pos;
	}
}
