package org.agilej.sparkle.core.route;


public enum ConditionMatchs implements ConditionMatchResult{
	
	DEFAULT_SUCCEED(true, false), EXPLICIT_SUCCEED(true, true), FAILED(false, true);

	
	private boolean succeed;
	private boolean explicit;

	private ConditionMatchs(boolean succeed, boolean explicit){
		this.succeed = succeed;
		this.explicit = explicit;
	}
	
	@Override
	public boolean succeed() {
		return this.succeed;
	}

	@Override
	public boolean isExplicitMatch() {
		return this.explicit;
	}

}
