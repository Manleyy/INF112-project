package inf112.skeleton.app.entity;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import inf112.skeleton.app.enums.Direction;
import inf112.skeleton.app.level.Level;
import inf112.skeleton.app.util.GameAssets;
import inf112.skeleton.app.util.GameConstants;
import inf112.skeleton.app.util.MusicManager;

import java.util.LinkedList;


import static inf112.skeleton.app.util.GameConstants.*;


public class Enemy extends GameObject{

    private final char type;
    private float currentHealth;
    private final int reward;
    private float speed;
    private float distanceToTile;
    private Direction currentDirection;
    private final LinkedList<Direction> directionLinkedList;
    private final float spawnDelay;
    private float elapsedTimeStart;

    private boolean alive = true;
    private final HealthBar hpBar;
    private boolean doubleSpeed;
    private final float height;
    public boolean hasEnteredMap;

    /**
     * Creates a new enemy object
     * @param type char representing which type of zombie this is
     * @param x start position on the x-axis
     * @param y start position on the y-axis
     * @param width width of the zombie(gameObject)
     * @param height height of the zombie(gameObject)
     * @param startHealth Start health of the zombie
     * @param directionLinkedList The directions used to reach the end, following the path
     * @param reward money awarded for killing this zombie
     * @param speed start speed of the zombie
     * @param spawnDelay the delay before the zombie get put on the map
     * @param texture the visual zombie texture
     * @param doubleSpeed boolean indicating whether the enemy has double speed active
     */
    public Enemy(char type, float x, float y, float width, float height, float startHealth, LinkedList<Direction> directionLinkedList, int reward, float speed, float spawnDelay, Sprite texture, boolean doubleSpeed){
        super(x, y, width, height);
        this.doubleSpeed = false;
        this.type = type;
        this.height = height;
        this.speed = speed;
        this.directionLinkedList = new LinkedList<>(directionLinkedList);
        this.currentHealth = startHealth;
        this.reward = reward;
        this.sprite = texture;
        this.doubleSpeed = doubleSpeed;

        this.spawnDelay = spawnDelay;
        this.elapsedTimeStart = 0;

        getNextDistance();
        this.hpBar = new HealthBar(x + 5, y + this.height, width - 10, height / 10, currentHealth);
    }

    /**
     * Creates a new zombie according to the char
     * @param type char symbolizing the type of zombie
     * @param level used to access the direction list
     * @param speedMultiplier increases the speed of zombies for each wave
     * @param healthMultiplier increases the health of zombies for each wave
     * @param spawnDelay sets the game time of which the zombie will spawn
     * @param doubleSpeed boolean indicating whether the enemy has double speed active
     * @return new zombie/enemy with these assigned values
     */
    public static Enemy newEnemy(char type, Level level,float speedMultiplier, float healthMultiplier, float spawnDelay, boolean doubleSpeed) {
        return switch(type) {
            case 'R'-> new Enemy(
                    type,
                    START_POS.x,
                    START_POS.y,
                    ENEMY_WIDTH,
                    ENEMY_HEIGHT,
                    (ENEMY_REGULAR_START_HP * healthMultiplier),
                    level.getMap().getDirections(),
                    ENEMY_REGULAR_BOUNTY,
                    (ENEMY_REGULAR_SPEED * speedMultiplier),
                    (spawnDelay),
                    GameAssets.zombieSprite,
                    doubleSpeed
            );
            case 'T' -> new Enemy(
                    type,
                    START_POS.x,
                    START_POS.y,
                    ENEMY_WIDTH,
                    ENEMY_HEIGHT,
                    (ENEMY_TANK_START_HP * healthMultiplier),
                    level.getMap().getDirections(),
                    ENEMY_TANK_BOUNTY,
                    (ENEMY_TANK_SPEED * speedMultiplier),
                    (spawnDelay),
                    GameAssets.tankSprite,
                    doubleSpeed
            );
            case 'Q' -> new Enemy(
                    type,
                    START_POS.x,
                    START_POS.y,
                    ENEMY_WIDTH,
                    ENEMY_HEIGHT,
                    (ENEMY_QUICK_START_HP * healthMultiplier),
                    level.getMap().getDirections(),
                    ENEMY_QUICK_BOUNTY,
                    (ENEMY_QUICK_SPEED * speedMultiplier),
                    (spawnDelay),
                    GameAssets.quickzombieSprite,
                    doubleSpeed
            );
            default -> throw new IllegalArgumentException("No available zombie for: " + type);
        };
    }

    /**
     * Called when an enemy is hit by a bullet.
     * Removes health according to the bullets damage, then checks if the enemy is still alive.
     * Updates the hpBar according to the new health.
     * @param damage the amount of health to remove from the enemy
     */
    public void shot(float damage){
        this.currentHealth -= damage;

        if (this.currentHealth <= 0){
            alive = false;
            isVisible = false;
            MusicManager.playZombieDeathScream();
        } else{
            hpBar.setHealth(currentHealth);
        }
    }

    /**
     * Returns the speed of the enemy.
     * This method is used for testing.
     * @return The speed of the enemy.
     */
    public float getSpeed(){
        return this.speed;
    }

    /**
     * Returns the direction list of the enemy.
     * This method is used for testing.
     * @return The direction list of the enemy.
     */
    public LinkedList<Direction> getDirectionLinkedList(){
        return this.directionLinkedList;
    }

    private void getNextDistance(){
        currentDirection = directionLinkedList.pollFirst();
        if (currentDirection != null){
            switch (currentDirection) {
                case UP:
                case DOWN:
                    distanceToTile = GameConstants.TILE_HEIGHT;
                    break;
                case RIGHT:
                case LEFT:
                    distanceToTile = GameConstants.TILE_WIDTH;
                    break;
            }
        }
    }

    /**
     * Renders the enemy and its health bar.
     * @param batch The SpriteBatch used in the project.
     */
    @Override
    public void render(SpriteBatch batch){
        if (elapsedTimeStart >= spawnDelay) {
            super.render(batch);
            hpBar.render(batch);
        }
    }

    /**
     * Updates the enemy's position and direction according to the elapsed time, speed, and direction.
     * @param elapsedTime The time since the last frame.
     */
    @Override
    public void update(float elapsedTime) {
        super.update(elapsedTime);
        elapsedTimeStart += elapsedTime;
        if (elapsedTimeStart < spawnDelay || currentDirection == null) {
            return;
        }
        if (distanceToTile <= 0){
            getNextDistance();
        }
        if (currentDirection != null){
            float movedDistance = speed * elapsedTime;

            switch (currentDirection) {
                case DOWN:
                    position.y -= movedDistance;
                    distanceToTile -= movedDistance;
                    if (distanceToTile < 0){
                        position.y += distanceToTile;
                    }
                    break;
                case UP:
                    position.y += movedDistance;
                    distanceToTile -= movedDistance;
                    if (distanceToTile < 0){
                        position.y += distanceToTile;
                    }
                    break;
                case LEFT:
                    position.x -= movedDistance;
                    distanceToTile -= movedDistance;
                    break;
                case RIGHT:
                    position.x += movedDistance;
                    distanceToTile -= movedDistance;
                    if (distanceToTile < 0){
                        position.x += distanceToTile;
                    }
                    break;

                default:
                    break;
            }
        }
        hpBar.updatePosition(position.x + 5, position.y + this.height);
    }


    /**
     * @return true of the enemy is alive
     */
   public boolean isAlive(){
        return alive;
   }

    /**
     * @return the bounty reward
     */
    public int getReward(){
        return reward;
    }

    /**
     * Used in testing
     * @return the type of the enemy
     */
    public char getType() {
        return type;
    }

    /**
     * @return enemy's healthBar
     */
    public HealthBar getHpBar(){
        return this.hpBar;
    }

    /**
     * multiplies the speed by two if the enemy does not already have doubleSpeed active
     */
    public void doubleSpeedClicked(){
        if(!this.doubleSpeed){
            speed *= 2;
            this.doubleSpeed = true;
        }
    }

    /**
     * halves the speed if the enemy has doubleSpeed active
     */
    public void normalSpeedClicked(){
        if(this.doubleSpeed){
            speed /= 2;
            this.doubleSpeed = false;
        }
    }

    /**
     * Used in testing
     * @return current health of the enemy
     */
    public float getEnemyHealth() {
        return this.currentHealth;
    }

    /**
     * Needed to let the enemies spawn outside the map without instantly removing them for being our of bounds.
     * Let the enemy know it has entered the map, so that outOfBounds can be handled accordingly.
     */
    public void enemyEnteredMap() {
        this.hasEnteredMap = true;
    }

    /**
     * Used in tests of EnemyController
     * @return true if the enemy is running double speed, false if not.
     */
    public boolean getDoubleSpeed() {
        return doubleSpeed;
    }
}