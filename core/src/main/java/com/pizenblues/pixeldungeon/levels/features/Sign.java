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
package com.pizenblues.pixeldungeon.levels.features;

import com.pizenblues.noosa.audio.Sample;
import com.pizenblues.pixeldungeon.Assets;
import com.pizenblues.pixeldungeon.Dungeon;
import com.pizenblues.pixeldungeon.effects.CellEmitter;
import com.pizenblues.pixeldungeon.effects.particles.ElmoParticle;
import com.pizenblues.pixeldungeon.levels.DeadEndLevel;
import com.pizenblues.pixeldungeon.levels.Terrain;
import com.pizenblues.pixeldungeon.scenes.GameScene;
import com.pizenblues.pixeldungeon.utils.GLog;
import com.pizenblues.pixeldungeon.windows.WndMessage;

public class Sign {

	private static final String TXT_DEAD_END = 
		"What are you doing here?!";
	
	private static final String[] TIPS = {

			// lvl 1

		"In this dungeon food is not abundant, so manage your rations well or you'll starve.",

		"If you are stuck use your magnifier glass to search for hidden doors",

		"Slay the Dungeon is a rougelike game - That means you will die. A lot.",

		"You can spend your gold in stores on deeper levels of the dungeon. The first one is on the 6th level.",
			
		"Beware of the baby dragon...",

			// lvl 3
		
		"Dungeon Store (tm) - You will S-L-A-Y with our brand new armors! ",

		"Identify your potions and scrolls as soon as possible. Don't put it off to the moment " +
			"when you actually need them.",

		"Being hungry doesn't hurt, but starving does hurt.",

		"Surprise attack has a better chance to hit. For example, you can ambush your enemy behind " +
			"a closed door when you know it is approaching.",
		
		"Don't let her out...",

			// lvl 4
		
		"Dungeon Store (tm) - Not other store can beat our prices, cause there are no other stores.",

		"When you're attacked by several monsters at the same time, try to retreat behind a door.",

		"If you are burning, you can't put out the fire in the water while levitating.",

		"There is no sense in possessing more than one Scroll of rebirth at the same time, because you will lose them upon resurrecting.",
		
		"The griffin is guarding it's nest...",

			// lvl 5
		
		"Dungeon Store (tm) - Necromancer keychans? we got it!",

		"When you upgrade an enchanted weapon, there is a chance to destroy that enchantment.",

		"Weapons and armors deteriorate faster than wands and rings, but there are more ways to fix them.",

		"The only way to obtain a Scroll of Wipe Out is to receive it as a gift from the dungeon spirits.",
		
		"This is the dance of the necromancer...",
		
		"Dungeon Store (tm) - 50% off on all Dungeon monster plushies"
	};
	
	private static final String TXT_BURN =
		"Save me, friend...";
	
	public static void read( int pos ) {
		
		if (Dungeon.level instanceof DeadEndLevel) {
			
			GameScene.show( new WndMessage( TXT_DEAD_END ) );
			
		} else {
			
			int index = Dungeon.depth - 1;
			
			if (index < TIPS.length) {
				GameScene.show( new WndMessage( TIPS[index] ) );
			} else {
				
				Dungeon.level.destroy( pos );
				GameScene.updateMap( pos );
				GameScene.discoverTile( pos, Terrain.SIGN );
				
				CellEmitter.get( pos ).burst( ElmoParticle.FACTORY, 6 );
				Sample.INSTANCE.play( Assets.SND_BURNING );
				
				GLog.w( TXT_BURN );
				
			}
		}
	}
}
