package com.programm.plus.core.components.material;

import com.programm.plus.core.component.ExtraComponent;
import com.programm.plus.render.api.Pencil;

public abstract class Material extends ExtraComponent {

    public abstract boolean prepareBackground(Pencil pencil);

    public abstract boolean prepareForeground(Pencil pencil);

}
