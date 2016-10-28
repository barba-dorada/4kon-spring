package ru.cwl.mappers;

import java.util.List;

/**
 * Created by tischenko on 28.10.2016.
 */
public interface RowMapper<T> {
    T map(List row);
}
