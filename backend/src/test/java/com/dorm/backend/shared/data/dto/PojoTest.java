package com.dorm.backend.shared.data.dto;


import com.openpojo.random.RandomFactory;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.PojoMethod;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.affirm.Affirm;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.Tester;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

@RunWith(Parameterized.class)
public class PojoTest {

    @Parameterized.Parameters(name = "pojoClass={0}")
    public static Iterable<?> pojoClasses() {
        return Arrays.asList(
                PictureDTO.class,
                ProfilePreviewDTO.class,
                RoomPreviewDTO.class
        );
    }

    @Parameterized.Parameter
    public Class<?> pojoClass;

    @Test
    public void shouldPojoHaveAffirmedContract() {
        assertPojo(pojoClass);
    }

    private static void assertPojo(Class<?> pojoClass) {
        PojoClass pojo = PojoClassFactory.getPojoClass(pojoClass);
        Validator pojoValidator = createPojoValidator();
        pojoValidator.validate(pojo);
    }

    private static Validator createPojoValidator() {
        return ValidatorBuilder.create()
                .with(new SetterMustExistRule())
                .with(new GetterMustExistRule())
                .with(new NoPublicFieldsExceptStaticFinalRule())
                .with(new NoStaticExceptFinalRule())
                .with(new SerializableMustHaveSerialVersionUIDRule())
                .with(new NoNestedClassRule())
                .with(new ConstructorTester())
                .with(new SetterTester())
                .with(new GetterTester())
                .build();
    }

    private static class ConstructorTester implements Tester {
        @Override
        public void run(PojoClass pojoClass) {
            for (PojoMethod constructor : pojoClass.getPojoConstructors()) {
                Object[] params = constructor.getPojoParameters()
                        .stream()
                        .map(RandomFactory::getRandomValue)
                        .toArray();

                Object instance = constructor.invoke(null, params);

                Affirm.affirmNotNull("Constructor is not working", instance);
            }
        }
    }

    @Test
    public void shouldPojoHaveAffirmedEqualsAndHash() {
        assertPojoEqualsAndHash(pojoClass);
    }

    private static void assertPojoEqualsAndHash(Class<?> pojoClass) {
        EqualsVerifier.forClass(pojoClass)
                .suppress(Warning.NONFINAL_FIELDS, Warning.STRICT_INHERITANCE)
                .withRedefinedSuperclass()
                .verify();
    }

}