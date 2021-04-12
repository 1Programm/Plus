package com.programm.projects.plus.resources.simple.utils;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class XmlReader {

    @RequiredArgsConstructor
    public static class Node{
        public final String name;
        public final List<Node> children;

        @Override
        public String toString() {
            if(children != null){
                StringBuilder sb = new StringBuilder(name + "{");
                for(int i=0;i<children.size();i++){
                    if(i != 0){
                        sb.append(", ");
                    }
                    sb.append(children.get(i).toString());
                }
                sb.append("}");

                return sb.toString();
            }

            return name;
        }
    }

    @AllArgsConstructor
    private static class Index{
        int i;

        @Override
        public String toString() {
            return "" + i;
        }
    }

    public static Node read(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();

        List<Integer> lineLengths = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))){
            String line;
            while((line = reader.readLine()) != null){
                lineLengths.add(line.length());
                sb.append(line);
            }
        }

        Node root;
        try {
            List<Node> children = readChildren(sb, new Index(0), new Index(sb.length() - 1));
            if(children.size() != 1){
                throw new XmlReadException("Invalid xml: There must be a single root node!");
            }

            root = children.get(0);
        }
        catch (XmlParseException e){
            int index = e.getIndex();
            int line = -1;
            while(index >= 0){
                index -= lineLengths.get(line + 1);
                line++;
            }

            throw new XmlReadException("Could not parse xml [" + line + "]: " + e.getMessage());
        }

        return root;
    }

    private static List<Node> readChildren(StringBuilder sb, Index start, Index stop) throws XmlParseException {
        int _nextOpen = sb.indexOf("<", start.i);
        if(_nextOpen > stop.i) _nextOpen = -1;

        int _nextClose = sb.indexOf(">", start.i);
        if(_nextClose > stop.i) _nextClose = -1;

        if(_nextOpen == -1 || _nextClose == -1){
            String content = sb.substring(start.i, stop.i);
            return Collections.singletonList(new Node(content, null));
        }


        List<Node> children = new ArrayList<>();

        while(start.i < stop.i) {
            while (sb.charAt(start.i) == ' ') {
                start.i++;
            }
            while (sb.charAt(stop.i) == ' ') {
                stop.i--;
            }

            if(start.i >= stop.i) break;

            //open <
            if (sb.charAt(start.i) != '<') {
                throw new XmlParseException("Invalid start of node: [" + sb.charAt(start.i) + "]", start.i);
            }
            start.i++;

            int nextClosing = sb.indexOf(">", start.i);

            if (nextClosing == -1) {
                throw new XmlParseException("No closing > for node!", start.i);
            }

            String content = sb.substring(start.i, nextClosing);
            int contentNextSpace = content.indexOf(' ');
            if (contentNextSpace == -1) {
                contentNextSpace = content.length();
            }

            String name = content.substring(0, contentNextSpace);

            start.i = nextClosing + 1;

            int earlyEndTest = nextClosing - 1;
            while(sb.charAt(earlyEndTest) == ' '){
                earlyEndTest--;
            }

            if(sb.charAt(earlyEndTest) == '/'){
                children.add(new Node(name, null));
                continue;
            }


            //Find closing tag

            Index endContent = new Index(0);
            int endTag = findEndTag(sb, start, stop, name, endContent);

            if(endTag == -1){
                throw new XmlParseException("No end tag for node: " + name, start.i);
            }

            List<Node> contentNodes = readChildren(sb, start, endContent);

            start.i = endTag + 1;

            Node child = new Node(name, contentNodes);
            children.add(child);
        }

        return children;
    }

    private static int findEndTag(StringBuilder sb, Index start, Index stop, String name, Index endContent){
        int open = 1;

        int i = start.i;
        int next = sb.indexOf(name, i);

        while(next != -1 && next <= stop.i) {
            if (sb.charAt(next - 1) == '/') {
                open--;
            } else {
                open++;
            }

            if (open == 0) {
                int nextClose = sb.indexOf(">", next);
                if(nextClose == -1) {
                    return -1;
                }

                endContent.i = next;
                while(sb.charAt(endContent.i) != '<'){
                    endContent.i--;
                }

                return nextClose;
            }
            else {
                i = next + name.length();
                next = sb.indexOf(name, i);
            }
        }

        return -1;
    }



}
