package inf112.skeleton.app.controller;

import inf112.skeleton.app.entity.Enemy;
import inf112.skeleton.app.level.Level;

public class WaveEnemyFactory implements EnemyFactory{
    private final String zombieChars;
    private int counter = 0;

    public WaveEnemyFactory(String zombieChars) {
        this.zombieChars = zombieChars;
    }

    /**
     * Creates a new zombie according to the string of zombie types
     * @param level used to give the enemy access to the directions it should follow
     * @return new zombie
     */
    @Override
    public Enemy getNext(Level level, float speed, float health, float delay) {
        char zombie = zombieChars.charAt(counter);
        counter = (counter + 1) % zombieChars.length();
        return Enemy.newEnemy(zombie, level, speed, health, delay);
    }
}
