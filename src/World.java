import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashSet;

public abstract class World extends Pane {
    private AnimationTimer timer;
    private HashSet<KeyCode> keysDown;

    public World(){
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                act(now);
                for(int i = 0; i < getChildren().size(); i++){
                	if (getChildren().get(i) instanceof Actor) {
                    Actor a = (Actor)getChildren().get(i);
                    a.act(now);
                	}
                }
            }
        };
        keysDown = new HashSet<>();
    }

    protected abstract void act(long now);

    public void add(Actor actor){
        getChildren().add(actor);
    }

    public void remove(Actor actor){
        getChildren().remove(actor);
    }

    public void start(){
        timer.start();

    }

    public void stop() {
        timer.stop();
    }

    public <A extends Actor> java.util.List<A> getObjects(java.lang.Class<A> cls){
        ArrayList<A> list = new ArrayList<>();
        for(Node n : getChildren()){
            Actor a = (Actor) n;
            if(cls.isInstance(a)){
                A actor = cls.cast(a);
                list.add(actor);
            }
        }
        return list;
    }
    
    public <A extends Actor> java.util.List<A> getBricks(java.lang.Class<A> cls){
        ArrayList<A> list = new ArrayList<>();
        for(Node n : getChildren()){
        	if (n.getClass() == Brick.class) {
            Actor a = (Actor) n;
            A actor = cls.cast(a);
            list.add(actor);
            /*if(cls.isInstance(a)){
                A actor = cls.cast(a);
                list.add(actor);
            }*/
        	}
        }
        return list;
    }
    
    public <A extends Actor> java.util.List<A> getStrong(java.lang.Class<A> cls){
        ArrayList<A> list = new ArrayList<>();
        for(Node n : getChildren()){
        	if (n.getClass() == StrongBrick.class) {
            Actor a = (Actor) n;
            A actor = cls.cast(a);
            list.add(actor);
            /*if(cls.isInstance(a)){
                A actor = cls.cast(a);
                list.add(actor);
            }*/
        	}
        }
        return list;
    }

    public void addKey(KeyCode code){
        keysDown.add(code);
    }

    public void removeKey(KeyCode code){
        keysDown.remove(code);
    }

    public boolean isKeyDown(KeyCode code){
        return keysDown.contains(code);
    }
}