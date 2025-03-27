package org.delivery.storeadmin.domain.user.controller.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.delivery.db.storeuser.enums.StoreUserRole;
import org.delivery.db.storeuser.enums.StoreUserStatus;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class StoreUserResponse {

    private  UserResponse user;

    private StoreResponse store;


    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class UserResponse {

        private Long id;

        private String email;

        private StoreUserStatus status;

        private StoreUserRole role;

        private LocalDateTime registeredAt;

        private LocalDateTime unregisteredAt;

        private LocalDateTime lastLoginAt;

        private UserResponse user;

        private StoreResponse store;
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    public static class StoreResponse{
        private  Long id;

        private String name;

    }
}
