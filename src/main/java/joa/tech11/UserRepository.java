package joa.tech11;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.NotFoundException;
import joa.tech11.event.UserEvent;
import joa.tech11.qualifier.AddEvent;
import joa.tech11.qualifier.DeleteEvent;

import java.util.List;

import static com.arjuna.ats.jdbc.TransactionalDriver.password;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    public UserEntity findById(Long id) {
        return find("id", id).firstResult();
    }

    public List<UserEntity> findUsers() {
        return listAll();
    }

    public void create(@Observes @AddEvent UserEvent userEvent) {
        UserEntity user = userEvent.getUser();
        user.setPassword(BcryptUtil.bcryptHash(password));
        persist(user);
    }

    public UserEntity update(Long id, UserEntity user) {
        return findByIdOptional(id)
                .map(u -> {
                    u.setPassword(user.getPassword());
                    u.setEmail(user.getEmail());
                    u.setBirthday(user.getBirthday());
                    u.setFirstName(user.getFirstName());
                    u.setLastName(user.getLastName());
                    return u;
                }).orElseThrow(() -> new NotFoundException("User not found"));
    }

    public void delete(@Observes @DeleteEvent UserEvent userEvent) {
        delete(userEvent.getUser());
    }
}
