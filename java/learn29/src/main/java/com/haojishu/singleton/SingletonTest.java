package com.haojishu.singleton;

public class SingletonTest {
	private static class SingTest{
		private static final SingletonTest INSTANCE = new SingletonTest();
	}

	private SingletonTest(){}

	public  static final SingletonTest getInstance(){
		return SingTest.INSTANCE;
	}
}
