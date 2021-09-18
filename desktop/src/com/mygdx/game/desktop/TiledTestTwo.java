package com.mygdx.game.desktop;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;


//This class has been taken from https://gamefromscratch.com/libgdx-tutorial-11-tiled-maps-part-2-adding-a-sprite-and-dealing-with-layers/
//albeit with certain reworks to fit for this project.

public class TiledTestTwo extends ApplicationAdapter implements InputProcessor {
    private Texture img;
    private OrthographicCamera camera;
    private Renderer renderer;
    private Zombie zombie;
    private Player player;
    private SpriteBatch batch;
    private int ispressed = 0;
    private MovableSubject movableSubject = MovableSubject.getInstance();
    private CollisionController collisionController = new CollisionController();


    @Override
    public void create () {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        batch = new SpriteBatch();
        TextureAtlas atlas = new TextureAtlas(Gdx.files.internal("sprites.atlas"));
        TextureAtlas atlasEric = new TextureAtlas(Gdx.files.internal("Eric_sprites.atlas"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false,w,h);
        camera.update();
        player = new Player(atlas,w/2,h/2,3);
        zombie = new Zombie(atlasEric, w/3, h/3, 3);
        renderer = Renderer.getInstance();
        renderer.addSprite(zombie);
        renderer.addSprite(player);
        Gdx.input.setInputProcessor(this);
    }

    @Override
    public void render () {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        renderer.setView(camera);
        renderer.render();
        //Notifies all movable objects about next render
        movableSubject.notifyUpdate();
        player.changePlayerSprite(ispressed);
        collisionController.checkCollisionRectangle(renderer, player);
    }

    @Override
    public boolean keyDown(int keycode) {
        ispressed = keycode;
        return player.getInputProcessor().keyDown(keycode);
    }

    @Override public boolean keyUp(int keycode) {
        return player.getInputProcessor().keyUp(keycode);
    }

    @Override public boolean keyTyped(char character) {

        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 clickCoordinates = new Vector3(screenX,screenY,0);
        Vector3 position = camera.unproject(clickCoordinates);
        player.setPosition(position.x, position.y);
        return true;
    }

    @Override public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

}