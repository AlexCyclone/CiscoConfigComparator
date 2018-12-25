package icu.cyclone.alex.utils;

import java.util.*;

public class Node<T> {
    private T data;
    private List<Node<T>> children = new ArrayList<>();
    private Node<T> parent;

    public static <T> Node<T> getNew() {
        return new Node<>();
    }

    public Node() {
    }

    public T getData() {
        return data;
    }

    public Node<T> setData(T data) {
        this.data = data;
        return this;
    }

    protected Node<T> setParent(Node<T> parent) {
        this.parent = parent;
        return this;
    }

    public Node<T> getParent() {
        return parent;
    }

    public boolean hasParent() {
        return parent != null;
    }

    public int parentsCount() {
        if (hasParent()) {
            return getParent().parentsCount() + 1;
        }
        return 0;
    }

    public Node<T> addChild(Node<T> child) {
        children.add(child.setParent(this));
        return this;
    }

    public Node<T> addChild(T data) {
        addChild(Node.<T>getNew().setData(data));
        return this;
    }

    public Node<T> addChild(int index, Node<T> child) throws IndexOutOfBoundsException {
        children.add(index, child.setParent(this));
        return this;
    }

    public Node<T> addChild(int index, T data) throws IndexOutOfBoundsException {
        addChild(index, Node.<T>getNew().setData(data));
        return this;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public Node<T> getChild(int index) throws IndexOutOfBoundsException {
        return children.get(index);
    }

    public List<Node<T>> getChildren(T data) {
        List<Node<T>> eqChildren = new ArrayList<>();
        for (Node<T> child : children) {
            if (child.getData().equals(data)) {
                eqChildren.add(child);
            }
        }
        return eqChildren;
    }

    public List<Node<T>> getChildren(T data, Comparator<T> comparator) {
        List<Node<T>> eqChildren = new ArrayList<>();
        for (Node<T> child : children) {
            if (comparator.compare(child.getData(), data) == 0) {
                eqChildren.add(child);
            }
        }
        return eqChildren;
    }

    public int getChildIndex(Node<T> child) {
        return children.indexOf(child);
    }

    public Node<T> removeAllChildren() {
        this.children = new ArrayList<>();
        return this;
    }

    public Node<T> removeChild(Node<T> child) {
        children.remove(child);
        return this;
    }

    public Node<T> removeChild(int index) throws IndexOutOfBoundsException {
        children.remove(index);
        return this;
    }

    public int childrenCount() {
        return children.size();
    }

    public boolean hasChildren() {
        return childrenCount() > 0;
    }

    @Override
    public String toString() {
        return data.toString() +
                ", hasParent=" + hasParent() +
                ", hasChildren=" + hasChildren();
    }
}
