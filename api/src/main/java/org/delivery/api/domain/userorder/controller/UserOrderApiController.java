package org.delivery.api.domain.userorder.controller;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.UserSession;
import org.delivery.api.common.api.Api;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.business.UserOrderBusiness;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-order")
public class UserOrderApiController {

    private final UserOrderBusiness userOrderBusiness;

    // 사용자 주문
    @PostMapping("")
    public Api<UserOrderResponse> userOrder(
            @Valid
            @RequestBody Api<UserOrderRequest> userOrderRequest,

            @Parameter(hidden = true)
            @UserSession
            User user
    ) {
        if (user == null) {
            throw new IllegalArgumentException("UserSession에서 user가 null입니다.");
        }

        if (userOrderRequest == null) {
            throw new IllegalArgumentException("userOrderRequest가 null입니다.");
        }

        if (userOrderRequest.getBody() == null) {
            throw new IllegalArgumentException("userOrderRequest의 body가 null입니다.");
        }
        var response = userOrderBusiness.userOrder(
                user,
                userOrderRequest.getBody());
        return Api.OK(response);
    }
}
