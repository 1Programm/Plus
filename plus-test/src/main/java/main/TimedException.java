package main;

import com.programm.projects.plus.core.IGameContext;
import com.programm.projects.plus.core.IUpdatableComponent;
import com.programm.projects.plus.core.exceptions.IThrowableMethod;
import com.programm.projects.plus.core.exceptions.PlusException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimedException implements IUpdatableComponent {

    private final long millis;
    private final PlusException exception;

    private long last;
    private long diff;

    public TimedException(long millis, PlusException exception) {
        this.millis = millis;
        this.exception = exception;
    }

    @Override
    public void update(IGameContext context) {
        if(context.enteredUpdateRange()){
            this.last = System.currentTimeMillis() - diff;
        }

        long now = System.currentTimeMillis();
        diff = now - last;

        if(diff > millis){
            last = now;
            context.engine().handleException(IThrowableMethod.Throw(exception));
        }
    }
}
