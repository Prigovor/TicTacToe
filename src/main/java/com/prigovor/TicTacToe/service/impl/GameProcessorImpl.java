package com.prigovor.TicTacToe.service.impl;

import com.prigovor.TicTacToe.persistence.enums.GameStatus;
import com.prigovor.TicTacToe.service.api.Constants;
import com.prigovor.TicTacToe.service.api.GameProcessor;
import com.prigovor.TicTacToe.service.api.MoveService;
import com.prigovor.TicTacToe.service.dto.GameDto;
import com.prigovor.TicTacToe.service.dto.MoveDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameProcessorImpl implements GameProcessor {

    private MoveService moveService;
    private final Long boardWinLength = Constants.BOARD_WIN_LINE_LENGTH.longValue();

    @Override
    public GameStatus makeMove(GameDto gameDto) {
        GameStatus result;
        switch (gameDto.getGameStatus()) {
            case "X_WON":
                result = GameStatus.X_WON;
                break;
            case "O_WON":
                result = GameStatus.O_WON;
                break;
            case "DRAW":
                result = GameStatus.DRAW;
                break;
            default:
                List<MoveDto> previousMoves = moveService.findByGameId(gameDto.getGameId());
                result = calculateStatus(gameDto.getCurrentMoveDto(), previousMoves);
                break;
        }
        return result;
    }

    private GameStatus calculateStatus(MoveDto currentMove, List<MoveDto> previousMoves) {
        GameStatus result;
        final Integer redundantCheck = 3;
        final Integer numberInc = 1;
        MoveDto lastMove = previousMoves.stream()
                .filter(Objects::nonNull)
                .filter(moveDto -> moveDto.getNumber() != null)
                .max(Comparator.comparing(MoveDto::getNumber))
                .orElse(null);
        if (lastMove == null) {
            /*Move number incrementation.*/
            currentMove.setNumber(numberInc);
            moveService.saveOrUpdate(currentMove);
            result = GameStatus.NEXT_O;
        } else {
            if (currentMove.getX().equals(lastMove.getX())
                    && currentMove.getY().equals(lastMove.getY())) {
                result = GameStatus.FILLED;
            } else if (previousMoves.size() > redundantCheck) {
                /*Incrementation move number*/
                currentMove.setNumber(lastMove.getNumber() + numberInc);
                result = calculateWinner(currentMove, previousMoves);
                if (result.isInProgress()) {
                    if (currentMove.getNumber() % 2 == 0) {
                        result = GameStatus.NEXT_X;
                    } else {
                        result = GameStatus.NEXT_O;
                    }
                }
                moveService.saveOrUpdate(currentMove);
            } else {
                /*Incrementation move number*/
                currentMove.setNumber(lastMove.getNumber() + numberInc);
                if (currentMove.getNumber() % 2 == 0) {
                    result = GameStatus.NEXT_X;
                } else {
                    result = GameStatus.NEXT_O;
                }
                moveService.saveOrUpdate(currentMove);
            }
        }
        return result;
    }

    private GameStatus calculateWinner(MoveDto currentLocation, List<MoveDto> previousMoves) {
        GameStatus result;
        final Long boardSize = 9L;
        Set<Location> locations = previousMoves.stream()
                .map(Location::convert)
                .collect(Collectors.toSet());
        locations.add(Location.convert(currentLocation));
        if (countMatches(locations, Constants.X_SIGN).equals(boardWinLength)) {
            result = GameStatus.X_WON;
        } else if (countMatches(locations, Constants.O_SIGN).equals(boardWinLength)) {
            result = GameStatus.O_WON;
        } else if (locations.size() == boardSize) {
            result = GameStatus.DRAW;
        } else {
            result = GameStatus.IN_PROGRESS;
        }
        return result;
    }

    private Long countMatches(Set<Location> locations, Character sign) {
        Long matches = 0L;
        if (matches < boardWinLength) {
            for (int i = 0; i < boardWinLength; i++) {
                if (matches >= boardWinLength) return matches;
                matches = rowsAndColumnsCheck(locations, i, sign);
            }
        }
        if (matches < boardWinLength) {
            matches = diagonalsCheck(locations, sign);
        }
        if (matches < boardWinLength) {
            matches = diagonalsCheck(locations, sign);
        }
        return matches;
    }

    private Long rowsAndColumnsCheck(Set<Location> locations, Integer count, Character sign) {
        Long result;
        result = locations.stream()
                .filter(location -> location.getX().equals(count))
                .filter(location -> location.getSign().equals(sign))
                .count();
        if (result < boardWinLength) {
            result = locations.stream()
                    .filter(location -> location.getY().equals(count))
                    .filter(location -> location.getSign().equals(sign))
                    .count();
        }
        return result;
    }

    private Long diagonalsCheck(Set<Location> locations, Character sign) {
        Long result = 0L;
        for (int xy = 0; xy < boardWinLength; xy++) {
            final Location location = new Location(xy, xy, sign);
            if (locations.contains(location)) {
                result++;
            }
        }
        if (result < boardWinLength) {
            result = 0L;
            for (int x = 0, y = boardWinLength.intValue() - 1; x < boardWinLength; x++, y--) {
                final Location location = new Location(x, y, sign);
                if (locations.contains(location)) {
                    result++;
                }
            }
        }
        return result;
    }

    private static class Location {

        private Integer x;
        private Integer y;
        private Character sign;

        private Location() {
        }

        private Location(Integer x, Integer y, Character sign) {
            this.x = x;
            this.y = y;
            this.sign = sign;
        }

        private static Location convert(MoveDto dto) {
            Location location = new Location();
            location.setX(dto.getX());
            location.setY(dto.getY());
            if (dto.getNumber() != null) {
                if (dto.getNumber() % 2 == 0) {
                    location.setSign(Constants.O_SIGN);
                } else {
                    location.setSign(Constants.X_SIGN);
                }
            }
            return location;
        }

        private Integer getX() {
            return x;
        }

        private void setX(Integer x) {
            this.x = x;
        }

        private Integer getY() {
            return y;
        }

        private void setY(Integer y) {
            this.y = y;
        }

        private Character getSign() {
            return sign;
        }

        private void setSign(Character sign) {
            this.sign = sign;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Location location = (Location) o;
            return Objects.equals(x, location.x) &&
                    Objects.equals(y, location.y) &&
                    Objects.equals(sign, location.sign);
        }

        @Override
        public int hashCode() {

            return Objects.hash(x, y, sign);
        }
    }

    /*Dependency injections*/
    @Autowired
    public void setMoveService(MoveService moveService) {
        this.moveService = moveService;
    }
}