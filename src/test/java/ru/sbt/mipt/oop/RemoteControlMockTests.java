package ru.sbt.mipt.oop;

import org.junit.Assert;
import org.junit.Test;
import ru.sbt.mipt.oop.remotecontrol.RemoteControlImpl;

import java.util.concurrent.atomic.AtomicInteger;

public class RemoteControlMockTests {
    @Test
    public void dispatch() {
        RemoteControlMock rcFirst = new RemoteControlMock(null);
        RemoteControlMock rcSecond = new RemoteControlMock(null);
        RemoteControlRegistryMock registryMock = new RemoteControlRegistryMock();
        registryMock.registerRemoteControl(rcFirst, "first");
        registryMock.registerRemoteControl(rcSecond, "second");

        Assert.assertTrue(rcFirst.getButtonCodes().isEmpty());
        Assert.assertTrue(rcSecond.getButtonCodes().isEmpty());

        registryMock.call("first", "A");
        Assert.assertArrayEquals(
                new String[]{"A"}
                , rcFirst.getButtonCodes().toArray());
        Assert.assertTrue(rcSecond.getButtonCodes().isEmpty());

        registryMock.call("second", "2");
        Assert.assertArrayEquals(
                new String[]{"A"}
                , rcFirst.getButtonCodes().toArray());
        Assert.assertArrayEquals(
                new String[]{"2"}
                , rcSecond.getButtonCodes().toArray());
    }

    @Test
    public void passThrough() {
        RemoteControlImpl rcFirst = new RemoteControlImpl("first");
        AtomicInteger flagFirst = new AtomicInteger();
        rcFirst.addCommand("1", () -> { flagFirst.set(1); });

        RemoteControlImpl rcSecond = new RemoteControlImpl("second");
        AtomicInteger flagSecond = new AtomicInteger();
        rcSecond.addCommand("B", () -> { flagSecond.set(1); });

        RemoteControlMock rcMockFirst = new RemoteControlMock(rcFirst);
        RemoteControlMock rcMockSecond = new RemoteControlMock(rcSecond);
        RemoteControlRegistryMock registryMock = new RemoteControlRegistryMock();
        registryMock.registerRemoteControl(rcMockFirst, "first");
        registryMock.registerRemoteControl(rcMockSecond, "second");

        Assert.assertTrue(rcMockFirst.getButtonCodes().isEmpty());
        Assert.assertTrue(rcMockFirst.getButtonCodes().isEmpty());

        registryMock.call("first", "1");
        Assert.assertArrayEquals(
                new String[]{"1"}
                , rcMockFirst.getButtonCodes().toArray());
        Assert.assertTrue(rcMockSecond.getButtonCodes().isEmpty());
        Assert.assertEquals(1, flagFirst.get());
        Assert.assertEquals(0, flagSecond.get());

        registryMock.call("second", "B");
        Assert.assertArrayEquals(
                new String[]{"1"}
                , rcMockFirst.getButtonCodes().toArray());
        Assert.assertArrayEquals(
                new String[]{"B"}
                , rcMockSecond.getButtonCodes().toArray());
        Assert.assertEquals(1, flagFirst.get());
        Assert.assertEquals(1, flagSecond.get());
    }
}
