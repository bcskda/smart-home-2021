package ru.sbt.mipt.oop;

import rc.RemoteControl;

import java.util.ArrayList;
import java.util.List;

public class RemoteControlMock implements RemoteControl {
    private final List<String> buttonCodes = new ArrayList<>();
    public final RemoteControl wrappedRc;

    public RemoteControlMock(RemoteControl wrappedRc) {
        this.wrappedRc = wrappedRc;
    }

    @Override
    public void onButtonPressed(String buttonCode) {
        buttonCodes.add(buttonCode);
        if (wrappedRc != null)
            wrappedRc.onButtonPressed(buttonCode);
    }

    public List<String> getButtonCodes() {
        return buttonCodes;
    }

    public void reset() {
        buttonCodes.clear();
    }
}
