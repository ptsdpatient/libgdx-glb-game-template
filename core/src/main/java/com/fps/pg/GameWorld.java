package com.fps.pg;

import static com.fps.pg.Methods.getRightDirection;
import static com.fps.pg.Methods.loadModel;
import static com.fps.pg.Methods.moveForward;
import static com.fps.pg.Methods.moveRight;
import static com.fps.pg.Methods.print;
import static com.fps.pg.Methods.getForwardDirection;
import static com.fps.pg.StartScreen.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.utils.Array;

import net.mgsx.gltf.scene3d.lights.DirectionalLightEx;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneManager;

import java.util.Objects;


public class GameWorld {
    public ModelBatch batch;
    public Array<GameObject> gameObjects;
    public GameObject player;

    public SceneManager sceneManager;
    public Environment environment;
    public DirectionalLightEx light;
    public Model cubeModel;
    private static PerspectiveCamera camera;
    private ShapeRenderer shapeRenderer;

    public float deltaX=0,deltaY=0,sensitivity = 0.1f,playerSpeed=0.05f;
    public Vector3 right=new Vector3(),leftMove=new Vector3(),rightMove=new Vector3(),forward=new Vector3(),backward=new Vector3();

    public GameWorld(Vector3 position){
        gameObjects=new Array<>();
//        shapeRenderer=new ShapeRenderer();
        batch=new ModelBatch();
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

//        cubeModel = new ModelBuilder().createBox(1f, 1f, 1f,
//            new Material(ColorAttribute.createDiffuse(Color.RED)),
//            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        sceneManager.setAmbientLight(0.01f);



    }



    public void addObject(String name,String model, Vector3 position){
//        gameObjects.add(new GameObject(value,new Scene(loadModel(value+".gltf").scene)));
        gameObjects.add(new GameObject(name,position,new Scene(loadModel(model+".gltf").scene)));
//        gameObject=new Scene(loadModel(value+".gltf").scene);
        sceneManager.addScene(gameObjects.get(gameObjects.size-1).scene);
    }




    private void handleCamera() {
        deltaX = -Gdx.input.getDeltaX();
        deltaY = -Gdx.input.getDeltaY();

        sceneManager.camera.direction.rotate(sceneManager.camera.up, deltaX * sensitivity);

        right = sceneManager.camera.direction.cpy().crs(sceneManager.camera.up).nor();
        sceneManager.camera.direction.rotate(right, deltaY * sensitivity);
        if(camera.direction.y<-0.9) {
            sceneManager.camera.direction.rotate(right, deltaY * sensitivity*-1.2f);
        }



        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveForward(player,playerSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveForward(player,-playerSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveRight(player,-playerSpeed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight(player,playerSpeed);
        }
    }

    public void resize(int width,int height){
        sceneManager.updateViewport(width, height);
        sceneManager.camera.update();
    }

    public void checkCollisions() {
        for (int i = 0; i < gameObjects.size; i++) {
            GameObject obj1 = gameObjects.get(i);

            // Check collision with every other object
            for (int j = i + 1; j < gameObjects.size; j++) {
                GameObject obj2 = gameObjects.get(j);

                if (obj1.checkCollision(obj2)) {
                    print("Collision detected between " + i + " and " + j);
                }
            }
        }
    }



    public void render(float delta){

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        handleCamera();

        for(GameObject obj : gameObjects){
            obj.scene.modelInstance.transform.rotate(Vector3.Y, ((Objects.equals(obj.name, "pyramid"))?-1:1) * 30f * delta);
            obj.updateBoundingBox();
        }
        checkCollisions();

        camera.update();
        sceneManager.update(delta);
        sceneManager.render();


    }
}
