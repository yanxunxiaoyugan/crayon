package com.liu.cachespringbootstarter.paser;

import org.aopalliance.intercept.MethodInvocation;

import com.liu.cachespringbootstarter.annotation.CacheAble;
import com.liu.cachespringbootstarter.dto.CacheKeyDto;

public interface Ipaser {
	CacheKeyDto getCacheAbleKey(MethodInvocation methodInvocation,
			CacheAble cacheAble);

}
