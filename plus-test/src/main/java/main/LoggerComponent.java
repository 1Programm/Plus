package main;

import com.programm.projects.plus.core.IGameContext;
import com.programm.projects.plus.core.IUpdatableComponent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoggerComponent implements IUpdatableComponent {

    private final String name;
    private final int time;
    private long last;
    private int count;

    public LoggerComponent(String name, int time) {
        this.name = name;
        this.time = time;
        this.last = System.currentTimeMillis();
    }

    @Override
    public void update(IGameContext context) {
        long now = System.currentTimeMillis();

        if(now - last >= time){
            last = now;
            count++;
            log.info(name + " [" + count + "]");
        }
    }
}
