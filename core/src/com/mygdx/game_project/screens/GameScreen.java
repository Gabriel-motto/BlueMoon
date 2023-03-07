package com.mygdx.game_project.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game_project.MainClass;
import com.mygdx.game_project.entities.*;
import com.mygdx.game_project.utils.*;
import com.mygdx.game_project.utils.Input;

import java.util.ArrayList;

import static com.mygdx.game_project.constants.Constant.*;

/**
 * @// TODO: Menus and UI's
 * @// TODO: Boss room
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
	public boolean isFirst;
	public Preferences prefs = Gdx.app.getPreferences("Scores");

	// Entities
	public Player player;
	public ArrayList<Enemy> enemies;
	public ArrayList<Enemy> delEnemies;
	public ArrayList<Objects> objects;
	public ArrayList<Objects> delObjects;
	public ArrayList<Bullets> delBullets;
	public OrthogonalTiledMapRenderer tmr;
	public TiledMap tiledMap;
	public TiledCollisions tiledCollisions;
	public String mapRoute;
	public enum State {
		RUNNING,
		PAUSED
	}
	public static State state = State.RUNNING;
	public PauseScreen pauseScreen;

	//endregion
	public GameScreen(MainClass mainClass, boolean isFirst, int map) {
		this.mainClass = mainClass;
		this.isFirst = isFirst;

		if (map == -1) {
			mapRoute = "Maps\\StartMap.tmx";
		} else {
			mapRoute = "Maps\\Map"+map+".tmx";
		}

		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		debugRenderer = new Box2DDebugRenderer();

		stage = new Stage(viewport);
		//stage.getCamera().position.x = (WORLD_WIDTH) / 2f;
		controller = new Controller();

		gravity = new Vector2(0,0);
		world = new World(gravity, false);
		world.setContactListener(new CollisionListener());

		tiledMap = new TmxMapLoader().load(mapRoute);
		tmr = new OrthogonalTiledMapRenderer(tiledMap, 3);
		tiledCollisions = new TiledCollisions(world, tiledMap.getLayers().get("collisions").getObjects());

		enemies = new ArrayList<>();
		delEnemies = new ArrayList<>();
		objects = new ArrayList<>();
		delObjects = new ArrayList<>();
		delBullets = new ArrayList<>();
		spawnEntities();

		batch = new SpriteBatch();

		System.out.println(camera.position.x + " : " + camera.position.y);

		Gdx.app.log("INFO/DIMENSIONS",Gdx.graphics.getWidth() + " : " + Gdx.graphics.getHeight());
		Gdx.app.log("INFO/PPU", "" + PPU);

		touchpad = controller.createTouchpad();
		stage.addActor(touchpad);
		UIScreen.initUI(stage);

		pauseScreen = new PauseScreen(prefs, viewport);
	}
	public void spawnEntities() {
		for (MapObject mapObject : tiledMap.getLayers().get("enemies").getObjects()) {
			if (mapObject.getProperties().get("state", Float.class) == 0) {
				enemies.add(new Enemy(world, new Vector2(mapObject.getProperties().get("x", Float.class) * 3, mapObject.getProperties().get("y", Float.class) * 3),
						32,32,
						mapObject.getProperties().get("speed", Float.class), mapObject.getProperties().get("dmg", Float.class),
						mapObject.getProperties().get("armor", Float.class), mapObject.getProperties().get("hp", Float.class)));
			}
		}
		if (isFirst) {
			player = new Player(world, camera, mainClass);
		} else {
			player = new Player(world, PLAYER_INIT_POS, 50,50,
					PlayerData.speed, PlayerData.dmg, PlayerData.armor, PlayerData.hp,
					camera, mainClass, PlayerData.atkSpeed);
		}
	}

	@Override
	public void render (float delta) {
		world.step(1 / 60f, 6, 2);
		camera.update();

		switch (state) {
			case RUNNING:
				input = new Input(world, player, enemies, stage);
				update(delta);
				break;

			case PAUSED:
				pauseScreen.render(delta);
				break;
		}
	}

	public void update(float delta) {
		tmr.setView((OrthographicCamera) viewport.getCamera());
		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Input.movementInput(delta, player, touchpad);
		Input.atackInput(delta, player, player.getAtkSpeed(), enemies, world);
		for (Enemy enemy : enemies) {
			if (!enemy.isAlive()) {
				prefs.putInteger("enemiesKilled", prefs.getInteger("enemiesKilled") + 1);
			}
		}
		prefs.flush();

		PlayerData.setData(player.getSpeed(), player.getDmg(), player.getArmor(), player.getHp(), player.getAtkSpeed());

		for (Enemy enemy : enemies) {
			enemy.updateBehavior(delta, player);
		}

		if (enemies.isEmpty() && !tiledCollisions.isDoorCreated) {
			tiledCollisions = new TiledCollisions(world, tiledMap.getLayers().get("doors").getObjects());
			for (MapObject mapObject : tiledMap.getLayers().get("chest").getObjects()) {
				objects.add(new Objects(world, new Vector2(mapObject.getProperties().get("x", Float.class) * 3, mapObject.getProperties().get("y", Float.class) * 3),
						32, 32));
			}
		}

		if (!player.isAlive()) {
			mainClass.setScreen(new EndScreen(mainClass));
			dispose();
		}

		UIScreen.updateUI(player);

		tmr.render();
		debugRenderer.render(world, viewport.getCamera().combined.scl(32));

		stage.getCamera().update();
		stage.getViewport().apply();
		stage.act(delta);
		stage.draw();

		batch.begin();

		for (Objects delObject : delObjects) {
			delObject.draw(batch);
		}
		for (Enemy enemy : enemies) {
			enemy.draw(batch);
		}
		if (!objects.isEmpty()) {
			for (Objects object : objects) {
				object.draw(batch);
			}
		}
		player.draw(batch);

		batch.end();

		deleteBodies();
	}

	public void deleteBodies() {
		for (Enemy enemy : enemies) {
			if (!enemy.isAlive()) {
				world.destroyBody(enemy.getBody());
				delEnemies.add(enemy);
			}
		}
		for (Enemy delEnemy : delEnemies) {
			if (delEnemy.currentState == Enemy.states.AVOID && !delEnemy.isSpawnedChest()) {
				objects.add(new Objects(world, delEnemy.getPosition(), 32, 32));
				delEnemy.setSpawnedChest(true);
			}
		}
		enemies.removeAll(delEnemies);

		for (Objects object : objects) {
			if (!object.isAlive()) {
				world.destroyBody(object.getBody());
				delObjects.add(object);
			}
		}
		objects.removeAll(delObjects);

		for (Bullets bullet : Input.getBullets()) {
			if (!bullet.isAlive()) {
				delBullets.add(bullet);
				world.destroyBody(bullet.getBody());
			}
		}
		Input.getBullets().removeAll(delBullets);
	}

	@Override
	public void dispose () {
		world.dispose();
		tiledMap.dispose();
		tmr.dispose();
		batch.dispose();
		stage.dispose();
		pauseScreen.dispose();
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		stage.getViewport().update(width, height);
		pauseScreen.resize(width, height);
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
