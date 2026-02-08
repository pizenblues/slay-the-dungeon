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
import com.pizenblues.pixeldungeon.effects.FireDoor;
import com.pizenblues.pixeldungeon.windows.WndMenu;


public class TitleScene extends PixelScene {
	private static final String TXT_PLAY = "Tap to Start";
	private static final String TXT_BUTTON = "";
	private FireDoor doorAnimation;
    private MenuButton btnMenu;

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
		title.y = PixelDungeon.landscape() ? 8 : doorAnimation.y - title.height() - 20;

		clickArea btnPlay = new clickArea( TXT_PLAY);
		float titlePositionY = doorAnimation.y;
		btnPlay.setPos( (w - btnPlay.width()) / 2, titlePositionY );
		add( btnPlay );

        btnMenu = new MenuButton();
        add( btnMenu );
        btnMenu.setPos( w - btnMenu.width(), 1 );

		fadeIn();
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
            image = new Image(Assets.STATUS, 113, 2, 14, 15);
            add(image);
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
            parent.add( new WndMenu() );
        }
    }
}
