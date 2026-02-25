package org.antonio.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class VoteSummary {
  private long validCount;
  private long blankCount;
  private long nullCount;

  @Override
  public String toString() {
    return "VoteSummary(ValidCount=" +this.validCount +
        ", blankCount=" + this.blankCount + ", nullCount=" + this.nullCount + ")";
  }
}
