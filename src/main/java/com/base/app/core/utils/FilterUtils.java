package com.base.app.core.utils;

import com.base.app.core.annotations.Searchable;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class FilterUtils {
    private FilterUtils() {
    }

    public static <T> boolean recursionListFilter(List<T> lists, String searchParams) {
        if (lists == null) {
            return false;
        } else {
            Iterator var2 = lists.iterator();

            Object fieldValue;
            label50:
            do {
                while(var2.hasNext()) {
                    T t = (T) var2.next();
                    if (t instanceof String) {
                        fieldValue = (String)t;
                        continue label50;
                    }

                    try {
                        List<Field> searchableFields = FieldUtils.getAllFieldsList(t.getClass());
                        Iterator var5 = searchableFields.iterator();

                        while(var5.hasNext()) {
                            Field field = (Field)var5.next();
                            field.setAccessible(true);
                            fieldValue = field.get(t);
                            if (field.getType() == List.class) {
                                boolean find = recursionListFilter((List)fieldValue, searchParams);
                                if (find) {
                                    return true;
                                }
                            } else if (fieldValue != null) {
                                String value = fieldValue.toString();
                                if (value.contains(searchParams)) {
                                    return true;
                                }
                            }
                        }
                    } catch (Exception var9) {
                        var9.printStackTrace();
                    }
                }

                return false;
            } while(!StringUtils.containsIgnoreCase((CharSequence) fieldValue, searchParams));

            return true;
        }
    }

    public static <T> List<T> filter(List<T> lists, String searchParams) {
        return StringUtils.isEmpty(searchParams) ? lists : (List)lists.stream().filter((t) -> {
            try {
                Annotation annotation = t.getClass().getAnnotation(Searchable.class);
                List<Field> searchableFields = FieldUtils.getAllFieldsList(t.getClass());
                Iterator var4 = searchableFields.iterator();

                while(true) {
                    Field field;
                    do {
                        if (!var4.hasNext()) {
                            return false;
                        }

                        field = (Field)var4.next();
                    } while(annotation != null && field.getAnnotation(Searchable.class) == null);

                    field.setAccessible(true);
                    Object fieldValue = field.get(t);
                    if (field.getType() == List.class) {
                        boolean find = recursionListFilter((List)fieldValue, searchParams);
                        if (find) {
                            return true;
                        }
                    } else if (fieldValue != null) {
                        String value = fieldValue.toString();
                        if (StringUtils.containsIgnoreCase(value, searchParams)) {
                            return true;
                        }
                    }
                }
            } catch (Exception var8) {
                var8.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList());
    }

    public static <T> Page<T> filterWithPaging(List<T> lists, Pageable pageable, String searchParams) {
        searchParams = EncDecUtils.decode(searchParams);
        List<T> pagedList = new LinkedList();
        LinkedList filteredList;
        if (StringUtils.isEmpty(searchParams)) {
            filteredList = new LinkedList(lists);
        } else {
            filteredList = new LinkedList();
            String[] asSearchParams = searchParams.split(",");
            String[] var6 = asSearchParams;
            int var7 = asSearchParams.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                String searchParam = var6[var8];
                List<T> filterList = filter(lists, searchParam);
                filterList.stream().filter((t) -> {
                    return !filteredList.contains(t);
                }).forEach(filteredList::add);
            }
        }

        int start = pageable.getPageNumber() * pageable.getPageSize();
        int end = start + pageable.getPageSize();
        if (end > filteredList.size()) {
            end = filteredList.size();
        }

        if (start < end) {
            pagedList = filteredList.subList(start, end);
        }

        return new PageImpl((List)pagedList, pageable, (long)filteredList.size());
    }
}
