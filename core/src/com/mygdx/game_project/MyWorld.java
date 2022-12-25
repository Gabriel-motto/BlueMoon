package com.mygdx.game_project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.mygdx.game_project.entities.Enemy;
import com.mygdx.game_project.entities.Objects;
import com.mygdx.game_project.entities.Player;
import com.mygdx.game_project.utils.CollisionListener;
import com.mygdx.game_project.utils.Input;
import com.mygdx.game_project.utils.TiledCollisions;

/**
 * @// TODO: 22/12/2022 CollisionListener
 * @// TODO: 22/12/2022
 */

/**
 * @author Gabriel Motto Comesaña
 */

public class MyWorld extends ApplicationAdapter {
	//region $Variables

	private OrthographicCamera camera;
	private ExtendViewport viewport;
	private World world;
	private final float WORLD_WIDTH = 640;
	private final float WORLD_HEIGHT = 480;
	private Vector2 gravity;
	private Box2DDebugRenderer debugRenderer;

	// Entities
	public Player player;
	public Enemy enemy;
	public Objects objects;

	private OrthogonalTiledMapRenderer tmr;
	private TiledMap tiledMap;

	//endregion
	@Override
	public void create () {
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
				24,24, 1, 1, 3, 10);
		enemy = new Enemy(world, new Vector2(400, 400),
				36,16, 1, 1, 3, 10);
		objects = new Objects(world, new Vector2(200,100), 0f,0f, 5f);
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		tmr.render();
		debugRenderer.render(world, viewport.getCamera().combined.scl(32));
	}

	public void update(float delta) {
		world.step(1 / 60f, 6, 2);

		camera.update();
		tmr.setView((OrthographicCamera) viewport.getCamera());

		Input.updateInput(delta, player.getBody());
	}

	@Override
	public void dispose () {
		world.dispose();
		tiledMap.dispose();
		tmr.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}
}
