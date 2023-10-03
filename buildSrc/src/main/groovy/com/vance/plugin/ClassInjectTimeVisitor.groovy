package com.vance.plugin;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class ClassInjectTimeVisitor extends ClassVisitor {

    String className

    ClassInjectTimeVisitor(ClassVisitor cv, String fileName) {
        super(Opcodes.ASM5, cv)
        className = fileName.substring(0, fileName.lastIndexOf("."))
    }

    @Override
    void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces)

    }
    boolean  isOverride
    @Override
    MethodVisitor visitMethod(int access, String name, String desc, String signature,
                              String[] exceptions) {
        if (name.equals("onDestory")){
            isOverride = true
        }
        MethodVisitor mv = super.visitMethod(access, name, desc, signature,
                exceptions)
        return new MethodAdapterVisitor(mv, access, name, desc, className)
    }

}