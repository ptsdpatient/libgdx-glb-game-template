package com.fps.pg;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;

import net.mgsx.gltf.scene3d.scene.Scene;

public class GameObject {
    String name;
    Scene scene;
    BoundingBox bounds;
    public GameObject(String name, Vector3 position, Scene scene){
        this.name=name;
        this.scene=scene;
        this.scene.modelInstance.transform.setToTranslation(position);
        this.bounds=new BoundingBox();
        this.scene.modelInstance.calculateBoundingBox(bounds);
        bounds.min.mul(scene.modelInstance.transform);
        bounds.max.mul(scene.modelInstance.transform);
    }
    public boolean checkCollision(GameObject otherObject) {
        return bounds.intersects( otherObject.bounds);
    }

    public void updateBoundingBox() {
        scene.modelInstance.calculateBoundingBox(bounds);
        bounds.min.mul(scene.modelInstance.transform);
        bounds.max.mul(scene.modelInstance.transform);
    }
}
