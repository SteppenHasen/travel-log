package com.kodality.travellog;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TravelLogRowMapper implements RowMapper<TravelLog> {
    @Override
    public TravelLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        TravelLog travellog = new TravelLog(rs.getString("id"), rs.getInt("startodometer"), rs.getInt("endodometer"), rs.getString("registrationnumber"), rs.getString("owner"), rs.getString("route"), rs.getString("description"), rs.getString("date"));

        return travellog;
    }
}
