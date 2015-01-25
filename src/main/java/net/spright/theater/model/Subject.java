/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.spright.theater.model;

import net.spright.theater.control.Observer;

/**
 *
 * @author 嘉平
 */
public interface Subject {
    public boolean attach(Observer observer);
    public boolean detach(Observer observer);
    public void inform();
}
