package com.mygdx.game.enemy;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.mygdx.game.Enemy;
import com.mygdx.game.Entity;
import com.mygdx.game.Hero;
import com.mygdx.game.Media;
import com.mygdx.game.Enums.ENTITYTYPE;
import com.mygdx.game.box2d.Box2DHelper;
import com.mygdx.game.box2d.Box2DWorld;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Player;

public class Gobelin extends Entity {
    Enemy enemy;
    public float health, maxHealth; // Current health  // Maximum health
    public int coins, exp;

    // Constructor for Gobelin class

    public Gobelin(Vector3 pos, Box2DWorld box2d){
        type = ENTITYTYPE.ENNEMY; // Set the type of the entity to HERO
        width = 3; // Set the width of the hero
        height = 4; // Set the height of the hero
        this.pos.x = pos.x; // Set the x position of the hero in 3D space
        this.pos.y = pos.y; // Set the y position of the hero in 3D space
        texture = Media.gobelin; // Set the texture of the hero
        speed = 0.5f; // Set the speed of the hero
        coins = 100; // Set the coins of the hero
        exp = 50; // Set the experience

        reset(box2d, pos);
        body = Box2DHelper.createBody(box2d.world, width, height,width, 0, pos, BodyType.DynamicBody);
    }

    public void reset(Box2DWorld box2d, Vector3 pos) {
        this.pos.set(pos);
        body = Box2DHelper.createBody(box2d.world, width, height, width, 0, pos, BodyType.DynamicBody);
    }

    public void test(Enemy enemy){
        this.enemy = enemy;
        this.health = enemy.getHp();
        this.maxHealth = enemy.getMaxhp();
    }

    public void drawHealthBar(SpriteBatch batch) {
        Texture healthBarTexture = new Texture("assets/Heroes/HealthBar.png"); 
        float healthBarHeight = 1; 


        this.health = enemy.getHp();

        if (this.health <= 0) {
            this.health = 0;
        }

        this.maxHealth = enemy.getMaxhp();
        // Calculate the width of the health bar based on the hero's current health
        float healthPercentage = health / maxHealth;
        float healthBarWidth = width * healthPercentage;
        // Draw the health bar
        batch.draw(healthBarTexture, pos.x, pos.y + height + 1, healthBarWidth, healthBarHeight);
    }

    public void removeRectangel(){
        body.destroyFixture(body.getFixtureList().get(0));
    }

    public void follow(Hero target) {

        float distance = Vector3.dst(target.pos.x,target.pos.y,0,this.pos.x,this.pos.y,0);
        if(distance > 5){
            // Calculate the direction towards the target
            float directionX = target.getX() - this.pos.x;
            float directionY = target.getY() - this.pos.y;
        
            this.body.setLinearVelocity(directionX * 0.2f, directionY * 0.2f);
            this.pos.x = body.getPosition().x - width/2;
            this.pos.y = body.getPosition().y - height/4;
            this.setPosition(this.pos.x, this.pos.y);
        }
        else{
            this.body.setLinearVelocity(0, 0);
        }
        
    }

    public void setPosition(float x, float y) {
        this.pos.x = x;
        this.pos.y = y;
    }

    public void test(){

        pos.x = body.getPosition().x - width/2;
        pos.y = body.getPosition().y - height/4;
    }

    public void dropCoins(Hero hero){
        // Drop coins
        hero.coins += coins;
    }

    public void dropExp(Player player){
        // Drop experience
        player.setExperience(player.getExperience() + exp);
    }
}
