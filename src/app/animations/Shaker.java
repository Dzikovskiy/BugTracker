package app.animations;

import javafx.scene.Node;

public class Shaker {
    static public void shakeFields(Node... t) {
        for(int i=0; i<t.length;i++){
            Shake errorAnimation = new Shake(t[i]);
            errorAnimation.playAnimation();
        }

    }
}
