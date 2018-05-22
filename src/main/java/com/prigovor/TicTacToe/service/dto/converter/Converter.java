package com.prigovor.TicTacToe.service.dto.converter;

import java.util.List;

public interface Converter<DOMAIN, DTO> {

    default DOMAIN toDomain(DTO dto) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    default DTO toDto(DOMAIN domain) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    default List<DOMAIN> toDomain(List<DTO> gameList) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    default List<DTO> toDto(List<DOMAIN> gameList) {
        throw new UnsupportedOperationException("Not implemented.");
    }

}