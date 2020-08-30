package com.dorm.backend.shared.data.entity.message;

import com.dorm.backend.shared.data.entity.User;
import com.dorm.backend.shared.data.entity.base.BaseEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class Message extends BaseEntity {

    @OneToOne
    private User from;

    @ManyToOne
    private Chat chat;

    private String text;
}
