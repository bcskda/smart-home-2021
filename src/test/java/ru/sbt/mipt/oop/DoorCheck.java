package ru.sbt.mipt.oop;

import org.junit.Assert;

public class DoorCheck implements Action {
    final String doorId;
    final boolean isOpenExpected;

    public DoorCheck(String doorId, boolean isOpen) {
        this.doorId = doorId;
        this.isOpenExpected = isOpen;
    }

    @Override
    public void execute(HomeComponent component) {
        if (! (component instanceof Door))
            return;
        Door door = (Door) component;
        if (doorId.equals(door.getId()))
            Assert.assertEquals(isOpenExpected, door.isOpen());
    }
}