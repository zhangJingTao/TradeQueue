package util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**************************************************************
 **
 ** @author Zhang Jingtao
 **
 ** @date:2015年1月8日 上午10:31:10
 ** 
 ** @describe:将可序列化的对象存储到cache/file中
 **
 **************************************************************
 */
public final class SerializationUtil {
    /**
     * 序列化一个对象为字节数组
     * 
     * @param obj
     * @return
     * @throws Exception
     */
    public final static byte[] serialize(Serializable obj) throws Exception {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        serialize(obj, baos);
        byte[] b = baos.toByteArray();
        baos.close();
        return b;
    }

    /**
     * 将对象序列化到一个指定文件中�?
     * 
     * @param obj
     * @param filePath
     */
    public final static void serialize(Serializable obj, String filePath) throws Exception {
        serialize(obj, new FileOutputStream(filePath));
    }

    /**
     * 将对象序列化到一个指定输出流中�??
     * 
     * @param obj
     * @param output
     * @throws Exception
     */
    public final static void serialize(Serializable obj, OutputStream output) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(output);
        oos.writeObject(obj);
        oos.close();
    }

    /**
     * 将指定字节数组反序列化为�?个对象�??
     * 
     * @param b
     * @return
     * @throws Exception
     */
    public final static Serializable deserialize(byte[] b) throws Exception {
    	if (b==null) {
			return null;
		}
        ByteArrayInputStream bais = new ByteArrayInputStream(b);
        Serializable obj = deserialize(bais);
        bais.close();
        return obj;
    }

    /**
     * 将指定序列化文件反序列化为一个对象�??
     * 
     * @param filePath
     * @return
     * @throws Exception
     */
    public final static Serializable deserialize(String filePath) throws Exception {
        return deserialize(new FileInputStream(filePath));
    }

    /**
     * 将指定序列化输入流反序列化为�?个对象�??
     * 
     * @param input
     * @return
     * @throws Exception
     */
    public final static Serializable deserialize(InputStream input) throws Exception {
        ObjectInputStream ois = new ObjectInputStream(input);
        Serializable obj = (Serializable) ois.readObject();
        ois.close();
        return obj;
    }

    /**
     * 将一个Map中的key和value全部序列化，并生成一个新的Map。被序列化的Map中的key和value必须都是Serializable的�??
     * 
     * @param m
     * @return
     * @throws Exception
     */
    public final static Map<byte[], byte[]> serialize(Map<?, ?> m) throws Exception {
        if (m == null || m.isEmpty()) {
            throw new NullPointerException("map is null or empty.");
        }
        Map<byte[], byte[]> newMap = new HashMap<byte[], byte[]>();
        for (Object obj : m.keySet()) {
            byte[] key = serialize((Serializable) obj);
            byte[] value = serialize((Serializable) m.get(obj));
            newMap.put(key, value);
        }
        return newMap;
    }

    /**
     * 将已序列化过内部元素的Map反序列化�?
     * 
     * @param m
     * @return
     * @throws Exception
     */
    public final static Map<?, ?> deserialize(Map<byte[], byte[]> m) throws Exception {
        if (m == null || m.isEmpty()) {
            throw new NullPointerException("map is null or empty.");
        }
        Map<Object, Object> newMap = new HashMap<Object, Object>();
        for (byte[] obj : m.keySet()) {
            Object key = deserialize(obj);
            Object value = deserialize(m.get(obj));
            newMap.put(key, value);
        }
        return newMap;
    }

    /**
     * 将指定List中所有元素序列化，并生成新的List，被序列化的List中元素必须是Serializable的�??
     * 
     * @param l
     * @return
     * @throws Exception
     */
    public final static List<byte[]> serialize(List<?> l) throws Exception {
        if (l == null || l.size() == 0) {
            throw new NullPointerException("list is null or empty.");
        }
        List<byte[]> newList = new ArrayList<byte[]>();
        for (Object obj : l) {
            byte[] e = serialize((Serializable) obj);
            newList.add(e);
        }
        return newList;
    }

    /**
     * 将内部元素已经序列化的List反序列化�?
     * 
     * @param l
     * @return
     * @throws Exception
     */
    public final static List<?> deserializeList(List<byte[]> l) throws Exception {
        if (l == null || l.size() == 0) {
            throw new NullPointerException("list is null or empty.");
        }
        List<Object> newList = new ArrayList<Object>();
        for (byte[] obj : l) {
            Object e = deserialize(obj);
            newList.add(e);
        }
        return newList;
    }

    /**
     * 将指定Set的所有元素序列化，并生成新的Set，被序列化的Set的元素必须是Serializable的�??
     * 
     * @param s
     * @return
     * @throws Exception
     */
    public final static Set<byte[]> serialize(Set<?> s) throws Exception {
        if (s == null || s.size() == 0) {
            throw new NullPointerException("set is null or empty.");
        }
        Set<byte[]> newSet = new HashSet<byte[]>();
        for (Object obj : s) {
            byte[] e = serialize((Serializable) obj);
            newSet.add(e);
        }
        return newSet;
    }

    /**
     * 将内部元素已经序列化的Set反序列化�?
     * 
     * @param s
     * @return
     * @throws Exception
     */
    public final static Set<?> deserialize(Set<byte[]> s) throws Exception {
        if (s == null || s.size() == 0) {
            throw new NullPointerException("set is null or empty.");
        }
        Set<Object> newSet = new HashSet<Object>();
        for (byte[] obj : s) {
            Object e = deserialize(obj);
            newSet.add(e);
        }
        return newSet;
    }
}
