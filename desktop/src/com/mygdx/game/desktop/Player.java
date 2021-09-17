package com.mygdx.game.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import static com.badlogic.gdx.Gdx.files;

public class Player extends Sprite implements Movable{
    TextureAtlas textureAtlas;
    boolean leftMove;
    boolean rightMove;
    boolean UpMove;
    boolean DownMove;
    Weapon weapon;

    int speed = 80;

    float x;
    float y;

    public Player(TextureAtlas atlas, float posX, float posY, float scale) {
        super(atlas.getRegions().get(0));
        textureAtlas = atlas;
        this.setPosition(posX, posY);
        this.setScale(scale);
        this.x = posX;
        this.y = posY;
        this.weapon = new Weapon();
        setRegion(textureAtlas.findRegion("Adam_back"));
        MovableSubject movableSubject = MovableSubject.getInstance();
        movableSubject.attach(this);
    }

    public Weapon getWeapon() {return this.weapon;}

    public float getX() {
        return super.getX();
    }
    public float getY(){
        return super.getY();
    }
    public void setPosition(float posX, float posY){
        this.setX(posX);
        this.setY(posY);
    }

    private void setLeftMove(boolean t)
    {
        if(rightMove && t) rightMove = false;
        leftMove = t;

    }
    private void setRightMove(boolean t)
    {
        if(leftMove && t) leftMove = false;
        rightMove = t;
    }

    private void setUpMove(boolean t)
    {
        if(DownMove && t) DownMove = false;
        UpMove = t;
    }

    private void setDownMove(boolean t)
    {
        if(UpMove && t) UpMove = false;
        DownMove = t;
    }

    public void changePlayerSprite(int ispresssed){
        if(ispresssed == 21){
            this.setRegion(this.textureAtlas.findRegion("Adam_left"));
        }
        if(ispresssed == 22){
            this.setRegion(this.textureAtlas.findRegion("Adam_right"));
        }
        if(ispresssed == 19){
            this.setRegion(this.textureAtlas.findRegion("Adam_forward"));
        }
        if(ispresssed == 20){
            this.setRegion(this.textureAtlas.findRegion("Adam_back"));
        }
    }

    private final InputProcessor inputProcessor = new InputAdapter() {
        @Override
        public boolean keyDown(int keycode) {
            switch (keycode)
            {
                case Input.Keys.LEFT:
                    setLeftMove(true);
                    break;
                case Input.Keys.RIGHT:
                    setRightMove(true);
                    break;
                case Input.Keys.UP:
                    setUpMove(true);
                    break;
                case Input.Keys.DOWN:
                    setDownMove(true);
                    break;
                case Input.Keys.G:
                    Shoot(0);
                    break;
            }
            return true;
        }

        @Override
        public boolean keyUp(int keycode) {
            switch (keycode)
            {
                case Input.Keys.LEFT:
                    setLeftMove(false);
                    break;
                case Input.Keys.RIGHT:
                    setRightMove(false);
                    break;
                case Input.Keys.UP:
                    setUpMove(false);
                    break;
                case Input.Keys.DOWN:
                    setDownMove(false);
                    break;
            }
            return true;
        }
    };

    private void Shoot(float angle) {
        weapon.fireWeapon(angle, getX(), getY());
    }

    public InputProcessor getInputProcessor(){
        return inputProcessor;
    }

    @Override
    public void update() {
        if (leftMove)
            translateX(-speed * Gdx.graphics.getDeltaTime());
        if (rightMove)
            translateX(speed * Gdx.graphics.getDeltaTime());
        if (UpMove)
            translateY(speed * Gdx.graphics.getDeltaTime());
        if (DownMove)
            translateY(-speed * Gdx.graphics.getDeltaTime());
    }
}
