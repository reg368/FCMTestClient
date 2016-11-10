package hyweb.core.cache;

import java.io.Serializable;

public class CacheConfig implements Serializable{
	private static final long serialVersionUID = -8887379680496414917L;

	/*All*/
	public String factoryName;

	/*Guava，是否被動載入新資料*/
	public boolean isLazyLoad = true;

	/*Guava，是否要額外取出碰撞次數等數值，此選項會降低cahce效率*/
	public boolean isDebugMode= true;
	
	/*Guava，庫存多少幾筆Cache後開始清除*/
	public int entrySize = 100;

	/*Guava，多少分鐘後過期*/
	public long timeoutMinutes = 60;

	/*Guava，段，預設16，數值不到2的次方則會取到2的某次方*/
	public int concurrencyLevel = 16;

	/*Guava，初始化大小*/
	public int initialCapacity = 10;
}
