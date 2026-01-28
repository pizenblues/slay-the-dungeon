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

import android.graphics.Color;

import java.nio.Buffer;
import java.nio.FloatBuffer;

import com.watabou.gltextures.Gradient;
import com.watabou.gltextures.SmartTexture;
import com.watabou.glwrap.Quad;
import com.watabou.noosa.Camera;
import com.watabou.noosa.ColorBlock;
import com.watabou.noosa.Game;
import com.watabou.noosa.Group;
import com.watabou.noosa.Image;
import com.watabou.noosa.NoosaScript;
import com.watabou.noosa.TextureFilm;
import com.watabou.noosa.Visual;
import com.watabou.noosa.audio.Music;
import com.watabou.pixeldungeon.Assets;
import com.watabou.pixeldungeon.Badges;
import com.watabou.pixeldungeon.Dungeon;
import com.watabou.pixeldungeon.actors.hero.HeroClass;
import com.watabou.pixeldungeon.ui.Archs;
import com.watabou.pixeldungeon.ui.RedButton;
import com.watabou.utils.Point;
import com.watabou.utils.Random;

public class SurfaceScene extends PixelScene {
	private static final int FRAME_WIDTH	= 255;
	private static final int FRAME_HEIGHT	= 320;
	private static final int FRAME_MARGIN_TOP	= 9;
	private static final int FRAME_MARGIN_X		= 4;
	private static final int BUTTON_HEIGHT	= 20;
	private static final int BUTTON_WIDTH	= 64;
	private static final int SKY_WIDTH	= 120;
	private static final int SKY_HEIGHT	= 150;

	private Camera viewport;
	@Override
	public void create() {

		super.create();

		Music.INSTANCE.play( Assets.EXTERIOR, true );
		Music.INSTANCE.volume( 1f );

		uiCamera.visible = false;

		int w = Camera.main.width;
		int h = Camera.main.height;

		Archs archs = new Archs();
		archs.reversed = true;
		archs.setSize( w, h );
		add( archs );

		float vx = align( (w - SKY_WIDTH) / 2 );
		float vy = align( (h - SKY_HEIGHT - BUTTON_HEIGHT) / 2 );

		Point s = Camera.main.cameraToScreen( vx, vy );
		viewport = new Camera( s.x, s.y, SKY_WIDTH, SKY_HEIGHT, defaultZoom );
		Camera.add( viewport );

		Group window = new Group();
		window.camera = viewport;
		add( window );

		Sky sky = new Sky( true );
		sky.scale.set( SKY_WIDTH, SKY_HEIGHT );
		window.add( sky );

		Cloud cloud = new Cloud(21);
		window.add( cloud );

		Image frame = new Image( Assets.SURFACE );
		frame.frame( 0, 0, FRAME_WIDTH, FRAME_HEIGHT );
		frame.scale.set(0.5f);
		frame.x = vx - FRAME_MARGIN_X;
		frame.y = vy - FRAME_MARGIN_TOP;
		add( frame );

		RedButton gameOverButton = new RedButton( "Leave dungeon" ) {
			protected void onClick() {
				Game.switchScene( TitleScene.class );
			}
		};

		gameOverButton.setSize( BUTTON_WIDTH, BUTTON_HEIGHT );
		gameOverButton.setPos( ((w - BUTTON_WIDTH) / 2), frame.y + 16 + (frame.height / 2) );
		add( gameOverButton );

		Avatar a = new Avatar( Dungeon.hero.heroClass.title());
		//Avatar a = new Avatar( "tank");
		a.scale.set(0.6f);
		a.x = PixelScene.align( (SKY_WIDTH - (a.width * 0.6f)) / 2 );
		a.y = (gameOverButton.top() - (a.height * 0.6f));
		add( a );

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

		private ColorBlock skyBackground;

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
			speed.x = 8;
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

