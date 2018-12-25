package icu.cyclone.alex.cisco;

import icu.cyclone.alex.utils.Node;
import icu.cyclone.alex.utils.Tree;
import icu.cyclone.alex.utils.UDiff;
import icu.cyclone.alex.utils.UText;

import java.util.Comparator;
import java.util.List;

public class ConfigComparator {
    private Tree<ComparedSection> tree = new Tree<>();
    private String originName;
    private String comparedName;
    private int equalsCount;
    private int differencesCount;

    public ConfigComparator(Config origin, Config compared, int strictIndex) {
        compare(origin.getConfigTree(), compared.getConfigTree(), strictIndex);
    }

    public ConfigComparator(String origin, String compared, int strictIndex) {
        compare(Config.getNew().loadConfigFromFile(origin).getConfigTree(),
                Config.getNew().loadConfigFromFile(compared).getConfigTree(),
                strictIndex);
    }

    private void compare(Tree<String> origin, Tree<String> compared, int strictIndex) {
        fillRoot(origin.getRoot().getData(), compared.getRoot().getData());
        fillOrigin(origin.getRoot(), tree.getRoot());
        fillCompared(compared.getRoot(), tree.getRoot());
        if (strictIndex > 0) {
            compareNonStrict(tree.getRoot(), strictIndex);
        }
        finaliseCompare(tree.getRoot());
        calcStatistic(tree.getRoot());
    }

    private void fillRoot(String origin, String compared) {
        String rootString = "'" + origin + "' compared to '" + compared + "'";
        tree.getRoot().setData(ComparedSection.getNew()
                .setOrigin(rootString)
                .setCompared(rootString));
        originName = origin;
        comparedName = compared;
    }

    private void fillOrigin(Node<String> origin, Node<ComparedSection> startNode) {
        for (Node<String> oChild : origin.getChildren()) {
            startNode.addChild(ComparedSection.getNew().setOrigin(oChild.getData()));
            if (oChild.hasChildren()) {
                fillOrigin(oChild, startNode.getChild(startNode.childrenCount() - 1));
            }
        }
    }

    private void fillCompared(Node<String> compared, Node<ComparedSection> startNode) {
        int lastSuccess = -1;
        for (Node<String> cChild : compared.getChildren()) {
            ComparedSection sec = ComparedSection.getNew().setOrigin(cChild.getData());

            List<Node<ComparedSection>> found =
                    startNode.getChildren(sec,
                            Comparator.comparing(o -> o.getOrigin() != null ? o.getOrigin() : ""));
            if (found.size() > 0) {
                lastSuccess = startNode.getChildIndex(found.get(0));
                startNode.getChild(lastSuccess).getData().setCompared(cChild.getData());
            } else {
                lastSuccess += 1;
                startNode.addChild(lastSuccess, ComparedSection.getNew().setCompared(cChild.getData()));
            }
            if (cChild.hasChildren()) {
                fillCompared(cChild, startNode.getChild(lastSuccess));
            }

        }
    }

    private void compareNonStrict(Node<ComparedSection> startNode, int strictIndex) {
        for (int i = 0; i < startNode.childrenCount(); i++) {
            ComparedSection comparedSection = startNode.getChild(i).getData();

            if (comparedSection.isEqual() == null && comparedSection.getOrigin() != null &&
                    !startNode.getChild(i).hasChildren()) {
                int similarIndex = findSimilar(startNode, comparedSection.getOrigin(), strictIndex);
                if (similarIndex >= 0) {
                    comparedSection.setCompared(startNode.getChild(similarIndex).getData().getCompared());
                    startNode.removeChild(similarIndex);
                    i = similarIndex <= i ? i - 1 : i;
                }
            }

            if (startNode.getChild(i).hasChildren()) {
                compareNonStrict(startNode.getChild(i), strictIndex);
            }
        }
    }

    private int findSimilar(Node<ComparedSection> startNode, String origin, int strictIndex) {
        int differences = Integer.MAX_VALUE;
        int similarChild = -1;
        List<Node<ComparedSection>> children = startNode.getChildren();
        for (int i = 0; i < children.size(); i++) {
            String compared = children.get(i).getData().getCompared();
            if (compared == null || !UText.firstWord(compared).equals(UText.firstWord(origin))) {
                continue;
            }
            int diff = UDiff.differences(origin.split(" "), compared.split(" "));
            if (diff < differences && diff <= strictIndex) {
                differences = diff;
                similarChild = i;
            }
        }
        return similarChild;
    }

    private void finaliseCompare(Node<ComparedSection> startNode) {
        for (Node<ComparedSection> node : startNode.getChildren()) {
            if (!node.getData().hasOrigin()) {
                node.getData().setOrigin("");
            }
            if (!node.getData().hasCompared()) {
                node.getData().setCompared("");
            }
            if (node.hasChildren()) {
                finaliseCompare(node);
            }
        }
    }

    private void calcStatistic(Node<ComparedSection> startNode) {
        for (Node<ComparedSection> node : startNode.getChildren()) {
            if (node.getData().isEqual()) {
                equalsCount += 1;
            } else {
                differencesCount += 1;
            }
            if (node.hasChildren()) {
                calcStatistic(node);
            }
        }
    }

    @Override
    public String toString() {
        return tree.getRoot().getData().getOrigin()
                + ", equals count: " + equalsCount
                + ", differences count: " + differencesCount;
    }

    public String fullView() {
        return tree.toStringTree(" ")
                .replace("ORIGIN", originName)
                .replace("COMPARED", comparedName);
    }

}
