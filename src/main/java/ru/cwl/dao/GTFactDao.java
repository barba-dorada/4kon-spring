package ru.cwl.dao;

import com.google.api.services.sheets.v4.Sheets;
import ru.cwl.mappers.FactMapper;
import ru.cwl.model.Fact;
import ru.cwl.util.Util;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tischenko on 10.11.2016.
 */
public class GTFactDao implements FactDaoInt {

    Sheets gtService;

    public void setGtService(Sheets gtService) {
        this.gtService = gtService;
    }

    @Override
    public List<Fact> findAll() {
        final String SHEET_ID = "1EmCA5qbW0VnT09QwskgBag-jO4O4rDzYQlHRlHXnMpk";
        final String FACTS = "'ф1'!A2:H";
        List<Fact> facts = new ArrayList<>();
        try {
            facts = Util.load(gtService, SHEET_ID, FACTS, new FactMapper());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return facts;
    }

    @Override
    public List<Fact> findFromTo(LocalDate from, LocalDate to) {
        return findAll();
    }

    @Override
    public Fact findById(Long id) {
        return null;
    }

    @Override
    public Fact insert(Fact f) {
        return null;
    }

    @Override
    public void update(Fact f) {

    }

    @Override
    public void delete(Long id) {

    }
}
