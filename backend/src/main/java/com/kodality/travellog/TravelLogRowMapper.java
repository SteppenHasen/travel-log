package com.kodality.travellog;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
public class TravelLogRowMapper implements RowMapper<TravelLog> {
    @Override
    public TravelLog mapRow(ResultSet rs, int rowNum) throws SQLException {
        TravelLog travellog = new TravelLog(rs.getString("id"),
                rs.getString("traveldate"),
                rs.getString("registrationnumber"),
                rs.getString("description"),
                rs.getString("route"),
                rs.getString("owner"),
                rs.getInt("startodometer"),
                rs.getInt("endodometer")
                );

        return travellog;
    }
}
