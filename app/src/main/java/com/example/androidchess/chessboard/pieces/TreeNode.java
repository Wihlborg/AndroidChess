package com.example.androidchess.chessboard.pieces;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;



    public class TreeNode implements Iterable<TreeNode> {
        double calculation;
        String fen;

        @Override
        public String toString() {
            return "TreeNode{" +
                    "calculation=" + calculation +
                    ", fen='" + fen + '\'' +
                    '}';
        }

         public ArrayList<TreeNode> children;

        public TreeNode(double calculation,String fen) {
            children = new ArrayList<>();
            this.calculation=calculation;
            this.fen=fen;
        }



        public boolean addChild(TreeNode n) {
            return children.add(n);
        }

        public boolean removeChild(TreeNode n) {
            return children.remove(n);
        }

        public Iterator<TreeNode> iterator() {
            return children.iterator();
        }

    }

