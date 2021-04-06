package com.programm.projects.plus.engine.api;

import com.programm.projects.core.lifecycle.ILifecycle;

public enum EnginePhase {


    // STARTING PHASES

    /**
     * Engine initial state
     */
    ALIVE,

    /**
     * The {@link IEngine#startup()} method was called. The subsystems are in the process of setting up and linking callbacks
     */
    STARTING,

    /**
     * All subsystems have been linked and are in the process of starting via {@link ILifecycle#startup()}
     */
    PREPARED,

    /**
     * All subsystems {@link ILifecycle#startup()} method has been called and the {@link IEngine} has successfully started
     */
    STARTED,




    // SHUTDOWN PHASES

    /**
     * The {@link IEngine#shutdown()} method was called. The subsystems are in the process of shutting down via the {@link ILifecycle#shutdown()} method.
     */
    CLEANUP,

    /**
     * All subsystems {@link ILifecycle#shutdown()} method has been called and the {@link IEngine} was shut down successfully.
     */
    SHUTDOWN,


    // ERROR PHASES

    /**
     * The {@link IEngine} is in an error state - an exception was thrown and is trying to shut down (changing to -> {@link EnginePhase#CLEANUP} phase).
     */
    ERROR,

    /**
     * The {@link IEngine} was in the {@link EnginePhase#ERROR} state and was unable to shut down.
     */
    DEAD,


}
