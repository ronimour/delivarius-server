package com.delivarius.server.spring.domain.utils;

import org.springframework.data.domain.Persistable;
import org.springframework.lang.NonNull;

public class PersistableUtils {
	
	public static boolean isNew(Persistable<Long> persistable) {
		return persistable.getId() == null;
	}
	
	@NonNull
	public static boolean equalsPersistable(Object o1, Object o2 ) {
		if(o1 instanceof Persistable && o2 instanceof Persistable 
				&& o1.getClass().equals(o2.getClass())) {
			Persistable p1 = (Persistable) o1;
			Persistable p2 = (Persistable) o2;
			if(p1.isNew() && p2.isNew()) {
				return p1 == p2;
			} else if(p1.getId() == p2.getId()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
