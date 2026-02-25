package org.antonio.service;

import org.antonio.config.DBConnection;
import org.antonio.model.*;

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

  // question 4
  public VoteSummary computeVoteSummary () {
    String sql = """
        select
          sum(case when v.vote_type = 'VALID' then 1 else 0 end) as valid_count,
          sum(case when v.vote_type = 'BLANK' then 1 else 0 end) as blank_count,
          sum(case when v.vote_type = 'NULL' then 1 else 0 end) as null_count
        from vote v;
    """ ;

    try (Connection connection = DBConnection.getConnection();
      PreparedStatement ps = connection.prepareStatement(sql)) {
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        VoteSummary voteSummary = new VoteSummary();
        voteSummary.setValidCount(rs.getLong("valid_count"));
        voteSummary.setBlankCount(rs.getLong("blank_count"));
        voteSummary.setNullCount(rs.getLong("null_count"));
        return voteSummary;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

    return null;
  }

  // question 5
  public double computeTurnoutRate() {
    String sql = """
        SELECT
            COUNT(DISTINCT v.voter_id) AS voters_who_voted,
            (SELECT COUNT(id) FROM voter) AS total_voters,
            COUNT(DISTINCT v.voter_id) * 100.0 / (SELECT COUNT(id) FROM voter) AS turnout_rate
        FROM vote v;
    """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        long votersWhoVoted = rs.getLong("voters_who_voted");
        long totalVoters = rs.getLong("total_voters");
        double turnoutRate = rs.getDouble("turnout_rate");
        System.out.println("Voters who voted: " + votersWhoVoted);
        System.out.println("Total voters: " + totalVoters);
        return turnoutRate;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return 0;
  }

  // question 6
  public ElectionResult findWinner() {
    String sql = """
        SELECT c.name AS candidate_name,
               SUM(CASE WHEN v.vote_type = 'VALID' THEN 1 ELSE 0 END) AS valid_vote_count
        FROM vote v
        JOIN candidate c ON v.candidate_id = c.id
        GROUP BY c.name
        ORDER BY valid_vote_count DESC
        LIMIT 1;
    """;

    try (Connection connection = DBConnection.getConnection();
         PreparedStatement ps = connection.prepareStatement(sql)) {
      ResultSet rs = ps.executeQuery();
      if (rs.next()) {
        return new ElectionResult(
            rs.getString("candidate_name"),
            rs.getLong("valid_vote_count")
        );
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
    return null;
  }
}
