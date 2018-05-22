$(function () {
    /*Move*/
    var moveListItem = $("#moveList").val();
    if (moveListItem !== undefined) {
        var moveListJson = $.parseJSON(moveListItem);
        $.each(moveListJson, function (moveId, moveData) {
            var buttonId = moveData.x + "-" + moveData.y;
            console.log(buttonId);
            $("#" + buttonId).text(moveData.sign)
        });
    }
    /*Game*/
    var gameDtoId;
    var gameDtoTitle;
    var gameDtoStatus;
    var gameDtoItem = $("#gameDto").val();
    if (gameDtoItem !== undefined) {
        var gameDtoJson = $.parseJSON(gameDtoItem);
        console.log(gameDtoJson["gameId"]);
        gameDtoId = gameDtoJson["gameId"];
        gameDtoTitle = gameDtoJson["gameTitle"];
        gameDtoStatus = gameDtoJson["gameStatus"];
        console.log("Game Status: " + gameDtoStatus);
    }
    if (gameDtoStatus === 'X_WON') {
        $("#gameMessage").text("'X' WON!");
    } else if (gameDtoStatus === 'O_WON') {
        $("#gameMessage").text("'0' WON!");
    } else if (gameDtoStatus === 'DRAW') {
        $("#gameMessage").text("DRAW!");
    } else {
        if (gameDtoStatus === 'NEXT_X') {
            $("#gameMessage").text("'X' moves next!");
        } else if (gameDtoStatus === 'NEXT_O') {
            $("#gameMessage").text("'0' moves next!");
        }
    }
    /*Ajax status checking*/
    $(".btn.btn-light").on('click', function () {
        var moveX = $(this).attr("name");
        var moveY = $(this).attr("value");
        var currentButton = $(this);
        var move = {
            gameId: gameDtoId,
            x: moveX,
            y: moveY
        };
        var game = {
            gameId: gameDtoId,
            gameTitle: gameDtoTitle,
            gameStatus: gameDtoStatus,
            currentMoveDto: move
        };
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/restGame/makeMove",
            data: JSON.stringify(game),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                console.log(data);
                if (data === 'NEXT_X') {
                    $("#gameMessage").text("'X' moves next!");
                    $(currentButton).text("0");
                } else if (data === 'NEXT_O') {
                    $("#gameMessage").text("'0' moves next!");
                    $(currentButton).text("X");
                } else if (data === 'FILLED') {
                    $("#gameMessage").text("The place already taken!");
                } else if (data === 'X_WON'
                    && gameDtoStatus !== 'X_WON') {
                    $("#gameMessage").text("'X' WON!");
                    $(currentButton).text("X");
                    location.reload();
                } else if (data === 'O_WON'
                    && gameDtoStatus !== 'O_WON') {
                    $("#gameMessage").text("'0' WON!");
                    $(currentButton).text("0");
                    location.reload();
                } else if (data === 'DRAW'
                    && gameDtoStatus !== 'DRAW') {
                    $("#gameMessage").text("DRAW!");
                    $(currentButton).text("X");
                    location.reload();
                }
            }
        });
    });
});