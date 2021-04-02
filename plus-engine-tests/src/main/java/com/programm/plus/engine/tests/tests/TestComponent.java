package com.programm.plus.engine.tests.tests;

import com.programm.plus.core.GameContext;
import com.programm.plus.core.component.UpdateComponent;

public class TestComponent extends UpdateComponent {

    @Override
    public void onUpdate(GameContext gameContext) {
        gameContext.getObjectHandler().runTagAction(Test.DRAW, (context, obj) -> {
            System.out.println("Test: " + obj);
        });
    }
}
