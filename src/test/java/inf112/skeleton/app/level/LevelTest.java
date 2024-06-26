package inf112.skeleton.app.level;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import inf112.skeleton.app.controller.TowerController;
import inf112.skeleton.app.map.Board;
import inf112.skeleton.app.util.GameConstants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;

import java.util.Set;

public class LevelTest {
    private static HeadlessApplication application;
    private Level level;
    private Board board;
    private Set<Vector2> mockPathPoints;

    static MockedConstruction<SpriteBatch> batch;
    static MockedConstruction<BitmapFont> font;
    static MockedConstruction<Animation> animation;
    static MockedConstruction<TextureRegion> region;

    @BeforeAll
    static void setupBeforeAll() {
        /*batch = mockConstruction(SpriteBatch.class);
        font = mockConstruction(BitmapFont.class);
        animation = mockConstruction(Animation.class);
        region = mockConstruction(TextureRegion.class);*/

        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        application = new HeadlessApplication(new ApplicationAdapter() {}, config);
        Gdx.gl = Mockito.mock(GL20.class);
        Gdx.gl20 = Gdx.gl;
        Mockito.when(Gdx.gl.glGenTexture()).thenReturn(1);
    }

    @AfterAll
    static void deregister() {
        /*batch.close();
        font.close();
        animation.close();
        region.close();*/

        if (application != null) {
            application.exit();
            application = null;
        }
        Gdx.graphics = null;
        Gdx.gl = null;
        Gdx.gl20 = null;
    }

    @BeforeEach
    public void setUp() {
        level = new Level(1);
    }

    @Test
    public void testInitialConditions() {
        assertEquals(0, level.getScore());
        assertEquals(GameConstants.START_MONEY, level.getMoney());
        assertEquals(GameConstants.REMAINING_HEALTH, level.getUserHealth());
        assertEquals(0, level.getCurrentWave());
        assertNotNull(level.getMap());
    }

    @Test
    public void testAddMoney() {
        int initialMoney = level.getMoney();
        level.addMoney(100);
        assertEquals(initialMoney + 100, level.getMoney());
    }

    @Test
    public void testRemoveMoney() {
        int moneyToBeRemoved = 100;
        level.removeMoney(moneyToBeRemoved);
        assertEquals(GameConstants.START_MONEY - moneyToBeRemoved, level.getMoney());
    }

    @Test
    public void testEnemyKilledUpdates() {
        int initialScore = level.getScore();
        int initialMoney = level.getMoney();
        int initialEnemies = level.getEnemiesKilled();

        level.enemyKilled(100);

        assertEquals(initialScore + GameConstants.SCORE_INCREASE, level.getScore());
        assertEquals(initialMoney + 100, level.getMoney());
        assertEquals(initialEnemies + 1, level.getEnemiesKilled());
    }

    @Test
    public void testRestartGameResetsValues() {
        level.restart();
        testInitialConditions();
    }

    @Test
    public void testRemoveMoneyNotBelowZero() {
        level.removeMoney(GameConstants.START_MONEY + 1);
        assertEquals(GameConstants.START_MONEY, level.getMoney());
    }

    @Test
    public void testRemoveMoneySelectedTowerUpgrade() {
        TowerController mockTowerController = Mockito.mock(TowerController.class);
        when(mockTowerController.isSelectedTowerUpgrade()).thenReturn(true);
        level.setTowerController(mockTowerController);

        level.removeMoney(100);

        assertEquals(GameConstants.START_MONEY - 100, level.getMoney());
    }


    @Test
    public void testEnemyCompletedPath() {
        int initialHealth = level.getUserHealth();
        level.enemyCompletedPath();
        assertEquals(initialHealth - 1, level.getUserHealth());
    }

    @Test
    public void testEnemyCompletedPath0Health() {
        int initialHealth = level.getUserHealth();
        level.enemyCompletedPath();
        level.enemyCompletedPath();
        level.enemyCompletedPath();
        level.enemyCompletedPath();
        level.enemyCompletedPath();
        level.enemyCompletedPath();
        assertEquals(initialHealth - 6, level.getUserHealth());
    }
}
