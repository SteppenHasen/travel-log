package com.kodality.travellog;

import javax.inject.Singleton;

import io.micronaut.context.annotation.Requires;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import io.micronaut.data.jdbc.annotation.JdbcRepository;

import java.util.List;

@Singleton
@JdbcRepository
@Requires(beans = JdbcTemplate.class)
public class TravelLogRepository {

    private final JdbcTemplate jdbcTemplate;

    public TravelLogRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int create(TravelLog e){
        String query="INSERT INTO travellogs VALUES( '"
                +e.getId()+"','"
                +e.getDate()+"','"
                +e.getRegistrationNumber()+"', '"
                +e.getDescription()+"','"
                +e.getRoute()+"','"
                +e.getOwner()+"', '"
                +e.getStartOdometer()+"','"
                +e.getEndOdometer()+"')";
        return jdbcTemplate.update(query);
    }
    public int update(TravelLog e){
        String query="UPDATE travellogs SET " +
                "description='"+e.getDescription()+
                "',owner='"+e.getOwner()+
                "', traveldate='"+e.getDate()+
                "', registrationnumber='"+e.getRegistrationNumber()+
                "', route='"+e.getRoute()+
                "', startodometer='"+e.getStartOdometer()+
                "', endodometer='"+e.getEndOdometer()+
                "' WHERE id='"+e.getId()+"'";
        return jdbcTemplate.update(query);
    }
    public int destroy(String id){
        String query="DELETE FROM travellogs WHERE id='"+id+"'";
        return jdbcTemplate.update(query);
    }

    public List<TravelLog> all() {
        String query="SELECT * FROM travellogs";

        try {
            return jdbcTemplate.query(query, new TravelLogRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    public TravelLog find(String id) {
        String query="SELECT * FROM travellogs WHERE id= ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[] { id }, new TravelLogRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<TravelLog> where(String paramName, String param) {
        String query = "SELECT * FROM travellogs WHERE "+ paramName + "='"+param+"'";

        try {
            return jdbcTemplate.query(query, new TravelLogRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<TravelLog> whereDate(String beginDate, String endDate) {
        String query = "SELECT * FROM travellogs WHERE " +
                "traveldate >='"+beginDate+
                "' AND traveldate <='"+endDate+"'";;
        try {
            return jdbcTemplate.query(query, new TravelLogRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<TravelLog> whereTwoParams(String owner, String registrationnumber) {
        String query = "SELECT * FROM travellogs WHERE " +
                "owner='"+owner+"' AND registrationnumber='"+registrationnumber+"'";

        try {
            return jdbcTemplate.query(query, new TravelLogRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<TravelLog> whereParamAndDate(String paramname, String param, String begindate, String enddate) {
        String query = "SELECT * FROM travellogs WHERE " +
                ""+paramname+"='"+param+"' AND " +
                "traveldate >='"+begindate+
                "' AND traveldate <='"+enddate+"'";

        try {
            return jdbcTemplate.query(query, new TravelLogRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<TravelLog> whereDateOwnerRegnumber(
            String owner, String registrationnumber, String begindate, String enddate) {
        String query = "SELECT * FROM travellogs WHERE " +
                "owner='"+owner+"' AND registrationnumber='"+registrationnumber+"'" +
                " AND traveldate >='"+begindate+"'" +
                " AND traveldate <='"+enddate+"'";

        try {
            return jdbcTemplate.query(query, new TravelLogRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}