package com.onemillionworlds.deeptokenstest;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.system.AppSettings;

public class TestApplication extends SimpleApplication{


    public static void main(String[] args){
        AppSettings settings = new AppSettings(true);

        TestApplication app = new TestApplication();
        app.setDisplayStatView(false);
        app.setSettings(settings);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        viewPort.setBackgroundColor(ColorRGBA.DarkGray);

        cam.setLocation(new Vector3f(0, 2, 10));
        cam.lookAt(new Vector3f(0, 0, 0), Vector3f.UNIT_Y);

        Spatial deepToken = assetManager.loadModel("/Models/weatherIcon.j3o");

        rootNode.attachChild(deepToken);

        deepToken.addControl(new AbstractControl(){
            double rotation = 0;
            @Override
            protected void controlUpdate(float tpf){
                rotation+= 0.25*tpf;
                spatial.setLocalRotation(spatial.getLocalRotation().fromAngleAxis((float)rotation, Vector3f.UNIT_Y));
            }

            @Override
            protected void controlRender(RenderManager rm, ViewPort vp){}
        });


        Vector3f lightPosition = new Vector3f(5, 10, 10);
        PointLight pointLight = new PointLight();

        pointLight.setPosition(lightPosition);
        rootNode.addLight(pointLight);

        AmbientLight ambientLight = new AmbientLight(new ColorRGBA(0.3f, 0.3f, 0.3f, 1));
        rootNode.addLight(ambientLight);
    }

}
