package com.programm.plus.tags;

import static com.programm.plus.tags.TagSystem.*;
import com.programm.plus.core.component.*;
import com.programm.plus.goh.api.GameObjectInitializer;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Slf4j
class GameObjectTagInitializer implements GameObjectInitializer {

    String tagInitMode;

    public void accept(List<Tag> tags,
                       Mover mover,
                       Map<Class<? extends ExtraComponent>, ExtraComponent> extraComponents,
                       Map<Class<? extends UpdateComponent>, UpdateComponent> updateComponents,
                       Consumer<Component> addComponent){

        for(Tag tag : tags){
            initTag(mover, extraComponents, updateComponents, tag, addComponent);
        }
    }

    private boolean containsClass(Class cls,
                                  Map<Class<? extends ExtraComponent>, ExtraComponent> extraComponents,
                                  Map<Class<? extends UpdateComponent>, UpdateComponent> updateComponents){

        if(extraComponents.containsKey(cls)) return true;
        if(updateComponents.containsKey(cls)) return true;

        return false;
    }

    private void initTag(Mover mover,
                         Map<Class<? extends ExtraComponent>, ExtraComponent> extraComponents,
                         Map<Class<? extends UpdateComponent>, UpdateComponent> updateComponents,
                         Tag tag,
                         Consumer<Component> addComponent){

        for(RequiredClassInfo info : tag.requiredComponents){
            if(info.getRequiredComponentClass() == Mover.class){
                if(mover == null){
                    doesntHaveComponent(tag, info, addComponent);
                }
            }
            else if(!containsClass(info.getRequiredComponentClass(), extraComponents, updateComponents)){
                doesntHaveComponent(tag, info, addComponent);
            }
        }

        if(tag.parent != null){
            initTag(mover, extraComponents, updateComponents, tag.parent, addComponent);
        }
    }

    private void doesntHaveComponent(Tag tag,
                                     RequiredClassInfo info,
                                     Consumer<Component> addComponent){

        if(tagInitMode.equals(MODE_REQUIRED_CHECK_WARN)){
            log.warn("The GameObject has a specific Tag ({}), that requires a Component ({}) which was not added.", tag.getName(), info.getRequiredComponentClass());
        }
        else if(tagInitMode.equals(MODE_REQUIRED_CHECK_ERROR)){
            throw new RuntimeException("The GameObject has a specific Tag (" + tag.getName() + "), that requires a Component (" + info.getRequiredComponentClass() + ") which was not added.");
        }
        else if(tagInitMode.equals(MODE_REQUIRED_FORCE_CREATE)){
            forceCreateComponent(info, addComponent);
        }
        else if(tagInitMode.equals(MODE_REQUIRED_WARN_FORCE_CREATE)){
            log.info("Forceful creating \"{}\" which is required by Tag: \"{}\".", info.getRequiredComponentClass(), tag.getName());
            forceCreateComponent(info, addComponent);
        }
    }

    @SuppressWarnings("unchecked")
    private void forceCreateComponent(RequiredClassInfo info,
                                      Consumer<Component> addComponent){

        Class<? extends Component> cls = info.getRequiredComponentClass();
        Object[] params = info.getDefaultConstructorParams();

        Constructor<? extends Component> constructor = null;

        for(Constructor<?> constr : cls.getConstructors()){
            if(constr.getParameterCount() == info.numParams()){
                constructor = (Constructor<? extends Component>) constr;

                for(int i=0;i<info.numParams();i++) {
                    Class<?> neededParam = constr.getParameterTypes()[i];
                    Class<?> actualParam = params[i].getClass();

                    if(neededParam != params[i].getClass()) {
                        if (neededParam == int.class) {
                            if (actualParam == Integer.class) {
                                continue;
                            }
                        }
                        else if(neededParam == float.class){
                            if(actualParam == Float.class){
                                continue;
                            }
                        }
                        else if(neededParam == boolean.class){
                            if(actualParam == Boolean.class){
                                continue;
                            }
                        }
                        else if(neededParam == char.class){
                            if(actualParam == Character.class){
                                continue;
                            }
                        }

                        constructor = null;
                        break;
                    }
                }

            }
        }

        if(constructor == null) {
            String _types = "";

            for(int i=0;i<params.length;i++){
                Object o = params[i];
                _types += o.getClass().getSimpleName() + "(" + o + ")";
                if(i + 1 < params.length){
                    _types += ", ";
                }
            }

            log.error("No Constructor found for class {}, with types {}", cls, params);
            return;
        }

        try {
            addComponent.accept(constructor.newInstance(params));
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            log.error("Failed to Force-Create Component '{}'.", cls);
            log.error(e.getMessage());
        }

    }
}
