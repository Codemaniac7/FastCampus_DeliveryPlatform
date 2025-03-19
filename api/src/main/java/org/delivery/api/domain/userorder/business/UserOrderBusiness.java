package org.delivery.api.domain.userorder.business;


import lombok.RequiredArgsConstructor;
import org.delivery.api.common.annotation.Business;
import org.delivery.api.domain.storemenu.service.StoreMenuService;
import org.delivery.api.domain.user.model.User;
import org.delivery.api.domain.userorder.controller.model.UserOrderRequest;
import org.delivery.api.domain.userorder.controller.model.UserOrderResponse;
import org.delivery.api.domain.userorder.converter.UserOrderConverter;
import org.delivery.api.domain.userorder.service.UserOrderService;
import org.delivery.api.domain.userordermenu.converter.UserOrderMenuConverter;
import org.delivery.api.domain.userordermenu.service.UserOrderMenuService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Business
public class UserOrderBusiness {

    private final UserOrderService userOrderService;
    private final UserOrderConverter userOrderConverter;

    private final StoreMenuService storeMenuService;
    private final UserOrderMenuConverter userOrderMenuConverter;
    private final UserOrderMenuService userOrderMenuService;
    // 1. 사용자, 메뉴 id
    // 2. userOrder 생성
    // 3. userOrderMenu 생성
    // 4. 응답 생성
    public UserOrderResponse userOrder(User user, UserOrderRequest body) {
        System.out.println("💡 user: " + user);
        System.out.println("💡 body: " + body);

        if (body == null) {
            throw new IllegalArgumentException("❌ UserOrderRequest body가 null입니다.");
        }

        if (body.getStoreMenuIdList() == null || body.getStoreMenuIdList().isEmpty()) {
            throw new IllegalArgumentException("❌ storeMenuIdList가 null이거나 비어 있습니다.");
        }
        var storeMenuEntityList = body.getStoreMenuIdList().stream()
                .map(it->storeMenuService.getStoreMenuWithThrow(it))
                .collect(Collectors.toList());

        var userOrderEntity = userOrderConverter.toEntity(user, storeMenuEntityList);

        // 주문
        var newUserOrderEntity = userOrderService.order(userOrderEntity);

        // 맵핑
        var userOrderMenuEntityList = storeMenuEntityList.stream()
                .map(it->{
                    // menu + user order
                    var userOrderMenuEntity =  userOrderMenuConverter.toEntity(newUserOrderEntity, it);

                    return userOrderMenuEntity;
                }).collect(Collectors.toList());

        // 주문내역 기록 남기기
        userOrderMenuEntityList.forEach(it -> {
            userOrderMenuService.order(it);
        });

        // response
        return userOrderConverter.toResponse(newUserOrderEntity);
    }
}
