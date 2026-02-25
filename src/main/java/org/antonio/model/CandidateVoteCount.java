package org.antonio.model;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@EqualsAndHashCode
public class CandidateVoteCount {
  private String candidateName;
  private long validVoteCount;

  @Override
  public String toString() {
    return candidateName + "=" + validVoteCount;
  }
}
