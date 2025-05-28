package org.emp.shelterhub.lib.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;
import org.emp.shelterhub.feature.settings.data.User;
import org.emp.shelterhub.lib.db.dao.UserDAO;
import org.emp.shelterhub.lib.db.entity.UserEntity;
import org.emp.shelterhub.lib.infrastructure.mapper.UserMapper;

public class UserRepository {

  private static UserRepository instance;
  private static final UserDAO userDAO = new UserDAO();

  private User loggedUser;

  private UserRepository() {}

  public static synchronized UserRepository getInstance() {
    if (instance == null) {
      instance = new UserRepository();
    }
    return instance;
  }

  public boolean createUser(User user) {
    UserEntity entity = UserMapper.toEntity(user);
    boolean success = userDAO.addUser(entity);

    if (success && entity.id != null) {
      user.setId(entity.id);
    }

    return success;
  }

  public List<User> getAllUsers() {
    List<UserEntity> entityList = userDAO.getAllUsers();
    List<User> userList = new ArrayList<>();

    for (UserEntity entity : entityList) {
      userList.add(UserMapper.toDomain(entity));
    }

    return userList;
  }

  public User getUserByUsername(String username) {
    UserEntity entity = userDAO.getUserByLogin(username);
    return (entity != null) ? UserMapper.toDomain(entity) : null;
  }

  public boolean deleteUser(int userId) {
    return userDAO.deleteUserById(userId);
  }

  public User authenticate(String username, String password) {
    UserEntity entity = userDAO.getUserByLogin(username);

    if (entity != null && entity.password.equals(password)) {
      loggedUser = UserMapper.toDomain(entity);
      return loggedUser;
    }

    return null;
  }

  public User getLoggedUser() {
    return loggedUser;
  }

  public void logout() {
    loggedUser = null;
  }
}
