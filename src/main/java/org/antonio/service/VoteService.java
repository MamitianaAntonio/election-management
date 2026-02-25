package org.antonio.service;

import org.antonio.config.DBConnection;
import org.antonio.model.CandidateVoteCount;
import org.antonio.model.VoteType;
import org.antonio.model.VoteTypeCount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

  // question 2
  public List<VoteTypeCount> countVotesByTypes () {
    List<VoteTypeCount> result = new ArrayList<>();
    String sql = """
      select vote.vote_type as vote_type, count(vote.id) as total_vote
      from vote
      group by vote.vote_type
      order by total_vote DESC;
    """;

    try (Connection connection = DBConnection.getConnection();
      PreparedStatement ps = connection.prepareStatement(sql)) {
      ResultSet rs = ps.executeQuery();

      while(rs.next()) {
        VoteTypeCount voteTypeCount = new VoteTypeCount();
        voteTypeCount.setVoteType(VoteType.valueOf(rs.getString("vote_type")));
        voteTypeCount.setCount(rs.getLong("total_vote"));
        result.add(voteTypeCount);
      }

      return result;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }

  // question 3
  public List<CandidateVoteCount> countValidVotesByCandidate () {
    List<CandidateVoteCount> result = new ArrayList<>();
    String sql = """
       select c.name as candidate_name, count(v.id) as total_valid
           from candidate c
           left join vote v on c.id = v.candidate_id
           and v.vote_type = 'VALID'
           group by c.name;
    """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
      ResultSet rs = ps.executeQuery();

      while (rs.next()) {
        CandidateVoteCount candidateVoteCount = new CandidateVoteCount();
        candidateVoteCount.setCandidateName(rs.getString("candidate_name"));
        candidateVoteCount.setValidVoteCount(rs.getLong("total_valid"));
        result.add(candidateVoteCount);
      }

      return result;
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
