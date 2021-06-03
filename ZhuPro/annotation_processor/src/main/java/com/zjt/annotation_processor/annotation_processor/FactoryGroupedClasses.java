package com.zjt.annotation_processor.annotation_processor;


import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class FactoryGroupedClasses {
    /**
     * Will be added to the name of the generated factory class
     */
    private static final String SUFFIX = "Factory";
    private String qualifiedClassName;

    private Map<String, FactoryAnnotatedClass> itemsMap = new LinkedHashMap<>();

    public FactoryGroupedClasses(String qualifiedClassName) {
        this.qualifiedClassName = qualifiedClassName;
    }

    public void add(FactoryAnnotatedClass toInsert) {
        FactoryAnnotatedClass factoryAnnotatedClass = itemsMap.get(toInsert.getId());
        if (factoryAnnotatedClass != null) {
            throw new IdAlreadyUsedException(factoryAnnotatedClass);
        }
        itemsMap.put(toInsert.getId(), toInsert);
    }

    /**
     * # MethodSpec
     *     代表一个构造函数或方法声明
     * # TypeSpec
     *     代表一个类，接口，或者枚举声明
     * # FieldSpec
     *     代表一个成员变量，一个字段声明
     * # JavaFile
     *     包含一个顶级类的Java文件
     * # ParameterSpec
     *     用来创建参数
     * # AnnotationSpec
     *     用来创建注解
     * # ClassName
     *     用来包装一个类
     * # TypeName
     *     类型，如在添加返回值类型是使用 TypeName.VOID
     *
     * $S 字符串，如：$S, ”hello”
     *
     * $T 类、接口，如：$T, MainActivity
     */
    public void generateCode(Elements elementUtils, Filer filer) throws IOException {
        TypeElement superClassName = elementUtils.getTypeElement(qualifiedClassName);
        String factoryClassName = superClassName.getSimpleName() + SUFFIX;
//        String qualifiedFactoryClassName = qualifiedClassName + SUFFIX;
        PackageElement pkg = elementUtils.getPackageOf(superClassName);
        String packageName = pkg.isUnnamed() ? null : pkg.getQualifiedName().toString();

        System.out.println("......superClassName "+superClassName+", factoryClassName = "+factoryClassName);

        MethodSpec.Builder method = MethodSpec.methodBuilder("create")
                .addModifiers(Modifier.PUBLIC)
                .addParameter(String.class, "id")
                .returns(TypeName.get(superClassName.asType()));

        // check if id is null
        method.beginControlFlow("if (id == null)")
                .addStatement("throw new IllegalArgumentException($S)", "id is null!")
                .endControlFlow();

        // Generate items map

        for (FactoryAnnotatedClass item : itemsMap.values()) {
            method.beginControlFlow("if ($S.equals(id))", item.getId())
                    .addStatement("return new $L()", item.getTypeElement().getQualifiedName().toString())
                    .endControlFlow();
        }

        method.addStatement("throw new IllegalArgumentException($S + id)", "Unknown id = ");

        TypeSpec typeSpec = TypeSpec
                .classBuilder(factoryClassName)
                .addModifiers(Modifier.PUBLIC)
                .addMethod(method.build())
                .build();

        // Write file
        JavaFile.builder(packageName, typeSpec).build().writeTo(filer);
    }
}
