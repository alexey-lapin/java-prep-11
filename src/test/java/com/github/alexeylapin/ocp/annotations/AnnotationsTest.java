package com.github.alexeylapin.ocp.annotations;

import java.io.Serializable;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

@AnnotationsTest.Ann
public class AnnotationsTest {

    @interface Ann {
    }

    void id() {
        class Ann2 {
            Object obj() {
                Object obj = new Object() {
                    int i = 1;

                    {
                        var o = 0;
                    }
                };
//                obj.i
                return obj;
            }
        }

//        new Ann2().obj().o;
    }

    void id2() {
//        new Ann2();
    }

    @interface Ann2 {
    }

    // @formatter:off

    @Target(ElementType.FIELD)
    @interface FieldTarget {}

    @Target(ElementType.METHOD)
    @interface MethodTarget {}

    @Target(ElementType.CONSTRUCTOR)
    @interface ConstructorTarget {}

    @Target(ElementType.PARAMETER)
    @interface ParameterTarget {}

    @Target(ElementType.LOCAL_VARIABLE)
    @interface LocalVariableTarget {}

    @Target(ElementType.TYPE_PARAMETER)
    @interface TypeParameterTarget {}

    @Target(ElementType.TYPE)
    @interface TypeTarget {}

    @Target(ElementType.ANNOTATION_TYPE)
    @interface AnnotationTypeTarget {}

    @Target(ElementType.TYPE_USE)
    @interface TypeUseTarget {}

    @Target(ElementType.PACKAGE)
    @interface PackageTarget {}

    @Target(ElementType.MODULE)
    @interface ModuleTarget {}

    @TypeTarget @TypeUseTarget class Some<@TypeParameterTarget @TypeUseTarget T extends @TypeUseTarget Number>
            extends @TypeUseTarget Object
            implements @TypeUseTarget Serializable {

        private @FieldTarget @TypeUseTarget int a;

        private @FieldTarget @TypeUseTarget String b;

        @ConstructorTarget public @TypeUseTarget Some(@TypeUseTarget @ParameterTarget int a) {
            this.a  = a;
        }

        @MethodTarget void m(@ParameterTarget @TypeUseTarget String param) {
            @TypeUseTarget List<@TypeUseTarget String> list = new @TypeUseTarget ArrayList<>(10);
            @LocalVariableTarget ArrayList<String> arrayList = (@TypeUseTarget ArrayList<@TypeUseTarget String>) list;
        }

        @TypeUseTarget String m2() {
            return "text";
        }

        protected @TypeUseTarget Some<@TypeUseTarget ?> m3() {
            Some.class.getAnnotationsByType(TypeUseTarget.class);
            return new @TypeUseTarget AnnotationsTest().new @TypeUseTarget Some<@TypeUseTarget Long>(1);
        }

    }
    // @formatter:on

}
