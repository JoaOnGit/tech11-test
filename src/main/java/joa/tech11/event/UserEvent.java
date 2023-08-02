package joa.tech11.event;

import joa.tech11.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class UserEvent {
    protected UserEntity user;
}
