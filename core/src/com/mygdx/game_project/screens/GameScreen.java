package com.mygdx.game_project.screens;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
	public Player player;
	public ArrayList<Enemy> enemies;
	public ArrayList<Enemy> delEnemies;
	public ArrayList<RaidBoss> raidBoss;
	public ArrayList<RaidBoss> delRaidBoss;
	public ArrayList<Objects> objects;
	public ArrayList<Objects> delObjects;
	public ArrayList<Bullets> delBullets, delBulletsBoss;
	public OrthogonalTiledMapRenderer tmr;
	public TiledMap tiledMap;
	public TiledCollisions tiledCollisions;
	public static String mapRoute;
	public enum State {
		RUNNING,
		PAUSED,
		END
	}
	public static State state = State.RUNNING;
	public SettingsScreen settingsScreen;
	public EndScreen endScreen;
	public TextureRegion playerBulletTexture = new TextureAtlas("Player/Samurai.atlas").findRegion("shuriken");

	//endregion

	public GameScreen(MainClass mainClass, boolean isFirst, int map) {
		this.mainClass = mainClass;
		this.isFirst = isFirst;

		Gdx.app.log("INFO/MAP", "" + PlayerData.mapCount);

		if (map == -1) {
			mapRoute = "Maps\\StartMap.tmx";
		} else {
			if (PlayerData.mapCount == 5) {
				mapRoute = "Maps\\BossMap.tmx";
			} else {
				mapRoute = "Maps\\Map"+map+".tmx";
			}
		}

		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		debugRenderer = new Box2DDebugRenderer();
		debugRenderer.setDrawBodies(false);

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
		raidBoss = new ArrayList<>();
		delRaidBoss = new ArrayList<>();
		delBulletsBoss = new ArrayList<>();
		spawnEntities();

		batch = new SpriteBatch();

		System.out.println(camera.position.x + " : " + camera.position.y);

		Gdx.app.log("INFO/DIMENSIONS",Gdx.graphics.getWidth() + " : " + Gdx.graphics.getHeight());
		Gdx.app.log("INFO/PPU", "" + PPU);

		touchpad = controller.createTouchpad();
		stage.addActor(touchpad);
		UIScreen.initUI(stage);

		input = new Input(world, player, enemies, raidBoss, stage);
		settingsScreen = new SettingsScreen(prefs, viewport, mainClass, false);
		endScreen = new EndScreen(prefs, viewport, mainClass);
	}

	/**
	 * Crea las entidades que contiene el mapa
	 */
	public void spawnEntities() {
		for (MapObject mapObject : tiledMap.getLayers().get("enemies").getObjects()) {
			if (mapObject.getProperties().get("state", Integer.class) == 0) {
				enemies.add(new Enemy(world, new Vector2(mapObject.getProperties().get("x", Float.class) * 3, mapObject.getProperties().get("y", Float.class) * 3),
						32,32,
						mapObject.getProperties().get("speed", Float.class), mapObject.getProperties().get("dmg", Float.class),
						mapObject.getProperties().get("armor", Float.class), mapObject.getProperties().get("hp", Float.class)));
			} else {
				raidBoss.add(new RaidBoss(world, new Vector2(mapObject.getProperties().get("x", Float.class) * 3, mapObject.getProperties().get("y", Float.class) * 3),
						75,75));
			}
		}
		if (isFirst) {
			player = new Player(world, camera, mainClass);
		} else {
			player = new Player(world, PLAYER_INIT_POS, 50,50,
					PlayerData.speed, PlayerData.atk, PlayerData.armor, PlayerData.hp,
					camera, mainClass, PlayerData.atkSpeed);
		}
	}

	@Override
	public void render (float delta) {
		world.step(1 / 60f, 6, 2);
		camera.update();

		switch (state) {
			case RUNNING:
				input.setInpProcessors();
				update(delta);
				break;

			case PAUSED:
				settingsScreen.render(delta);
				break;

			case END:
				endScreen.render(delta);
				break;
		}
	}

	/**
	 * Actualiza el comportamiento general del juego
	 * @param delta
	 */
	public void update(float delta) {
		tmr.setView((OrthographicCamera) viewport.getCamera());
		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Input.movementInput(delta, player, touchpad);
		Input.atackInput(delta, player.getAtkSpeed());
		for (Enemy enemy : enemies) {
			if (!enemy.isAlive()) {
				prefs.putInteger("enemiesKilled", prefs.getInteger("enemiesKilled") + 1);
			}
		}
		prefs.flush();

		PlayerData.setData(player.getSpeed(), player.getAtk(), player.getArmor(), player.getHp(), player.getAtkSpeed());

		for (Enemy enemy : enemies) {
			enemy.updateBehavior(delta, player);
		}
		for (RaidBoss boss : raidBoss) {
			boss.update(delta, player);
		}

		if ((enemies.isEmpty() && raidBoss.isEmpty()) && !tiledCollisions.isDoorCreated) {
			tiledCollisions = new TiledCollisions(world, tiledMap.getLayers().get("doors").getObjects());
			for (MapObject mapObject : tiledMap.getLayers().get("chest").getObjects()) {
				objects.add(new Objects(world, new Vector2(mapObject.getProperties().get("x", Float.class) * 3, mapObject.getProperties().get("y", Float.class) * 3),
						32, 32));
			}
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
		for (RaidBoss boss : raidBoss) {
			boss.draw(batch);
		}
		player.draw(batch);

		batch.end();

		deleteBodies();
	}

	/**
	 * Borra los cuerpos fisicos
	 */
	public void deleteBodies() {
		if (!player.isAlive()) {
			world.destroyBody(player.getBody());
		}

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
				world.destroyBody(bullet.getBody());
				delBullets.add(bullet);
			}
		}
		Input.getBullets().removeAll(delBullets);

		for (RaidBoss boss : raidBoss) {
			if (!boss.isAlive()) {
				world.destroyBody(boss.getBody());
				delRaidBoss.add(boss);
			}

			for (Bullets bullet : boss.getBullets()) {
				if (!boss.isAlive()) bullet.setAlive(false);
				if (!bullet.isAlive()) {
					world.destroyBody(bullet.getBody());
					delBulletsBoss.add(bullet);
				}
			}
			boss.getBullets().removeAll(delBulletsBoss);
		}
		raidBoss.removeAll(delRaidBoss);
	}

	@Override
	public void dispose () {
		world.dispose();
		tiledMap.dispose();
		tmr.dispose();
		batch.dispose();
		stage.dispose();
		settingsScreen.dispose();
		Gdx.input.setInputProcessor(null);
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
		stage.getViewport().update(width, height);
		settingsScreen.resize(width, height);
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
