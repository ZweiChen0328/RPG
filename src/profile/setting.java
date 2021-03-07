package profile;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.Transition;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class setting {

    public static boolean reLife = false;

    public static void AnimateText(Label lbl, String descImp) {//打字機
        String content = descImp;
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(3500));
            }

            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                lbl.setText(content.substring(0, n));
            }
        };
        animation.play();
    }

    public static void AnimateText2(Label lbl, String descImp) {//打字機對話
        String content = descImp;
        final Animation animation = new Transition() {
            {
                setCycleDuration(Duration.millis(1000));
            }

            protected void interpolate(double frac) {
                final int length = content.length();
                final int n = Math.round(length * (float) frac);
                lbl.setText(content.substring(0, n));
            }
        };
        animation.play();
    }

    public static FadeTransition fadeIn = new FadeTransition(
            Duration.millis(1000)
    );

}
