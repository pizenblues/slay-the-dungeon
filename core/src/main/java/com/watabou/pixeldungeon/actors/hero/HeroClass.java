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
package com.watabou.pixeldungeon.actors.hero;

import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Badges;
import com.watabou.pixeldungeon.items.TomeOfMastery;
import com.watabou.pixeldungeon.items.armor.ClothArmor;
import com.watabou.pixeldungeon.items.bags.Keyring;
import com.watabou.pixeldungeon.items.food.Food;
import com.watabou.pixeldungeon.items.potions.PotionOfStrength;
import com.watabou.pixeldungeon.items.potions.PotionOfHealing;
import com.watabou.pixeldungeon.items.rings.RingOfShadows;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfIdentify;
import com.watabou.pixeldungeon.items.scrolls.ScrollOfMagicMapping;
import com.watabou.pixeldungeon.items.wands.WandOfMagicMissile;
import com.watabou.pixeldungeon.items.weapon.melee.Dagger;
import com.watabou.pixeldungeon.items.weapon.melee.Knuckles;
import com.watabou.pixeldungeon.items.weapon.melee.ShadowBlade;
import com.watabou.pixeldungeon.items.weapon.melee.ShortSword;
import com.watabou.pixeldungeon.items.weapon.missiles.Dart;
import com.watabou.pixeldungeon.items.weapon.missiles.Boomerang;
import com.watabou.pixeldungeon.ui.QuickSlot;
import com.watabou.utils.Bundle;

public enum HeroClass {

	WARRIOR( "warrior" ), MAGE( "mage" ), ROGUE( "rogue" ), HUNTRESS( "tank" );
	
	private String title;
	
	private HeroClass( String title ) {
		this.title = title;
	}
	
	public static final String[] WAR_PERKS = {
		"Starts with 11 points of Strength and an unique sword.",
		"Less proficient with missile weapons.",
		"Any piece of food restores some health when eaten.",
		"Potions of Strength are identified from the beginning.",
	};
	
	public static final String[] MAG_PERKS = {
		"Starts with a unique Wand of Magic Missile.",
		"Recharges their wands faster.",
		"When eaten, any piece of food restores 1 charge for all wands in the inventory.",
		"Scrolls of Identify are identified from the beginning."
	};
	
	public static final String[] ROG_PERKS = {
		"Starts with a Ring of Shadows+1.",
		"Identify a type of a ring on equipping it.",
		"Proficient in detecting hidden doors and traps.",
		"Can go without food longer.",
		"Scrolls of Magic Mapping are identified from the beginning."
	};
	
	public static final String[] HUN_PERKS = {
		"Starts with 15 points of Health and a unique shield.",
		"Gets damage bonus with missile weapons.",
		"Gain more health from berries.",
		"Sense neighbouring monsters even if they are hidden behind obstacles."
	};
	
	public void initHero( Hero hero ) {
		
		hero.heroClass = this;
		
		initCommon( hero );
		
		switch (this) {
		case WARRIOR:
			initWarrior( hero );
			break;
			
		case MAGE:
			initMage( hero );
			break;
			
		case ROGUE:
			initRogue( hero );
			break;
			
		case HUNTRESS:
			initHuntress( hero );
			break;
		}
		
		if (Badges.isUnlocked( masteryBadge() )) {
			new TomeOfMastery().collect();
		}
		
		hero.updateAwareness();
	}
	
	private static void initCommon( Hero hero ) {
		(hero.belongings.armor = new ClothArmor()).identify();
		new Food().identify().collect();
		new Keyring().collect();
	}
	
	public Badges.Badge masteryBadge() {
		switch (this) {
		case WARRIOR:
			return Badges.Badge.MASTERY_WARRIOR;
		case MAGE:
			return Badges.Badge.MASTERY_MAGE;
		case ROGUE:
			return Badges.Badge.MASTERY_ROGUE;
		case HUNTRESS:
			return Badges.Badge.MASTERY_HUNTRESS;
		}
		return null;
	}
	
	private static void initWarrior( Hero hero ) {
		hero.STR = hero.STR + 1;

		(hero.belongings.weapon = new ShortSword()).identify();
		new Dart( 8 ).identify().collect();

		QuickSlot.primaryValue = Dart.class;
		
		new PotionOfStrength().setKnown();
	}
	
	private static void initMage( Hero hero ) {	
		(hero.belongings.weapon = new Knuckles()).identify();
		
		WandOfMagicMissile wand = new WandOfMagicMissile();
		wand.identify().collect();
		
		QuickSlot.primaryValue = wand;
		
		new ScrollOfIdentify().setKnown();
	}
	
	private static void initRogue( Hero hero ) {
		(hero.belongings.weapon = new ShadowBlade()).identify();

		//values for testing
		hero.STR = hero.STR + 200;
		hero.HP = (hero.HT += 200);
		new PotionOfHealing( ).identify().collect();
		new PotionOfHealing( ).identify().collect();
		new PotionOfHealing( ).identify().collect();
		new PotionOfHealing( ).identify().collect();
		new PotionOfHealing( ).identify().collect();
		new PotionOfHealing( ).identify().collect();
		new PotionOfHealing( ).identify().collect();
		new PotionOfHealing( ).identify().collect();
		new PotionOfHealing( ).identify().collect();
		new PotionOfHealing( ).identify().collect();
		new PotionOfHealing( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		new ScrollOfMagicMapping( ).identify().collect();
		// remove testing values

		(hero.belongings.ring1 = new RingOfShadows()).upgrade().identify();
		new Dart( 8 ).identify().collect();
		
		hero.belongings.ring1.activate( hero );
		
		QuickSlot.primaryValue = Dart.class;
		
		new ScrollOfMagicMapping().setKnown();
	}
	
	private static void initHuntress( Hero hero ) {
		
		hero.HP = (hero.HT += 5);
		
		(hero.belongings.weapon = new Dagger()).identify();
		Boomerang boomerang = new Boomerang();
		boomerang.identify().collect();
		
		QuickSlot.primaryValue = boomerang;
	}

	public String title() {
		return title;
	}

	public String description() {
		switch (this) {
			case WARRIOR:
				return "The Warrior has increased strength, but muscle power requires more food, meals can cure his wounds tho!";
			case MAGE:
				return "The Mage comes with a unique projectile wand, but has weaker mele attacks. Food can restore her magical powers!";
			case ROGUE:
				return "The Rogue is hard to hit but has less HP, so try not to get hit a lot. Has a keen eye for spotting tramps";
			case HUNTRESS:
				return "The Tank has increased HP due to his dwarf build. Can pick extra berries (yum!) and sense extra monsters (yuck!)";
		}

		return null;
	}
	
	public String spritesheet() {
		switch (this) {
		case WARRIOR:
			return Assets.WARRIOR;
		case MAGE:
			return Assets.MAGE;
		case ROGUE:
			return Assets.ROGUE;
		case HUNTRESS:
			return Assets.HUNTRESS;
		}
		return null;
	}

	public String splash() {
		switch (this) {
			case WARRIOR:
				return Assets.SPLASH_warrior;
			case MAGE:
				return Assets.SPLASH_mage;
			case ROGUE:
				return Assets.SPLASH_rogue;
			case HUNTRESS:
				return Assets.SPLASH_tank;
		}
		return null;
	}

	public String banner() {
		switch (this) {
			case WARRIOR:
				return Assets.BANNER_WARRIOR;
			case MAGE:
				return Assets.BANNER_MAGE;
			case ROGUE:
				return Assets.BANNER_ROGUE;
			case HUNTRESS:
				return Assets.BANNER_TANK;
		}
		return null;
	}

	public String portrait() {
		switch (this) {
			case WARRIOR:
				return Assets.PORTRAIT_warrior;
			case MAGE:
				return Assets.PORTRAIT_mage;
			case ROGUE:
				return Assets.PORTRAIT_rogue;
			case HUNTRESS:
				return Assets.PORTRAIT_huntress;
		}
		return null;
	}
	
	public String[] perks() {
		switch (this) {
		case WARRIOR:
			return WAR_PERKS;
		case MAGE:
			return MAG_PERKS;
		case ROGUE:
			return ROG_PERKS;
		case HUNTRESS:
			return HUN_PERKS;
		}
		return null;
	}

	private static final String CLASS	= "class";
	
	public void storeInBundle( Bundle bundle ) {
		bundle.put( CLASS, toString() );
	}
	
	public static HeroClass restoreInBundle( Bundle bundle ) {
		String value = bundle.getString( CLASS );
		return value.length() > 0 ? valueOf( value ) : ROGUE;
	}
}
