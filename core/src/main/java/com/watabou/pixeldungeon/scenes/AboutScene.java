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
package com.watabou.pixeldungeon.scenes;

import android.content.Intent;
import android.net.Uri;
import com.watabou.noosa.BitmapTextMultiline;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.ui.Archs;
import com.watabou.pixeldungeon.ui.ExitButton;
import com.watabou.pixeldungeon.ui.Icons;
import com.watabou.pixeldungeon.ui.RedButton;

public class AboutScene extends PixelScene {

	private static final String TXT =
		"Delicious Pixel Dungeon is a roguelike dungeon crawler game. " +
		"Sprites and music by Valentina.\n";

	private static final String TXT_THANKS =
			"Based on the original Pixel Dungeon by Watabou, inspired by Brian Walker's Brouge. \n" +
			"Voices (uh's and ah's) by @cicifyre, @JDSherbert, @daniconshow and @ATTurtlez. \n" +
			"Especial thanks to Evan Debenham (Shattered pixel dungeon) for pixel-dungeon-gradle!";
	
	private static final String LINK = "@drTamagotchi";

	@Override
	public void create() {
		super.create();
		float newPosition = PixelDungeon.landscape() ? 12 : 72;
		int w = Camera.main.width;

		Image tamago_icon = Icons.PIZEN.get();
		tamago_icon.x = align( (w - tamago_icon.width) / 2 );
		tamago_icon.y = newPosition;
		add( tamago_icon );

		BitmapTextMultiline text = createMultiline( TXT, 7);
		text.maxWidth = Math.min( w, 100 );
		text.measure();
		text.x = align( (w - text.width()) / 2 );
		text.y = newPosition + tamago_icon.height() + 4;
		add( text );

		newPosition += tamago_icon.height() + text.height() + 12;

		RedButton linkButton = new RedButton( "@drTamagotchi" ) {
			protected void onClick() {
				Intent intent = new Intent( Intent.ACTION_VIEW, Uri.parse( "https://www.instagram.com/drtamagotchi/" + LINK ) );
				Game.instance.startActivity( intent );
			}
		};

		linkButton.setSize( 56, 16 );
		linkButton.setPos( ((w - 56) / 2), newPosition);
		add( linkButton );

		newPosition += 24;

		// credits
		BitmapTextMultiline creditsText = createMultiline( TXT_THANKS, 7);
		creditsText.maxWidth = Math.min( w, 100 );
		creditsText.measure();
		add( creditsText );
		creditsText.x = align( (w - text.width()) / 2 );
		creditsText.y = newPosition;

		// bg and ui
		Archs archs = new Archs();
		archs.setSize( w, Camera.main.height );
		addToBack( archs );
		
		ExitButton btnExit = new ExitButton();
		btnExit.setPos( 4, 4 );
		add( btnExit );
		
		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		PixelDungeon.switchNoFade( TitleScene.class );
	}
}
