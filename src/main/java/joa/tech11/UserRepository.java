package joa.tech11;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import joa.tech11.event.AddUserEvent;
import joa.tech11.event.DeleteUserEvent;

import java.util.List;

import static com.arjuna.ats.jdbc.TransactionalDriver.password;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    public UserEntity findById(Long id){
        return find("id", id).firstResult();
    }

    public  List<UserEntity> findUsers(){
        return listAll();
    }

    public void create(@Observes AddUserEvent userEvent){
        UserEntity user = userEvent.getUser();
        user.setPassword(BcryptUtil.bcryptHash(password));
        persist(user);
    }

    public void update(Long id, UserEntity user){
        update(id, user);
    }

    public void delete(@Observes DeleteUserEvent userEvent){
        delete(userEvent.getUser());
    }
}
