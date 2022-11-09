package players;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class Player {
    private float xPosition;
    private float yPosition;
    private float health;
    private float damage;

    public Player(){}
    public Player(float xPosition, float yPosition, float health, float damage){
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.health = health;
        this.damage = damage;
    }
    public float getxPosition() {
        return xPosition;
    }

    public void setxPosition(float xPosition) {
        this.xPosition = xPosition;
    }

    public float getyPosition() {
        return yPosition;
    }

    public void setyPosition(float yPosition) {
        this.yPosition = yPosition;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public float getdamage() {
        return damage;
    }

    public void setdamage(float damage) {
        this.damage = damage;
    }

    public float[] playerData(){
        return(new float[] {xPosition, yPosition, health, damage});
    }
}
