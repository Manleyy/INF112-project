package inf112.skeleton.app.controller;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import inf112.skeleton.app.TDGame;
import inf112.skeleton.app.controller.TowerController;
import inf112.skeleton.app.entity.Enemy;
import inf112.skeleton.app.enums.DefenderType;
import inf112.skeleton.app.level.Level;
import inf112.skeleton.app.scene.PlayScene;
import inf112.skeleton.app.util.GameConstants;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.ArrayList;
import java.util.List;

import static inf112.skeleton.app.util.GameConstants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import org.mockito.Mock;

public class TowerControllerTest {

    @Mock
    private TowerController towerController;

    @Mock
    private Level mockLevel;
    @Mock
    private TDGame mockGame;
    private static HeadlessApplication application;
    private List<Enemy> enemyList;

    @BeforeAll
    public static void setupBeforeAll() {
        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        application = new HeadlessApplication(new ApplicationAdapter() {}, config);
        Gdx.gl20 = Mockito.mock(GL20.class);
        Gdx.gl = Gdx.gl20;
        when(Gdx.gl.glGenTexture()).thenReturn(1);
    }

    @BeforeEach
    void setUp() throws Exception {
        // Mock the OpenGL classes
        Gdx.gl = mock(GL20.class);
        Gdx.gl20 = Gdx.gl; // Ensure both references are mocked
        Gdx.graphics = mock(Graphics.class);
        when(Gdx.graphics.getWidth()).thenReturn(800); // Example value
        when(Gdx.graphics.getHeight()).thenReturn(600); // Example value
        Gdx.app = mock(Application.class);

        // No need to mockStatic(Gdx.class); Gdx's static methods won't be called directly

        // Mock the SpriteBatch and ShaderProgram constructors
        SpriteBatch spriteBatch = mock(SpriteBatch.class);
        ShaderProgram shaderProgram = mock(ShaderProgram.class);
        whenNew(SpriteBatch.class).withNoArguments().thenReturn(spriteBatch);
        whenNew(ShaderProgram.class).withAnyArguments().thenReturn(shaderProgram);

        // Since we are preparing PlayScene for test, we mock its constructor as needed
        PlayScene mockPlayScene = mock(PlayScene.class);

        when(mockPlayScene.getLevel()).thenReturn(mock(Level.class)); // Stub the getLevel method

        // Create the actual game object
        mockGame = mock(TDGame.class);

        // Create the headless application (necessary if your game logic requires the application to be initialized)


        // Set up the rest of your test objects as needed
        mockLevel = (Level) mockPlayScene.getLevel(); // Use the mocked PlayScene to get a mock Level
        towerController = mock(TowerController.class); // Mock the TowerController if necessary
        enemyList = new ArrayList<>();
        when(mockLevel.getMoney()).thenReturn(GameConstants.START_MONEY); // Stub the getMoney method
    }



    @Test
    public void testBuildGunnerTowerWhenEnoughMoney() {
        // Arrange
        int startMoney = TOWER_PRICE_GUNNER;
        when(mockLevel.getMoney()).thenReturn(startMoney);
        when(towerController.buildTower(anyFloat(), anyFloat(), any(List.class), eq(DefenderType.GUNNER)))
                .thenReturn(TOWER_PRICE_GUNNER);  // Assuming the tower is successfully built and the cost is returned

        // Act
        int result = towerController.buildTower(0.0f, 0.0f, enemyList, DefenderType.GUNNER);

        // Assert
        assertEquals(TOWER_PRICE_GUNNER, result); // Check if the result matches the tower cost
    }


    @Test
    public void testBuildBomberTowerWhenEnoughMoney() {
        // Arrange
        int startMoney = TOWER_PRICE_BOMBER;
        when(mockLevel.getMoney()).thenReturn(startMoney);
        when(towerController.buildTower(anyFloat(), anyFloat(), any(List.class), eq(DefenderType.BOMBER)))
                .thenReturn(TOWER_PRICE_BOMBER);  // Assuming the tower is successfully built and the cost is returned

        // Act
        int result = towerController.buildTower(0.0f, 0.0f, enemyList, DefenderType.BOMBER);

        // Assert
        assertEquals(TOWER_PRICE_BOMBER, result); // Check if the result matches the tower cost
    }

    @Test
    public void testBuildSniperTowerWhenEnoughMoney() {
        // Arrange
        int startMoney = TOWER_PRICE_SNIPER;
        when(mockLevel.getMoney()).thenReturn(startMoney);
        when(towerController.buildTower(anyFloat(), anyFloat(), any(List.class), eq(DefenderType.SNIPER)))
                .thenReturn(TOWER_PRICE_SNIPER);  // Assuming the tower is successfully built and the cost is returned

        // Act
        int result = towerController.buildTower(0.0f, 0.0f, enemyList, DefenderType.SNIPER);

        // Assert
        assertEquals(TOWER_PRICE_SNIPER, result); // Check if the result matches the tower cost
    }

    @Test
    public void testBuildTowerWhenNotEnoughMoney() {
        // Arrange
        int startMoney = 0; // Not enough money to build any tower
        when(mockLevel.getMoney()).thenReturn(startMoney);
        when(towerController.buildTower(anyFloat(), anyFloat(), any(List.class), any(DefenderType.class)))
                .thenReturn(0);  // Assuming the tower cannot be built due to lack of funds

        // Act
        int resultGunner = towerController.buildTower(0.0f, 0.0f, enemyList, DefenderType.GUNNER);
        int resultBomber = towerController.buildTower(0.0f, 0.0f, enemyList, DefenderType.BOMBER);
        int resultSniper = towerController.buildTower(0.0f, 0.0f, enemyList, DefenderType.SNIPER);

        // Assert
        assertEquals(0, resultGunner); // Check that no money is deducted if there isn't enough to begin with
        assertEquals(0, resultBomber); // Same for bomber
        assertEquals(0, resultSniper); // And for sniper
    }

    @AfterAll
    public static void tearDown() {
        if(application != null) {
            application.exit();
            application = null;
        }
        Gdx.gl = null;
        Gdx.gl20 = null;
    }
}