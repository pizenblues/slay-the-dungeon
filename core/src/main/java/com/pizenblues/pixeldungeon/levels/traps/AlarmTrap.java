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
package com.pizenblues.pixeldungeon.levels.traps;

import com.pizenblues.noosa.audio.Sample;
import com.pizenblues.pixeldungeon.Assets;
import com.pizenblues.pixeldungeon.Dungeon;
import com.pizenblues.pixeldungeon.actors.Char;
import com.pizenblues.pixeldungeon.actors.mobs.Mob;
import com.pizenblues.pixeldungeon.effects.CellEmitter;
import com.pizenblues.pixeldungeon.effects.Speck;
import com.pizenblues.pixeldungeon.utils.GLog;

public class AlarmTrap {

	// 0xDD3333
	
	public static void trigger( int pos, Char ch ) {
		
		for (Mob mob : Dungeon.level.mobs) {
			if (mob != ch) {
				mob.beckon( pos );
			}
		}
		
		if (Dungeon.visible[pos]) {
			GLog.w( "The trap emits a piercing sound that echoes throughout the dungeon!" );
			CellEmitter.center( pos ).start( Speck.factory( Speck.SCREAM ), 0.3f, 3 );
		}
		
		Sample.INSTANCE.play( Assets.SND_ALERT );
	}
}
