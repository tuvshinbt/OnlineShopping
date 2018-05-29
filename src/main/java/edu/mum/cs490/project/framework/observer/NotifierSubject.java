package edu.mum.cs490.project.framework.observer;

import java.util.Observable;

public class NotifierSubject extends Observable {
    public void doNotify() {
        setChanged();
        notifyObservers();
    }
}
