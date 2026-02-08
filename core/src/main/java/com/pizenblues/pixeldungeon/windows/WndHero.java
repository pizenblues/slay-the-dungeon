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

import java.util.Locale;

import com.pizenblues.noosa.BitmapText;
import com.pizenblues.noosa.BitmapTextMultiline;
import com.pizenblues.noosa.Image;
import com.pizenblues.pixeldungeon.Dungeon;
import com.pizenblues.pixeldungeon.actors.hero.Hero;
import com.pizenblues.pixeldungeon.scenes.PixelScene;
import com.pizenblues.pixeldungeon.ui.Icons;
import com.pizenblues.pixeldungeon.utils.Utils;
import com.pizenblues.pixeldungeon.ui.Window;
import com.pizenblues.gltextures.SmartTexture;
import com.pizenblues.gltextures.TextureCache;
import com.pizenblues.noosa.TextureFilm;
import com.pizenblues.pixeldungeon.Assets;
import com.pizenblues.pixeldungeon.actors.buffs.Buff;
import com.pizenblues.pixeldungeon.ui.BuffIndicator;

public class WndHero extends Window {
	private static final String TXT_EXP		= "XP";
	private static final String TXT_STR		= "STR";
	private static final String TXT_HEALTH	= "HP";
	private static final String TXT_DEPTH	= "FLOOR";
	private static final String TEXT_SEP	= " ";
	private static final int WIDTH = 120;
	private static final int HEIGHT	= 160;
	private static final String TXT_TITLE		= "%s - lvl %d";
	Image strengthIcon = Icons.STREGTHMINI.get();
	Image healthIcon = Icons.HEALTHMINI.get();
	Image expIcon = Icons.EXPMINI.get();
	Image depthIcon = Icons.DEPTHMINI.get();
	private static final int GAP = 5;
	private float pos;
	private float positionX;
	private SmartTexture icons;
	private TextureFilm film;
	private static final String DOT	= "\u007F";
	public float width;

	public WndHero() {
		super();
		resize( WIDTH, HEIGHT );
		Hero hero = Dungeon.hero;
		pos = 8;

		BitmapText title = PixelScene.createText(
				Utils.format( TXT_TITLE, hero.className(), hero.lvl ).toUpperCase( Locale.ENGLISH ), 9 );
		title.hardlight( TITLE_COLOR );
        title.y = 2;
		add( title );

		pos += 8;


		// stats

		statSlot( TXT_HEALTH, hero.HP + "/" + hero.HT, healthIcon);
		statSlot( TXT_EXP, hero.exp + "/" + hero.maxExp(), expIcon);
		statSlot( TXT_STR, ":"+hero.STR(), strengthIcon);
		statSlot( TXT_DEPTH, Dungeon.depth, depthIcon);

		BitmapText separator1 = PixelScene.createText(TEXT_SEP, 9 );
		add( separator1 );
		separator1.y = pos + 22;
		pos += 28 + GAP;

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
			item.maxWidth = (int)(WIDTH - dotWidth - 5);
			item.measure();
			add( item );

			pos += item.height() + 2;
			float w = item.width();
			if (w > width) {
				width = w;
			}
		}

		BitmapText separator2 = PixelScene.createText(TEXT_SEP, 9 );
		add( separator2 );
		separator2.y = pos;
		pos += GAP + 8;

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

	private void statSlot( String label, String value, Image icon) {

		add(icon);
		icon.y = pos;
		icon.x = positionX;

		BitmapText txt = PixelScene.createText( label, 7 );
		txt.y = pos+9;
		txt.x = positionX;
		txt.measure();
		add( txt );

		BitmapText stat = PixelScene.createText( value, 7 );
		stat.hardlight( 0xFFEBA4 );
		stat.x = positionX;
		stat.y = pos + txt.height() + 8;
		stat.measure();
		add( stat );

		positionX += 10 + stat.width();
	}

	private void statSlot( String label, int value, Image icon) {
		statSlot( label, Integer.toString( value ), icon);
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

		pos += 2 + icon.height;
	}

	public float height() {
		return pos;
	}
}
