package soongsil.kidbean.server.program.dto.response;

import lombok.Builder;
import soongsil.kidbean.server.member.domain.Member;
import soongsil.kidbean.server.program.domain.Program;
import soongsil.kidbean.server.program.domain.Star;

import java.util.List;

import static org.bouncycastle.asn1.x500.style.RFC4519Style.member;

@Builder
public record StarResponse (
        Long starId
){
}
