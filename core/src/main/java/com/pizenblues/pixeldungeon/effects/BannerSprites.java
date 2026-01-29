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
package com.pizenblues.pixeldungeon.effects;

import com.pizenblues.noosa.Image;
import com.pizenblues.pixeldungeon.Assets;

public class BannerSprites {

	public enum  Type {
		PIXEL_DUNGEON,
		BOSS_SLAIN,
		GAME_OVER,
		THE_END
	};
	
	public static Image get( Type type ) {
		Image icon = new Image( Assets.BANNERS );
		switch (type) {
		case PIXEL_DUNGEON:
			icon.frame( icon.texture.uvRect( 0, 0, 115, 78 ) );
			break;
		case BOSS_SLAIN:
			icon.frame( icon.texture.uvRect( 0, 78, 99, 149 ) );
			break;
		case GAME_OVER:
			icon.frame( icon.texture.uvRect( 0, 149, 122, 219 ) );
			break;
		case THE_END:
			icon.frame( icon.texture.uvRect( 0, 223, 98, 270 ) );
			break;
		}
		return icon;
	}
}
