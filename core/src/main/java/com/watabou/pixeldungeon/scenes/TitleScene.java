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
package com.watabou.pixeldungeon.scenes;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLES20;

import com.watabou.noosa.BitmapText;
import com.watabou.noosa.Camera;
import com.watabou.noosa.Game;
import com.watabou.noosa.Image;
import com.watabou.noosa.audio.Music;
import com.watabou.noosa.audio.Sample;
import com.watabou.noosa.ui.Button;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.PixelDungeon;
import com.watabou.pixeldungeon.effects.BannerSprites;
import com.watabou.pixeldungeon.effects.Fireball;
import com.watabou.pixeldungeon.ui.ExitButton;
import com.watabou.pixeldungeon.ui.PrefsButton;

public class TitleScene extends PixelScene {

	private static final String TXT_PLAY		= "Enter the dungeon";
	private static final String TXT_HIGHSCORES	= "";
	private static final String TXT_BADGES		= "";
	private static final String TXT_ABOUT		= "";

	@Override
	public void create() {
		super.create();
		Music.INSTANCE.play( Assets.THEME, true );
		Music.INSTANCE.volume( 1f );
		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;
		float topPadding = 12;
		float bottomPadding = 24;

		Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON );
		add( title );

		title.x = (w - title.width()) / 2;
		title.y = topPadding;

		placeTorch( title.x + 18, title.y + 20 );
		placeTorch( title.x + title.width - 18, title.y + 20 );

		Image signs = new Image( BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON_SIGNS ) ) {
			private float time = 0;
			@Override
			public void update() {
				super.update();
				am = (float)Math.sin( -(time += Game.elapsed) );
			}
			@Override
			public void draw() {
				GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE );
				super.draw();
				GLES20.glBlendFunc( GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA );
			}
		};
		signs.x = title.x;
		signs.y = title.y;
		add( signs );

		DashboardItem btnBadges = new DashboardItem( TXT_BADGES, 3 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( BadgesScene.class );
			}
		};
		add( btnBadges );

		DashboardItem btnAbout = new DashboardItem( TXT_ABOUT, 1 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( AboutScene.class );
			}
		};
		add( btnAbout );

		clickArea btnPlay = new clickArea( TXT_PLAY) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( StartScene.class );
			}
		};
		add( btnPlay );

		DashboardItem btnHighscores = new DashboardItem( TXT_HIGHSCORES, 2 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( RankingsScene.class );
			}
		};
		add( btnHighscores );

		btnHighscores.setPos( (w - 60) / 2, h - bottomPadding);
		btnBadges.setPos( btnHighscores.right() + 12, h - bottomPadding);
		btnAbout.setPos( btnBadges.right() + 12, h - bottomPadding);
		btnPlay.setPos( (w - btnPlay.width()) / 2, btnHighscores.top() - btnPlay.height());

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

	private void placeTorch( float x, float y ) {
		Fireball fb = new Fireball();
		fb.setPos( x, y );
		add( fb );
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
		public static final float H = 100;
		public static final float W = 200;
		private BitmapText label;

		public clickArea(String text) {
			super();
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
		}
	}
}
