package com.fps.pg;
import static com.fps.pg.Methods.files;
import static com.fps.pg.Methods.loadModel;
import static com.fps.pg.Methods.print;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.loaders.ModelLoader;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.loader.G3dModelLoader;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.fps.pg.Methods;

import net.mgsx.gltf.scene3d.attributes.PBRCubemapAttribute;
import net.mgsx.gltf.scene3d.attributes.PBRTextureAttribute;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;


public class StartScreen implements Screen {

    public PlayGround game;
    //    public SpriteBatch batch;
    private static PerspectiveCamera camera;
    //    private static OrthographicCamera camera;
    public ShapeRenderer shapeRenderer;
    public Viewport viewport;
    public Texture image;


    public ModelBatch modelBatch;
    public Environment environment;
    private SceneManager sceneManager;
    private SceneAsset sceneAsset;
    private Scene scene;
    public ModelInstance modelInstance;

    public StartScreen(PlayGround game){
        print("screen initiated");

        this.game=game;

        Gdx.input.setCursorCatched(true);

//        camera = new PerspectiveCamera(45, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

//        camera=new OrthographicCamera();
//        camera.setToOrtho(false, 1280 / 2f, 720 / 2f);


        sceneAsset = loadModel("floor_basic.glb");


//        viewport=new ExtendViewport(1280/2f,720/2f,camera);
//        viewport.apply();

        shapeRenderer=new ShapeRenderer();

        // Set up the environment
        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.8f, 0.8f, 0.8f, 1f));
        environment.add(new DirectionalLight().set(1f, 1f, 1f, -1f, -0.8f, -0.2f));

        scene = new Scene(sceneAsset.scene);
        sceneManager = new SceneManager();
        sceneManager.addScene(scene);


        camera = new PerspectiveCamera(60f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        float d = .02f;
        camera.near = d / 1000f;
        camera.far = 200;
        sceneManager.setCamera(camera);
        camera.position.set(0,0.5f, 4f);

        sceneManager.setAmbientLight(1f);

//        ModelBuilder modelBuilder = new ModelBuilder();
//        model = modelBuilder.createBox(
//            5f, 5f, 5f, // Dimensions
//            new Material(ColorAttribute.createDiffuse(0, 0, 1, 1)), // Blue color
//            VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal
//        );


//        modelInstance = new ModelInstance(model);
//        modelInstance.transform.setToTranslation(new Vector3(0, 0, 0));

        // Initialize the model batch
        modelBatch = new ModelBatch();

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputProcessor() {
            @Override
            public boolean keyDown(int keycode) {
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                return false;
            }

            @Override
            public boolean keyTyped(char character) {
                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
                return false;
            }

            @Override
            public boolean touchDragged(int screenX, int screenY, int pointer) {
                return false;
            }

            @Override
            public boolean mouseMoved(int screenX, int screenY) {

                return false;
            }

            @Override
            public boolean scrolled(float amountX, float amountY) {
                return false;
            }
        });
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        handleCameraControls();

        camera.update();
//        float deltaTime = Gdx.graphics.getDeltaTime();
//
//        scene.modelInstance.transform.rotate(Vector3.Y, 10f * deltaTime);

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        sceneManager.update(delta);
        sceneManager.render();

    }


    private void handleCameraControls() {
        float deltaX = -Gdx.input.getDeltaX();
        float deltaY = -Gdx.input.getDeltaY();

        float sensitivity = 0.2f;

        camera.direction.rotate(camera.up, deltaX * sensitivity);
        Vector3 right = camera.direction.cpy().crs(camera.up).nor();
        camera.direction.rotate(right, deltaY * sensitivity);

    }


    @Override
    public void resize(int width, int height) {
        sceneManager.updateViewport(width, height);

//        viewport.update(width, height);
        camera.update();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
//        model.dispose();
    }
}
