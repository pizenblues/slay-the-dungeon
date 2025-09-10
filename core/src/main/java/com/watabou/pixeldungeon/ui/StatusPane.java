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

import com.watabou.input.Touchscreen.Touch;
import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Image;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.TouchArea;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.particles.BitmaskEmitter;
import com.watabou.noosa.particles.Emitter;
import com.watabou.noosa.ui.Button;
import com.watabou.noosa.ui.Component;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.effects.Speck;
import com.watabou.pixeldungeon.effects.particles.BloodParticle;
import com.watabou.pixeldungeon.items.keys.IronKey;
import com.watabou.pixeldungeon.scenes.GameScene;
import com.watabou.pixeldungeon.scenes.PixelScene;
import com.watabou.pixeldungeon.windows.WndGame;
import com.watabou.pixeldungeon.windows.WndHero;
import com.watabou.pixeldungeon.windows.WndBuff;
import com.watabou.utils.PointF;

public class StatusPane extends Component {
	private NinePatch shield;
	private Emitter blood;
	private int lastTier = 0;
	private Image hp;
	private Image exp;
	private int lastLvl = -1;
	private int lastKeys = -1;
	private BitmapText level;
	private BitmapText depth;
	private BitmapText keys;
	private DangerIndicator danger;
	private LootIndicator loot;
	private ResumeButton resume;
	private BuffIndicator buffs;
	private Compass compass;
	private MenuButton btnMenu;
	private Image portrait;

	@Override
	protected void createChildren() {
		shield = new NinePatch( Assets.STATUS, 105, 0, 21, 0 );
		add( shield );
		
		add( new TouchArea( 0, 0, 40, 50 ) {
			@Override
			protected void onClick( Touch touch ) {
				Image sprite = Dungeon.hero.sprite;
				if (!sprite.isVisible()) {
					Camera.main.focusOn( sprite );
				}
				GameScene.show( new WndHero() );
			};			
		} );

		add( new TouchArea( 44, 15, 12, 6 ) {
			@Override
			protected void onClick( Touch touch ) {
				GameScene.show( new WndBuff() );
			};
		} );

		portrait = new Image();
		portrait.texture(Dungeon.hero.heroClass.portrait());
		portrait.frame( 0, 0, 44, 40 );
		portrait.x = 2;
		portrait.y = 2;
		add(portrait);
		
		btnMenu = new MenuButton();
		add( btnMenu );
		
		blood = new BitmaskEmitter( portrait );
		blood.pour( BloodParticle.FACTORY, 0.3f );
		blood.autoKill = false;
		blood.on = false;
		add( blood );
		
		compass = new Compass( Dungeon.level.exit );
		add( compass );
		
		hp = new Image( Assets.HP_BAR );	
		add( hp );
		
		exp = new Image( Assets.XP_BAR );
		add( exp );
		
		level = new BitmapText( PixelScene.font1x );
		level.hardlight( 0xFFEBA4 );
		level.scale = new PointF(0.8f, 0.8f);
		add( level );
		
		depth = new BitmapText( Integer.toString( Dungeon.depth ), PixelScene.font1x );
		depth.scale = new PointF(0.8f, 0.8f);
		depth.hardlight( 0xCACFC2 );
		depth.measure();
		add( depth );
		
		Dungeon.hero.belongings.countIronKeys();
		keys = new BitmapText( PixelScene.font1x );
		keys.scale = new PointF(0.8f, 0.8f);
		keys.hardlight( 0xCACFC2 );
		add( keys );
		
		danger = new DangerIndicator();
		add( danger );
		
		loot = new LootIndicator();
		add( loot );
		
		resume = new ResumeButton();
		add( resume );
		
		buffs = new BuffIndicator( Dungeon.hero );
		add( buffs );
	}
	
	@Override
	protected void layout() {
		height = 32;
		shield.size( width, shield.height );
		
		compass.x = 20;
		compass.y = 42;
		
		hp.x = 58;
		hp.y = 2;

		exp.x = 58;
		exp.y = 7;

		keys.y = 1;

		depth.x = width - 8 - depth.width() - 16;
		depth.y = 7;


		layoutTags();
		
		buffs.setPos( 48, 16 );
		
		btnMenu.setPos( width - btnMenu.width(), 1 );
	}
	
	private void layoutTags() {
		
		float pos = 32;
		
		if (tagDanger) {
			danger.setPos( width - danger.width(), pos );
			pos = danger.bottom() + 1;
		}
		
		if (tagLoot) {
			loot.setPos( width - loot.width(), pos );
			pos = loot.bottom() + 1;
		}
		
		if (tagResume) {
			resume.setPos( width - resume.width(), pos );
		}
	}
	
	private boolean tagDanger	= false;
	private boolean tagLoot		= false;
	private boolean tagResume	= false;
	
	@Override
	public void update() {
		super.update();
		
		if (tagDanger != danger.visible || tagLoot != loot.visible || tagResume != resume.visible) {
			tagDanger = danger.visible;
			tagLoot = loot.visible;
			tagResume = resume.visible;
			layoutTags();
		}
		
		float health = (float)Dungeon.hero.HP / Dungeon.hero.HT;
		float experience = (float)Dungeon.hero.exp / Dungeon.hero.maxExp();

		if(Dungeon.hero.isLeveling){
			portrait.frame( 0, 40, 48, 40 );
			blood.on = false;
		}else{
			if (health < 0.25f) {
				portrait.frame( 144, 0, 48, 40 );
				blood.on = true;
			} else if (health < 0.55f) {
				portrait.frame( 96, 0, 48, 40);
				blood.on = true;
			}else if(health < 0.85f){
				portrait.frame( 48, 0, 48, 40 );
				blood.on = false;
			} else {
				portrait.frame( 0, 0, 48, 40 );
				blood.on = false;
			}
		}

		hp.scale.x = health;
		exp.scale.x = experience;

		if (Dungeon.hero.lvl != lastLvl) {
			if (lastLvl != -1) {
				Emitter emitter = (Emitter)recycle( Emitter.class );
				emitter.revive();
				emitter.pos( 27, 27 );
				emitter.burst( Speck.factory( Speck.STAR ), 12 );
			}
			lastLvl = Dungeon.hero.lvl;
			level.text( "LVL " + Integer.toString( lastLvl ) );
			level.measure();
			level.x = 2;
			level.y = PixelScene.align( 45.0f - level.baseLine() / 2 );
		}
		
		int k = IronKey.curDepthQuantity;
		if (k != lastKeys) {
			lastKeys = k;
			keys.text( Integer.toString( lastKeys ) );
			keys.measure();
			keys.x = width - 8 - keys.width() - 16;
		}
		
		int tier = Dungeon.hero.tier();
		if (tier != lastTier) {
			lastTier = tier;
			//avatar.copy( HeroSprite.avatar( Dungeon.hero.heroClass, tier ) );
		}
	}
	
	private static class MenuButton extends Button {
		private Image image;
		
		public MenuButton() {
			super();
			width = image.width + 4;
			height = image.height + 4;
		}
		
		@Override
		protected void createChildren() {
			super.createChildren();
			image = new Image( Assets.STATUS, 114, 3, 12, 11 );
			add( image );
		}
		
		@Override
		protected void layout() {
			super.layout();
			image.x = x + 2;
			image.y = y + 2;
		}
		
		@Override
		protected void onTouchDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK );
		}
		
		@Override
		protected void onTouchUp() {
			image.resetColor();
		}
		
		@Override
		protected void onClick() {
			GameScene.show( new WndGame() );
		}
	}
}
