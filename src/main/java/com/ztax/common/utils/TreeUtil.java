package com.ztax.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

public final class TreeUtil {
    private static final Logger logger = LoggerFactory.getLogger(TreeUtil.class);

    private TreeUtil() {
    }

    public static <T> List<T> toTree(List<T> data, Function<T, Object> getIdFunction, Function<T, Object> getParentIdFunction, BiConsumer<T, List<T>> addChildFunction) {
        return toTree(data, getIdFunction, getParentIdFunction, addChildFunction, (Function)null, (Predicate)null, false);
    }

    public static <T> List<T> toTree(List<T> data, Function<T, Object> getIdFunction, Function<T, Object> getParentIdFunction, BiConsumer<T, List<T>> addChildFunction, boolean showOutLineNode) {
        return toTree(data, getIdFunction, getParentIdFunction, addChildFunction, (Function)null, (Predicate)null, showOutLineNode);
    }

    public static <T> List<T> toTree(List<T> data, Function<T, Object> getIdFunction, Function<T, Object> getParentIdFunction, BiConsumer<T, List<T>> addChildFunction, Function<T, Object> getOrderColumnFunction) {
        return toTree(data, getIdFunction, getParentIdFunction, addChildFunction, getOrderColumnFunction, (Predicate)null, false);
    }

    public static <T> List<T> toTree(List<T> data, Function<T, Object> getIdFunction, Function<T, Object> getParentIdFunction, BiConsumer<T, List<T>> addChildFunction, Function<T, Object> getOrderColumnFunction, boolean showOutLineNode) {
        return toTree(data, getIdFunction, getParentIdFunction, addChildFunction, getOrderColumnFunction, (Predicate)null, showOutLineNode);
    }

    public static <T> List<T> toTree(List<T> data, Function<T, Object> getIdFunction, Function<T, Object> getParentIdFunction, BiConsumer<T, List<T>> addChildFunction, Function<T, Object> getOrderColumnFunction, Predicate<Object> parentIsTopFunction) {
        return toTree(data, getIdFunction, getParentIdFunction, addChildFunction, getOrderColumnFunction, parentIsTopFunction, false);
    }

    public static <T> List<T> toTree(List<T> data, Function<T, Object> getIdFunction, Function<T, Object> getParentIdFunction, BiConsumer<T, List<T>> addChildFunction, Function<T, Object> getOrderColumnFunction, Predicate<Object> parentIsTopFunction, boolean showOutLineNode) {
        if (data == null) {
            return null;
        } else {
            if (getOrderColumnFunction != null) {
                data.sort(Comparator.comparing((obj) -> {
                    return (Comparable)getOrderColumnFunction.apply(obj);
                }));
            }

            List<T> answer = new ArrayList();
            int cap = (int)Math.ceil((double)data.size() / 0.75D) + 1;
            Map<String, List<T>> groupByParentIdMap = new LinkedHashMap(cap);
            Iterator var10 = data.iterator();

            while(true) {
                Object record;
                while(var10.hasNext()) {
                    record = var10.next();
                    Object parentIdValue = getParentIdFunction.apply((T)record);
                    Object idValue = getIdFunction.apply((T)record);
                    if (parentIdValue != null && !parentIdValue.equals(idValue) && (parentIsTopFunction == null || !parentIsTopFunction.test(parentIdValue))) {
                        String parentId = String.valueOf(parentIdValue);
                        ((List)groupByParentIdMap.computeIfAbsent(parentId, (k) -> {
                            return new ArrayList();
                        })).add(record);
                    } else {
                        answer.add((T)record);
                    }
                }

                var10 = data.iterator();

                while(var10.hasNext()) {
                    record = var10.next();
                    String idValue = String.valueOf(getIdFunction.apply((T)record));
                    if (groupByParentIdMap.containsKey(idValue)) {
                        addChildFunction.accept((T)record, groupByParentIdMap.get(idValue));
                        groupByParentIdMap.remove(idValue);
                    }
                }

                if (groupByParentIdMap.size() > 0) {
                    var10 = groupByParentIdMap.entrySet().iterator();

                    while(var10.hasNext()) {
                        Entry<String, List<T>> entry = (Entry)var10.next();
                        if (showOutLineNode) {
                            answer.addAll((Collection)entry.getValue());
                        } else {
                            logger.warn("有[{}]个子节点因未找到其宣称的父节点[{}]而未显示", ((List)entry.getValue()).size(), entry.getKey());
                        }
                    }
                }

                return answer;
            }
        }
    }
}