import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public abstract class Actor extends ImageView {

    public abstract void act(long now);

    public void move(double dx, double dy){
        setX(getX() + dx);
        setY(getY() + dy);
    }

    public World getWorld(){
        return (World)getParent();
    }

    public double getHeight(){
        return getBoundsInParent().getHeight();
    }

    public double getWidth(){
        return getBoundsInParent().getWidth();
    }

    public <A extends Actor> java.util.List<A> getIntersectingObjects(java.lang.Class<A> cls){
        ArrayList<A> list = new ArrayList<>();
        for(Node n : getWorld().getChildren()){
        	if (n instanceof Actor) {
            Actor a = (Actor) n;
            if(a.equals(this) || !cls.isInstance(a)) continue;
            if(this.intersects(a.getBoundsInLocal())) list.add(cls.cast(a));
        	}
        }
        return list;
    }

    public <A extends Actor> A getOneIntersectingObject(java.lang.Class<A> cls){
        for(Node n : getWorld().getChildren()){
        	if (n instanceof Actor) {
            Actor a = (Actor) n;
            if(a.equals(this) || !cls.isInstance(a)) continue;
            if(this.intersects(a.getBoundsInLocal())) return cls.cast(a);
        	}
        }
        return null;
    }
}
