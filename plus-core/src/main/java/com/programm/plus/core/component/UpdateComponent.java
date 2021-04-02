package com.programm.plus.core.component;

import com.programm.plus.core.GameContext;

public abstract class UpdateComponent extends Component {

    public abstract void onUpdate(GameContext context);

    protected final void removeComponent(){
        this.gameObject.getComponents().toBeRemoved.add(this.getClass());
    }
}
