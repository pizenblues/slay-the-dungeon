package com.pizenblues.pixeldungeon.sprites;

import com.pizenblues.noosa.TextureFilm;
import com.pizenblues.pixeldungeon.Assets;

public class dungeonDoorSprite extends CharSprite{

    public dungeonDoorSprite() {
        super();

        texture( Assets.ENTER );

        TextureFilm frames = new TextureFilm( texture, 377, 256 );

        idle = new Animation( 12, true );
        idle.frames( frames, 0,1,2,3);

        play( idle );
    }
}
