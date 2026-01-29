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
package com.pizenblues.pixeldungeon.scenes;

import com.pizenblues.noosa.BitmapText;
import com.pizenblues.noosa.Camera;
import com.pizenblues.noosa.Game;
import com.pizenblues.noosa.Image;
import com.pizenblues.noosa.audio.Music;
import com.pizenblues.noosa.audio.Sample;
import com.pizenblues.noosa.particles.BitmaskEmitter;
import com.pizenblues.noosa.ui.Button;
import com.pizenblues.pixeldungeon.Assets;
import com.pizenblues.pixeldungeon.PixelDungeon;
import com.pizenblues.pixeldungeon.effects.BannerSprites;
import com.pizenblues.pixeldungeon.ui.ExitButton;
import com.pizenblues.pixeldungeon.ui.PrefsButton;
import com.pizenblues.pixeldungeon.effects.Speck;
import com.pizenblues.noosa.particles.Emitter;
import com.pizenblues.noosa.TouchArea;
import com.pizenblues.input.Touchscreen.Touch;

public class TitleScene extends PixelScene {
	private static final String TXT_PLAY		= "Tap to Start";
	private static final String TXT_BUTTON = "";
	private Image entranceImage;
	private Emitter emitter;


	@Override
	public void create() {
		super.create();
		Music.INSTANCE.play( Assets.THEME, true );
		Music.INSTANCE.volume( 1f );
		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;
		float padding = 24;

		entranceImage = new Image(Assets.ENTRANCE);
		entranceImage.x = (w - (entranceImage.width() * 0.7f)) / 2;
		entranceImage.y = h/2.5f;
		entranceImage.scale.set(0.7f);
		add(entranceImage);
		emitter = new BitmaskEmitter( entranceImage );
		emitter.start( Speck.factory( Speck.LIGHT ), 0.05f, 100 );
		add( emitter );

		add( new TouchArea(entranceImage) {
			protected void onClick( Touch touch ) {
				emitter.revive();
				emitter.start( Speck.factory( Speck.LIGHT ), 0.05f, 7 );
			};
		} );

		Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON );
		add( title );
		title.x = (w - title.width()) / 2;
		title.y = PixelDungeon.landscape() ? 8 : padding;

		DashboardItem btnHighscores = new DashboardItem(TXT_BUTTON, 2 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( RankingsScene.class );
			}
		};
		btnHighscores.setPos( (w - 60) / 2, h - padding);
		add( btnHighscores );

		DashboardItem btnBadges = new DashboardItem( TXT_BUTTON, 3 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( SurfaceScene.class );
			}
		};
		btnBadges.setPos( btnHighscores.right() + 12, h - padding);
		add( btnBadges );

		DashboardItem btnAbout = new DashboardItem( TXT_BUTTON, 1 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( AboutScene.class );
			}
		};
		btnAbout.setPos( btnBadges.right() + 12, h - padding);
		add( btnAbout );

		clickArea btnPlay = new clickArea( TXT_PLAY);
		float playTextPosition = entranceImage.y + (entranceImage.height() * 0.7f) / 2;
		playTextPosition = PixelDungeon.landscape() ? h/2 : playTextPosition;
		btnPlay.setPos( (w - btnPlay.width()) / 2, playTextPosition );
		add( btnPlay );

		BitmapText version = new BitmapText( "v " + Game.version, font1x );
		version.measure();
		version.hardlight( 0x888888 );
		version.x = w - version.width();
		version.y = h - version.height();
		add( version );

		PrefsButton btnPrefs = new PrefsButton();
		btnPrefs.setPos( w - btnPrefs.width() - 4, 4);
		add( btnPrefs );

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( 4, 4 );
		add( btnExit );

		fadeIn();
	}

	private static class DashboardItem extends Button {
		public static final float SIZE	= 16;
		private static final int IMAGE_SIZE	= 16;
		private Image image;
		private BitmapText label;

		public DashboardItem( String text, int index ) {
			super();

			image.frame( image.texture.uvRect( index * IMAGE_SIZE, 0, (index + 1) * IMAGE_SIZE, IMAGE_SIZE ) );
			this.label.text( text );
			this.label.measure();

			setSize( SIZE, SIZE );
		}

		@Override
		protected void createChildren() {
			super.createChildren();

			image = new Image( Assets.DASHBOARD );
			add( image );

			label = createText( 9 );
			add( label );
		}

		@Override
		protected void layout() {
			super.layout();
			image.x = align( x + (width - image.width()) / 2 );
			image.y = align( y );
			label.x = align( image.x + image.width() + 2 );
			label.y = align( y + 4);
		}

		@Override
		protected void onTouchDown() {
			image.brightness( 1.5f );
			Sample.INSTANCE.play( Assets.SND_CLICK, 1, 1, 0.8f );
		}

		@Override
		protected void onTouchUp() {
			image.resetColor();
		}
	}

	private static class clickArea extends Button {
		public static final float H = 60;
		public static final float W = 200;
        private final String text;
        private BitmapText label;

		public clickArea(String text) {
			super();
            this.text = text;
            this.label.text(text);
			this.label.measure();
			setSize(W, H);
		}

		@Override
		protected void createChildren() {
			super.createChildren();
			label = createText(9);
			add(label);
		}

		@Override
		protected void layout() {
			super.layout();
			label.x = align(x + (width - label.width()) / 2);
			label.y = align(y + (height - label.height()) / 2);
		}

		@Override
		protected void onTouchDown() {
			Sample.INSTANCE.play(Assets.SND_CLICK, 1, 1, 0.8f);
			PixelDungeon.switchNoFade( StartScene.class );
		}
	}
}
