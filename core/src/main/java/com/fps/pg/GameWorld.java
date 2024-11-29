package com.fps.pg;

import static com.fps.pg.Methods.loadModel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;

public class GameWorld {
    public SceneManager sceneManager;
    public Environment environment;
    public DirectionalLightEx light;
    private static PerspectiveCamera camera;
    public float deltaX=0,deltaY=0,sensitivity = 0.2f;
    public Vector3 right=new Vector3();

    public GameWorld(){
        sceneManager=new SceneManager();
        environment = sceneManager.environment;
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f));

        sceneManager.setCamera(new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        sceneManager.camera.position.set(3f, 2f, 5f);
        sceneManager.camera.lookAt(Vector3.Zero);
        sceneManager.camera.update();

        light = new DirectionalLightEx();
        light.direction.set(1, -3, 1).nor();
        light.color.set(Color.WHITE);
        sceneManager.environment.add(light);
        camera = new PerspectiveCamera(60f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        camera.near = 10;
        camera.far = 400;

        sceneManager.setCamera(camera);
        camera.position.set(0,15f, 10f);

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
