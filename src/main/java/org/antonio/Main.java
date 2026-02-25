package org.antonio;

import org.antonio.service.VoteService;

public class Main {
  public static void main(String[] args) {
    // question 1
    VoteService service = new VoteService();
    long totalVotes = service.countAllVotes();
    System.out.println("totalVote=" + totalVotes);
  }
}