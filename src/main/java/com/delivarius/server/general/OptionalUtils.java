package com.delivarius.server.general;

import java.util.Optional;

import javax.validation.constraints.NotNull;

public class OptionalUtils {
	
	@SafeVarargs
	public static boolean isAllPresent(@NotNull Optional<? extends Object>... optionals) {
		for(Optional<? extends Object> opt : optionals) {
			if(!opt.isPresent())
				return false;
		}
		
		return true;
	}
}
