package com.programm.plus.core.component;

import com.programm.plus.core.obj.GameObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class ComponentHandler {

    @Getter private Mover mover;
    @Getter private Map<Class<? extends ExtraComponent>, ExtraComponent> extraComponents = new HashMap<>();
    @Getter private Map<Class<? extends UpdateComponent>, UpdateComponent> updateComponents = new HashMap<>();

    List<Class> toBeRemoved = new ArrayList<>();

    private ComponentHandler() {}

    public ComponentHandler(Mover mover, Map<Class<? extends ExtraComponent>, ExtraComponent> extraComponents, Map<Class<? extends UpdateComponent>, UpdateComponent> updateComponents) {
        this.mover = mover;
        this.extraComponents = extraComponents;
        this.updateComponents = updateComponents;
    }

    public void init(GameObject gameObject){
        if(mover != null){
            mover.init(gameObject);
        }
        for(Class c : extraComponents.keySet()){
            extraComponents.get(c).init(gameObject);
        }
        for (Class c : updateComponents.keySet()){
            updateComponents.get(c).init(gameObject);
        }
    }

    public void onCreate(){
        if(mover != null){
            mover.onCreate();
        }
        for(Class c : extraComponents.keySet()){
            extraComponents.get(c).onCreate();
        }
        for (Class c : updateComponents.keySet()){
            updateComponents.get(c).onCreate();
        }
    }

    public void onDestroy(){
        if(mover != null){
            mover.onDestroy();
        }
        for(Class c : extraComponents.keySet()){
            extraComponents.get(c).onDestroy();
        }
        for (Class c : updateComponents.keySet()){
            updateComponents.get(c).onDestroy();
        }
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T get(Class<T> cls){
        if(ExtraComponent.class.isAssignableFrom(cls)){
            return (T)extraComponents.get(cls);
        }
        else if(UpdateComponent.class.isAssignableFrom(cls)){
            return (T)updateComponents.get(cls);
        }

        log.warn("Class {} is no valid Component implementation.", cls);

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> T getFromSuper(Class<? super T> cls){
        if(ExtraComponent.class.isAssignableFrom(cls)){
            for(Class<? extends ExtraComponent> c : extraComponents.keySet()){
                if(cls.isAssignableFrom(c)){
                    return (T)extraComponents.get(c);
                }
            }
        }
        else if(UpdateComponent.class.isAssignableFrom(cls)){
            for(Class<? extends UpdateComponent> c : updateComponents.keySet()){
                if(cls.isAssignableFrom(c)){
                    return (T)updateComponents.get(c);
                }
            }
        }
        else {
            log.warn("Class {} is no valid Component implementation.", cls);
        }

        return null;
    }

    @SuppressWarnings("unchecked")
    public <T extends Component> List<T> listFromSuper(Class<T> cls){
        List<T> ret = new ArrayList<>();

        if(ExtraComponent.class.isAssignableFrom(cls)){
            for(Class<? extends ExtraComponent> c : extraComponents.keySet()){
                if(cls.isAssignableFrom(c)){
                    ret.add((T)extraComponents.get(c));
                }
            }
        }
        else if(UpdateComponent.class.isAssignableFrom(cls)){
            for(Class<? extends UpdateComponent> c : updateComponents.keySet()){
                if(cls.isAssignableFrom(c)){
                    ret.add((T)updateComponents.get(c));
                }
            }
        }
        else {
            log.warn("Class {} is no valid Component implementation.", cls);
            return null;
        }

        return ret;
    }

    public void clearUpdateComponents(){
        for(Class cls : toBeRemoved){
            updateComponents.remove(cls);
        }

        toBeRemoved.clear();
    }

    @Override
    public ComponentHandler clone() {
        ComponentHandler handler = new ComponentHandler();

        if(this.mover != null) {
            handler.mover = this.mover.copy();
        }

        for(Class<? extends ExtraComponent> cls : extraComponents.keySet()){
            ExtraComponent eComp = (ExtraComponent)extraComponents.get(cls).copy();
            handler.extraComponents.put(cls, eComp);
        }

        for(Class<? extends UpdateComponent> cls : updateComponents.keySet()){
            UpdateComponent uComp = (UpdateComponent)updateComponents.get(cls).copy();
            handler.updateComponents.put(cls, uComp);
        }

        return handler;
    }
}
