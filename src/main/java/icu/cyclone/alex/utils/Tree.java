package icu.cyclone.alex.utils;

import java.util.List;

public class Tree<T> {
    private Node<T> root;

    public static <T> Tree<T> getNew() {
        return new Tree<>();
    }

    public Tree() {
        root = new Node<>();
    }

    public Node<T> getRoot() {
        return root;
    }

    public Tree<T> setRoot(Node<T> root) {
        root.setParent(null);
        this.root = root;
        return this;
    }

    @Override
    public String toString() {
        return "Tree (root node): " + root.toString();
    }

    public String toStringTree() {
        return root.getData() + System.lineSeparator()
                + childToStrWithTreePrefix(root, "");
    }
    public String toStringTree(String depthPrefix) {
        return root.getData() + System.lineSeparator()
                + childToStrWithDepthPrefix(root, depthPrefix);
    }

    private String childToStrWithTreePrefix(Node<T> node, String prefix) {
        List<Node<T>> children = node.getChildren();
        if (children.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < children.size() - 1; i++) {
            sb.append(UText.eachLineWithPrefix(
                    children.get(i).getData().toString(), prefix + "├ ", prefix + "│ "))
                    .append(System.lineSeparator())
                    .append(childToStrWithTreePrefix(children.get(i), prefix + "│ "));
        }
        sb.append(UText.eachLineWithPrefix(
                children.get(children.size() - 1).getData().toString(), prefix + "└ ", prefix + "  "))
                .append(System.lineSeparator())
                .append(childToStrWithTreePrefix(children.get(children.size() - 1), prefix + "  "));

        return sb.toString();
    }

    private String childToStrWithDepthPrefix(Node<T> node, String depthPrefix) {
        List<Node<T>> children = node.getChildren();
        if (children.size() == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        for (Node<T> n: node.getChildren()) {
            sb.append(UText.stringRepeat(depthPrefix, n.parentsCount()))
                    .append(n.getData())
                    .append(System.lineSeparator())
                    .append(childToStrWithDepthPrefix(n,depthPrefix));
        }
        return sb.toString();
    }
}
