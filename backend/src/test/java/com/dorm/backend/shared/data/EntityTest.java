package com.dorm.backend.shared.data;

import com.dorm.backend.shared.data.entity.Room;
import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.entity.address.Address;
import com.dorm.backend.shared.data.entity.address.City;
import com.dorm.backend.shared.data.entity.message.Chat;
import com.dorm.backend.shared.data.entity.message.Message;
import com.dorm.backend.shared.data.entity.picture.LocalPicture;
import com.dorm.backend.shared.data.entity.picture.LocalPictureEntity;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static com.dorm.backend.shared.data.PojoTest.createPojoValidator;

@RunWith(Parameterized.class)
public class EntityTest {


    @Parameterized.Parameters(name = "pojoClass={0}")
    public static Iterable<?> pojoClasses() {
        return Arrays.asList(
                Address.class,
                City.class,
                Chat.class,
                Message.class,
                LocalPicture.class,
                LocalPictureEntity.class,
                Room.class,
                User.class
        );
    }

    @Parameterized.Parameter
    public Class<?> entityClass;

    @Test
    public void shouldEntityHaveAffirmedContract() {
        assertEntity(entityClass);
    }

    private static void assertEntity(Class<?> entityClass) {
        PojoClass pojo = PojoClassFactory.getPojoClass(entityClass);
        Validator pojoValidator = createPojoValidator();
        pojoValidator.validate(pojo);
    }
}
