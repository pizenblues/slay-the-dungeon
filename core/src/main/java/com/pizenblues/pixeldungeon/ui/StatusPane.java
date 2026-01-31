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
package com.pizenblues.pixeldungeon.ui;

import com.pizenblues.input.Touchscreen.Touch;
import com.pizenblues.noosa.BitmapText;
import com.pizenblues.noosa.Camera;
import com.pizenblues.noosa.Image;
import com.pizenblues.noosa.NinePatch;
import com.pizenblues.noosa.TouchArea;
import com.pizenblues.noosa.audio.Sample;
import com.pizenblues.noosa.particles.BitmaskEmitter;
import com.pizenblues.noosa.particles.Emitter;
import com.pizenblues.noosa.ui.Button;
import com.pizenblues.noosa.ui.Component;
import com.pizenblues.pixeldungeon.Assets;
import com.pizenblues.pixeldungeon.Dungeon;
import com.pizenblues.pixeldungeon.effects.particles.BloodParticle;
import com.pizenblues.pixeldungeon.items.keys.IronKey;
import com.pizenblues.pixeldungeon.scenes.GameScene;
import com.pizenblues.pixeldungeon.scenes.PixelScene;
import com.pizenblues.pixeldungeon.windows.WndGame;
import com.pizenblues.pixeldungeon.windows.WndHero;
import com.pizenblues.utils.PointF;

public class StatusPane extends Component {
	private NinePatch shield;
	private Emitter blood;
	private Image hp;
	private Image exp;
	private int lastKeys = -1;
	private int currentSTR = 0;
	private float currentHealth = 0;
	private BitmapText depth;
	private BitmapText keys;
	private BitmapText strength;
	private DangerIndicator danger;
	private LootIndicator loot;
	private ResumeButton resume;
	private BuffIndicator buffs;
	private Compass compass;
	private MenuButton btnMenu;
	private Image heroImage;

	@Override
	protected void createChildren() {
		shield = new NinePatch( Assets.STATUS, 105, 0, 21, 0 );
		add( shield );
		
		add( new TouchArea( 0, 0, 50, 50 ) {
			@Override
			protected void onClick( Touch touch ) {
				Sample.INSTANCE.play( Assets.SND_CLICK );
				Image sprite = Dungeon.hero.sprite;
				if (!sprite.isVisible()) {
					Camera.main.focusOn( sprite );
				}
				GameScene.show( new WndHero() );
			};			
		} );

		//portrait = new Image();
		//portrait.texture(Dungeon.hero.heroClass.portrait());
		//portrait.frame( 0, 0, 44, 40 );
		//portraitDefault = new Image( Dungeon.hero.heroClass.portrait(), 0, 0, 48, 40 );

		heroImage = new Image();
		heroImage.texture(Dungeon.hero.heroClass.portrait());
		updatePortrait(Dungeon.hero.HP, false);
		heroImage.x = 2;
		heroImage.y = 2;
		add(heroImage);
		
		btnMenu = new MenuButton();
		add( btnMenu );


		blood = new BitmaskEmitter( heroImage );
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
		
		depth = new BitmapText("FLOOR: "+Integer.toString( Dungeon.depth ), PixelScene.font1x );
		depth.scale = new PointF(0.75f, 0.75f);
		depth.hardlight( 0xCACFC2 );
		depth.measure();
		add( depth );
		
		Dungeon.hero.belongings.countIronKeys();
		keys = new BitmapText( PixelScene.font1x );
		keys.scale = new PointF(0.75f, 0.75f);
		keys.hardlight( 0xCACFC2 );
		add( keys );

		strength = new BitmapText( PixelScene.font1x );
		strength.scale = new PointF(0.75f, 0.75f);
		strength.hardlight( 0xFFEBA4 );
		add( strength );
		
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

		buffs.setPos( 3, 44 );

		compass.x = 35;
		compass.y = 28;

		hp.x = 58;
		hp.y = 2;

		exp.x = 58;
		exp.y = 7;

		strength.x = 48;
		strength.y = 12;

		keys.x = 58;
		keys.y = 12;

		depth.x = 78;
		depth.y = 12;

		layoutTags();

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

	private void updatePortrait(float health, boolean leveling){
		if(leveling){
			heroImage.frame( 0, 40, 48, 40 );
		}else{
			if (health < 0.25f) {
				heroImage.frame( 144, 0, 48, 40 );
			} else if (health < 0.55f) {
				blood.on = true;
				heroImage.frame( 96, 0, 48, 40);
			}else if(health < 0.85f){
				heroImage.frame( 48, 0, 48, 40 );
			} else {
				heroImage.frame( 0, 0, 48, 40 );
			}
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

		hp.scale.x = health;
		exp.scale.x = experience;

		if (health != currentHealth){
			updatePortrait(health, false);
			currentHealth = health;
		}else if(Dungeon.hero.isLeveling){
			updatePortrait(health , true);
		}
		
		int k = IronKey.curDepthQuantity;
		if (k != lastKeys) {
			lastKeys = k;
			keys.text("KEYS: "+Integer.toString( lastKeys ) );
		}

		int heroSTR = Dungeon.hero.STR();
		if (currentSTR != heroSTR) {
			currentSTR = heroSTR;
			strength.text( ":"+ Integer.toString(currentSTR));
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
