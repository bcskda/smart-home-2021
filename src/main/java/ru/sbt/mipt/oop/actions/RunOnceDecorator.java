package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Actionable;

public class RunOnceDecorator implements Action {
    private final Runnable func;
    private boolean isCalled = false;

    public RunOnceDecorator(Runnable func) {
        this.func = func;
    }

    @Override
    public void execute(Actionable component) {
        if (!isCalled) {
            isCalled = true;
            func.run();
        }
    }
}
