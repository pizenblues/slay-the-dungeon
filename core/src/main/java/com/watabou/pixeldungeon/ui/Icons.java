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
package com.watabou.pixeldungeon.ui;

import com.watabou.noosa.Image;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.actors.hero.HeroClass;

public enum Icons {

	INFOICON,
	SKULL,
	BUSY,
	COMPASS, 
	PREFS,
	WARNING,
	TARGET,
	WATA,
	PIZEN,
	WARRIOR,
	MAGE,
	ROGUE,
	HUNTRESS,
	CLOSE,
	DEPTH,
	SLEEP,
	ALERT,
	SUPPORT,
	SUPPORTED,
	BACKPACK,
	SEED_POUCH,
	SCROLL_HOLDER,
	WAND_HOLSTER,
	KEYRING,
	CHECKED,
	UNCHECKED,
	EXIT,
	CHALLENGE_OFF,
	CHALLENGE_ON,
	RESUME,
	STREGTHMINI,
	HEALTHMINI,
	EXPMINI,
	DEPTHMINI,
	GOLDMINI;
	
	public Image get() {
		return get( this );
	}
	
	public static Image get( Icons type ) {
		Image icon = new Image( Assets.ICONS );
		switch (type) {
		case INFOICON:
			icon.frame( icon.texture.uvRect( 16, 0, 30, 14 ) );
			break;
		case SKULL:
			icon.frame( icon.texture.uvRect( 0, 0, 8, 8 ) );
			break;
		case BUSY:
			icon.frame( icon.texture.uvRect( 8, 0, 16, 8 ) );
			break;
		case COMPASS:
			icon.frame( icon.texture.uvRect( 80, 48, 96, 64 ) );
			break;
		case PREFS:
			icon.frame( icon.texture.uvRect( 30, 0, 46, 16 ) );
			break;
		case WARNING:
			icon.frame( icon.texture.uvRect( 46, 0, 64, 12 ) );
			break;
		case TARGET:
			icon.frame( icon.texture.uvRect( 0, 13, 16, 29 ) );
			break;
		case WATA:
			icon.frame( icon.texture.uvRect( 30, 16, 45, 26 ) );
			break;
			case PIZEN:
				icon.frame(icon.texture.uvRect(64,48,79,63));
			break;
		case WARRIOR:
			icon.frame( icon.texture.uvRect( 0, 30, 16, 46 ) );
			break;
		case MAGE:
			icon.frame( icon.texture.uvRect( 16, 30, 32, 45 ) );
			break;
		case ROGUE:
			icon.frame( icon.texture.uvRect( 35, 30, 44, 41 ) );
			break;
		case HUNTRESS:
			icon.frame( icon.texture.uvRect( 48, 30, 62, 44 ) );
			break;
		case CLOSE:
			icon.frame( icon.texture.uvRect( 0, 48, 12, 60 ) );
			break;
		case DEPTH:
			icon.frame( icon.texture.uvRect( 45, 12, 54, 20 ) );
			break;
		case SLEEP:
			icon.frame( icon.texture.uvRect( 13, 48, 22, 55 ) );
			break;
		case ALERT:
			icon.frame( icon.texture.uvRect( 22, 48, 30, 56 ) );
			break;
		case SUPPORT:
			icon.frame( icon.texture.uvRect( 30, 45, 46, 61 ) );
			break;
		case SUPPORTED:
			icon.frame( icon.texture.uvRect( 46, 45, 62, 61 ) );
			break;
		case BACKPACK:
			icon.frame( icon.texture.uvRect( 58, 0, 68, 10 ) );
			break;
		case SCROLL_HOLDER:
			icon.frame( icon.texture.uvRect( 69, 0, 77, 10 ) );
			break;
		case SEED_POUCH:
			icon.frame( icon.texture.uvRect( 78, 0, 88, 10 ) );
			break;
		case WAND_HOLSTER:
			icon.frame( icon.texture.uvRect( 88, 0, 98, 10 ) );
			break;
		case KEYRING:
			icon.frame( icon.texture.uvRect( 66, 29, 75, 41 ) );
			break;
		case CHECKED:
			icon.frame( icon.texture.uvRect( 54, 12, 66, 24 ) );
			break;
		case UNCHECKED:
			icon.frame( icon.texture.uvRect( 66, 12, 78, 24 ) );
			break;
		case EXIT:
			icon.frame( icon.texture.uvRect( 98, 0, 114, 16 ) );
			break;
		case CHALLENGE_OFF:
			icon.frame( icon.texture.uvRect( 78, 16, 92, 29 ) );
			break;
		case CHALLENGE_ON:
			icon.frame( icon.texture.uvRect( 102, 16, 116, 29 ) );
			break;
		case RESUME:
			icon.frame( icon.texture.uvRect( 114, 0, 126, 12 ) );
			break;
		case STREGTHMINI:
			icon.frame( icon.texture.uvRect( 80, 32, 88, 40 ) );
			break;
		case HEALTHMINI:
			icon.frame( icon.texture.uvRect( 88, 32, 96, 40 ) );
			break;
		case EXPMINI:
			icon.frame( icon.texture.uvRect( 96, 32, 104, 40 ) );
			break;
		case DEPTHMINI:
			icon.frame( icon.texture.uvRect( 104, 32, 112, 40 ) );
			break;
		case GOLDMINI:
			icon.frame( icon.texture.uvRect( 112, 32, 120, 40 ) );
			break;
		}
		return icon;
	}
	
	public static Image get( HeroClass cl ) {
		switch (cl) {
		case WARRIOR:
			return get( WARRIOR );
		case MAGE:
			return get( MAGE );
		case ROGUE:
			return get( ROGUE );
		case HUNTRESS:
			return get( HUNTRESS );
		default:
			return null;
		}
	}
}
