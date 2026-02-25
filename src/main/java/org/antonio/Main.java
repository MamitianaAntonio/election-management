package org.antonio;

import org.antonio.model.CandidateVoteCount;
import org.antonio.model.VoteSummary;
import org.antonio.model.VoteTypeCount;
import org.antonio.service.VoteService;

import java.util.List;

public class Main {
  public static void main(String[] args) {
    VoteService service = new VoteService();
    // question 1
    long totalVotes = service.countAllVotes();
    System.out.println("totalVote=" + totalVotes);

    // question 2
    List<VoteTypeCount> voteByType = service.countVotesByTypes();
    System.out.println(voteByType);

    // question 3
    List<CandidateVoteCount> validVotesByCandidate = service.countValidVotesByCandidate();
    System.out.println(validVotesByCandidate);

    // question 4
    VoteSummary voteSummary = service.computeVoteSummary();
    System.out.println(voteSummary);
  }
}