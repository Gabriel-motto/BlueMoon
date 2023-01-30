package com.mygdx.game_project.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game_project.MainClass;
import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Bullets;
import com.mygdx.game_project.entities.Player;
import com.mygdx.game_project.utils.*;
import com.mygdx.game_project.utils.Input;

import java.util.ArrayList;

import static com.mygdx.game_project.constants.Constant.*;

/**
 * @// TODO: 22/12/2022 CollisionListener
 * @// TODO: 22/12/2022
 */

/**
 * @author Gabriel Motto Comesaña
 */

public class GameScreen implements Screen {
	//region $Variables

	final MainClass mainClass;
	public OrthographicCamera camera;
	public ExtendViewport viewport;
	public World world;
	public Vector2 gravity;
	public Box2DDebugRenderer debugRenderer;
	public SpriteBatch batch;
	public Stage stage;
	public Controller controller;
	public Touchpad touchpad;
	public Input input;

	// Entities
	public Player player;
	public Vector2 playerPos;
	public ArrayList<Enemy> enemies;
	public Bullets objects;
	public OrthogonalTiledMapRenderer tmr;
	public TiledMap tiledMap;
	public TiledCollisions tiledCollisions;
	public String mapRoute = "Maps\\Map1.tmx";

	//endregion
	public GameScreen(MainClass mainClass) {
		this.mainClass = mainClass;

		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		debugRenderer = new Box2DDebugRenderer();

		stage = new Stage(viewport);
		stage.getCamera().position.x = (WORLD_WIDTH) / 2f;
		controller = new Controller();

		gravity = new Vector2(0,0);
		world = new World(gravity, false);
		world.setContactListener(new CollisionListener());

		tiledMap = new TmxMapLoader().load(mapRoute);
		tmr = new OrthogonalTiledMapRenderer(tiledMap, 3);
		tiledCollisions = new TiledCollisions(world, tiledMap.getLayers().get("collisions").getObjects());

		player = new Player(world, PLAYER_INIT_POS,
				50,50, 5, 1, 3, 10, camera, mainClass);

		enemies = new ArrayList<>();
		spawnEntities(36,16, 3, 1, 1, 10, Enemy.states.HOSTILE);
//		enemies.add(new Enemy(world, new Vector2(400, 400),
//				36,16, 3, 1, 1, 10, Enemy.states.HOSTILE));
//		enemies.add(new Enemy(world, new Vector2(200, 100),
//				36,16, 5, 1, 2, 10, Enemy.states.SLEEP));
		//objects = new Objects(world, new Vector2(200,100), 5f);

		batch = new SpriteBatch();

		System.out.println(camera.position.x + " : " + camera.position.y);

		Gdx.app.log("INFO/DIMENSIONS",Gdx.graphics.getWidth() + " : " + Gdx.graphics.getHeight());
		Gdx.app.log("INFO/PPU", "" + PPU);

		touchpad = controller.createTouchpad();
		stage.addActor(touchpad);

		TextureAtlas hpBarAtlas = new TextureAtlas("HpBar/hpBar.atlas");
		Image hpBar = new Image(hpBarAtlas.findRegion("hp10"));
		hpBar.setBounds((WORLD_WIDTH)-75*PPU, (WORLD_HEIGHT)-25*PPU, 64*PPU, 16*PPU);
		stage.addActor(hpBar);

		input = new Input(world, player, enemies, stage);
	}
	public void spawnEntities(int width, int height, float speed, float dmg, float armor, float hp, Enemy.states currentState) {
		for (MapObject mapObject : tiledMap.getLayers().get("enemies").getObjects()) {
			enemies.add(new Enemy(world, new Vector2(mapObject.getProperties().get("x", Float.class) * 3, mapObject.getProperties().get("y", Float.class) * 3),
					width, height, speed, dmg, armor, hp, currentState));
		}
	}
	public void InitUI() {

	}
	@Override
	public void render (float delta) {
		update(delta);

		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tmr.render();
		debugRenderer.render(world, viewport.getCamera().combined.scl(32));

		stage.getCamera().update();
		stage.getViewport().apply();
		stage.act(delta);
		stage.draw();

		batch.begin();

		player.draw(batch);

		batch.end();
	}

	/**
	 *
	 * @param delta Tiempo en segundos desde el último render.
	 */
	public void update(float delta) {
		world.step(1 / 60f, 6, 2);

		camera.update();
		tmr.setView((OrthographicCamera) viewport.getCamera());

		Input.movementInput(delta, player, touchpad);
		Input.atackInput(delta, player, enemies, world);
		Input.deleteBullets(world);
		Input.deleteEnemies(world, enemies, player);

		for (Enemy enemy : enemies) {
			enemy.updateBehavior(delta, player);
		}

		if (enemies.isEmpty() && !tiledCollisions.isDoorCreated) {
			tiledCollisions = new TiledCollisions(world, tiledMap.getLayers().get("doors").getObjects());
		}

		if (!player.isAlive()) {
			mainClass.setScreen(new EndScreen(mainClass));
			dispose();
		}

		//System.out.println(objects.getPosition().x + " : " + objects.getPosition().y);
		//System.out.println(enemy.getBody().getPosition().x*32 + " : " + enemy.getBody().getPosition().y*32);
	}
	@Override
	public void dispose () {
		world.dispose();
		tiledMap.dispose();
		tmr.dispose();
		batch.dispose();
		stage.dispose();
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		stage.getViewport().update(width, height);
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}
}
