package soongsil.kidbean.server.auth.util;

import soongsil.kidbean.server.member.domain.Member;

public interface SocialLoginProvider {
    Member getUserData(String accessToken);
}
