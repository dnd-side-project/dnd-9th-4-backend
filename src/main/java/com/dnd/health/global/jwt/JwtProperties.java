package com.dnd.health.global.jwt;

public interface JwtProperties {
    String SECRET = "MFswDQYJKoZIhvcNAQEBBQADSgAwRwJAaJd167MUifo3ZSytpDPd9z7oSS+1KXjf\n"
            + "HJ3oMvI61Id26fdQHgWE1QMLcrhOmRzTxkCU+gesx5ANkSSIrPHNswIDAQAB";
    long ACCESS_TOKEN_EXPIRATION = 1000L * 60 * 60 * 24; // 1일
    long REFRESH_TOKEN_EXPIRATION = 1000 * 60 * 60 * 24 * 7; //일주일
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_STRING = "Authorization";
}
