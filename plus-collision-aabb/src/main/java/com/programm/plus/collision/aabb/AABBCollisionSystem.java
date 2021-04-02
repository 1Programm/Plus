package com.programm.plus.collision.aabb;

import com.programm.plus.collision.api.CollisionChecker;
import com.programm.plus.collision.api.CollisionInfoCollector;
import com.programm.plus.collision.api.CollisionResolver;
import com.programm.plus.collision.api.CollisionSystem;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AABBCollisionSystem extends CollisionSystem<AABBCollisionInfo> {

    @Setter private CollisionChecker checker;
    @Setter private CollisionInfoCollector<AABBCollisionInfo> collector;
    @Setter private CollisionResolver<AABBCollisionInfo> resolver;

    @Override
    protected AABBCollisionInfo initInfo() {
        return new AABBCollisionInfo();
    }

    @Override
    protected CollisionChecker initChecker() {
        if(checker == null){
            log.debug("No Checker set. Default Checker: {}.", AABBCollisionChecker.class);
            checker = new AABBCollisionChecker();
        }

        return checker;
    }

    @Override
    protected CollisionInfoCollector<AABBCollisionInfo> initCollector() {
        if(collector == null){
            log.debug("No Collector set. Default Collector: {}.", AABBCollisionInfoCollector.class);
            collector = new AABBCollisionInfoCollector();
        }

        return collector;
    }

    @Override
    protected CollisionResolver<AABBCollisionInfo> initResolver() {
        if(resolver == null){
            log.debug("No Resolver set. Default Resolver: {}.", AABBCollisionResolver.class);
            resolver = new AABBCollisionResolver();
        }

        return resolver;
    }
}
