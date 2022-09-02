package parser;

//import org.objectweb.asm.MethodAdapter;
import objects.Class;
import objects.Fields;
import objects.Methods;
import org.objectweb.asm.*;

import static org.objectweb.asm.Opcodes.ASM4;

/**
 * @author samin on 10/28/21
 * @project jparser
 */
public class ClassIterator extends ClassVisitor {

    public String name;
    private Class c;

    public ClassIterator() {

        super(ASM4);
        c = new Class();
    }

    Class getParsedClass(){
        return this.c;
    }

    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
        super.visit(version, access, name, signature, superName, interfaces);
        this.name = name;
        c.className = name;
        c.classAccessFlag = access;
        c.signature = signature;
        c.superClassName = superName;
        c.interfaces = interfaces;
    }

    @Override
    public void visitSource(String source, String debug) {
        super.visitSource(source, debug);
        c.sourceFile = source;
    }

    @Override
    public ModuleVisitor visitModule(String name, int access, String version) {
        return super.visitModule(name, access, version);
    }

    @Override
    public void visitOuterClass(String owner, String name, String desc) {
        super.visitOuterClass(owner, name, desc);
        c.outterClassName = name;
        c.outterClassOwner = owner;
        c.outterClassDesc = desc;
    }

    @Override
    public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
        return super.visitAnnotation(desc, visible);
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
        return super.visitTypeAnnotation(typeRef, typePath, desc, visible);
    }

    @Override
    public void visitAttribute(Attribute attr) {
        super.visitAttribute(attr);
        c.attributes.add(attr);
    }

    @Override
    public void visitInnerClass(String name, String outerName, String innerName, int access) {
        super.visitInnerClass(name, outerName, innerName, access);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
        Fields f = new Fields(access,name,desc,signature,value);
        c.fields.add(f);
        return super.visitField(access, name, desc, signature, value);

    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {

        MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions);

        //MethodAdapter ma  = new MethodAdapter(mv);

        Methods m = new Methods(access,this.c,name,desc);
        c.methods.add(m);



        //test code

        /*
        final Type[] argTypes = Type.getArgumentTypes(desc);
        final int methodParameterCount  = m.getParameterCount();
        final String[] methodParametersNames = new String[methodParameterCount];

        return new MethodAdapter(mv) {
            public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                //If it is a static method, the first parameter is the method parameter, the non-static method, then the first parameter is this, and then the parameter of the method.
                int methodParameterIndex = 0;
                if (0 <= methodParameterIndex && methodParameterIndex < methodParameterCount) {
                    methodParametersNames[methodParameterIndex] = name;
                    System.out.println(name);
                }
                super.visitLocalVariable(name,desc,signature,start,end,index);
                //super.visitLocalVariable(name, desc, signature, start, end, index);
            }
        };
        */
        return mv;


    }


    @Override
    public void visitEnd() {
        super.visitEnd();
    }
}