package com.vance.asminject;


import org.junit.Test;
import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.commons.AdviceAdapter;
import org.objectweb.asm.commons.Method;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ASMUnitTest {

    @Test
    public void test() throws IOException {
        /**
         * 1、获得待插桩的字节码数据
         */
        FileInputStream fis = new FileInputStream(
                "F:\\vance\\APM\\ASM\\ASMInject\\app\\src\\test\\java\\com\\vance" +
                        "\\asminject\\InjectTest.class");

        /**
         * 2、执行插桩 修改class数据
         */
        ClassReader cr = new ClassReader(fis);
        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        //ClassVisitor: 类访问者，接收类信息的回调
        //MethodVisitor: 方法访问者，操作方法体
        cr.accept(new ClassVisitor(Opcodes.ASM7, cw) {

            //在解析到一个方法时候回调一次
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor,
                                             String signature, String[] exceptions) {
                if (name.equals("main")) {
                    //  执行插桩...
                    MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                    return new MyMethodVisitor(api, mv, access, name, descriptor);
                } else {
                    return super.visitMethod(access, name, descriptor, signature, exceptions);
                }
            }

            @Override
            public AnnotationVisitor visitAnnotation(String descriptor, boolean visible) {
                return super.visitAnnotation(descriptor, visible);
            }

            @Override
            public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
                return super.visitField(access, name, descriptor, signature, value);
            }
        }, ClassReader.EXPAND_FRAMES);

        /**
         * 3、输出结果
         */
        byte[] bytes = cw.toByteArray();
        FileOutputStream fos = new FileOutputStream(
                "F:\\vance\\APM\\ASM\\ASMInject\\app\\src\\test\\java2\\com\\vance\\asminject\\InjectTest.class");
        fos.write(bytes);
        fos.close();
        System.out.println("=======================");
    }

    class MyMethodVisitor extends AdviceAdapter {

        protected MyMethodVisitor(int api, MethodVisitor methodVisitor, int access, String name, String descriptor) {
            super(api, methodVisitor, access, name, descriptor);
        }

        // 进入方法 回调
        // AppMethodBeta.i
        @Override
        protected void onMethodEnter() {
            super.onMethodEnter();
            //AppMethodBeta.i();

            // 插入 invokeStatic指令：调用静态方法
            invokeStatic(Type.getType("Lcom/vance/asminject/AppMethodBeta;"),
                    new Method("i", "()V"));

            // System.out("111")
            getStatic(Type.getType("Ljava/lang/System;"),"out",Type.getType("Ljava/io/PrintStream;"));
//
            visitLdcInsn("Hello world！");
//
            invokeVirtual(Type.getType("Ljava/io/PrintStream;"),
                    new Method("println","(Ljava/lang/String;)V"));

//            mv.visitMethodInsn(INVOKESTATIC, "com/vance/asminject/AppMethodBeta",
//                    "i", "()V", false);

        }


        /**
         * 退出方法
         * AppMethodBeta.o
         * @param opcode
         */
        @Override
        protected void onMethodExit(int opcode) {
            super.onMethodExit(opcode);
//             AppMethodBeta.o();

            invokeStatic(Type.getType("Lcom/vance/asminject/AppMethodBeta;"),
                    new Method("o", "()V"));
//            mv.visitMethodInsn(INVOKESTATIC, "com/vance/asminject/AppMethodBeta",
//                    "o", "()V", false);
        }

    }

}
