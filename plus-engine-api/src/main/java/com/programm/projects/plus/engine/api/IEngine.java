package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.events.IEventHandler;
import com.programm.projects.plus.core.lifecycle.ILifecycle;
import com.programm.projects.plus.core.lifecycle.IObservableLifecycle;
import com.programm.projects.plus.engine.api.exceptions.IExceptionHandler;

/**
 * An API representation of an Engine
 * Extends from {@link IObservableLifecycle} and {@link IEngineContext}
 *
 *
 * Should be used like the following example:
 *
 * <code>
 *     IEngine engine = ...;
 *
 *     engine.events().listenFor(..., ...); //Listen for some event
 *     engine.setSceneInitializer(...);     //Set the scene initializer where objects can be created
 *
 *     engine.startup();
 *
 * </code>
 *
 */
public interface IEngine extends IEngineContext, IObservableLifecycle {

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

    IEventHandler events();

    void setExceptionHandler(IExceptionHandler handler);

    //TEMPORARY
    void setScene(Scene scene);
}
