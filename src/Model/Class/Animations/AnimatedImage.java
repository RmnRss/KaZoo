package Model.Class.Animations;

import javafx.scene.image.Image;

/***
 * Class to use mulitple images to create an animation
 */
public class AnimatedImage {

    private Image[] frames;
    private double duration;

    public Image getOneFrame(double time){
        int index = (int) ((time % (frames.length*duration)) / duration);
        return frames[index];
    }

    public Image[] getFrames() {
        return frames;
    }

    public void setFrames(Image[] frames) {
        this.frames = frames;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }



}
