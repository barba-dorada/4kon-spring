package ru.cwl.dao;

import ru.cwl.model.Fact;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by tischenko on 10.11.2016.
 */
public interface FactDaoInt {
    List<Fact> findAll();
    List<Fact> findFromTo(LocalDate from,LocalDate to);
    Fact findById(Long id);
    Fact insert(Fact f);
    void update(Fact f);
    void delete(Long id);
}
