/**
 * 
 */
package evaluation;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import state.nation.Nation;

/**
 * Immutable class representing a vector of scores for agents.
 * 
 * @author ngales
 */
public class ScoreVector {

  private final Map<Nation, Integer> scores;
  private final int total;

  private ScoreVector(Map<Nation, Integer> scores) {
    this.scores = ImmutableMap.copyOf(scores);

    int sum = 0;
    for (Integer score : scores.values()) {
      sum += score;
    }
    this.total = sum;
  }

  public static ScoreVector copyOf(Map<Nation, Integer> scores) {
    return new Builder().setAll(scores).build();
  }

  /**
   * Gets the score corresponding to the given agent. If the agent is not in the score vector,
   * throws an IllegalStateException.
   * 
   * @param agent of which to retrieve score.
   * @throws IllegalStateException if agent is not in the score vector.
   * @return score of agent.
   */
  public int getScore(Nation agent) {
    Preconditions.checkNotNull(agent);
    Integer score = scores.get(agent);
    if (score == null) {
      throw new IllegalStateException("agent " + agent + " is not in score vector");
    }
    return score;
  }

  public int totalScore() {
    return total;
  }

  public int compareScore(ScoreVector other, Nation agent) {
    System.out.print("comparing " + this + " with " + other);
    // TODO: strategy pattern to make swapping out comparison algorithms
    // simple comparison, side with greatest sum of agent of interest, with ties broken by smallest
    // sum of all others
    int count = getScore(agent);
    int otherCount = other.getScore(agent);
    if (count != otherCount) {
      System.out.println(" => " + Integer.valueOf(count).compareTo(Integer.valueOf(otherCount)));
      return Integer.valueOf(count).compareTo(Integer.valueOf(otherCount));
    }
    int enemyCount = totalScore();
    int otherEnemyCount = other.totalScore();
    System.out.println(" => "
        + Integer.valueOf(enemyCount).compareTo(Integer.valueOf(otherEnemyCount)));
    return Integer.valueOf(enemyCount).compareTo(Integer.valueOf(otherEnemyCount));
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof ScoreVector)) {
      return false;
    }
    ScoreVector otherVector = (ScoreVector) other;
    return scores.equals(otherVector.scores);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(scores);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(scores);
    return sb.toString();
  }

  public static class Builder {
    private final Map<Nation, Integer> scores;

    public Builder() {
      this.scores = new HashMap<Nation, Integer>();
    }

    public Builder add(Nation agent, int value) {
      Preconditions.checkNotNull(agent);
      Integer score = scores.get(agent);
      if (score == null) {
        score = 0;
      }
      scores.put(agent, score + value);
      return this;
    }

    public Builder addAll(Map<Nation, Integer> scores) {
      Preconditions.checkNotNull(scores);
      for (Entry<Nation, Integer> entry : scores.entrySet()) {
        Nation agent = entry.getKey();
        Integer score = entry.getValue();
        Preconditions.checkNotNull(score);
        // agent null check is handled by called method
        add(agent, score);
      }
      return this;
    }

    public Builder addAll(ScoreVector scores) {
      // scores of scores
      return addAll(scores.scores);
    }

    public Builder set(Nation agent, int value) {
      Preconditions.checkNotNull(agent);
      scores.put(agent, value);
      return this;
    }

    public Builder setAll(Map<Nation, Integer> scores) {
      Preconditions.checkNotNull(scores);
      for (Entry<Nation, Integer> entry : scores.entrySet()) {
        Nation agent = entry.getKey();
        Integer score = entry.getValue();
        Preconditions.checkNotNull(score);
        // agent null check is handled by called method
        set(agent, score);
      }
      return this;
    }

    public Builder setAll(ScoreVector scores) {
      // scores of scores
      return setAll(scores.scores);
    }

    public ScoreVector build() {
      return new ScoreVector(scores);
    }
  }
}
