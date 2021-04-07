package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Actionable;

import java.util.List;
import java.util.Objects;

public class AllDecorator implements Action {
    private final List<Action> toExecute;

    public AllDecorator(List<Action> toExecute) {
        this.toExecute = toExecute;
    }

    @Override
    public void execute(Actionable component) {
        toExecute.stream().filter(Objects::nonNull).forEach(action -> action.execute(component));
    }
}
