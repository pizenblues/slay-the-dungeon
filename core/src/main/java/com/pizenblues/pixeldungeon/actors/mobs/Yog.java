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

import java.util.ArrayList;
import java.util.HashSet;

import com.pizenblues.pixeldungeon.Dungeon;
import com.pizenblues.pixeldungeon.ResultDescriptions;
import com.pizenblues.pixeldungeon.Statistics;
import com.pizenblues.pixeldungeon.actors.Actor;
import com.pizenblues.pixeldungeon.actors.Char;
import com.pizenblues.pixeldungeon.actors.blobs.Blob;
import com.pizenblues.pixeldungeon.actors.blobs.Fire;
import com.pizenblues.pixeldungeon.actors.blobs.ToxicGas;
import com.pizenblues.pixeldungeon.actors.buffs.Amok;
import com.pizenblues.pixeldungeon.actors.buffs.Buff;
import com.pizenblues.pixeldungeon.actors.buffs.Burning;
import com.pizenblues.pixeldungeon.actors.buffs.Charm;
import com.pizenblues.pixeldungeon.actors.buffs.Ooze;
import com.pizenblues.pixeldungeon.actors.buffs.Poison;
import com.pizenblues.pixeldungeon.actors.buffs.Sleep;
import com.pizenblues.pixeldungeon.actors.buffs.Terror;
import com.pizenblues.pixeldungeon.actors.buffs.Vertigo;
import com.pizenblues.pixeldungeon.effects.Pushing;
import com.pizenblues.pixeldungeon.effects.particles.ShadowParticle;
import com.pizenblues.pixeldungeon.items.keys.SkeletonKey;
import com.pizenblues.pixeldungeon.items.scrolls.ScrollOfPsionicBlast;
import com.pizenblues.pixeldungeon.items.weapon.enchantments.Death;
import com.pizenblues.pixeldungeon.levels.Level;
import com.pizenblues.pixeldungeon.mechanics.Ballistica;
import com.pizenblues.pixeldungeon.scenes.GameScene;
import com.pizenblues.pixeldungeon.sprites.BurningFistSprite;
import com.pizenblues.pixeldungeon.sprites.CharSprite;
import com.pizenblues.pixeldungeon.sprites.LarvaSprite;
import com.pizenblues.pixeldungeon.sprites.RottingFistSprite;
import com.pizenblues.pixeldungeon.sprites.YogSprite;
import com.pizenblues.pixeldungeon.utils.GLog;
import com.pizenblues.pixeldungeon.utils.Utils;
import com.pizenblues.utils.Random;

public class Yog extends Mob {
	
	{
		name = Dungeon.depth == Statistics.deepestFloor ? "The abomination" : "The abomination";
		spriteClass = YogSprite.class;
		
		HP = HT = 300;
		
		EXP = 50;
		
		state = PASSIVE;
	}
	
	private static final String TXT_DESC =
		"A corrupted mind, prisoner of flesh, ruler of the dungeons. The abomination is the result " +
			"of a power-hungry mortal soul that could not handle the power it was given; as a result"+
				"it merged with the dungeon it self. \n\n" +
		"It looks like it is in pain. You can sense the soul of Eden trapped in the abomination. The mouth looks like a weak point to attack. ";
	
	private static int fistsCount = 0;
	
	public Yog() {
		super();
	}
	
	public void spawnFists() {
		RottingFist fist1 = new RottingFist();
		BurningFist fist2 = new BurningFist();
		
		do {
			fist1.pos = pos + Level.NEIGHBOURS8[Random.Int( 8 )];
			fist2.pos = pos + Level.NEIGHBOURS8[Random.Int( 8 )];
		} while (!Level.passable[fist1.pos] || !Level.passable[fist2.pos] || fist1.pos == fist2.pos);
		
		GameScene.add( fist1 );
		GameScene.add( fist2 );
	}
	
	@Override
	public void damage( int dmg, Object src ) {
		
		if (fistsCount > 0) {
			
			for (Mob mob : Dungeon.level.mobs) {
				if (mob instanceof BurningFist || mob instanceof RottingFist) {
					mob.beckon( pos );
				}
			}
			
			dmg >>= fistsCount;
		}
		
		super.damage( dmg, src );
	}
	
	@Override
	public int defenseProc( Char enemy, int damage ) {

		ArrayList<Integer> spawnPoints = new ArrayList<Integer>();
		
		for (int i=0; i < Level.NEIGHBOURS8.length; i++) {
			int p = pos + Level.NEIGHBOURS8[i];
			if (Actor.findChar( p ) == null && (Level.passable[p] || Level.avoid[p])) {
				spawnPoints.add( p );
			}
		}
		
		if (spawnPoints.size() > 0) {
			Larva larva = new Larva();
			larva.pos = Random.element( spawnPoints );
			
			GameScene.add( larva );
			Actor.addDelayed( new Pushing( larva, pos, larva.pos ), -1 );
		}

		return super.defenseProc(enemy, damage);
	}
	
	@Override
	public void beckon( int cell ) {
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void die( Object cause ) {

		for (Mob mob : (Iterable<Mob>)Dungeon.level.mobs.clone()) {
			if (mob instanceof BurningFist || mob instanceof RottingFist) {
				mob.die( cause );
			}
		}
		
		GameScene.bossSlain();
		Dungeon.level.drop( new SkeletonKey(), pos ).sprite.drop();
		super.die( cause );
		
		yell( "..." );
	}
	
	@Override
	public void notice() {
		super.notice();
		yell( "I have no mouth and i must..." );
	}
	
	@Override
	public String description() {
		return TXT_DESC;
			
	}
	
	private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
	static {
		
		IMMUNITIES.add( Death.class );
		IMMUNITIES.add( Terror.class );
		IMMUNITIES.add( Amok.class );
		IMMUNITIES.add( Charm.class );
		IMMUNITIES.add( Sleep.class );
		IMMUNITIES.add( Burning.class );
		IMMUNITIES.add( ToxicGas.class );
		IMMUNITIES.add( ScrollOfPsionicBlast.class );
	}
	
	@Override
	public HashSet<Class<?>> immunities() {
		return IMMUNITIES;
	}
	
	public static class RottingFist extends Mob {
	
		private static final int REGENERATION	= 4;
		
		{
			name = "rotting fist";
			spriteClass = RottingFistSprite.class;
			
			HP = HT = 300;
			defenseSkill = 25;
			
			EXP = 0;
			
			state = WANDERING;
		}
		
		public RottingFist() {
			super();
			fistsCount++;
		}
		
		@Override
		public void die( Object cause ) {
			super.die( cause );
			fistsCount--;
		}
		
		@Override
		public int attackSkill( Char target ) {
			return 36;
		}
		
		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 24, 36 );
		}
		
		@Override
		public int dr() {
			return 15;
		}
		
		@Override
		public int attackProc( Char enemy, int damage ) {
			if (Random.Int( 3 ) == 0) {
				Buff.affect( enemy, Ooze.class );
				enemy.sprite.burst( 0xFF000000, 5 );
			}
			
			return damage;
		}
		
		@Override
		public boolean act() {
			
			if (Level.water[pos] && HP < HT) {
				sprite.emitter().burst( ShadowParticle.UP, 2 );
				HP += REGENERATION;
			}
			
			return super.act();
		}
		
		@Override
		public String description() {
			return TXT_DESC;
				
		}
		
		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
		static {
			RESISTANCES.add( ToxicGas.class );
			RESISTANCES.add( Death.class );
			RESISTANCES.add( ScrollOfPsionicBlast.class );
		}
		
		@Override
		public HashSet<Class<?>> resistances() {
			return RESISTANCES;
		}
		
		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add( Amok.class );
			IMMUNITIES.add( Sleep.class );
			IMMUNITIES.add( Terror.class );
			IMMUNITIES.add( Poison.class );
			IMMUNITIES.add( Vertigo.class );
		}
		
		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
	
	public static class BurningFist extends Mob {
		
		{
			name = "burning fist";
			spriteClass = BurningFistSprite.class;
			
			HP = HT = 200;
			defenseSkill = 25;
			
			EXP = 0;
			
			state = WANDERING;
		}
		
		public BurningFist() {
			super();
			fistsCount++;
		}
		
		@Override
		public void die( Object cause ) {
			super.die( cause );
			fistsCount--;
		}
		
		@Override
		public int attackSkill( Char target ) {
			return 36;
		}
		
		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 20, 32 );
		}
		
		@Override
		public int dr() {
			return 15;
		}
		
		@Override
		protected boolean canAttack( Char enemy ) {
			return Ballistica.cast( pos, enemy.pos, false, true ) == enemy.pos;
		}
		
		@Override
		public boolean attack( Char enemy ) {
			
			if (!Level.adjacent( pos, enemy.pos )) {
				spend( attackDelay() );
				
				if (hit( this, enemy, true )) {
					
					int dmg =  damageRoll();
					enemy.damage( dmg, this );
					
					enemy.sprite.bloodBurstA( sprite.center(), dmg );
					enemy.sprite.flash();
					
					if (!enemy.isAlive() && enemy == Dungeon.hero) {
						Dungeon.fail( Utils.format( ResultDescriptions.BOSS, name, Dungeon.depth ) );
						GLog.n( TXT_KILL, name );
					}
					return true;
					
				} else {
					
					enemy.sprite.showStatus( CharSprite.NEUTRAL,  enemy.defenseVerb() );
					return false;
				}
			} else {
				return super.attack( enemy );
			}
		}
		
		@Override
		public boolean act() {
			for (int i=0; i < Level.NEIGHBOURS9.length; i++) {
				GameScene.add( Blob.seed( pos + Level.NEIGHBOURS9[i], 2, Fire.class ) );
			}
			
			return super.act();
		}
		
		@Override
		public String description() {
			return TXT_DESC;
				
		}
		
		private static final HashSet<Class<?>> RESISTANCES = new HashSet<Class<?>>();
		static {
			RESISTANCES.add( ToxicGas.class );
			RESISTANCES.add( Death.class );
			RESISTANCES.add( ScrollOfPsionicBlast.class );
		}
		
		@Override
		public HashSet<Class<?>> resistances() {
			return RESISTANCES;
		}
		
		private static final HashSet<Class<?>> IMMUNITIES = new HashSet<Class<?>>();
		static {
			IMMUNITIES.add( Amok.class );
			IMMUNITIES.add( Sleep.class );
			IMMUNITIES.add( Terror.class );
			IMMUNITIES.add( Burning.class );
		}
		
		@Override
		public HashSet<Class<?>> immunities() {
			return IMMUNITIES;
		}
	}
	
	public static class Larva extends Mob {
		
		{
			name = "god's larva";
			spriteClass = LarvaSprite.class;
			
			HP = HT = 25;
			defenseSkill = 20;
			
			EXP = 0;
			
			state = HUNTING;
		}
		
		@Override
		public int attackSkill( Char target ) {
			return 30;
		}
		
		@Override
		public int damageRoll() {
			return Random.NormalIntRange( 15, 20 );
		}
		
		@Override
		public int dr() {
			return 8;
		}
		
		@Override
		public String description() {
			return TXT_DESC;
				
		}
	}
}
