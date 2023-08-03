package joa.tech11;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import joa.tech11.event.UserEvent;
import joa.tech11.qualifier.AddEvent;
import joa.tech11.qualifier.DeleteEvent;
import org.jboss.logging.Logger;

import java.util.List;

@ApplicationScoped
public class UserRepository implements PanacheRepository<UserEntity> {

    @Inject
    Logger log;

    @Inject
    public void init(){
        System.out.println("name of logger: " + log.getName());
    }

    public UserEntity findById(Long id) {
        return find("id", id).firstResult();
    }

    public List<UserEntity> findUsers() {
        return listAll();
    }

    public void create(@Observes @AddEvent UserEvent userEvent) {
        log.info("creating user");
        UserEntity user = userEvent.getUser();
        user.setPassword(BcryptUtil.bcryptHash(user.getPassword()));
        persist(user);
        log.info("created user");
    }

    public UserEntity update(Long id, UserEntity user) {
        log.info("updating user");
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
