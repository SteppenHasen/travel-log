package com.kodality.travellog;

import javax.inject.Singleton;

import io.micronaut.data.annotation.*;

import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

@Singleton
@Repository
public class TravelLogRepository {
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int save(TravelLog e){
        String query="insert into travellog values( '"+e.getId()+"','"+e.getDate()+"','"+e.getRegistrationNumber()+"', '"+e.getDescription()+"','"+e.getRoute()+"','"+e.getOwner()+"', '"+e.getStartOdometer()+"','"+e.getEndOdometer()+"')";
        return jdbcTemplate.update(query);
    }
    public int update(TravelLog e){
        String query="update travellog set description='"+e.getDescription()+"',owner='"+e.getOwner()+"', traveldate='"+e.getDate()+"', registrationnumber='"+e.getRegistrationNumber()+"', route='"+e.getRoute()+"', startodometer='"+e.getStartOdometer()+"', endodometer='"+e.getEndOdometer()+"' where id='"+e.getId()+"' ";
        return jdbcTemplate.update(query);
    }
    public int destroy(String id){
        String query="delete from travellog where id='"+id+"' ";
        return jdbcTemplate.update(query);
    }

    public List<TravelLog> all() {
        String query="select * from travellog";
        return jdbcTemplate.query(query, new TravelLogRowMapper());
    }

    public TravelLog findById(String id) {
        String query="select * from travellog where id='"+id+"'";
        return jdbcTemplate.queryForObject(query, new Object[] { id }, new TravelLogRowMapper());
    }

    public List<TravelLog> findByOneParam(String param, String paramValue) {
        String query;
        if (param == "date") {
            query="select * from travellog where "+param+" >='"+paramValue+"' and "+param+" <='"+paramValue+"'";
        } else {
            query="select * from travellog where "+param+"='"+paramValue+"'";
        }

        return jdbcTemplate.query(query, new TravelLogRowMapper());
    }

}
//ololo = new Class(relation, filter: {owner: bla, data})
////index

