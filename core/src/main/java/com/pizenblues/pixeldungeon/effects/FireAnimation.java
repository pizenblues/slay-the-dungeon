package com.pizenblues.pixeldungeon.effects;

import com.pizenblues.noosa.MovieClip;
import com.pizenblues.noosa.TextureFilm;
import com.pizenblues.pixeldungeon.Assets;

public class FireAnimation extends MovieClip{
    private static boolean second = false;

    public FireAnimation() {
        this(second);
        second = !second;
    }

    public FireAnimation(boolean second) {
        texture(Assets.FIRE );
        TextureFilm frames = new TextureFilm( texture, 377, 256 );
        MovieClip.Animation anim = new MovieClip.Animation( 12, true );
        anim.frames( frames, 0,1,2,3,4,5,6,7);
        play( anim );
    }
}
