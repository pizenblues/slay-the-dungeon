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
package com.pizenblues.pixeldungeon.actors.mobs;

import java.util.HashSet;

import com.pizenblues.noosa.Camera;
import com.pizenblues.noosa.audio.Sample;
import com.pizenblues.pixeldungeon.Assets;
import com.pizenblues.pixeldungeon.Badges;
import com.pizenblues.pixeldungeon.Dungeon;
import com.pizenblues.pixeldungeon.Statistics;
import com.pizenblues.pixeldungeon.actors.Actor;
import com.pizenblues.pixeldungeon.actors.Char;
import com.pizenblues.pixeldungeon.actors.buffs.Buff;
import com.pizenblues.pixeldungeon.actors.buffs.Paralysis;
import com.pizenblues.pixeldungeon.effects.CellEmitter;
import com.pizenblues.pixeldungeon.effects.Speck;
import com.pizenblues.pixeldungeon.effects.particles.ElmoParticle;
import com.pizenblues.pixeldungeon.items.keys.SkeletonKey;
import com.pizenblues.pixeldungeon.items.rings.RingOfThorns;
import com.pizenblues.pixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.pizenblues.pixeldungeon.items.weapon.enchantments.Death;
import com.pizenblues.pixeldungeon.levels.Level;
import com.pizenblues.pixeldungeon.levels.Terrain;
import com.pizenblues.pixeldungeon.scenes.GameScene;
import com.pizenblues.pixeldungeon.sprites.DM300Sprite;
import com.pizenblues.pixeldungeon.utils.GLog;
import com.pizenblues.utils.Random;

public class DM300 extends Mob {
	
	{
		name = Dungeon.depth == Statistics.deepestFloor ? "Griffin" : "The return of the Griffin";
		spriteClass = DM300Sprite.class;
		
		HP = HT = 200;
		EXP = 30;
		defenseSkill = 18;
		
		loot = new RingOfThorns().random();
		lootChance = 0.333f;
	}
	
	@Override
	public int damageRoll() {
		return Random.NormalIntRange( 18, 24 );
	}
	
	@Override
	public int attackSkill( Char target ) {
		return 28;
	}
	
	@Override
	public int dr() {
		return 10;
	}
	
	@Override
	public void move( int step ) {
		super.move( step );
		
		if (Dungeon.level.map[step] == Terrain.INACTIVE_TRAP && HP < HT) {
			
			HP += Random.Int( 1, HT - HP );
			sprite.emitter().burst( ElmoParticle.FACTORY, 5 );
			
			if (Dungeon.visible[step] && Dungeon.hero.isAlive()) {
				GLog.n( "The Griffin is licking it's wounds!" );
			}
		}

		int[] cells = {
			step-1, step+1, step-Level.WIDTH, step+Level.WIDTH, 
			step-1-Level.WIDTH, 
			step-1+Level.WIDTH, 
			step+1-Level.WIDTH, 
			step+1+Level.WIDTH
		};
		int cell = cells[Random.Int( cells.length )];
		
		if (Dungeon.visible[cell]) {
			CellEmitter.get( cell ).start( Speck.factory( Speck.ROCK ), 0.07f, 10 );
			Camera.main.shake( 3, 0.7f );
			Sample.INSTANCE.play( Assets.SND_ROCKS );
			
			if (Level.water[cell]) {
				GameScene.ripple( cell );
			} else if (Dungeon.level.map[cell] == Terrain.EMPTY) {
				Level.set( cell, Terrain.EMPTY_DECO );
				GameScene.updateMap( cell );
			}
		}

		Char ch = Actor.findChar( cell );
		if (ch != null && ch != this) {
			Buff.prolong( ch, Paralysis.class, 2 );
		}
	}
	
	@Override
	public void die( Object cause ) {
		
		super.die( cause );
		
		GameScene.bossSlain();
		Dungeon.level.drop( new SkeletonKey(), pos ).sprite.drop();
		
		Badges.validateBossSlain();
		
		yell( "GRWDRRRRRR!!" );
	}
	
	@Override
	public void notice() {
		super.notice();
		yell( "The Griffin locks his sight on you." );
	}
	
	@Override
	public String description() {
		return
			"A half eagle, half lion creature. With sharp claws and even sharper senses. " +
			"The beast is protecting it's nests. It may get extremely aggressive to any invader.";
	}
	
	private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
	static {
		RESISTANCES.add( Death.class );
		RESISTANCES.add( ScrollOfPsionicBlast.class );
	}
	
	@Override
	public HashSet<Class<?>> resistances() {
		return RESISTANCES;
	}

}
