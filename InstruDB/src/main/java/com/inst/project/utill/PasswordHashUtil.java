package com.inst.project.utill;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.inst.project.common.GlobalConfig;

public final class PasswordHashUtil {

    // 비용(cost) 기본은 10. 서버 성능 여유 있으면 12~14도 고려.
    private static final PasswordEncoder BCRYPT = new BCryptPasswordEncoder(12);

    private PasswordHashUtil() {}

    /**
     * 비밀번호를 BCrypt로 해싱하여 저장용 문자열을 만든다.
     * - 반환값은 salt와 cost가 포함된 포맷 문자열이므로 DB에 그대로 저장하면 된다.
     */
    public static String hashWithBcrypt(String rawPassword) {
        if (rawPassword == null || StringUtils.isBlank(rawPassword)) {
            throw new IllegalArgumentException(GlobalConfig.RESULT_NULL_DATA_MSG);
        }
        return BCRYPT.encode(rawPassword);
    }

    /**
     * 사용자가 입력한 비밀번호(raw)와 DB에 저장된 해시(hashed)가 일치하는지 검증한다.
     */
    public static boolean matchesBcrypt(String rawPassword, String storedHash) {
        if (rawPassword == null || storedHash == null || StringUtils.isBlank(storedHash)) {
            return false;
        }
        return BCRYPT.matches(rawPassword, storedHash);
    }
}