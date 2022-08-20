package com.zyj.disk.sys.generate.file;

import com.zyj.disk.sys.generate.FieldInfo;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

public abstract class FileType{
	protected final String type;
	protected final String className;
	static String sourceName;
	static List<FieldInfo> fieldInfos;

	protected FileType(String type){
		this.type = type;
		this.className = oneStrToUp(sourceName) + ("Entity".equals(type) ? "" : type);
	}

	public static void init(String name,List<FieldInfo> fieldInfos) throws IOException, ClassNotFoundException {
		FileType.sourceName = name;
		FileType.fieldInfos = deepCopy(fieldInfos);
	}

	public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(byteOut);
		out.writeObject(src);
		ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
		ObjectInputStream in = new ObjectInputStream(byteIn);
		@SuppressWarnings("unchecked")
		List<T> dest = (List<T>) in.readObject();
		return dest;
	}

	public void create(String path){
		try(BufferedOutputStream bos = createFile(path)){
			createHead(bos,path.replace("/","."));
			createBody(bos);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	abstract void createHead(BufferedOutputStream bos,String packageName)throws IOException;

	abstract void createBody(BufferedOutputStream bos)throws IOException;

	BufferedOutputStream createFile(String path)throws IOException{
		String finallyPath = "./src/main/java/" + path + type.toLowerCase() + "/" + sourceName.toLowerCase() + "/" + sourceName + ("Entity".equals(type) ? "" : type) + ".java";
		return createDirectoryAndFile(finallyPath);
	}

	BufferedOutputStream createDirectoryAndFile(String path) throws IOException {
		int index = path.lastIndexOf("/");
		String directoryPath = path.substring(0,index);
		File directory = new File(directoryPath);
		if(!directory.exists() && !directory.mkdir()) throw new RuntimeException("创建文件夹出错");
		String filePath = directory.getAbsolutePath() + path.substring(index);
		File file = new File(filePath);
		if(!file.exists() && !file.createNewFile()) throw new RuntimeException("创建文件出错");
		return new BufferedOutputStream(new FileOutputStream(file,true));
	}

	public static String oneStrToUp(String str){
//		String temp = str.toLowerCase();
//		return temp.substring(0,1).toUpperCase() + temp.substring(1);
		return str.substring(0,1).toUpperCase() + str.substring(1);
	}

	byte[] packages(String path){
		return ("package " + path + ";\n").getBytes(StandardCharsets.UTF_8);
	}

	byte[] imports(String path){
		return ("\nimport " + path).getBytes(StandardCharsets.UTF_8);
	}

	byte[] annotations(String name){return ("\n" + "@" + name).getBytes(StandardCharsets.UTF_8);}
}