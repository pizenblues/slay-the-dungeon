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
import com.pizenblues.noosa.Image;
import com.pizenblues.noosa.audio.Music;
import com.pizenblues.noosa.audio.Sample;
import com.pizenblues.noosa.ui.Button;
import com.pizenblues.pixeldungeon.Assets;
import com.pizenblues.pixeldungeon.PixelDungeon;
import com.pizenblues.pixeldungeon.effects.BannerSprites;
import com.pizenblues.pixeldungeon.ui.ExitButton;
import com.pizenblues.pixeldungeon.ui.PrefsButton;
import com.pizenblues.pixeldungeon.effects.FireDoor;


public class TitleScene extends PixelScene {
	private static final String TXT_PLAY = "Tap to Start";
	private static final String TXT_BUTTON = "";
	private FireDoor doorAnimation;

	@Override
	public void create() {
		super.create();

		Music.INSTANCE.play(Assets.THEME, true);
		Music.INSTANCE.volume(1f);

		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;

		doorAnimation = placeAnim();

		Image title = BannerSprites.get( BannerSprites.Type.PIXEL_DUNGEON );
		add( title );
		title.x = (w - title.width()) / 2;
		title.y = PixelDungeon.landscape() ? 0 : doorAnimation.y - title.height() - 20;

		clickArea btnPlay = new clickArea( TXT_PLAY);
		float titlePositionY = doorAnimation.y;
		btnPlay.setPos( (w - btnPlay.width()) / 2, titlePositionY );
		add( btnPlay );

	// buttons - let's turn this into a menu later pls!

		PrefsButton btnPrefs = new PrefsButton();
		btnPrefs.setPos( w - btnPrefs.width() - 4, 5);
		add( btnPrefs );

		ExitButton btnExit = new ExitButton();
		btnExit.setPos( 4, 4 );
		add( btnExit );

		DashboardItem btnRank = new DashboardItem(TXT_BUTTON, 2 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( RankingsScene.class );
			}
		};
		add( btnRank );

		DashboardItem btnBadges = new DashboardItem( TXT_BUTTON, 3 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( BadgesScene.class );
			}
		};
		add( btnBadges );

		DashboardItem btnAbout = new DashboardItem( TXT_BUTTON, 1 ) {
			@Override
			protected void onClick() {
				PixelDungeon.switchNoFade( AboutScene.class );
			}
		};
		add( btnAbout );

		if(PixelDungeon.landscape()){
			btnBadges.setPos( w - btnRank.width() - 4, btnPrefs.bottom() + 8);
			btnRank.setPos( w - btnRank.width() - 4, btnBadges.bottom() + 8);
			btnAbout.setPos( w - btnRank.width() - 4, btnRank.bottom() + 8);
		}else{
			btnRank.setPos( (w - btnRank.width()) / 2, 4);
			btnAbout.setPos( btnRank.right() + 4, 4);
			btnBadges.setPos( btnRank.left() - btnBadges.width() - 4, 4);
		}

		fadeIn();
	}

	private static class DashboardItem extends Button {
		public static final float SIZE	= 14;
		private static final int IMAGE_SIZE	= 14;
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
		public static final float H = 180;
		public static final float W = 256;
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

	private FireDoor placeAnim() {
		FireDoor doorSprite = new FireDoor();
		doorSprite.scale.set(0.6f);
		doorSprite.x = (Camera.main.width - doorSprite.width()) / 2f;
		doorSprite.y = Camera.main.height - doorSprite.height();

		add( doorSprite );
		return doorSprite;
	}
}
