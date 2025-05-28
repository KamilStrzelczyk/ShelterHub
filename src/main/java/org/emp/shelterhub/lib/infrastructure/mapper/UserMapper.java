package org.emp.shelterhub.lib.infrastructure.mapper;

import org.emp.shelterhub.feature.settings.data.User;
import org.emp.shelterhub.feature.settings.data.UserRole;
import org.emp.shelterhub.lib.db.entity.UserEntity;

public class UserMapper {

  public static UserEntity toEntity(User user) {
    if (user == null) return null;

    String roleString = null;
    if (user.getUserRole() != null) {
      roleString = mapUserRoleToString(user.getUserRole());
    }

    return new UserEntity(user.getId(), user.getUsername(), user.getPassword(), roleString, null);
  }

  public static User toDomain(UserEntity entity) {
    if (entity == null) return null;

    UserRole role = null;
    if (entity.role != null) {
      role = mapStringToUserRole(entity.role);
    }

    return new User(entity.id, entity.username, null, null, role, entity.password);
  }

  private static String mapUserRoleToString(UserRole role) {
    return switch (role) {
      case ADMINISTRATOR -> "admin";
      case STANDARD_USER -> "user";
      case GUEST -> "guest";
      default -> null;
    };
  }

  private static UserRole mapStringToUserRole(String role) {
    if (role == null) return null;

    return switch (role.toLowerCase()) {
      case "admin" -> UserRole.ADMINISTRATOR;
      case "user" -> UserRole.STANDARD_USER;
      case "guest" -> UserRole.GUEST;
      default -> null;
    };
  }
}
