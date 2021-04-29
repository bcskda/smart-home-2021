package ru.sbt.mipt.oop.actions;

import ru.sbt.mipt.oop.Action;
import ru.sbt.mipt.oop.Actionable;
import ru.sbt.mipt.oop.Light;

public class ToggleLights implements Action {
    @Override
    public void execute(Actionable component) {
        if (!(component instanceof Light))
            return;
        Light light = (Light) component;
        light.setOn(!light.isOn());
    }
}
