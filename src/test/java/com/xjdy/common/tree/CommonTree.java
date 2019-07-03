package com.xjdy.common.tree;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

import static com.xjdy.common.tree.TreeUtil.convertToTree;
import static com.xjdy.common.tree.TreeUtil.convertToTreeCanFilter;

/**
 * @author ：xusi
 * @date ：Created in 2019-07-03 15:41
 */
public class CommonTree {

    public static void main(String[] args) {
        testTree();
        testTreeWithFilter();
    }

    private static void testTree(){
        List<Tree> trees = new ArrayList<Tree>(){{
            add(new Tree("1",null,"jim",new ArrayList<>()));
            add(new Tree("2","1","tom",new ArrayList<>()));
            add(new Tree("3",null,"jack",new ArrayList<>()));
            add(new Tree("4","2","hello",new ArrayList<>()));
            add(new Tree(null,"1","hello",new ArrayList<>()));
        }};
        List<Tree> treeAndFilterList = convertToTreeCanFilter(trees,Tree::getCode,Tree::getParentCode,Tree::getChild,(Tree tree) -> tree.getName().equals("hello"));
        System.out.println(treeAndFilterList);
    }

    private static void testTreeWithFilter(){
        List<Tree> trees = new ArrayList<Tree>(){{
            add(new Tree("1",null,"jim",new ArrayList<>()));
            add(new Tree("2","1","tom",new ArrayList<>()));
            add(new Tree("3",null,"jack",new ArrayList<>()));
            add(new Tree("4","2","hello",new ArrayList<>()));
            add(new Tree(null,"1","hello",new ArrayList<>()));
        }};
        List<Tree> treeList = convertToTree(trees,Tree::getCode,Tree::getParentCode,Tree::getChild);
        System.out.println(treeList);
    }

    @Data
    @AllArgsConstructor
    @ToString
    static class Tree{
        private String code;
        private String parentCode;
        private String name;
        private List<Tree> child = new ArrayList<>();
    }
}
