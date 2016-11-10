package ru.cwl.dao;

import ru.cwl.model.Fact;

import java.util.List;

/**
 * Created by tischenko on 10.11.2016.
 */
public interface FactDaoInt {
    List<Fact> findAll();
    Fact findById(Long id);
    Fact insert(Fact f);
}
