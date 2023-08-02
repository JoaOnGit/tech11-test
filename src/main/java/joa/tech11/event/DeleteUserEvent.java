package joa.tech11.event;


import joa.tech11.UserEntity;
import joa.tech11.qualifier.DeleteEvent;

@DeleteEvent
public class DeleteUserEvent extends UserEvent {

    public DeleteUserEvent(UserEntity user){
        super(user);
    }
}
