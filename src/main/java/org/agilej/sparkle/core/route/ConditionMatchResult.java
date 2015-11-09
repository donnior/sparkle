package org.agilej.sparkle.core.route;


public interface ConditionMatchResult extends MatchedCondition{

	boolean succeed();

	boolean isExplicitMatch();

}
