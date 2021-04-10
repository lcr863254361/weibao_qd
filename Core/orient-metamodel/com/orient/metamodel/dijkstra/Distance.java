package com.orient.metamodel.dijkstra;

import java.util.HashMap;

/**
 * ${DESCRIPTION}
 *
 * @author Administrator
 * @create 2017-07-15 9:32
 */
public interface Distance {

    public static final MinStep UNREACHABLE = new MinStep(false, -1);
    /**
     * @param start
     * @param end
     * @param stepLength
     * @return
     * @Author:lulei
     * @Description: 起点到终点的最短路径
     */
    public MinStep getMinStep(int start, int end, final HashMap<Integer, HashMap<Integer, Integer>> stepLength);
}
