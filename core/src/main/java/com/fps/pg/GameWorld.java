package com.fps.pg;

import static com.fps.pg.Methods.loadModel;
import static com.fps.pg.Methods.print;
import static com.fps.pg.StartScreen.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;

import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;


public class GameWorld {
    public SceneManager sceneManager;
    public Environment environment;
    public DirectionalLightEx light;
    private static PerspectiveCamera camera;
    public float deltaX=0,deltaY=0,sensitivity = 0.1f,playerSpeed=0.3f;
    public Vector3 right=new Vector3(),leftMove=new Vector3(),rightMove=new Vector3(),forward=new Vector3(),backward=new Vector3();

    public GameWorld(Vector3 position){

        sceneManager=new SceneManager();
        environment = sceneManager.environment;
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f));



        light = new DirectionalLightEx();
        light.direction.set(1, -3, 1).nor();
        light.color.set(Color.WHITE);
        sceneManager.environment.add(light);

        camera = new PerspectiveCamera(65f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        camera.near = 1f;
        camera.far = 600;
        sceneManager.setCamera(camera);
        camera.position.set(position.x,position.y, position.z);

        sceneManager.setAmbientLight(0.01f);
    }
    public void addObject(String value){
        sceneManager.addScene(new Scene(loadModel(value+".gltf").scene));
    }


    private void handleCamera() {
        deltaX = -Gdx.input.getDeltaX();
        deltaY = -Gdx.input.getDeltaY();
        sceneManager.camera.direction.rotate(sceneManager.camera.up, deltaX * sensitivity);
        right = sceneManager.camera.direction.cpy().crs(sceneManager.camera.up).nor();
        sceneManager.camera.direction.rotate(right, deltaY * sensitivity);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            forward = new Vector3(sceneManager.camera.direction.x, 0, sceneManager.camera.direction.z).nor();
            sceneManager.camera.translate(forward.scl(playerSpeed));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            backward = new Vector3(sceneManager.camera.direction.x, 0, sceneManager.camera.direction.z).nor();
            sceneManager.camera.translate(backward.scl(-playerSpeed));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            leftMove = right.scl(-playerSpeed);
            sceneManager.camera.translate(leftMove);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            rightMove = right.scl(playerSpeed);
            sceneManager.camera.translate(rightMove);
        }

    }

    public void resize(int width,int height){
        sceneManager.updateViewport(width, height);
        sceneManager.camera.update();
    }


    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        handleCamera();
        camera.update();
        sceneManager.update(delta);
        sceneManager.render();
    }

}
