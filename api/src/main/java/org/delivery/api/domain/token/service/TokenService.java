package org.delivery.api.domain.token.service;


import lombok.RequiredArgsConstructor;
import org.delivery.api.common.error.ErrorCode;
import org.delivery.api.common.exception.ApiException;
import org.delivery.api.domain.token.ifs.TokenHelperIfs;
import org.delivery.api.domain.token.model.TokenDto;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;

/**
 * token에 대한 메인 로직
 */
@RequiredArgsConstructor
@Service
public class TokenService {

    private final TokenHelperIfs tokenHelperIfs;

    public TokenDto issueAccessToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);

        System.out.println("Issuing Token with Data: " + data); // ✅ 디버깅 로그 추가
        return tokenHelperIfs.issueAccessToken(data);
    }

    public TokenDto issueRefreshToken(Long userId) {
        var data = new HashMap<String, Object>();
        data.put("userId", userId);
        return tokenHelperIfs.issueRefreshToken(data);
    }

    public Long validationToken(String token) {
        var map = tokenHelperIfs.validationTokenWithThrow(token);
        var userId = map.get("userId");

        System.out.println("Decoded userId from token: " + userId); // ✅ 디버깅용 출력

        Objects.requireNonNull(userId, ()->{throw new ApiException(ErrorCode.NULL_POINT);});

        return Long.parseLong(userId.toString());
    }
}
