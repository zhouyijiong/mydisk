package com.zyj.disk.sys.generate;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.zyj.disk.sys.annotation.generate.GenerateParam;
import com.zyj.disk.sys.generate.file.*;

public final class Generate {
    private final String path;
    private static final List<FieldInfo> FIELD_INFOS = new ArrayList<>();

    public Generate(Class<?> clazz, String name) throws InstantiationException, IllegalAccessException, IOException, ClassNotFoundException {
        this.path = init(clazz);
        FileType.init(FileType.oneStrToUp(name), FIELD_INFOS);
    }

    public void start(FileType... fileType) {
        for (FileType type : fileType) type.create(path);
    }

    private String init(Class<?> clazz) throws InstantiationException, IllegalAccessException {
        String className = clazz.getName();
        Class<GenerateParam> annotationClass = GenerateParam.class;
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(annotationClass)) {
                field.setAccessible(true);
                GenerateParam annotation = field.getAnnotation(annotationClass);
                FieldInfo fieldInfo = new FieldInfo(
                        getType(0, field, null),
                        field.getName(),
                        field.get(clazz.newInstance()),
                        annotation.primary(),
                        annotation.unique(),
                        annotation.required(),
                        getType(1, field, annotation.length()));
                FIELD_INFOS.add(fieldInfo);
            }
        }
        return className.substring(0, className.indexOf("sys")).replace(".", "/");
    }

    private String getType(int status, Field field, String length) {
        boolean customize = status == 1 && !"".equals(length = length.trim()) && length.charAt(0) != '(';
        switch (field.getType().getSimpleName().hashCode()) {
            case 2086184:
                return status == 0 ? "Byte" : customize ? length : "tinyint" + length;
            case 79860828:
                return status == 0 ? "Short" : customize ? length : "smallint" + length;
            case -672261858:
                return status == 0 ? "Integer" : customize ? length : "int" + length;
            case 2374300:
                return status == 0 ? "Long" : customize ? length : "bigint" + length;
            case 67973692:
                return status == 0 ? "Float" : customize ? length : "float" + length;
            case 2052876273:
                return status == 0 ? "Double" : customize ? length : "double" + length;
            case -1808118735:
                return status == 0 ? "String" : customize ? length : "varchar" + length;
            case 1438607953:
                return status == 0 ? "BigDecimal" : customize ? length : "BigDecimal" + length;
            default:
                return null;
        }
    }
}