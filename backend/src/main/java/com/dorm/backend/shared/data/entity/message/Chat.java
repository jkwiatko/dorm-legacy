package com.dorm.backend.shared.data.entity.message;


import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@Entity
public class Chat extends BaseEntity {

    @ManyToOne
   private User owner;

    @ManyToOne
    private User mate;

    @OneToMany(mappedBy = "chat")
    private List<Message> messages;
}
