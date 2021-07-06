package com.internship.sep.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<E, D> {

    D map(E entity);

    default List<D> mapList(List<E> entities) {
        return entities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    E unmap(D dto);

    default List<E> unmapList(List<D> dtoList) {
        return dtoList.stream()
                .map(this::unmap)
                .collect(Collectors.toList());
    }
}
