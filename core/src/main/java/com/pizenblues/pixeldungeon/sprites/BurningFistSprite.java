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
package com.pizenblues.pixeldungeon.sprites;

import com.pizenblues.noosa.TextureFilm;
import com.pizenblues.noosa.audio.Sample;
import com.pizenblues.pixeldungeon.Assets;
import com.pizenblues.pixeldungeon.effects.MagicMissile;
import com.pizenblues.utils.Callback;

public class BurningFistSprite extends MobSprite {
	
	public BurningFistSprite() {
		super();
		
		texture( Assets.BURNING );
		
		TextureFilm frames = new TextureFilm( texture, 24, 32 );
		
		idle = new Animation( 8, true );
		idle.frames( frames, 0, 1, 2, 3 );

		run = new Animation( 12, true );
		run.frames( frames, 3,2, 1, 0, 3 );

		attack = new Animation( 12, false );
		attack.frames( frames, 3,0,1,2,4,5,6,7,3,3,3,3);

		die = new Animation( 8, false );
		die.frames( frames, 2,4,5,6,7,7,7,7,7,7,7,7,7 );
		
		play( idle );
	}
	
	private int posToShoot;
	
	@Override
	public void attack( int cell ) {
		posToShoot = cell;	
		super.attack( cell );
	}
	
	@Override
	public void onComplete( Animation anim ) {
		if (anim == attack) {

			Sample.INSTANCE.play( Assets.SND_ZAP );
			MagicMissile.shadow( parent, ch.pos, posToShoot, 
				new Callback() {			
					@Override
					public void call() {
						ch.onAttackComplete();
					}
				} );
			
			idle();
			
		} else {
			super.onComplete( anim );
		}
	}
}
