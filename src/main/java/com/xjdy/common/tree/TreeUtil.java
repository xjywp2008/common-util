package com.xjdy.common.tree;

import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author ：xusi
 * @date ：Created in 2019-07-03 11:36
 */
public class TreeUtil {

    /**
     * 自动组装成树结构
     *
     * 注意：
     *      1：childCode 与 parentCode类型必须要一致，否则数据会不准
     *      2：childs属性不能为null，必须要之前初始化好
     *
     * @param source
     * @param childCodeFunction
     * @param parentCodeFunction
     * @param getChildsFunction
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> List<T> convertToTree(List<T> source, Function<T,R> childCodeFunction, Function<T,R> parentCodeFunction,Function<T,List<T>> getChildsFunction){

        if(CollectionUtils.isEmpty(source)){
            return Lists.newArrayList();
        }

        source = source.stream().filter(Objects::nonNull).filter(item -> childCodeFunction.apply(item) != null).collect(Collectors.toList());

        Map<R,T> childCodeMap = source.stream().collect(Collectors.toMap(childCodeFunction, (p) -> p));

        return convertToResult(childCodeMap,source,parentCodeFunction,getChildsFunction);
    }

    /**
     * 支持搜索的树自动组装服务
     *
     *  注意：
     *       1：childCode 与 parentCode类型必须要一致，否则数据会不准
     *       2：childs属性不能为null，必须要之前初始化好
     *
     * @param source
     * @param childCodeFunction
     * @param parentCodeFunction
     * @param getChildsFunction
     * @param searchFunction
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> List<T> convertToTreeCanFilter(List<T> source, Function<T,R> childCodeFunction, Function<T,R> parentCodeFunction,Function<T,List<T>> getChildsFunction,Function<T,Boolean> searchFunction){

        if(CollectionUtils.isEmpty(source)){
            return Lists.newArrayList();
        }

        source = source.stream().filter(Objects::nonNull).filter(item -> childCodeFunction.apply(item) != null).collect(Collectors.toList());

        Map<R,T> searchMap = new HashMap<>();
        Map<R,T> allMap = new HashMap<>();

        source.forEach(item ->{
            R childCode = childCodeFunction.apply(item);
            searchMap.put(childCode,item);
            allMap.put(childCode,item);
        });

        List<T> allFilterData = Lists.newArrayList();

        List<T> filterList = source.stream().filter(searchFunction::apply).collect(Collectors.toList());

        filterList.forEach(item -> {

            T parentObj = item;

            do{
                allFilterData.add(parentObj);
                searchMap.remove(childCodeFunction.apply(parentObj));

                parentObj = searchMap.get(parentCodeFunction.apply(parentObj));

            }while(parentObj != null);

        });

        return convertToResult(allMap,allFilterData,parentCodeFunction,getChildsFunction);
    }

    private static <T,R> List<T> convertToResult(Map<R,T> childCodeMap,List<T> source, Function<T,R> parentCodeFunction,Function<T,List<T>> getChildsFunction){
        List<T> result = Lists.newArrayList();

        source.forEach(item -> {
            R parentCode = parentCodeFunction.apply(item);
            T parentObj = childCodeMap.get(parentCode);

            if(parentObj == null){
                result.add(item);
            }else{
                List<T> childs = getChildsFunction.apply(parentObj);
                if(childs == null){
                    throw new RuntimeException("不支持childs为null的对象");
                }
                childs.add(item);
            }
        });

        return result;
    }

    public static void main(String[] args) {
        List<Tree> trees = new ArrayList<Tree>(){{
           add(new Tree("1",null,"jim",new ArrayList<>()));
           add(new Tree("2","1","tom",new ArrayList<>()));
           add(new Tree("3",null,"jack",new ArrayList<>()));
           add(new Tree("4","2","hello",new ArrayList<>()));
           add(new Tree(null,"1","hello",new ArrayList<>()));
        }};

        List<Tree> treeList = convertToTreeCanFilter(trees,Tree::getCode,Tree::getParentCode,Tree::getChild,(Tree tree) -> tree.getName().equals("hello"));
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
