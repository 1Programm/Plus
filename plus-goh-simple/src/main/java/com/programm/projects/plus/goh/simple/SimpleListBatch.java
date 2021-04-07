package com.programm.projects.plus.goh.simple;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IObjectBatch;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SimpleListBatch implements IObjectBatch {

    private final List<GameObject> objects = new ArrayList<>();

    @Override
    public Iterator<GameObject> iterator() {
        return objects.listIterator();
    }

    public void add(GameObject o){
        objects.add(o);
    }

    public void remove(GameObject o){
        objects.remove(o);
    }

}
