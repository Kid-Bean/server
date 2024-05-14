package soongsil.kidbean.server.program.dto.response;

public record StarResponse(
        String starStatus
) {
    public static StarResponse from(String starStatus) {
        return new StarResponse(starStatus);
    }
}
