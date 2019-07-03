package com.xjdy.common.tree;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
     * @param currentCodeFunction
     * @param parentCodeFunction
     * @param getChildsFunction
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> List<T> convertToTree(List<T> source, Function<T,R> currentCodeFunction, Function<T,R> parentCodeFunction,Function<T,List<T>> getChildsFunction){

        if(CollectionUtils.isEmpty(source)){
            return Lists.newArrayList();
        }

        Map<R,T> childCodeMap = source.stream().filter(Objects::nonNull).filter(item -> currentCodeFunction.apply(item) != null).collect(Collectors.toMap(currentCodeFunction, (p) -> p));

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
     * @param currentCodeFunction
     * @param parentCodeFunction
     * @param getChildsFunction
     * @param searchFunction
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> List<T> convertToTreeCanFilter(List<T> source, Function<T,R> currentCodeFunction, Function<T,R> parentCodeFunction,Function<T,List<T>> getChildsFunction,Function<T,Boolean> searchFunction){

        if(CollectionUtils.isEmpty(source)){
            return Lists.newArrayList();
        }

        List<T> filterList = Lists.newArrayList();

        Map<R,T> searchMap = new HashMap<>();
        Map<R,T> allMap = new HashMap<>();

        source.stream().filter(Objects::nonNull).filter(item -> currentCodeFunction.apply(item) != null).map(item -> {

            R childCode = currentCodeFunction.apply(item);
            searchMap.put(childCode,item);
            allMap.put(childCode,item);

            if(searchFunction.apply(item)){
                filterList.add(item);
            }

            return item;
        }).collect(Collectors.toList());

        List<T> allFilterData = Lists.newArrayList();

        filterList.forEach(item -> {

            T parentObj = item;

            do{
                allFilterData.add(parentObj);
                searchMap.remove(currentCodeFunction.apply(parentObj));

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
}
