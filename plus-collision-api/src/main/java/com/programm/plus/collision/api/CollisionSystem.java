package com.programm.plus.collision.api;

import com.programm.plus.core.obj.GameObject;
import com.programm.plus.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CollisionSystem<T extends CollisionInfo> {

    private CollisionChecker checker;
    private CollisionInfoCollector<T> collector;
    private CollisionResolver<T> resolver;

    public final void init(){
        log.debug("Initializing Collision System ...");

        this.checker = initChecker();
        this.collector = initCollector();
        this.resolver = initResolver();

        if(this.checker == null) {
            log.debug("No Checker set - will collect all information even if objects dont collide.");

            if(this.collector == null){
                log.warn("No Checker and no Collector is set - this system will always say objects dont collide and won't do anything.");
            }
        }

        if(this.checker != null && this.collector == null){
            log.debug("No Collector set - will only be able to check for collision and not resolving or collecting other info.");
        }

        if(this.resolver == null){
            log.debug("No resolver set - will only be able to check and collect info but not resolve collision.");
        }
    }

    protected abstract T initInfo();

    protected abstract CollisionChecker initChecker();
    protected abstract CollisionInfoCollector<T> initCollector();
    protected abstract CollisionResolver<T> initResolver();

    public boolean test(GameObject o1, GameObject o2){
        if(checker != null){
            return checker.collides(o1, o2);
        }

        log.warn("Could not test, because no checker is set.");

        return false;
    }

    public void collectInfo(T info, GameObject o1, GameObject o2){
        if(this.collector != null){
            collector.collect(info, o1, o2);
        }
        else {
            log.warn("Could not collect any info, because no collector is set.");
        }
    }

    public T testAndCollectConditional(GameObject o1, GameObject o2){
        boolean collides = test(o1, o2);

        T info = initInfo();

        if(!collides) return info;

        info.setCollides(collides);

        collectInfo(info, o1, o2);

        return info;
    }

    public void runAndResolve(GameObject o1, GameObject o2, Collider collider){
        T info = testAndCollectConditional(o1, o2);

        if(!info.isCollides()) return;

        collider.onCollision(o2);

        resolveOn(info, o1);
    }

    public void resolveOn(T info, GameObject obj){
        if(this.resolver != null){
            if(obj.hasTag(Tag.OBJECT_IMMOVABLE)) return;
            if(obj.hasTag(Tag.ENTITY_NO_COLLISION_RESOLVING)) return;

            this.resolver.resolve(info, obj);
        }
        else {
            log.warn("Could not resolve information {}, because no resolver is set.", info);
        }
    }

}
