package com.jqh.libcomplier;

import java.util.HashMap;

import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class MethodInfo {
    public final String packageName ;
    public final String className;
    public HashMap<Integer, String> grantMethodMap = new HashMap<>();
    public HashMap<Integer, String> deniedMethodMap = new HashMap<>();
    public HashMap<Integer, String> rationalMethodMap = new HashMap<>();

    private static final String PROXY_NAME = "PermissionProxy";
    public String fileName ;

    public MethodInfo(Elements elementUtils, TypeElement typeElement){
        PackageElement packageElement = elementUtils.getPackageOf(typeElement);
        packageName = packageElement.getQualifiedName().toString();
        className = ClassValidator.getClassName(typeElement, packageName);

        fileName = className + "$$" + PROXY_NAME ;
    }

    public String generateJavaCode(){
        StringBuilder builder = new StringBuilder();
        builder.append("// generate code, do not modify\n");
        builder.append("package ").append(packageName).append("; \n\n");
        builder.append("import com.jqh.libpermissionhelper.*;");
        builder.append("\n");

        builder.append("public class ").append(fileName).append(" implements " + PROXY_NAME+"<" + className+">");
        builder.append("{\n");
        generateMethods(builder);
        builder.append("\n}");
        return builder.toString();
    }

    private void generateMethods(StringBuilder builder){
        generateGrantMethod(builder);
        generateDeniedMethod(builder);
        generateRationalMethod(builder);
    }

    private void generateGrantMethod(StringBuilder builder) {
        builder.append("@Override\n");
        builder.append("public void grant(int requestCode, "+className+" source, String[] permissions{\n");
        builder.append("switch(requestCode){");
        for(int requestCode : grantMethodMap.keySet()){
            builder.append("case " + requestCode + ":");
            builder.append("source." + grantMethodMap.get(requestCode) + "(permissions);");
            builder.append("break;");
        }
        builder.append("}");
        builder.append("}\n");
    }
    private void generateDeniedMethod(StringBuilder builder) {
        builder.append("@Override\n");
        builder.append("public void denied(int requestCode, "+className+" source, String[] permissions{\n");
        builder.append("switch(requestCode){");
        for(int requestCode : grantMethodMap.keySet()){
            builder.append("case " + requestCode + ":");
            builder.append("source." + grantMethodMap.get(requestCode) + "(permissions);");
            builder.append("break;");
        }
        builder.append("}");
        builder.append("}\n");
    }
    private void generateRationalMethod(StringBuilder builder) {
        builder.append("@Override\n");
        builder.append("public void rational(int requestCode, "+className+" source, String[] permissions{\n");
        builder.append("switch(requestCode){");
        for(int requestCode : grantMethodMap.keySet()){
            builder.append("case " + requestCode + ":");
            builder.append("source." + grantMethodMap.get(requestCode) + "(permissions);");
            builder.append("return true");
        }
        builder.append("}\n");
        builder.append("return false");
        builder.append("}\n");
    }
}
