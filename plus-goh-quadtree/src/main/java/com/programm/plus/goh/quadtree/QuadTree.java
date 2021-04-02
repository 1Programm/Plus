package com.programm.plus.goh.quadtree;

import com.programm.plus.core.obj.GameObject;

import java.util.List;

public class QuadTree {

    private QuadTree top_left;
    private QuadTree top_right;
    private QuadTree btm_left;
    private QuadTree btm_right;

    private int maxObjects;

    private List<GameObject> objects;

}
