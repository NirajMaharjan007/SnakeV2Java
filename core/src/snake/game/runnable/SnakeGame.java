package snake.game.runnable;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import snake.game.Scorer;
import snake.game.asset.Asset;
import snake.game.asset.SoundPlayer;
import snake.game.entities.Board;
import snake.game.entities.GameObject;
import snake.game.entities.Snake;

public class SnakeGame {

    private static final int WIDTH = Gdx.graphics.getWidth();
    private static final int HEIGHT = Gdx.graphics.getHeight();

    private Board board;
    private Snake snake;
    private float timeState;
    private BitmapFont font;

    private GameObject food;
    private boolean isGameOver;

    public SnakeGame() {
        TextureAtlas atlas = Asset.instance().get(Asset.SNAKE_PACK);
        snake = new Snake(atlas);
        board = new Board(snake, WIDTH, HEIGHT);
        food = board.generateFood();
        font = new BitmapFont();
        init();
    }

    private void init() {
        SoundPlayer.init();
        SoundPlayer.playMusic(Asset.MEMO_SOUND, true);
    }

    public void update(float delta) {
        if (snake.hasLive()) {
            timeState += delta;
            snake.handleEvents();
            if (timeState >= .09f) {
                snake.moveBody();
                timeState = 0;
            }
            if (snake.isCrash()) {
                snake.reset();
                snake.popLife();
                SoundPlayer.playSound(Asset.CRASH_SOUND, false);
            }
            if (snake.isFoodTouch(food)) {
                SoundPlayer.playSound(Asset.EAT_FOOD_SOUND, false);
                Scorer.score();
                snake.grow();
                food = board.generateFood();
            }
        } else {
            gameOver();
            if (Gdx.input.isKeyJustPressed(Input.Keys.ANY_KEY))
                start();
        }
    }

    private void gameOver() {
        isGameOver = true;
        SoundPlayer.stopMusic(Asset.MEMO_SOUND);
        SoundPlayer.playMusic(Asset.GAME_OVER_SOUND, false);
    }

    private void start() {
        SoundPlayer.playMusic(Asset.MEMO_SOUND, false);
        SoundPlayer.stopMusic(Asset.GAME_OVER_SOUND);

        isGameOver = false;
        snake.reset();
        snake.restoreHealth();
        food = board.generateFood();
        Scorer.reset();
    }

    public void render(SpriteBatch batch) {
        board.render(batch);
        food.draw(batch);
        snake.render(batch);

        font.setColor(Color.BLACK);

        if (isGameOver) {
            font.draw(batch, "GAME OVER", (WIDTH - 100) / 2, (HEIGHT + 100) / 2);
            font.draw(batch, "Press any key to continue", (WIDTH - 250) / 2, (HEIGHT + 50) / 2);
        }

        font.draw(batch, "Score: " + Scorer.getScore(), GameInfo.SCALE / 2,
                GameInfo.SCREEN_HEIGHT - 10);
        font.draw(batch, "Size: " + snake.getBody().size(), GameInfo.SCALE / 2,
                GameInfo.SCREEN_HEIGHT - 40);

    }

}
