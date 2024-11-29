package com.fps.pg;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import net.mgsx.gltf.loaders.glb.GLBLoader;
import net.mgsx.gltf.loaders.gltf.GLTFLoader;
import net.mgsx.gltf.scene3d.scene.SceneAsset;
import net.mgsx.gltf.scene3d.scene.SceneManager;

public  class Methods {

    public static void print(String value){
        Gdx.app.log("Game",value);
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



}
