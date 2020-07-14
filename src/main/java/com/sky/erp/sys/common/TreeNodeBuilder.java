package com.sky.erp.sys.common;

import java.util.ArrayList;
import java.util.List;

public class TreeNodeBuilder {

    /**
     * 构建菜单的层级关系
     * @param list
     * @param topId
     * @return
     */
    public static List<TreeNode> builder(List<TreeNode> list, Integer topId){
        List<TreeNode> nodes = new ArrayList<>();
        for (TreeNode node1 : list) {
            if (node1.getPid()==topId){
                nodes.add(node1);
            }
            for (TreeNode node2 : nodes) {
                if (node1.getPid()==node2.getId()){
                    node2.getChildren().add(node1);
                }
            }
        }
        return nodes;
    }
}
