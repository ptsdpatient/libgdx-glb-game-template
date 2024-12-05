package com.fps.pg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;

import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.Scene;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;

public  class Methods {

    public static void print(String value){
        Gdx.app.log("Game",value);
    }
    public static void print(Vector3 value){
        Gdx.app.log("Game",value+"");
    }
    public static void print(String tag,String value){
        Gdx.app.log(tag,value);
    }
    public static FileHandle files(String value){
        return Gdx.files.internal(value);
    }

    public static SceneAsset loadModel(String value){
        return new GLTFLoader().load(files(value));
    }

    public static Vector3 getForwardDirection(ModelInstance modelInstance, float scale) {
        Vector3 forward = new Vector3(0, 0, -1);
        modelInstance.transform.getRotation(new Quaternion(), true).transform(forward);
        return forward.nor().scl(scale);
    }

    public static Vector3 getRightDirection(ModelInstance modelInstance, float scale) {
        Vector3 right = new Vector3(1, 0, 0);
        modelInstance.transform.getRotation(new Quaternion(), true).transform(right);
        return right.nor().scl(scale);
    }
    public static void moveForward(GameObject object,float scale){
        object.scene.modelInstance.transform.translate(getForwardDirection(object.scene.modelInstance,scale));
    }
    public static void moveRight(GameObject object,float scale){
        object.scene.modelInstance.transform.translate(getRightDirection(object.scene.modelInstance,scale));

    }
}
