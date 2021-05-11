package h2o;

import java.lang.ref.SoftReference;
import java.util.HashMap;

public final class Cache {
	private static SoftReference<HashMap<String, String>> cache;
	
	private static void init(){
		if(cache == null || cache.get() == null)
			cache = new SoftReference<HashMap<String, String>>(new HashMap<String, String>());
	}

	
	public static String get(String ip){
		init();
		return cache.get().get(ip);
	}
	
	public static String put(String ip, String result){
		init();
		return cache.get().put(ip, result);
	}

	public static boolean pre(String ip) throws Exception{
		init();
		if(cache.get().containsKey(ip)){
			if(cache.get().get(ip) == null){
				throw new Exception("另一线程正在进行测试，请稍后刷新");
			}else{
				return false;
			}
		}else{
			cache.get().put(ip, null);
			return true;
		}
	}

}
