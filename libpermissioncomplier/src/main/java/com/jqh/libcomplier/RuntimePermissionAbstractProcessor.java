package com.jqh.libcomplier;

import com.google.auto.service.AutoService;
import com.jqh.libannotion.annotion.PermissionDenied;
import com.jqh.libannotion.annotion.PermissionGrant;
import com.jqh.libannotion.annotion.PermissionRational;

import java.io.IOException;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
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
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

//@AutoService(Processor.class)
public class RuntimePermissionAbstractProcessor extends AbstractProcessor {
    private Elements elementUtils;
    private Messager messager ;
    private HashMap<String, MethodInfo> methodMap = new HashMap<>();
    private Filer filer ;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        messager.printMessage(Diagnostic.Kind.NOTE, "process init...");
        elementUtils = processingEnvironment.getElementUtils();
        messager = processingEnvironment.getMessager();
        filer = processingEnvironment.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        methodMap.clear();
        //  打印
        messager.printMessage(Diagnostic.Kind.NOTE, "process star...");
//        if(!handleAnnotationInfo(roundEnvironment, PermissionGrant.class)){
//            return false;
//        }
//        if(!handleAnnotationInfo(roundEnvironment, PermissionDenied.class)){
//            return false;
//        }
//        if(!handleAnnotationInfo(roundEnvironment, PermissionRational.class)){
//            return false;
//        }
        messager.printMessage(Diagnostic.Kind.NOTE, "process start");
        for(String className:methodMap.keySet()){
            MethodInfo methodInfo = methodMap.get(className);
            try{
                JavaFileObject sourceFile = filer.createSourceFile(methodInfo.packageName + "." + methodInfo.fileName);
                Writer writer = sourceFile.openWriter();
                writer.write(methodInfo.generateJavaCode());
                writer.flush();
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE, "write file failed:" + e.getMessage());
            }
        }
        messager.printMessage(Diagnostic.Kind.NOTE, "process end");
        return false;
    }

    private boolean handleAnnotationInfo(RoundEnvironment roundEnvironment, Class<? extends Annotation> annotation){
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(annotation);
        for(Element element:elements){
            if(!checkMethodValidator(element, annotation)){
                return  false;
            }
            ExecutableElement methodElement = (ExecutableElement) element ;
            TypeElement enclosingElement = (TypeElement) methodElement.getEnclosedElements();
            String className = enclosingElement.getQualifiedName().toString();

            MethodInfo methodInfo = methodMap.get(className);
            if(methodInfo == null)
                methodInfo = new MethodInfo(elementUtils,enclosingElement);
            methodMap.put(className, methodInfo);

            Annotation annotationClaz = methodElement.getAnnotation(annotation);
            String methodName = methodElement.getSimpleName().toString();
            // 获取自己这个方法的int参数
            List<? extends VariableElement> parameters = methodElement.getParameters();
            if (parameters == null || parameters.size() < 1) {
                String message = "the method %s marked by annotation %s must hava an unique paramter [String[] permissions]";
                throw new IllegalArgumentException(String.format(message, methodName, annotationClaz.getClass().getClass().getSimpleName()));
            }
            if(annotationClaz instanceof PermissionGrant){
                int requestCode = ((PermissionGrant)annotationClaz).value();
                methodInfo.grantMethodMap.put(requestCode, methodName);
            } else if(annotationClaz instanceof PermissionDenied){
                int requestCode = ((PermissionDenied)annotationClaz).value();
                methodInfo.deniedMethodMap.put(requestCode, methodName);
            } else if(annotationClaz instanceof PermissionRational){
                int requestCode = ((PermissionRational)annotationClaz).value();
                methodInfo.rationalMethodMap.put(requestCode, methodName);
            }
        }
        return true;
    }

    // 检测方法是否有效
    private boolean checkMethodValidator(Element element, Class<? extends Annotation> annotation){
        // 判断元素是否是方法
        if(element.getKind() != ElementKind.METHOD){
            return false;
        }
        if(ClassValidator.isPrivate(element) || ClassValidator.isAbstract(element)){
            return false;
        }
        return true;
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportList = new HashSet();
        supportList.add(PermissionGrant.class.getCanonicalName());
        supportList.add(PermissionDenied.class.getCanonicalName());
        supportList.add(PermissionRational.class.getCanonicalName());
        return supportList ;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return super.getSupportedSourceVersion();
    }
}
