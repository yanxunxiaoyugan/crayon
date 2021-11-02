package com.liu.asm;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GenerateClassTest {
    public static void main(String[] args) throws IOException {
        ClassWriter classWriter = new ClassWriter(0);
        String className = "com/liu/asm/GeneratedClazz";
        String signature = "L"+className.replace(".","/")+";";
        System.out.println(Object.class.getName());
        classWriter.visit(Opcodes.V1_8,Opcodes.ACC_PUBLIC,className,signature,Object.class.getName().replace(".","/"),null);
        classWriter.visitAnnotation("Llombok/Getter;",true);


        FieldVisitor fieldVisitor = classWriter.visitField(Opcodes.ACC_PUBLIC, "age", "I", null, 1);
        fieldVisitor.visitAnnotation("Llombok/Getter;",true);
        classWriter.visitEnd();
        byte[] bytes = classWriter.toByteArray();
        File file = new File("d:/tmp.com.liu.asm.GeneratedClazz.class");
        file.createNewFile();


            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(bytes);
            }

    }
}
