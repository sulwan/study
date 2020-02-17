package com.haojishu.factory;

public class NeedsFactory {

	public Needs getNeeds(String need){
		if (need == null){
			return null;
		}
        if (need.equalsIgnoreCase("phone")){
        	return new Phone();
		}
        if (need.equalsIgnoreCase("computer")){
        	return new Computer();
		}

		return  null;
	}
}
