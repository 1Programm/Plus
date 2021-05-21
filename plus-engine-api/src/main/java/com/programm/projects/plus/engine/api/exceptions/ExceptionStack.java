package com.programm.projects.plus.engine.api.exceptions;

import com.programm.projects.plus.core.exceptions.PlusException;

import java.util.ArrayList;
import java.util.List;

public class ExceptionStack {

    private final List<PlusException> A = new ArrayList<>();
    private final List<PlusException> B = new ArrayList<>();

    private List<PlusException> exceptionFront = A;
    private List<PlusException> exceptionBack = B;

    public void add(PlusException e){
        exceptionBack.add(e);
    }

    public void handle() throws PlusException {
        if(exceptionFront == A){
            exceptionFront = B;
            exceptionBack = A;
        }
        else {
            exceptionFront = A;
            exceptionBack = B;
        }

        PlusException ex = null;

        if(exceptionFront.size() == 1) {
            ex = exceptionFront.get(0);
        }
        else if(exceptionFront.size() > 1) {
            ex = new PlusCollectedException("Multiple Exceptions: [" + exceptionFront.size() + "]");

            for (int i = 0; i < exceptionFront.size(); i++) {
                ex.addSuppressed(exceptionFront.get(i));
            }
        }

        exceptionFront.clear();

        if(ex != null){
            throw ex;
        }
    }

}
