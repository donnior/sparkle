package me.donnior.sparkle.route;

public enum ConditionMatchs implements ConditionMatchResult{
	
	DEFAULT(true, false), EXPLICIT(true, true), FAILED(false, true);

	
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
