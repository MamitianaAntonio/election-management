package org.antonio.model;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ElectionResult {
  private String candidateName;
  private long validVoteCount;

  @Override
  public String toString() {
    return "ElectionResult{" +
        "candidateName='" + candidateName + '\'' +
        ", validVoteCount=" + validVoteCount +
        '}';
  }
}
