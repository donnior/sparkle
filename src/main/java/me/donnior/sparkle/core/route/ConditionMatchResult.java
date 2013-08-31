package me.donnior.sparkle.core.route;


public interface ConditionMatchResult extends MatchedCondition{

	boolean succeed();

	boolean isExplicitMatch();

}
