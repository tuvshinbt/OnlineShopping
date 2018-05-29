package edu.mum.cs490.project.framework.observer;

import edu.mum.cs490.project.domain.Order;

import java.util.Observable;

public class TransferSubject extends Observable {
    public void doNotify() {
        setChanged();
        notifyObservers();
    }
}
