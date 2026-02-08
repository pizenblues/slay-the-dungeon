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

import java.nio.Buffer;
import java.nio.FloatBuffer;

import com.pizenblues.gltextures.Gradient;
import com.pizenblues.gltextures.SmartTexture;
import com.pizenblues.glwrap.Quad;
import com.pizenblues.noosa.BitmapTextMultiline;
import com.pizenblues.noosa.Camera;
import com.pizenblues.noosa.Game;
import com.pizenblues.noosa.Group;
import com.pizenblues.noosa.Image;
import com.pizenblues.noosa.NoosaScript;
import com.pizenblues.noosa.Visual;
import com.pizenblues.noosa.audio.Music;
import com.pizenblues.pixeldungeon.Assets;
import com.pizenblues.pixeldungeon.Badges;
import com.pizenblues.pixeldungeon.Dungeon;
import com.pizenblues.pixeldungeon.PixelDungeon;
import com.pizenblues.pixeldungeon.effects.BannerSprites;
import com.pizenblues.pixeldungeon.ui.PrimaryButton;
import com.pizenblues.utils.Point;
import com.pizenblues.utils.Random;

public class SurfaceScene extends PixelScene {
	private static final int FRAME_WIDTH	= 255;
	private static final int FRAME_HEIGHT	= 320;
	private static final int FRAME_MARGIN_TOP	= 9;
	private static final int FRAME_MARGIN_X		= 4;
	private static final int BUTTON_HEIGHT	= 20;
	private static final int BUTTON_WIDTH	= 64;
	private static final int SKY_WIDTH	= 120;
	private static final int SKY_HEIGHT	= 150;
	private static final String TXT = "You have broken the curse. You are free from the Dungeon's hold, and can, once again, look at the sky.";

	private Camera viewport;
	@Override
	public void create() {

		super.create();

		Music.INSTANCE.play( Assets.EXTERIOR, true );
		Music.INSTANCE.volume( 1f );

		uiCamera.visible = false;
		int w = Camera.main.width;
		int h = Camera.main.height;
		float vx = align( (w - SKY_WIDTH) / 2 );
		float vy = align( (h - SKY_HEIGHT) / 2 );

		Point s = Camera.main.cameraToScreen( vx, vy );
		viewport = new Camera( s.x, s.y, SKY_WIDTH, SKY_HEIGHT, defaultZoom );
		Camera.add( viewport );

		Group window = new Group();
		window.camera = viewport;
		add( window );

		Sky sky = new Sky( true );
		sky.scale.set( SKY_WIDTH, SKY_HEIGHT );
		window.add( sky );

		Cloud cloud = new Cloud(41);
		window.add( cloud );

		Image frame = new Image( Assets.SURFACE );
		frame.frame( 0, 0, FRAME_WIDTH, FRAME_HEIGHT );
		frame.scale.set(0.5f);
		frame.x = vx - FRAME_MARGIN_X;
		frame.y = vy - FRAME_MARGIN_TOP;
		add( frame );

		BitmapTextMultiline text = createMultiline( TXT, 7);
		text.maxWidth = Math.min( w, 100 );
		text.measure();
		text.x = PixelDungeon.landscape() ? (w - text.width() - 8) : align( (w - text.width()) / 2 );
		text.y = PixelDungeon.landscape() ? 8 : h - text.height() - BUTTON_HEIGHT - 24;
		add( text );

		float buttonPositionY = text.y + text.height() + 8;
		float buttonPositionX = PixelDungeon.landscape() ? text.x : (w - BUTTON_WIDTH) / 2;

		PrimaryButton gameOverButton = new PrimaryButton( "Leave dungeon" ) {
			protected void onClick() {
				Game.switchScene( TitleScene.class );
			}
		};
		gameOverButton.setSize( BUTTON_WIDTH, BUTTON_HEIGHT );
		gameOverButton.setPos(buttonPositionX,buttonPositionY);
		add( gameOverButton );

		Avatar characteravatar = new Avatar( Dungeon.hero.heroClass.title());
		//Avatar characteravatar = new Avatar( "tank");
		characteravatar.scale.set(0.6f);
		float avatarTrueWidth = characteravatar.width() * 0.6f;
		characteravatar.x = (w - avatarTrueWidth) / 2;
		characteravatar.y = (frame.y + (frame.height() * 0.5f)) - 5;
		add( characteravatar );

		Image title = BannerSprites.get( BannerSprites.Type.THE_END );
		add( title );
		title.x = (w - title.width()) / 2;
		title.y = 4;

		Badges.validateHappyEnd();

		fadeIn();
	}

	@Override
	public void destroy() {
		Badges.saveGlobal();

		Camera.remove( viewport );
		super.destroy();
	}

	@Override
	protected void onBackPressed() {
	}

	private static class Sky extends Visual {
		private static final int[] day		= {0xFF4b54d4, 0xFF4b54d4};
		private SmartTexture texture;
		private FloatBuffer verticesBuffer;

		public Sky( boolean dayTime ) {
			super( 0, 0, 1, 1 );

			texture = new Gradient( day );

			float[] vertices = new float[16];
			verticesBuffer = Quad.create();
			vertices[2]		= 0.25f;
			vertices[6]		= 0.25f;
			vertices[10]	= 0.75f;
			vertices[14]	= 0.75f;
			vertices[3]		= 0;
			vertices[7]		= 1;
			vertices[11]	= 1;
			vertices[15]	= 0;
			vertices[0] 	= 0;
			vertices[1] 	= 0;
			vertices[4] 	= 1;
			vertices[5] 	= 0;
			vertices[8] 	= 1;
			vertices[9] 	= 1;
			vertices[12]	= 0;
			vertices[13]	= 1;
			((Buffer)verticesBuffer).position( 0 );
			verticesBuffer.put( vertices );
		}

		@Override
		public void draw() {
			super.draw();
			NoosaScript script = NoosaScript.get();
			texture.bind();
			script.camera( camera() );
			script.uModel.valueM4( matrix );
			script.lighting(
					rm, gm, bm, am,
					ra, ga, ba, aa );
			script.drawQuad( verticesBuffer );
		}
	}

	private static class Cloud extends Image {
		public Cloud( float y) {
			super( Assets.SURFACE );

			frame( 0, 322, 550, 122 );

			this.y = y;

			scale.set( 1 - y / SKY_HEIGHT );
			x = Random.Float( SKY_WIDTH + width() ) - width();
			speed.x = 12;
		}

		@Override
		public void update() {
			super.update();
			if (speed.x > 0 && x > SKY_WIDTH) {
				x = -width();
			} else if (speed.x < 0 && x < -width()) {
				x = SKY_WIDTH;
			}
		}
	}

	private static class Avatar extends Image {
		private static final int WIDTH	= 24;
		private static final int HEIGHT	= 28;

		public Avatar( String cl ) {
			super( Assets.SURFACE );
			int characterAxis;

			if (cl == "mage"){
				characterAxis = 406;
			}else if (cl == "rogue"){
				characterAxis = 556;
			}else if (cl == "tank"){
				characterAxis = 706;
			}else{
				characterAxis = 256;
			}

			frame( characterAxis, 0, 150, 130 );
		}
	}
}

