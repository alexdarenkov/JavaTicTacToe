const gameBoard = document.getElementById('game-board');
const messageDiv = document.getElementById('message');
let webGame;

function createCell(row, col) {
    const cell = document.createElement('div');
    cell.className = 'cell';
    cell.dataset.row = row;
    cell.dataset.col = col;
    cell.addEventListener('click', () => handleCellClick(row, col));
    return cell;
}

async function createNewGame() {
    try {
        const response = await fetch('http://localhost:8080/game/new', {
            method: 'GET',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (response.ok) {
            webGame = await response.json(); // Преобразуем ответ в JSON
            console.log('Новая игра получена:', newGame);
            // Здесь вы можете обработать новую игру, например, отобразить её на экране
        } else {
            console.error('Ошибка при получении новой игры:', response.status, response.statusText);
        }
    } catch (error) {
        console.error('Ошибка сети:', error);
    }
}


function initializeBoard() {
    for (let i = 0; i < 3; i++) {
        for (let j = 0; j < 3; j++) {
            gameBoard.appendChild(createCell(i, j));
        }
    }
}

function handleCellClick(row, col) {
    if (webGame.webGameField.field[row][col] !== 0) {
        return; // Ячейка уже занята
    }

    webGame.webGameField.field[row][col] = 1; // Ход игрока
    updateBoard();
    sendMoveToServer(row, col);
}

function updateBoard() {
    const cells = document.querySelectorAll('.cell');
    cells.forEach(cell => {
        const row = cell.dataset.row;
        const col = cell.dataset.col;

        // Преобразуем row и col в числа, если они строковые
        const cellValue = webGame.webGameField.field[row][col];

        if (cellValue === 1) {
            cell.textContent = 'X';
            cell.classList.add('x'); // Добавляем класс для стилизации
        } else if (cellValue === 2) {
            cell.textContent = 'O';
            cell.classList.add('o'); // Добавляем класс для стилизации
        } else {
            cell.textContent = ''; // Очищаем ячейку, если она пустая
            cell.classList.remove('x', 'o'); // Убираем классы для пустой ячейки
        }
    });
}

async function sendMoveToServer(row, col) {
    const response = await fetch(`http://localhost:8080/game/${webGame.id}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(webGame)
    });

    if (response.ok) {
        const data = await response.json();
        webGame.webGameField = data.webGameField; // Обновляем состояние игры
        updateBoard();
        checkGameStatus(data.gameStatus)
    } else {
        messageDiv.textContent = 'Ошибка при отправке хода';
    }
}

function checkGameStatus(gameStatus) {
    if (gameStatus) {
        // Проверяем, является ли сообщение победой или поражением
        if (gameStatus === "WIN") {
            messageDiv.textContent = "You win!";
        } else if (gameStatus === "LOSE") {
            messageDiv.textContent = "You lose!";
        } else if (gameStatus === "DRAW") {
            messageDiv.textContent = "Draw!";
        }
        messageDiv.style.display = 'flex';
    }
}

createNewGame();
initializeBoard();