/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.model;

import java.util.LinkedList;
import java.util.List;
import net.spright.theater.control.Observer;

/**
 *
 * @author 嘉平
 */
public class BaseSubject implements Subject {
    protected final List<Observer> observers = new LinkedList();
    @Override
    public boolean attach(Observer observer) {
        return observers.add(observer);
    }

    @Override
    public boolean detach(Observer observer) {
        return observers.remove(observer);
    }

    @Override
    public void inform() {
        observers.parallelStream().forEach((Observer observer) -> observer.update(this));
    }
    
}
