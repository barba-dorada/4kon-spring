package ru.cwl.dao;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import ru.cwl.model.Fact;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 17.11.2016.
 * http://docs.spring.io/spring/docs/current/spring-framework-reference/htmlsingle/#jdbc
 *
 */

public class JdbcTemplateFactDao implements FactDaoInt {
    public static final Log log=LogFactory.getLog(JdbcTemplateFactDao.class);
    DataSource dataSource;
    JdbcTemplate tpl;
    InsertFact insertTpl;

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        tpl = new JdbcTemplate(dataSource);
        insertTpl = new InsertFact(dataSource);
    }

    @Override
    public List<Fact> findAll() {
        String sql = "SELECT id, user, date,account,category,amount,comment FROM FACTS";
        List<Fact> facts = tpl.query(sql, new FactMapper());
        return facts;
    }

    @Override
    public List<Fact> findFromTo(LocalDate from, LocalDate to) {
        return null;
    }

    @Override
    public Fact findById(Long id) {
        return null;
    }

    @Override
    public Fact insert(Fact f) {
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("user", f.getUser());
        mapParam.put("date", java.sql.Date.valueOf(f.getDate()));
        mapParam.put("account", f.getAccount());
        mapParam.put("category", f.getCateory());
        mapParam.put("amount", f.getAmount());
        mapParam.put("comment", f.getComment());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        insertTpl.updateByNamedParam(mapParam, keyHolder);
        f.setId(keyHolder.getKey().longValue());
        log.info("new Fact inserted with id: "+keyHolder.getKey());
        return f;
    }

    @Override
    public void update(Fact f) {

    }

    @Override
    public void delete(Long id) {

    }

    private static final class FactMapper implements RowMapper<Fact> {

        @Override
        public Fact mapRow(ResultSet rs, int rowNum) throws SQLException {
            Fact f = new Fact();
            f.setId(rs.getLong("id"));
            f.setUser(rs.getString("user"));
            f.setDate(rs.getDate("date").toLocalDate());
            f.setAccount(rs.getString("account"));
            f.setCateory(rs.getString("category"));
            f.setAmount(rs.getBigDecimal("amount"));
            f.setComment(rs.getString("comment"));
            return f;
        }
    }

    static class InsertFact extends SqlUpdate {
        private static final String sql = "INSERT INTO FACTS (user, date,account,category,amount,comment) " +
                "values (:user, :date,:account,:category,:amount,:comment)";

        public InsertFact(DataSource dataSource) {
            super(dataSource, sql);
            super.declareParameter(new SqlParameter("user", Types.VARCHAR));
            super.declareParameter(new SqlParameter("date", Types.TIMESTAMP));
            super.declareParameter(new SqlParameter("account", Types.VARCHAR));
            super.declareParameter(new SqlParameter("category", Types.VARCHAR));
            super.declareParameter(new SqlParameter("amount", Types.NUMERIC));
            super.declareParameter(new SqlParameter("comment", Types.VARCHAR));
            super.setGeneratedKeysColumnNames(new String[]{"id"});
            super.setReturnGeneratedKeys(true);
        }
    }
}
