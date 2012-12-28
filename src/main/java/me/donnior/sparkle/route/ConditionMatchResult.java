package me.donnior.sparkle.route;

public interface ConditionMatchResult extends MatchedCondition{

	boolean succeed();

	boolean isExplicitMatch();

}
