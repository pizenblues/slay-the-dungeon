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

import com.watabou.noosa.Game;
import com.watabou.noosa.NinePatch;
import com.watabou.noosa.ui.Button;
import com.watabou.pixeldungeon.Chrome;

public class Tag extends Button {
	protected NinePatch bg;
	protected float lightness = 0;
	
	public Tag() {
		super();
	}
	
	@Override
	protected void createChildren() {
		super.createChildren();
		bg = Chrome.get( Chrome.Type.NOTIF );
		add( bg );
	}
	
	@Override
	protected void layout() {
		super.layout();
		bg.x = x;
		bg.y = y;
		bg.size( width, height );
	}
	
	public void flash() {
		lightness = 1f;
	}
	
	@Override
	public void update() {
		super.update();
	}
}
