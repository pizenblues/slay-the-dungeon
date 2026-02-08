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
package com.pizenblues.pixeldungeon.scenes;

import android.content.Intent;
import android.net.Uri;

import com.pizenblues.noosa.BitmapText;
import com.pizenblues.noosa.BitmapTextMultiline;
import com.pizenblues.noosa.Camera;
import com.pizenblues.noosa.Game;
import com.pizenblues.noosa.Image;
import com.pizenblues.pixeldungeon.PixelDungeon;
import com.pizenblues.pixeldungeon.effects.FireAnimation;
import com.pizenblues.pixeldungeon.ui.ExitButton;
import com.pizenblues.pixeldungeon.ui.Icons;
import com.pizenblues.pixeldungeon.ui.PrimaryButton;

public class AboutScene extends PixelScene {

	private static final String TXT =
		"Slay the Dungeon is a roguelike dungeon crawler game. " +
		"Sprites and music by Valentina.\n";

	private static final String TXT_THANKS =
			"Based on the original Pixel Dungeon by Watabou, inspired by Brian Walker's Brouge. \n" +
			"Voices (uh's and ah's) by @cicifyre, @JDSherbert, @daniconshow and @ATTurtlez. \n" +
			"Especial thanks to Evan Debenham (Shattered pixel dungeon) for pixel-dungeon-gradle!";
	
	private static final String LINK = "@drTamagotchi";

    private FireAnimation fireAnim;

	@Override
	public void create() {
		super.create();
		float newPosition = PixelDungeon.landscape() ? 12 : 72;
		int w = Camera.main.width;
		int h = Camera.main.height;

        fireAnim = placeAnim();

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

		PrimaryButton linkButton = new PrimaryButton( "@drTamagotchi" ) {
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
		
		//version
		BitmapText version = new BitmapText( "v " + Game.version, font1x );
		version.measure();
		version.hardlight( 0x888888 );
		version.x = (w - version.width()) / 2;
		version.y = h - version.height() - 8;
		add( version );
		
		ExitButton btnExit = new ExitButton();
		btnExit.setPos( 4, 4 );
		add( btnExit );
		
		fadeIn();
	}
	
	@Override
	protected void onBackPressed() {
		PixelDungeon.switchNoFade( TitleScene.class );
	}

    private FireAnimation placeAnim() {
        FireAnimation fireSprite = new FireAnimation();
        fireSprite.scale.set(0.6f);
        fireSprite.x = (Camera.main.width - fireSprite.width()) / 2f;
        fireSprite.y = Camera.main.height - fireSprite.height();

        add( fireSprite );
        return fireSprite;
    }
}
