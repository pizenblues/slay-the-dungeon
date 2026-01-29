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

import com.pizenblues.noosa.Game;
import com.pizenblues.pixeldungeon.windows.WndStory;

public class IntroScene extends PixelScene {

	private static final String TEXT = 	
		"Many heroes of all kinds ventured into the Dungeon before you. Some of them have returned with treasures and magical " +
		"artifacts, most have never been heard of since. \n\n" +

		"Eden was last seen entering these dungeons, looking for the King's cranium, "+
		"a cursed artifact which is said to give unlimited power to who may possess it." +
		"Eden is dear to you, but you know their hunger for power is their greatest weakness." + 
		"Althought these dungeons look dangerous, you have decided to bring Eden back. \n\n" +

		"You enter the dungeon...";
	
	@Override
	public void create() {
		super.create();
		
		add( new WndStory( TEXT ) {
			@Override
			public void hide() {
				super.hide();
				Game.switchScene( InterlevelScene.class );
			}
		} );
		
		fadeIn();
	}
}
