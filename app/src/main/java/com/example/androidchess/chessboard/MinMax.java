package com.example.androidchess.chessboard;

import java.util.LinkedList;

public class MinMax {

    static Integer minimax(int depth, int nodeIndex,LinkedList<Node> scores, int h)
    {

//Node index = Index Curr node
//Depth = is depth in tree
//Scores = leaves of gametree
//H = maximum height of tree

        if (depth == h)
            return nodeIndex;

        if (depth % 2 == 0 )
            return Math.min(minimax(depth-1, nodeIndex*2,scores , h),
                minimax(depth-1, nodeIndex*2 + 1,  scores, h));
        else
            return Math.max(minimax(depth-1, nodeIndex*2,scores  , h),
                    minimax(depth-1, nodeIndex*2 + 1,  scores, h));
    }


  /*  static int log2(int n)
    {
        return (n==1)? 0 : 1 + log2(n/2);
    }
*/


}

