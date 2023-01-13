package com.mygdx.game_project.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game_project.MainClass;
import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Objects;
import com.mygdx.game_project.entities.Player;
import com.mygdx.game_project.utils.Input;
import com.mygdx.game_project.utils.TiledCollisions;
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
	private OrthographicCamera camera;
	private ExtendViewport viewport;
	private World world;
	private Vector2 gravity;
	private Box2DDebugRenderer debugRenderer;
	private SpriteBatch batch;

	// Entities
	public Player player;
	public Enemy enemy;
	public Objects objects;

	private OrthogonalTiledMapRenderer tmr;
	private TiledMap tiledMap;

	//endregion

	public GameScreen(MainClass mainClass) {
		this.mainClass = mainClass;


		camera = new OrthographicCamera();
		camera.setToOrtho(false, WORLD_WIDTH+125, WORLD_HEIGHT);
		viewport = new ExtendViewport(WORLD_WIDTH, WORLD_HEIGHT, camera);
		debugRenderer = new Box2DDebugRenderer();

		gravity = new Vector2(0,0);
		world = new World(gravity, false);

		tiledMap = new TmxMapLoader().load("Map1.tmx");
		tmr = new OrthogonalTiledMapRenderer(tiledMap, 3);
		TiledCollisions.parseTiledObject(world, tiledMap.getLayers().get("collisions").getObjects());

		player = new Player(world, new Vector2(camera.position.x, camera.position.y),
				32,32, 1, 1, 3, 10);
		enemy = new Enemy(world, new Vector2(400, 400),
				36,16, 1, 1, 3, 10);
		objects = new Objects(world, new Vector2(200,100), 0f,0f, 5f);

		batch = new SpriteBatch();

		System.out.println(camera.position.x + " : " + camera.position.y);
	}

	@Override
	public void render (float delta) {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tmr.render();
		debugRenderer.render(world, viewport.getCamera().combined.scl(32));

		batch.begin();

		player.draw(batch);

		batch.end();
	}

	public void update(float delta) {
		world.step(1 / 60f, 6, 2);

		camera.update();
		tmr.setView((OrthographicCamera) viewport.getCamera());

		Input.updateInput(delta, player);

		//System.out.println(objects.getPosition().x + " : " + objects.getPosition().y);
		//System.out.println(enemy.getBody().getPosition().x*32 + " : " + enemy.getBody().getPosition().y*32);
	}

	@Override
	public void dispose () {
		world.dispose();
		tiledMap.dispose();
		tmr.dispose();
		batch.dispose();
	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
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
