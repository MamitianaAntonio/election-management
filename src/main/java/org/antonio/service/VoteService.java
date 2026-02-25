package org.antonio.service;

import org.antonio.config.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VoteService {

  // question 1
  public long countAllVotes () {
    String sql = """
        select count(id) as total_votes from vote;
    """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
      ResultSet rs = ps.executeQuery();
      if(rs.next()) {
        return rs.getLong("total_votes");
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return 0;
  }
}
