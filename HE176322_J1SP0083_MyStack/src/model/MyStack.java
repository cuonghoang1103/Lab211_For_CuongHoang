package model;

import constants.Message;
import java.util.ArrayList;
import java.util.Collections;

/**
 * MODEL: the stack the brief asks for - LIFO, "last in, first out".
 *
 * @author HE176322
 */
public class MyStack {

    // The brief's property: the values in the stack, bottom first; the LAST element is
    // the top.
    private ArrayList<Integer> stackValues;

    // JavaBean constructor: creates an empty stack.
    public MyStack() {
        stackValues = new ArrayList<>();
    }

    // Returns a COPY of the values, bottom first.
    public ArrayList<Integer> getStackValues() {
        return new ArrayList<>(stackValues);
    }

    // Replaces the whole content (bottom first); a copy is stored for the same reason
    // getStackValues returns one.
    public void setStackValues(ArrayList<Integer> stackValues) {
        this.stackValues = new ArrayList<>(stackValues);
    }

    // The brief's push(): puts a value on the top.
    public void push(int value) {
        stackValues.add(value);
    }

    // The brief's pop(): removes and returns the top value.
    public int pop() throws Exception {
        // the brief: pop on an empty stack must not crash - report it
        if (isEmpty()) {
            throw new Exception(Message.STACK_EMPTY);
        }
        return stackValues.remove(stackValues.size() - 1);
    }

    // The brief's get() (peek): returns the top value WITHOUT removing it.
    public int get() throws Exception {
        // the brief: get on an empty stack must not crash - report it
        if (isEmpty()) {
            throw new Exception(Message.STACK_EMPTY);
        }
        return stackValues.get(stackValues.size() - 1);
    }

    // Helper the brief suggests: tells whether the stack holds nothing.
    public boolean isEmpty() {
        return stackValues.isEmpty();
    }

    // Helper the brief suggests: how many values are stored.
    public int size() {
        return stackValues.size();
    }

    // Polymorphism: overrides Object.toString() to give the stack from TOP to bottom.
    @Override
    public String toString() {
        ArrayList<Integer> topFirst = getStackValues();
        Collections.reverse(topFirst);
        return topFirst.toString();
    }
}
