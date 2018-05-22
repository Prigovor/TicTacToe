package com.prigovor.TicTacToe.service.dto;

import java.util.List;

public class ComplexGameDto extends GameDto {

    private List<MoveDto> moveDtoList;

    public List<MoveDto> getMoveDtoList() {
        return moveDtoList;
    }

    public void setMoveDtoList(List<MoveDto> moveDtoList) {
        this.moveDtoList = moveDtoList;
    }
}
