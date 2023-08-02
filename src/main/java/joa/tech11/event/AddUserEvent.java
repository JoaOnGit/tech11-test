package joa.tech11.event;

import joa.tech11.UserEntity;
import joa.tech11.qualifier.AddEvent;

@AddEvent
public class AddUserEvent extends UserEvent {

    public  AddUserEvent(UserEntity user){
        super(user);
    }
}
