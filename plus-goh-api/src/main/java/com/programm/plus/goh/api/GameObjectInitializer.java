package com.programm.plus.goh.api;

import com.programm.plus.core.component.*;
import com.programm.plus.tags.Tag;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public interface GameObjectInitializer {

    void accept(List<Tag> tags,
                Mover mover,
                Map<Class<? extends ExtraComponent>, ExtraComponent> extraComponents,
                Map<Class<? extends UpdateComponent>, UpdateComponent> updateComponents,
                Consumer<Component> addComponent);

}
