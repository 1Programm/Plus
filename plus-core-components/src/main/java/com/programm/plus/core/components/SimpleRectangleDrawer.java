package com.programm.plus.core.components;

import com.programm.plus.core.GameContext;
import com.programm.plus.core.component.Component;
import com.programm.plus.core.components.material.ColorMaterial;
import com.programm.plus.core.component.UpdateComponent;
import com.programm.plus.core.components.material.Material;
import com.programm.plus.render.api.Pencil;
import lombok.Setter;

@Setter
public class SimpleRectangleDrawer extends UpdateComponent {

    @Override
    public void onUpdate(GameContext context) {
        Pencil pencil = context.getPencil();
        Material material = gameObject.getComponents().getFromSuper(Material.class);
        //ColorMaterial cMat = getComponent(ColorMaterial.class);

        if(material == null) return;

        float x = transform.getPosition().getX();
        float y = transform.getPosition().getY();
        float width = transform.getSize().getX();
        float height = transform.getSize().getY();

        if(material.prepareBackground(pencil)) {
            pencil.drawRect(x, y, width, height);
        }

        if(material.prepareForeground(pencil)) {
            pencil.drawRect(x, y, width, height);
        }
    }

    @Override
    public Component copy() {
        return new SimpleRectangleDrawer();
    }
}
