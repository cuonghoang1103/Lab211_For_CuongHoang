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
    // the top. Named ...List because a collection name ends with "List" (checklist 1.5).
    // brief: stackValues
    private ArrayList<Integer> stackValueList;

    // JavaBean constructor: creates an empty stack.
    public MyStack() {
        stackValueList = new ArrayList<>();
    }

    // Returns a COPY of the values, bottom first.
    public ArrayList<Integer> getStackValueList() {
        return new ArrayList<>(stackValueList);
    }

    // Replaces the whole content (bottom first); a copy is stored for the same reason
    // getStackValueList returns one.
    public void setStackValueList(ArrayList<Integer> stackValueList) {
        this.stackValueList = new ArrayList<>(stackValueList);
    }

    // The brief's push(): puts a value on the top.
    public void push(int value) {
        stackValueList.add(value);
    }

    // The brief's pop(): removes and returns the top value.
    public int pop() throws Exception {
        // the brief: pop on an empty stack must not crash - report it
        if (isEmpty()) {
            throw new Exception(Message.STACK_EMPTY);
        }

        // the top is the last element: remove it by its index
        return stackValueList.remove(stackValueList.size() - 1);
    }

    // The brief's get() (peek): returns the top value WITHOUT removing it.
    public int get() throws Exception {
        // the brief: get on an empty stack must not crash - report it
        if (isEmpty()) {
            throw new Exception(Message.STACK_EMPTY);
        }

        // read the last element, leave it in the stack
        return stackValueList.get(stackValueList.size() - 1);
    }

    // Helper the brief suggests: tells whether the stack holds nothing.
    public boolean isEmpty() {
        return stackValueList.isEmpty();
    }

    // Helper the brief suggests (size() in the brief): counts how many values are stored.
    public int countValues() {
        return stackValueList.size();
    }

    // Polymorphism: overrides Object.toString() to give the stack from TOP to bottom.
    @Override
    public String toString() {
        ArrayList<Integer> topFirstList = getStackValueList();

        // reverse the COPY, so the stack itself keeps its order
        Collections.reverse(topFirstList);
        return topFirstList.toString();
    }
}
