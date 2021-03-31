package ru.sbt.mipt.oop;

import org.junit.Assert;

public class LightCheck implements Action {
    final String lightId;
    final boolean isOnExpected;

    public LightCheck(String lightId, boolean isOnExpected) {
        this.lightId = lightId;
        this.isOnExpected = isOnExpected;
    }

    @Override
    public void execute(Actionable component) {
        if (! (component instanceof Light))
            return;
        Light light = (Light) component;
        if (lightId.equals(light.getId()))
            Assert.assertEquals(isOnExpected, light.isOn());
    }
}
