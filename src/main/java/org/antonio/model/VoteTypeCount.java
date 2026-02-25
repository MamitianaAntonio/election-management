package org.antonio.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class VoteTypeCount {
  private VoteType voteType;
  private long count;
}
