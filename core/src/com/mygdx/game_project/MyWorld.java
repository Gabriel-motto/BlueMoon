package com.mygdx.game_project;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.bullet.softbody.btSoftBody;
import com.badlogic.gdx.utils.viewport.ExtendViewport;

/**
 * @// TODO: 14/12/2022 Poner nombre
 */

/**
 * @author Gabriel Motto Comesaña
 */

public class MyWorld extends ApplicationAdapter {
	//region $Variables

	private OrthographicCamera camera;
	private ExtendViewport viewport;
	private World world;
	private Vector2 gravity;
	private Box2DDebugRenderer debugRenderer;

	public Body player;
	public Body enemy;

	//endregion
	@Override
	public void create () {
		camera = new OrthographicCamera();
		viewport = new ExtendViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), camera);
		debugRenderer = new Box2DDebugRenderer();

		gravity = new Vector2(0,0);
		world = new World(gravity, false);

		player = createHitbox(0,60,32,18,10, false);
		enemy = createHitbox(60,0,32,32,1,false);
		createHitbox(0,0,60,30,0,true);
		createHitbox(100,100, 20,20,0,true);
	}

	@Override
	public void render () {
		update(Gdx.graphics.getDeltaTime());

		Gdx.gl.glClearColor(0f,0f,0f,1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		debugRenderer.render(world, viewport.getCamera().combined);
	}

	public void update(float delta) {
		world.step(1 / 60f, 6, 2);

		updateInput(delta);
	}

	public void updateInput(float delta) {
		int horizontalForce = 0;
		int verticalForce = 0;

		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			player.applyLinearImpulse(new Vector2(1000000, 0), player.getWorldCenter(), true);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.J)) {
			horizontalForce--;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.L)) {
			horizontalForce++;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.I)) {
			verticalForce++;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.K)) {
			verticalForce--;
		}
		player.setLinearVelocity(horizontalForce * 500, verticalForce * 500);
	}

	@Override
	public void dispose () {
		world.dispose();
	}

	@Override
	public void resize(int width, int height) {
		viewport.update(width, height);
	}

	public Body createHitbox(int x, int y, int width, int height, float damping, boolean isStatic) {
		Body body;
		BodyDef def = new BodyDef();
		PolygonShape shape = new PolygonShape();

		if (isStatic) def.type = BodyDef.BodyType.StaticBody;
		else def.type = BodyDef.BodyType.DynamicBody;

		def.position.set(x, y);
		def.fixedRotation = true;
		body = world.createBody(def);

		shape.setAsBox(width / 2f, height / 2f);

		body.createFixture(shape, 1f);
		body.setLinearDamping(damping);
		shape.dispose();

		return body;
	}
}
