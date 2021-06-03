package com.zjt.annotation_processor.annotation_processor;

import com.google.auto.service.AutoService;
import com.zjt.annotation_my.Factory;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * Creaeted by ${za.zhu.jiangtao}
 * on 2019/7/9
 * <p>
 * 规则如下：
 * 1. 只有类可以被@Factory注解，因为接口或者抽象类并不能用new操作实例化
 * 2. 被@Factory注解的类，必须至少提供一个公开的默认构造器（即没有参数的构造函数）。否者我们没法实例化一个对象。
 * 3. 被@Factory注解的类必须直接或者间接的继承于type()指定的类型；
 * 4. 具有相同的type的注解类，将被聚合在一起生成一个工厂类。这个生成的类使用Factory后缀，例如type = Shape.class，将生成ShapeFactory工厂类；
 * 5. id只能是String类型，并且在同一个type组中必须唯一
 */

@AutoService(Processor.class)
public class FactoryProcessor extends AbstractProcessor {
    private Types typeUtils;
    private Messager messager;
    private Filer filer;
    private Elements elementUtils;
    private Map<String, FactoryGroupedClasses> factoryClasses = new LinkedHashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        typeUtils = processingEnvironment.getTypeUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
        elementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotations = new LinkedHashSet<>();
        info("CanonicalName is %s", Factory.class.getCanonicalName());
        annotations.add(Factory.class.getCanonicalName());
        return annotations;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * Checks if the annotated element observes our rules
     * 要检查被注解的类必须有只要一个公开的构造函数，不是抽象类，继承于特定的类型，以及是一个公开类
     */
    private void checkValidClass(FactoryAnnotatedClass item) throws ProcessingException {

        // 转换为TypeElement, 含有更多特定的方法
        TypeElement classElement = item.getTypeElement();

        if (!classElement.getModifiers().contains(Modifier.PUBLIC)) {
            throw new ProcessingException(classElement, "The class %s is not public.",
                    classElement.getQualifiedName().toString());
        }

        // 检查是否是一个抽象类
        if (classElement.getModifiers().contains(Modifier.ABSTRACT)) {
            throw new ProcessingException(classElement,
                    "The class %s is abstract. You can't annotate abstract classes with @%",
                    classElement.getQualifiedName().toString(), Factory.class.getSimpleName());
        }

        // 检查继承关系: 必须是@Factory.type()指定的类型子类
        TypeElement superClassElement = elementUtils.getTypeElement(item.getQualifiedFactoryGroupName());
        if (superClassElement.getKind() == ElementKind.INTERFACE) {
            // 检查接口是否实现了
            if (!classElement.getInterfaces().contains(superClassElement.asType())) {
                throw new ProcessingException(classElement,
                        "The class %s annotated with @%s must implement the interface %s",
                        classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                        item.getQualifiedFactoryGroupName());
            }
        } else {
            // Check subclassing
            TypeElement currentClass = classElement;
            while (true) {
                /**
                 * getSuperclass()
                 * Returns the direct superclass of this type element.
                 * If this type element represents an interface or the class java.lang.Object,
                 * then a NoType with kind NONE is returned.
                 */
                TypeMirror superClassType = currentClass.getSuperclass();

                if (superClassType.getKind() == TypeKind.NONE) {
                    // 到达了基本类型(java.lang.Object), 所以退出
                    throw new ProcessingException(classElement,
                            "The class %s annotated with @%s must inherit from %s",
                            classElement.getQualifiedName().toString(), Factory.class.getSimpleName(),
                            item.getQualifiedFactoryGroupName());
                }

                if (superClassType.toString().equals(item.getQualifiedFactoryGroupName())) {
                    // 找到了要求的父类
                    break;
                }

                // 在继承树上继续向上搜寻
                currentClass = (TypeElement) typeUtils.asElement(superClassType);
            }
        }

        // 检查是否提供了默认公开构造函数
        for (Element enclosed : classElement.getEnclosedElements()) {
            if (enclosed.getKind() == ElementKind.CONSTRUCTOR) {
                ExecutableElement constructorElement = (ExecutableElement) enclosed;
                if (constructorElement.getParameters().size() == 0 &&
                        constructorElement.getModifiers().contains(Modifier.PUBLIC)) {
                    // 找到了默认构造函数
                    return;
                }
            }
        }

        // 没有找到默认构造函数
        throw new ProcessingException(classElement,
                "The class %s must provide an public empty default constructor",
                classElement.getQualifiedName().toString());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        try {
            // 遍历所有被注解了@Factory的元素, Element 可以是类、方法或者变量
            for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(Factory.class)) {

                // 检查被注解为@Factory的元素是否是一个类
                if (annotatedElement.getKind() != ElementKind.CLASS) {
                    error(annotatedElement, "Only classes can be annotated with @%s", Factory.class.getSimpleName());
                    return true; // 推出处理
                    // 在传统Java应用中我们可能就抛出一个异常Exception。如果你在process()中抛出一个异常，那么运行注解处理器的JVM将会崩溃（就像其他Java应用一样），
                    // 使用我们注解处理器FactoryProcessor第三方开发者将会从javac中得到非常难懂的出错信息，因为它包含FactoryProcessor的堆栈跟踪（Stacktace）信息
//                    throw new ProcessingException(annotatedElement, "Only classes can be annotated with @%s",
//                            Factory.class.getSimpleName());
                }

                // 因为我们已经知道它是ElementKind.CLASS类型，所以可以直接强制转换
                TypeElement typeElement = (TypeElement) annotatedElement;
                info("simpleName is %s", typeElement.getSimpleName());
                FactoryAnnotatedClass annotatedClass = new FactoryAnnotatedClass(typeElement);

                checkValidClass(annotatedClass);

                // Everything is fine, so try to add
                FactoryGroupedClasses factoryClass = factoryClasses.get(annotatedClass.getQualifiedFactoryGroupName());
                if (factoryClass == null) {
                    String qualifiedGroupName = annotatedClass.getQualifiedFactoryGroupName();
                    info("qualifiedGroupName is %s", qualifiedGroupName);
                    factoryClass = new FactoryGroupedClasses(qualifiedGroupName);
                    factoryClasses.put(qualifiedGroupName, factoryClass);
                }

                // Checks if id is conflicting with another @Factory annotated class with the same id
                factoryClass.add(annotatedClass);
            }

            // Generate code
            for (FactoryGroupedClasses factoryClass : factoryClasses.values()) {
                factoryClass.generateCode(elementUtils, filer);
            }
            factoryClasses.clear();
        } catch (ProcessingException e) {
            error(e.getElement(), e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args), e);
    }

    private void error(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.ERROR, String.format(msg, args));
    }

    private void info(String msg, Object... args) {
        messager.printMessage(Diagnostic.Kind.NOTE, String.format(msg, args));
    }
}
