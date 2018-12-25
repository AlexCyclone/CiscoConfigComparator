package icu.cyclone.alex.cisco;

import icu.cyclone.alex.utils.InvalidFileFormatException;
import icu.cyclone.alex.utils.Node;
import icu.cyclone.alex.utils.Tree;
import icu.cyclone.alex.utils.UText;

import java.util.ArrayList;

class ConfigParser {
    static Tree<String> parse(ArrayList<String> text) throws InvalidFileFormatException {
        preParse(text);
        return startParse(text);
    }

    private static void preParse(ArrayList<String> text) throws InvalidFileFormatException {
        reformatBanners(text);
        text.removeIf(ConfigParser::isEmptyLine);
        contractionSpaces(text, 0, text.size() - 1, 0);
        expandBootSection(text);
    }

    private static void reformatBanners(ArrayList<String> text) {
        int bIndex = -1;
        String section = "";
        StringBuilder banner = new StringBuilder();

        for (int i = 0; i < text.size(); i++) {
            String line = text.get(i);
            if (bIndex < 0 && line.contains("banner")) {
                bIndex = i;
                banner.append(line, 0, UText.calcSpacesBefore(line)).append(" ^");
                section = line.substring(0, line.indexOf("^C"));
                line = line.substring(line.indexOf("^C") + 2);
            }
            if (bIndex > 0 && !line.contains("^C")) {
                banner.append(line).append(UText.SLS);
            } else if (bIndex > 0 && line.contains("^C")) {
                banner.append(line, 0, line.indexOf("^C"));
                replaceSection(text, bIndex, i, section, banner.toString());

                i = bIndex + 1;
                bIndex = -1;
                banner = new StringBuilder();
            }
        }
    }

    private static void replaceSection(ArrayList<String> text, int from, int to, String section, String data) {
        if (to >= from + 1) {
            text.subList(from + 1, to + 1).clear();
        }
        text.set(from, section);
        text.add(from + 1, data);
    }

    private static boolean isEmptyLine(String line) {
        String lineCopy = String.copyValueOf(line.toCharArray()).trim();
        if (lineCopy.equals("!")) {
            return true;
        } else return lineCopy.length() == 0;
    }

    private static void contractionSpaces(ArrayList<String> text, int from, int to, int depth) throws InvalidFileFormatException {
        if (text.size() == 0) throw new InvalidFileFormatException("Empty file");
        int subSection = -1;
        int diff = UText.calcSpacesBefore(text.get(from)) - depth;

        for (int i = from; i <= to; i++) {
            int downSpace = diff + depth - UText.calcSpacesBefore(text.get(i));
            downSpace = downSpace < 0 ? 0 : downSpace;
            text.set(i, text.get(i).substring(diff - downSpace));

            int spacesBefore = UText.calcSpacesBefore(text.get(i));
            if (spacesBefore > depth && subSection < 0) {
                subSection = i;
            }
            if (subSection >= 0 && (spacesBefore == depth || i == to)) {
                contractionSpaces(text, subSection, spacesBefore == depth ? i - 1 : i, depth + 1);
                subSection = -1;
            }
        }
    }

    private static void expandBootSection(ArrayList<String> text) {
        boolean hasBootSection = false;
        for (int i = 0; i < text.size(); i++) {
            if (!hasBootSection && text.get(i).contains("boot-start-marker")) {
                hasBootSection = true;
                continue;
            }
            if (hasBootSection) {
                text.set(i, " " + text.get(i));
            }
            if (text.get(i).contains("boot-end-marker")) {
                hasBootSection = false;
            }
        }
    }

    private static Tree<String> startParse(ArrayList<String> text) throws InvalidFileFormatException {
        Tree<String> configTree = new Tree<>();
        Node<String> node = configTree.getRoot();
        int prevDepth = 0;

        for (String line : text) {
            int depth = UText.calcSpacesBefore(line);

            if (prevDepth == depth) {
                addChild(node, line);
            } else if (depth > prevDepth) {
                node = node.getChild(node.childrenCount() - 1);
                addChild(node, line);
                prevDepth = depth;
            } else {
                for (int i = depth; i < prevDepth; i++) {
                    node = node.getParent();
                }
                addChild(node, line);
                prevDepth = depth;
            }
        }
        return configTree;
    }

    private static void addChild(Node<String> node, String data) throws InvalidFileFormatException {
        if (node.getChildren(data).size() > 0) {
            throw new InvalidFileFormatException();
        }
        if (data.contains(UText.SLS)) {
            node.addChild(UText.trimLeft(data));
        } else {
            node.addChild(data.trim());
        }
    }
}