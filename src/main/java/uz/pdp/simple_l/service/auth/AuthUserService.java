package uz.pdp.simple_l.service.auth;

import uz.pdp.simple_l.dto.auth.AuthUserCreateDto;

public interface AuthUserService {
    Long create(AuthUserCreateDto dto);
}
