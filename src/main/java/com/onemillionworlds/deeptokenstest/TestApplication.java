package com.onemillionworlds.deeptokenstest;

import com.jme3.app.SimpleApplication;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.system.AppSettings;

import java.util.List;

public class TestApplication extends SimpleApplication{

    List<String> testList = List.of(
            "/Models/examples/weatherIcon.j3o",
            "/Models/examples/problematicTripleHole.j3o", //because the two holes both branch off the same point this one tends to go wrong
            "/Models/examples/arch.j3o",
            "/Models/examples/difficultHolesAndIslands.j3o"
    );


    int currentlyDisplaying = 0;

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

        setUpToDisplayIndex(currentlyDisplaying);

        Vector3f lightPosition = new Vector3f(5, 10, 10);
        PointLight pointLight = new PointLight();

        pointLight.setPosition(lightPosition);
        rootNode.addLight(pointLight);

        AmbientLight ambientLight = new AmbientLight(new ColorRGBA(0.3f, 0.3f, 0.3f, 1));
        rootNode.addLight(ambientLight);

        inputManager.addMapping("Left", new KeyTrigger(KeyInput.KEY_Q));
        inputManager.addMapping("Right", new KeyTrigger(KeyInput.KEY_E));

         ActionListener actionListener = new ActionListener() {
            @Override
            public void onAction(String name, boolean isPressed, float tpf) {
                if (isPressed) {
                    if (name.equals("Left")) {
                        currentlyDisplaying++;
                    } else if (name.equals("Right")) {
                        currentlyDisplaying--;
                    }
                    if (currentlyDisplaying < 0) {
                        currentlyDisplaying += testList.size();
                    }
                    currentlyDisplaying = currentlyDisplaying% testList.size();
                    setUpToDisplayIndex(currentlyDisplaying);
                }
            }
        };

        inputManager.addListener(actionListener, "Left", "Right");
    }



    @Override
    public void simpleUpdate(float tpf){
        super.simpleUpdate(tpf);
    }

    private void setUpToDisplayIndex(int index){
        rootNode.detachAllChildren();

        Spatial deepToken = assetManager.loadModel(testList.get(index));

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
    }

}
