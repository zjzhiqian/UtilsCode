package com.hzq.common.redis;

public interface LockCallBack<T> {
	public T invoke();
}
