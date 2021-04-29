package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Actionable;

public class PairActionDecorator implements Action {
    Action first;
    Action second;

    public PairActionDecorator(Action first, Action second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public void execute(Actionable component) {
        first.execute(component);
        second.execute(component);
    }
}
