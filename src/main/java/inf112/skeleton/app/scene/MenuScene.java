package inf112.skeleton.app.scene;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import inf112.skeleton.app.util.GameConstants;
import inf112.skeleton.app.util.GameSettings;
import inf112.skeleton.app.util.MusicManager;

public class MenuScene extends AbstractGameScene {
    private Stage stage;
    private Skin uimenuskin;
    //BUTTONS
    private Button playButton;
    private Button exitButton;
    private Button optionsButton;
    private Button howToPlayButton;
    private Image image;

    /**
     * Creates the scene where user can choose to play, see how to play, change options or exit the game
     * @param game the game
     */
    public MenuScene(Game game) {
        super(game);
    }

    private void build() {
        uimenuskin = new Skin(Gdx.files.internal(GameConstants.SKIN_UI),
                new TextureAtlas(GameConstants.TEXTURE_ATLAS_UI));

        Table layerBackground = buildBg();
        Table layerControls = buildControls();

        stage.clear();
        Stack stack = new Stack();
        stage.addActor(stack);
        stack.setSize(GameConstants.UI_WIDTH, GameConstants.UI_HEIGHT);
        stack.add(layerBackground);
        stack.add(layerControls);

    }

    private Table buildBg() {
        Table layer = new Table();
        layer.setFillParent(true);

        // + Background
        image = new Image(uimenuskin, "background");
        image.setScaling(Scaling.stretch);
        image.setFillParent(true);
        layer.add(image).expand().fill();
        return layer;
    }

    private Table buildControls() {
        Table layer = new Table();

        // + Play Button
        playButton = new Button(uimenuskin, "play");
        layer.add(playButton);
        playButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onPlayClicked();
            }
        });
        layer.row();

        // How to play button
        howToPlayButton = new Button(uimenuskin, "tutorial");
        layer.add(howToPlayButton);
        howToPlayButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onHowToPlayClicked();
            }
        });
        layer.row();

        //+ Options Button
        optionsButton = new Button(uimenuskin, "options");
        layer.add(optionsButton);
        optionsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onOptionsClicked();
            }
        });
        layer.row();

        //+ exit button
        exitButton = new Button(uimenuskin, "exit");
        layer.add(exitButton);
        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onExitClicked();
            }
        });
        return layer;
    }

    private void onExitClicked() {
        System.exit(0);
    }

    private void onPlayClicked () {
        game.setScreen(new MapSelectionScene(game));
    }

    private void onOptionsClicked () {
        game.setScreen(new OptionScene(game));
    }

    private void onHowToPlayClicked() {
        game.setScreen(new HowToPlayScene(game));
    }

    @Override
    public void render (float deltaTime) {
        Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(deltaTime);
        stage.draw();
    }

    @Override
    public void resize (int width, int height) {
        stage.getViewport().update(width, height, true);
    }
    @Override
    public void hide () {
        stage.dispose();
        uimenuskin.dispose();
    }
    @Override
    public void show () {
        GameSettings preference = GameSettings.instance;
        stage = new Stage(new StretchViewport(GameConstants.UI_WIDTH, GameConstants.UI_HEIGHT));
        Gdx.input.setInputProcessor(stage);
        build();
        preference.load();
        MusicManager.changeMusicVolume();
        if(!GameSettings.getMusic()) {
            MusicManager.stopCurrentMusic();
        } else {
            MusicManager.play("menumusic.ogg", true);
        }

    }
    @Override public void pause () { }
}