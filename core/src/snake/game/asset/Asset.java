package snake.game.asset;

import com.badlogic.gdx.assets.AssetManager;
// import com.badlogic.gdx.assets.loaders.FileHandleResolver;
// import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
// import com.badlogic.gdx.graphics.Color;
// import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class Asset {
    private static Asset instance = new Asset();
    private AssetManager assetManager = new AssetManager();

    public static final String SNAKE_PACK = "images/snake.pack";
    public static final String MEMO_SOUND = "sounds/8bit_bg.mp3";
    public static final String GAME_OVER_SOUND = "sounds/game_over.mp3";
    public static final String EAT_FOOD_SOUND = "sounds/eat_food.wav";
    public static final String CRASH_SOUND = "sounds/crash.ogg";

    private Asset() {

    }

    public static Asset instance() {
        return instance;
    }

    public void loadAsset() {
        loadSounds();
        loadImages();
        assetManager.finishLoading();
    }

    private void loadImages() {
        assetManager.load(SNAKE_PACK, TextureAtlas.class);
    }

    private void loadSounds() {
        assetManager.load(MEMO_SOUND, Music.class);
        assetManager.load(GAME_OVER_SOUND, Music.class);
        assetManager.load(EAT_FOOD_SOUND, Sound.class);
        assetManager.load(CRASH_SOUND, Sound.class);

    }

    public <T> T get(String filename) {
        return assetManager.get(filename);
    }

    public Sprite getSprite(String name) {
        TextureAtlas atlas = get(Asset.SNAKE_PACK);
        return atlas.createSprite(name);
    }

    public void dispose() {
        assetManager.dispose();
    }

}
