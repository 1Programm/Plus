package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.lifecycle.ILifecycle;
import com.programm.projects.plus.core.lifecycle.IObservableLifecycle;
import com.programm.projects.plus.engine.api.exceptions.EngineRuntimeException;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.goh.api.ISceneInitializer;
import com.programm.projects.plus.renderer.api.IRenderer;

/**
 * An API representation of an Engine
 * Extends from {@link IObservableLifecycle} and {@link IEngineContext}
 */
public interface IEngine extends IObservableLifecycle, IEngineContext {

    /**
     * Method to retrieve the engine phase
     * @return
     * <code>
     *      {@link EnginePhase#ALIVE} if the Engine is in the initial state and {@link IEngine#startup()} has not been called yet.
     * </code><br><br>
     *
     * <code>
     *      {@link EnginePhase#STARTING} if {@link IEngine#startup()} has been called.
     * </code><br><br>
     *
     * <code>
     *      {@link EnginePhase#PREPARED} if the subsystems have been set up properly and are starting now.
     * </code><br><br>
     *
     * <code>
     *      {@link EnginePhase#STARTED} if all subsystems {@link ILifecycle#startup()} method has been called.
     * </code><br><br>
     *
     * <code>
     *      {@link EnginePhase#CLEANUP} if {@link IEngine#shutdown()} has been called and is currently in the process of cleaning up.
     * </code><br><br>
     *
     * <code>
     *      {@link EnginePhase#SHUTDOWN} if all subsystems {@link ILifecycle#shutdown()} method has been called and the {@link IEngine#shutdown()} finished.
     * </code><br><br>
     *
     * <code>
     *      {@link EnginePhase#ERROR} if the engine has caught an error unhandled exception. Trying to enter the {@link EnginePhase#CLEANUP} phase and shutting down the engine.
     * </code><br><br>
     *
     * <code>
     *      {@link EnginePhase#DEAD} if the {@link EnginePhase#ERROR} phase failed to enter the {@link EnginePhase#CLEANUP} phase or threw an exception.
     * </code>
     */
    EnginePhase phase();


    //TEMPORARY
    void setSceneInitializer(ISceneInitializer sceneInitializer);
}
