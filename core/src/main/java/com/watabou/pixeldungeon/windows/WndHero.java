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
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Image;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.hero.Hero;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.ui.Icons;
import com.watabou.pixeldungeon.utils.Utils;
import com.watabou.pixeldungeon.ui.Window;
import com.watabou.gltextures.SmartTexture;
import com.watabou.gltextures.TextureCache;
import com.watabou.noosa.TextureFilm;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.actors.buffs.Buff;
import com.watabou.pixeldungeon.ui.BuffIndicator;

public class WndHero extends Window {
	private static final String TXT_EXP		= "Experience";
	private static final String TXT_STR		= "Strength";
	private static final String TXT_HEALTH	= "Health";
	private static final String TXT_DEPTH	= "Current floor";
	private static final int WIDTH = 112;
	private static final int HEIGHT	= 140;
	private static final String TXT_TITLE		= "%s - Level %d";
	Image strengthIcon = Icons.STREGTHMINI.get();
	Image healthIcon = Icons.HEALTHMINI.get();
	Image expIcon = Icons.EXPMINI.get();
	Image depthIcon = Icons.DEPTHMINI.get();
	private static final int GAP = 4;
	private float pos;
	private SmartTexture icons;
	private TextureFilm film;
	private static final String DOT	= "\u007F";
	public float width;

	public WndHero() {
		super();
		resize( WIDTH, HEIGHT );
		Hero hero = Dungeon.hero;

		BitmapText title = PixelScene.createText(
				Utils.format( TXT_TITLE, hero.className(), hero.lvl ).toUpperCase( Locale.ENGLISH ), 9 );
		title.hardlight( TITLE_COLOR );
		title.measure();
		add( title );

		// stats

		pos = 8 + GAP;

		statSlot( TXT_STR, hero.STR(), strengthIcon);
		statSlot( TXT_HEALTH, hero.HP + "/" + hero.HT, healthIcon);
		statSlot( TXT_EXP, hero.exp + "/" + hero.maxExp(), expIcon);
		statSlot( TXT_DEPTH, Dungeon.depth, depthIcon);

		pos += GAP;

		// hero perks

		float dotWidth = 0;
		String[] items = hero.heroClass.perks();

		for (int i=0; i < items.length; i++) {
			if (i > 0) {
				pos++;
			}

			BitmapText dot = PixelScene.createText( DOT, 4 );
			dot.y = pos;
			if (dotWidth == 0) {
				dot.measure();
				dotWidth = dot.width();
			}
			add( dot );

			BitmapTextMultiline item = PixelScene.createMultiline( items[i], 7 );
			item.x = dot.x + dotWidth + 2;
			item.y = pos;
			item.maxWidth = (int)(WIDTH - dotWidth);
			item.measure();
			add( item );

			pos += item.height();
			float w = item.width();
			if (w > width) {
				width = w;
			}
		}

		pos = pos+4;

		// BUFF LIST

		icons = TextureCache.get( Assets.BUFFS_SMALL );
		film = new TextureFilm( icons, 7, 7 );

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

		BitmapText txt = PixelScene.createText( buff.toString(), 7 );
		txt.x = icon.width + 2;
		txt.y = pos + (int)(icon.height - txt.baseLine()) / 2;
		txt.hardlight( TITLE_COLOR );
		add( txt );

		pos += GAP + icon.height;
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
